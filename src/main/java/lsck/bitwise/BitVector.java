package lsck.bitwise;

import java.util.BitSet;
import java.util.Iterator;

import lsck.common.Exceptions;

/** An immutable, fixed-length vector of bits */
public interface BitVector {

  /** Returns the length of this bit vector */
  int getLength();

  /**
   * Returns the specified bit in this vector
   *
   * @param index The position of the desired bit.
   * @return The specified bit in this vector.
   */
  byte get(int index);

  /**
   * Attempts to generate a {@code byte} representation of this {@code BitVector}.
   *
   * @return A {@code byte} containing the bits in this {@code BitVector}.
   * @throws BitVectorTruncationException Thrown if the length of this vector exceeds the length of
   *     {@code byte}.
   */
  byte toByte();

  /**
   * Attempts to generate a {@code short} representation of this {@code BitVector}.
   *
   * @return A {@code short} containing the bits in this {@code BitVector}.
   * @throws BitVectorTruncationException Thrown if the length of this vector exceeds the length of
   *     {@code short}.
   */
  short toShort();

  /**
   * Attempts to generate an {@code int} representation of this {@code BitVector}.
   *
   * @return An {@code int} containing the bits in this {@code BitVector}.
   * @throws BitVectorTruncationException Thrown if the length of this vector exceeds the length of
   *     {@code int}.
   */
  int toInt();

  /**
   * Attempts to generate a {@code long} representation of this {@code BitVector}.
   *
   * @return A {@code long} containing the bits in this {@code BitVector}.
   * @throws BitVectorTruncationException Thrown if the length of this vector exceeds the length of
   *     {@code long}.
   */
  long toLong();

  /**
   * Attempts to generate a {@code BitSet} representation of this {@code BitVector}.
   *
   * @return A {@code BitSet} containing the bits in this {@code BitVector}.
   */
  BitSet toBitSet();

  /**
   * Returns a {@link BitVector} representing this vector in reverse order.
   *
   * @return A {@link BitVector} of the same length as this one with bits in reverse order.
   */
  BitVector reverse();

  /**
   * Increments this {@link BitVector} as if it were an unsigned integer.
   *
   * @return An incremented copy of this {@link BitVector}.
   */
  BitVector increment();

  /**
   * Performs a bitwise AND with a second vector of the same length.
   *
   * @param b A vector of the same length as this one.
   * @return The result of performing a bitwise and on the two vectors.
   */
  BitVector and(BitVector b);

  /**
   * Performs a bitwise OR with a second vector of the same length.
   *
   * @param b A vector of the same length as this one.
   * @return The result of performing a bitwise OR on the two vectors.
   */
  BitVector or(BitVector b);

  /**
   * Performs a bitwise XOR with a second vector of the same length.
   *
   * @param b A vector of the same length as this one.
   * @return The result of performing a bitwise XOR on the two vectors.
   */
  BitVector xor(BitVector b);

  /**
   * Returns the result of flipping every bit in this bit vector.
   *
   * @param b A vector of the same length as this one.
   * @return The result of flipping every bit in this bit vector.
   */
  BitVector not();

  /**
   * Creates a {@link BitVector} representing the given bits.
   *
   * <p>This method selects an appropriate implementation of {@link BitVector} based on the number
   * of bits provided.
   *
   * @param bits A list of bits, ordered from most-significant to least- significant. Any nonzero
   *     value is treated as a 1.
   * @return An instance of {@link BitVector} representing the given bits.
   */
  static BitVector fromBits(int... bits) {
    if (bits.length <= Long.SIZE) {
      return new LongBitVector(bits);
    } else {
      return new BitSetBitVector(bits);
    }
  }

  /**
   * Creates a {@link BitVector} representing the given {@code long}.
   *
   * <p>This method selects an appropriate implementation of {@link BitVector} based on the number
   * of bits provided.
   *
   * @param vector A {@code long} to source bits from.
   * @return An instance of {@link BitVector} representing the given bits.
   */
  static BitVector fromInteger(int length, long vector) {
    if (length <= Long.SIZE) {
      return new LongBitVector(length, vector);
    } else {
      return new BitSetBitVector(length, vector);
    }
  }

  /**
   * Creates a {@link BitVector} representing the given {@code int}.
   *
   * <p>This method selects an appropriate implementation of {@link BitVector} based on the number
   * of bits provided.
   *
   * <p>This is equivalent to calling {@code fromInteger(length, Integer.toUnsignedLong(vector))}.
   *
   * @param vector An {@code int} to source bits from.
   * @return An instance of {@link BitVector} representing the given bits.
   */
  static BitVector fromInteger(int length, int vector) {
    return fromInteger(length, Integer.toUnsignedLong(vector));
  }

