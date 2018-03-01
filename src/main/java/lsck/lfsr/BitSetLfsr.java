package lsck.lfsr;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import lsck.bitwise.BitVector;

/** An {@code Lfsr} implementation backed by {@code BitSet}s.
 * 
 * Supports registers of arbitrary length.
 */
public class BitSetLfsr extends Lfsr {
	
	private final int length;
	private BitSet fill;
	private BitSet taps;
	
	public BitSetLfsr(int length) {
		if (length <= 0) {
			throw new LfsrInvalidLengthException(length);
		}
		
		this.length = length;
		this.fill = new BitSet(length);
		this.taps = new BitSet(length);
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public BitVector getFill() {
		return BitVector.fromBitSet(length, fill);
	}

	@Override
	public byte getFillAt(int index) {
		if (index < 0 || index >= length) {
			throw new LfsrIndexOutOfBoundsException(index, length);
		}
		
		return (byte) (fill.get(index) ? 1 : 0);
	}

	@Override
	public BitVector getTaps() {
		return BitVector.fromBitSet(length, taps);
	}

	@Override
	public byte getTapAt(int index) {
		if (index < 0 || index >= length) {
			throw new LfsrIndexOutOfBoundsException(index, length);
		}

		return (byte) (taps.get(index) ? 1 : 0);
	}

	@Override
	public void setFill(BitVector fill) {
		if (fill.getLength() != length) {
			throw new LfsrInvalidLengthException(length, fill.getLength());
		}
		
		this.fill = fill.toBitSet();
	}

	@Override
	public void setFillAt(int index, int value) {
		if (index < 0 || index >= length) {
			throw new LfsrIndexOutOfBoundsException(index, length);
		}
		
		fill.set(index, value != 0);
	}

	@Override
	public void setTaps(BitVector taps) {
		if (taps.getLength() != length) {
			throw new LfsrInvalidLengthException(length, taps.getLength());
		}
		
		this.taps = taps.toBitSet();
	}

	@Override
	public void setTapsAt(int index, int value) {
		if (index < 0 || index >= length) {
			throw new LfsrIndexOutOfBoundsException(index, length);
		}
		
		taps.set(index, value != 0);
	}
	
	@Override
	public byte peek() {
		return getFillAt(0);
	}

	@Override
	public List<Byte> peek(int terms) {
		// Perform a regular shift and then reset the fill
		BitVector oldFill = getFill();
		List<Byte> output = shift(terms);
		setFill(oldFill);
		
		return output;
	}

	@Override
	public byte peekAt(int term) {
		// Perform a regular shiftAt and then reset the fill
		BitVector oldFill = getFill();
		byte result = shiftTo(term);
		setFill(oldFill);
		
		return result;
	}

	@Override
	public byte shift() {
		byte output = getFillAt(0);
		
		boolean inputBit = false;
		for (int i = 0; i < length; i++) {
			// Every time a tapped bit is 1, we flip our input bit
			if (fill.get(i) && taps.get(i)) {
				inputBit = !inputBit;
			}
		}
		
		// Right shift by one and append the new bit
		fill = fill.get(1, length);
		fill.set(length - 1, inputBit);
		
		return output;
	}

	@Override
	public List<Byte> shift(int terms) {
		List<Byte> output = new ArrayList<>(terms);
		
		long[] fillVectors = fill.toLongArray();
		long[] tapVectors = taps.toLongArray();
		
		for (int i = 0; i < terms; i++) {
			output.add(shiftVectors(length, fillVectors, tapVectors));
		}
		
		// Retain the final fill state
		fill = BitSet.valueOf(fillVectors);
		
		return output;
	}

	@Override
	public byte shiftTo(int term) {
		long[] fillVectors = fill.toLongArray();
		long[] tapVectors = taps.toLongArray();
		
		for (int i = 0; i < term; i++) {
			shiftVectors(length, fillVectors, tapVectors);
		}
		
		// Retain the final fill state
		fill = BitSet.valueOf(fillVectors);
		
		return shiftVectors(length, fillVectors, tapVectors);
	}
	
	/** An LFSR shift operation on the underlying long[] representations */
	private static byte shiftVectors(int length, long[] fillVectors, long[] tapVectors) {
		byte outputBit = getBit(fillVectors, 0);
		
		// Perform a single shift
		byte inputBit = nextBit(fillVectors, tapVectors);
		rightShift(fillVectors);
		setBit(fillVectors, length - 1, inputBit);
		
		return outputBit;
	}
	
	private static void rightShift(long[] fillVectors) {
		for (int i = 0; i < fillVectors.length; i++) {
			// Set the highest bit of the previous vector equal to the lowest
			// bit of this vector.
			if (i != 0) {
				fillVectors[i - 1] |= (fillVectors[i] & 1L) << (Long.SIZE - 1);
			}
			
			fillVectors[i] >>= 1;
		}
	}
	
	private static byte nextBit(long[] fillVectors, long[] tapVectors) {
		// For each long, count the number of tap positions where the fill is 1
		int overlaps = 0;
		for (int i = 0; i < fillVectors.length; i++) {
			overlaps += Long.bitCount(fillVectors[i] ^ tapVectors[i]);
		}
		
		return (byte) (overlaps % 2);
	}
	
	private static byte getBit(long[] fillVectors, int position) {
		int i = position / Long.SIZE;
		return (byte) ((fillVectors[i] >>> (position % Long.SIZE)) & 1L);
	}
	
	private static void setBit(long[] fillVectors, int position, byte bit) {
		if (bit != 0) {
			int i = position / Long.SIZE;
			fillVectors[i] |= 1L << (position % Long.SIZE);
		}
	}
}
