package lsck.bitwise;

import java.util.BitSet;

/** A {@code long}-based implementation of {@code BitVector}
 * 
 * Supports bit vectors up to 64 bits in length.
 */
public class LongBitVector extends BitVector {

	private final long vector;
	private final int length;
	
	/** Creates a constant vector of zeros.
	 * 
	 * @param length The number of bits in the resulting vector.
	 */
	public LongBitVector(int length) {
		if (length < 0) {
			throw new BitVectorLengthException(length);
		}
		
		this.length = length;
		this.vector = 0;
	}
	
	/** Creates a vector from a specified {@code BitSet}.
	 * 
	 * @param length The number of bits retained from {@code bits}. Any bits
	 * 	with index at or above {@code length} will be truncated.
	 * @param bits A {@code BitSet} to source bits from.
	 */
	public LongBitVector(int length, BitSet bits) {
		if (length < 0) {
			throw new BitVectorLengthException(length);
		} else if (length > Long.SIZE) {
			throw new BitVectorLengthException(length, Long.SIZE);
		}
		
		this.length = length;
		
		long vector = 0;
		for (int i = 0; i < length; i++) {
			if (bits.get(i)) {
				vector |= 1L << i;
			}
		}
		
		this.vector = vector;
	}
	
	/** Creates a vector from a specified list of bits.
	 * 
	 * @param bits A list of bits, ordered from least-significant to most-
	 * 	significant. Any nonzero value is treated as a 1.
	 */
	public LongBitVector(int ... bits) {
		this.length = bits.length;
		
		long vector = 0;
		for (int i = 0; i < bits.length; i++) {
			if (bits[i] != 0) {
				vector |= 1L << i;
			}
		}
		
		this.vector = vector;
	}
	
	/** Creates a vector from a specified {@code long}
	 * 
	 * @param length The number of bits to retain from {@code bits}. The least
	 * 	significant {@code length} bits are used.
	 * @param bits A {@code long} to source bits from.
	 */
	public LongBitVector(int length, long vector) {
		if (length < 0) {
			throw new BitVectorLengthException(length);
		} else if (length > Long.SIZE) {
			throw new BitVectorLengthException(length, Long.SIZE);
		}
		
		this.length = length;

		// Truncate any bits above index "length - 1"
		int complement = Long.SIZE - length;
		this.vector = (vector << complement) >>> complement;
	}
	
	@Override
	public int getLength() {
		return length;
	}
	
	@Override
	public byte get(int index) {
		if (index < 0 || index >= length) {
			throw new BitVectorIndexOutOfBoundsException(index, length);
		}
		return (byte) ((vector & (1L << index)) >>> index);
	}

	@Override
	public byte toByte() {
		if (length > Byte.SIZE) {
			throw new BitVectorTruncationException(length, "byte");
		}
		
		return (byte) vector;
	}

	@Override
	public short toShort() {
		if (length > Short.SIZE) {
			throw new BitVectorTruncationException(length, "short");
		}
		
		return (short) vector;
	}

	@Override
	public int toInt() {
		if (length > Integer.SIZE) {
			throw new BitVectorTruncationException(length, "int");
		}
		
		return (int) vector;
	}

	@Override
	public long toLong() {
		return vector;
	}

	@Override
	public BitSet toBitSet() {
		return BitSet.valueOf(new long[] {vector});
	}
}