  /**
   * Creates a {@link BitVector} representing the given {@code short}.
   *
   * <p>This method selects an appropriate implementation of {@link BitVector} based on the number
   * of bits provided.
   *
   * <p>This is equivalent to calling {@code fromInteger(length, Short.toUnsignedLong(vector))}.
   *
   * @param vector A {@code short} to source bits from.
   * @return An instance of {@link BitVector} representing the given bits.
   */
  static BitVector fromInteger(int length, short vector) {
    return fromInteger(length, Short.toUnsignedLong(vector));
  }

  /**
   * Creates a {@link BitVector} representing the given {@code byte}.
   *
   * <p>This method selects an appropriate implementation of {@link BitVector} based on the number
   * of bits provided.
   *
   * <p>This is equivalent to calling {@code fromInteger(length, Byte.toUnsignedLong(vector))}.
   *
   * @param vector A {@code byte} to source bits from.
   * @return An instance of {@link BitVector} representing the given bits.
   */
  static BitVector fromInteger(int length, byte vector) {
    return fromInteger(length, Byte.toUnsignedLong(vector));
  }

  /**
   * Creates a {@link BitVector} representing the given bits.
   *
   * <p>This method selects an appropriate implementation of {@link BitVector} based on the number
   * of bits provided. Any bits in the source {@link BitSet} with index at or above {@code length}
   * will be truncated.
   *
   * @param length The number of bits retained from {@code bits}.
   * @param bitSet A {@link BitSet} to source bits from.
   * @return An instance of {@code BitVector} representing the given bits.
   */
  static BitVector fromBitSet(int length, BitSet bitSet) {
    if (length <= Long.SIZE) {
      return new LongBitVector(length, bitSet);
    } else {
      return new BitSetBitVector(length, bitSet);
    }
  }

  /**
   * Creates a {@link BitVector} representing the given bits.
   *
   * <p>This method selects an appropriate implementation of {@link BitVector} based on the number
   * of bits provided.
   *
   * @param length The total number of bits to retain from {@code vectors}.
   * @param vectors An array of {@code long} values. Bits are extracted in ascending order of array
   *     index and in order of least significance.
   * @return An instance of {@link BitVector} representing the given bits.
   */
  static BitVector fromLongArray(int length, long[] vectors) {
    return new BitSetBitVector(length, BitSet.valueOf(vectors));
  }

  /**
   * Creates a constant zero {@link BitVector} of the given length.
   *
   * <p>This method selects an appropriate implementation of {@link BitVector} based on the number
   * of bits requested.
   *
   * @param length The length of the constant zero vector to be returned.
   * @return An instance of {@link BitVector} of the requested length, in which all bits are set to
   *     0.
   */
  static BitVector zeroVector(int length) {
    if (length <= LongBitVector.MAX_LENGTH) {
      return new LongBitVector(length);
    } else {
      return new BitSetBitVector(length);
    }
  }

  /**
   * Creates a constant one {@link BitVector} of the given length.
   *
   * <p>This method selects an appropriate implementation of {@link BitVector} based on the number
   * of bits requested.
   *
   * @param length The length of the constant one vector to be returned.
   * @return An instance of {@link BitVector} of the requested length, in which all bits are set to
   *     1.
   */
  static BitVector oneVector(int length) {
    if (length <= LongBitVector.MAX_LENGTH) {
      return new LongBitVector(length, BitUtility.lowerBitmask(length));
    } else {
      BitSet bits = new BitSet(length);
      bits.set(0, length);
      return new BitSetBitVector(length, bits);
    }
  }

  /**
   * Returns an {@link Iterable} that will allow iteration over all bit vectors of the given length.
   *
   * <p>This method enables syntax like the following: <br>
   * {@code for (BitVector v : BitVector.allBitVectors(5))}
   *
   * @param length The length of the vectors to be iterated over. Note that iterating over all
   *     vectors of length close to {@link Long#SIZE} or longer is likely to be infeasible.
   * @return An {@link Iterable} that can produce {@link Iterator}s over all bit vectors of the
   *     given length.
   */
  public static Iterable<? extends BitVector> allVectors(int length) {
    if (length <= 0) {
      throw Exceptions.nonPositiveLength(length);
    } else if (length <= Long.SIZE) {
      return LongBitVector.allVectors(length);
    } else {
      return BitSetBitVector.allVectors(length);
    }
  }
}
