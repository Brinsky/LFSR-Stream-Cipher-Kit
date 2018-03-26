package lsck.lfsr;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import lsck.bitwise.BitVector;
import lsck.bitwise.BitUtility;
import lsck.bitwise.BitVector;

/** An {@link Lfsr} implementation backed by {@link BitSet}.
 * 
 * Supports registers of arbitrary length.
 */
public class BitSetLfsr extends AbstractLfsr {
	
	private final int length;
	private BitSet fill;
	private BitSet taps;
	
	/** Creates a {@code BitSetLfsr} with the specified taps and fill.
	 * 
	 * @param length The length of the register to be created.
	 * @param taps A {@code BitVector} representing the tap configuration. See
	 * 	{@link BitSetLfsr#setTaps(BitVector)}.
	 * @param fill A {@code BitVector} representing the fill. See
	 * 	{@link BitSetLfsr#setFill(BitVector)}.
	 */
	public BitSetLfsr(int length, BitVector taps, BitVector fill) {
		if (length <= 0) {
			throw new LfsrInvalidLengthException(length);
		}

		this.length = length;
		setFill(fill);
		setTaps(taps);
	}
	
	/** Creates a {@code BitSetLfsr} with the specified taps and all-zero fill.
	 * 
	 * @param length The length of the register to be created.
	 * @param taps A {@code BitVector} representing the tap configuration. See
	 * 	{@link BitSetLfsr#setTaps(BitVector)}.
	 */
	public BitSetLfsr(int length, BitVector taps) {
		if (length <= 0) {
			throw new LfsrInvalidLengthException(length);
		}
		
		this.length = length;
		this.fill = new BitSet(length);
		setTaps(taps);
	}
	
	/** Creates a {@code BitSetLfsr} with all-zero taps and fill.
	 * 
	 * @param length The length of the register to be created.
	 */
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
	public byte getTapsAt(int index) {
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
		long[] fillVectors = fill.toLongArray();
		
		byte output = shiftVectors(length, fillVectors, taps.toLongArray());
		
		// Retain the final fill state
		fill = BitSet.valueOf(fillVectors);
		
		return output;
	}

	@Override
	public List<Byte> shift(int terms) {
		List<Byte> output = new ArrayList<>(terms);
		
		long[] fillVectors = fill.toLongArray();
		
		for (int i = 0; i < terms; i++) {
			output.add(shiftVectors(length, fillVectors, taps.toLongArray()));
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
		
		byte output = shiftVectors(length, fillVectors, tapVectors);
		fill = BitSet.valueOf(fillVectors); // Retain the final fill state
		
		return output;
	}
	
	/** An LFSR shift operation on the underlying long[] representations */
	private static byte shiftVectors(int length, long[] fillVectors, long[] tapVectors) {
		byte outputBit = BitUtility.getBit(fillVectors, 0);
		
		// Perform a single shift
		byte inputBit = nextBit(fillVectors, tapVectors);
		rightShift(fillVectors);
		BitUtility.setBit(fillVectors, length - 1, inputBit);
		
		return outputBit;
	}
	
	private static void rightShift(long[] fillVectors) {
		for (int i = 0; i < fillVectors.length; i++) {
			// Set the highest bit of the previous vector equal to the lowest
			// bit of this vector.
			if (i != 0) {
				fillVectors[i - 1] = 
						BitUtility.setBit(fillVectors[i - 1], Long.SIZE - 1,
								(int) fillVectors[i] & 1);
			}

			fillVectors[i] >>= 1;
		}
	}
	
	private static byte nextBit(long[] fillVectors, long[] tapVectors) {
		// For each long, count the number of tap positions where the fill is 1
		int overlaps = 0;
		for (int i = 0; i < fillVectors.length; i++) {
			overlaps += Long.bitCount(fillVectors[i] & tapVectors[i]);
		}
		
		return (byte) (overlaps % 2);
	}
	

}
