package lsck.bitwise;

import java.util.BitSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import lsck.common.Exceptions;

/**
 * A {@code long}-based implementation of {@code BitVector}
 *
 * <p>Supports bit vectors up to 64 bits in length.
 */
public class LongBitVector extends AbstractBitVector {

  public static final int MAX_LENGTH = Long.SIZE;

  private final long vector;
  private final int length;

  /**
   * Creates a constant vector of zeros.
   *
   * @param length The number of bits in the resulting vector.
   */
  public LongBitVector(int length) {
    lengthRangeCheck(length);

    this.length = length;
    this.vector = 0;
  }

  /**
   * Creates a vector from a specified {@code BitSet}.
   *
   * @param length The number of bits retained from {@code bits}. Any bits with index at or above
   *     {@code length} will be truncated.
   * @param bits A {@code BitSet} to source bits from.
   */
  public LongBitVector(int length, BitSet bits) {
    lengthRangeCheck(length);

    this.length = length;

    long vector = 0;
    for (int i = 0; i < length; i++) {
      if (bits.get(i)) {
        vector |= 1L << i;
      }
    }

    this.vector = vector;
  }

  /**
   * Creates a vector from a specified list of bits.
   *
   * @param bits A list of bits, ordered from most-significant to least- significant. Any nonzero
   *     value is treated as a 1.
   */
  public LongBitVector(int... bits) {
    lengthRangeCheck(bits.length);

    this.length = bits.length;

    long vector = 0;
    for (int i = 0; i < bits.length; i++) {
      if (bits[i] != 0) {
        vector |= 1L << (bits.length - 1 - i);
      }
    }

    this.vector = vector;
  }

  /**
   * Creates a vector from a specified {@code long}
   *
   * @param length The number of bits to retain from {@code vector}. The least significant {@code
   *     length} bits are used.
   * @param vector A {@code long} to source bits from.
   */
  public LongBitVector(int length, long vector) {
    lengthRangeCheck(length);

    this.length = length;

    // Truncate any bits above index "length - 1"
    int complement = Long.SIZE - length;
    this.vector = (vector << complement) >>> complement;
  }

  private static void lengthRangeCheck(int length) {
    if (length <= 0) {
      throw Exceptions.nonPositiveLength(length);
    } else if (length > MAX_LENGTH) {
      throw Exceptions.unsupportedLength(length, MAX_LENGTH);
    }
  }

  @Override
  public int getLength() {
    return length;
  }

  @Override
  public byte get(int index) {
    if (index < 0 || index >= length) {
      throw Exceptions.indexOutOfBounds(index, length);
    }
    return (byte) ((vector & (1L << index)) >>> index);
  }

  @Override
  public byte toByte() {
    if (length > Byte.SIZE) {
      throw Exceptions.vectorTruncation(length, "byte");
    }

    return (byte) vector;
  }

  @Override
  public short toShort() {
    if (length > Short.SIZE) {
      throw Exceptions.vectorTruncation(length, "short");
    }

    return (short) vector;
  }

  @Override
  public int toInt() {
    if (length > Integer.SIZE) {
      throw Exceptions.vectorTruncation(length, "int");
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

  @Override
  public BitVector reverse() {
    return new LongBitVector(length, BitUtility.reverse(length, vector));
  }

  @Override
  public BitVector increment() {
    // For vectors of fewer than 64 bits, we need to handle overflow manually via a bitmask
    return new LongBitVector(length, (vector + 1) & BitUtility.lowerBitmask(length));
  }

  @Override
  public BitVector and(BitVector b) {
    if (b.getLength() != length) {
      throw Exceptions.invalidVectorLength(length, b.getLength());
    }

    return new LongBitVector(length, b.toLong() & vector);
  }

  @Override
  public BitVector or(BitVector b) {
    if (b.getLength() != length) {
      throw Exceptions.invalidVectorLength(length, b.getLength());
    }

    return new LongBitVector(length, b.toLong() | vector);
  }

  @Override
  public BitVector xor(BitVector b) {
    if (b.getLength() != length) {
      throw Exceptions.invalidVectorLength(length, b.getLength());
    }

    return new LongBitVector(length, b.toLong() ^ vector);
  }

  @Override
  public int hashCode() {
    return Long.hashCode(vector);
  }

  @Override
  public BitVector not() {
    return new LongBitVector(length, ~vector & BitUtility.lowerBitmask(length));
  }

  /**
   * Returns an {@link Iterable} that will allow iteration over all bit vectors of the given length.
   *
   * <p>This method enables syntax like the following: <br>
   * {@code for (LongBitVector v : LongBitVector.allBitVectors(5))}
   *
   * @param length The length of the vectors to be iterated over.
   * @return An {@link Iterable} that can produce {@link Iterator}s over all bit vectors of the
   *     given length.
   */
  public static Iterable<LongBitVector> allVectors(int length) {
    lengthRangeCheck(length);

    return () -> new LongBitVectorIterator(length);
  }

  /** Allows access to every {@code long}-vector of a given length */
  private static class LongBitVectorIterator implements Iterator<LongBitVector> {

    private final int length;
    private final long lastVector;

    private boolean finished = false;
    private long currentVector = 0;

    public LongBitVectorIterator(int length) {
      lengthRangeCheck(length);

      this.length = length;
      lastVector = BitUtility.lowerBitmask(length);
    }

    @Override
    public boolean hasNext() {
      return !finished;
    }

    @Override
    public LongBitVector next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      
      if (currentVector == lastVector) {
        finished = true;
      }

      return new LongBitVector(length, currentVector++);
    }
  }
}
