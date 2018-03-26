package lsck.bitwise;

import java.util.BitSet;

/** A {@code BitSet}-based implementation of {@code BitVector} */
public class BitSetBitVector extends AbstractBitVector {
	
	private static long SHORT_MASK = 0xFFFF;
	private static long INT_MASK = ~0;
	
	private final BitSet bits;
	private final int length;
	
	/** Creates a constant vector of zeros.
	 * 
	 * @param length The number of bits in the resulting vector.
	 */
	public BitSetBitVector(int length) {
		if (length < 0) {
			throw new BitVectorLengthException(length);
		}
		
		this.length = length;
		this.bits = new BitSet(0);
	}
	
	/** Creates a vector from a specified {@code BitSet}.
	 * 
	 * Any bits in the source {@code BitSet} with index at or above
	 * {@code length} will be truncated.
	 * 
	 * @param length The number of bits retained from {@code bits}. 
	 * @param bits A {@code BitSet} to source bits from.
	 */
	public BitSetBitVector(int length, BitSet bits) {
		if (length < 0) {
			throw new BitVectorLengthException(length);
		}
		
		this.length = length;
		this.bits = (BitSet) bits.get(0, length);
	}
	
	/** Creates a vector from a specified list of bits.
	 * 
	 * @param bits A list of bits, ordered from most-significant to least-
	 * significant. Any nonzero value is treated as a 1.
	 */
	public BitSetBitVector(int ... bits) {
		this.length = bits.length;
		this.bits = new BitSet(bits.length);
		
		for (int i = 0; i < bits.length; i++) {
			if (bits[i] != 0) {
				this.bits.set(bits.length - 1 - i);
			}
		}
	}
	
	/** Creates a vector from a specified {@code long}
	 * 
	 * @param length The number of bits to retain from {@code vector}. The
	 * 	least significant {@code length} bits are used.
	 * @param vector A {@code long} to source bits from.
	 */
	public BitSetBitVector(int length, long vector) {
		if (length < 0) {
			throw new BitVectorLengthException(length);
		} else if (length > Long.SIZE) {
			throw new BitVectorLengthException(length, Long.SIZE);
		}
		
		this.length = length;
		this.bits = BitSet.valueOf(new long[] {vector});
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
		return (byte) (bits.get(index) ? 1 : 0);
	}

	@Override
	public byte toByte() {
		if (length > Byte.SIZE) {
			throw new BitVectorTruncationException(length, "byte");
		} else if (length == 0 || bits.isEmpty()) {
			return 0;
		}
		
		return bits.toByteArray()[0];
	}

	@Override
	public short toShort() {
		if (length > Short.SIZE) {
			throw new BitVectorTruncationException(length, "short");
		} else if (length == 0 || bits.isEmpty()) {
			return 0;
		}
		
		return (short) (bits.toLongArray()[0] & SHORT_MASK);
	}

	@Override
	public int toInt() {
		if (length > Integer.SIZE) {
			throw new BitVectorTruncationException(length, "int");
		} else if (length == 0 || bits.isEmpty()) {
			return 0;
		}
		
		return (int) (bits.toLongArray()[0] & INT_MASK);
	}

	@Override
	public long toLong() {
		if (length > Long.SIZE) {
			throw new BitVectorTruncationException(length, "long");
		} else if (length == 0 || bits.isEmpty()) {
			return 0;
		}
		
		return bits.toLongArray()[0];
	}

	@Override
	public BitSet toBitSet() {
		return (BitSet) bits.clone();
	}
}
