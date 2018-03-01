package lsck.bitwise;

import java.util.BitSet;

/** An immutable, fixed-length vector of bits */
public abstract class BitVector {

	/** Returns the length of this bit vector */
	public abstract int getLength();
	
	/** Returns the specified bit in this vector
	 * 
	 * @param index The position of the desired bit.
	 * @return The specified bit in this vector.
	 */
	public abstract byte get(int index);
	
	/** Attempts to generate a byte representation of this {@code BitVector}.
	 * 
	 * @return A {@code byte} containing the bits in this {@code BitVector}.
	 * @throws BitVectorTruncationException Thrown if the length of this vector
	 * 	exceeds the length of {@code byte}.
	 */
	public abstract byte toByte();
	
	/** Attempts to generate a byte representation of this {@code BitVector}.
	 * 
	 * @return A {@code byte} containing the bits in this {@code BitVector}.
	 * @throws BitVectorTruncationException Thrown if the length of this vector
	 * 	exceeds the length of {@code byte}.
	 */
	public abstract short toShort();
	
	/** Attempts to generate a byte representation of this {@code BitVector}.
	 * 
	 * @return A {@code byte} containing the bits in this {@code BitVector}.
	 * @throws BitVectorTruncationException Thrown if the length of this vector
	 * 	exceeds the length of {@code byte}.
	 */
	public abstract int toInt();
	
	/** Attempts to generate a {@code long} representation of this {@code BitVector}.
	 * 
	 * @return A {@code long} containing the bits in this {@code BitVector}.
	 * @throws BitVectorTruncationException Thrown if the length of this vector
	 * 	exceeds the length of {@code long}.
	 */
	public abstract long toLong();
	
	/** Attempts to generate a {@code BitSet} representation of this {@code BitVector}.
	 * 
	 * @return A {@code BitSet} containing the bits in this {@code BitVector}.
	 */
	public abstract BitSet toBitSet();
	
	/** Creates a {@code BitVector} representing the given bits.
	 * 
	 * This method selects an appropriate implementation of {@code BitVector}
	 * based on the number of bits provided.
	 * 
	 * @param bits A list of bits, ordered from least-significant to most-
	 * 	significant. Any nonzero value is treated as a 1.
	 * @return An instance of {@code BitVector} representing the given bits.
	 */
	public static BitVector fromBits(int ... bits) {
		if (bits.length <= Long.SIZE) {
			return new LongBitVector(bits);
		} else {
			return new BitSetBitVector(bits);
		}
	}
	
	/** Creates a {@code BitVector} representing the given bits.
	 * 
	 * This method selects an appropriate implementation of {@code BitVector}
	 * based on the number of bits provided.
	 * 
	 * @param bits A list of bits, ordered from least-significant to most-
	 * 	significant. Any nonzero value is treated as a 1.
	 * @return An instance of {@code BitVector} representing the given bits.
	 */
	public static BitVector fromLong(int length, long vector) {
		return new LongBitVector(length, vector);
	}
	
	/** Creates a {@code BitVector} representing the given bits.
	 * 
	 * This method selects an appropriate implementation of {@code BitVector}
	 * based on the number of bits provided.
	 * 
	 * @param bits A list of bits, ordered from least-significant to most-
	 * 	significant. Any nonzero value is treated as a 1.
	 * @return An instance of {@code BitVector} representing the given bits.
	 */
	public static BitVector fromBitSet(int length, BitSet bitSet) {
		if (length <= Long.SIZE) {
			return new LongBitVector(length, bitSet);
		} else {
			return new BitSetBitVector(length, bitSet);
		}
	}
}
