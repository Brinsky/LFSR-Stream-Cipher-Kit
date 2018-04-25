package lsck.bitwise;

import java.util.BitSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import lsck.common.Exceptions;

/** A {@code BitSet}-based implementation of {@code BitVector} */
public class BitSetBitVector extends AbstractBitVector {

  private static long SHORT_MASK = 0xFFFF;
  private static long INT_MASK = ~0;

  private final BitSet bits;
  private final int length;

  /**
   * Creates a constant vector of zeros.
   *
   * @param length The number of bits in the resulting vector.
   */
  public BitSetBitVector(int length) {
    lengthRangeCheck(length);

    this.length = length;
    this.bits = new BitSet(0);
  }

  /**
   * Creates a vector from a specified {@code BitSet}.
   *
   * <p>Any bits in the source {@code BitSet} with index at or above {@code length} will be
   * truncated.
   *
   * @param length The number of bits retained from {@code bits}.
   * @param bits A {@code BitSet} to source bits from.
   */
  public BitSetBitVector(int length, BitSet bits) {
    lengthRangeCheck(length);

    this.length = length;
    this.bits = (BitSet) bits.get(0, length);
  }

  /**
   * Creates a vector from a specified list of bits.
   *
   * @param bits A list of bits, ordered from most-significant to least- significant. Any nonzero
   *     value is treated as a 1.
   */
  public BitSetBitVector(int... bits) {
    lengthRangeCheck(bits.length);

    this.length = bits.length;
    this.bits = new BitSet(bits.length);

    for (int i = 0; i < bits.length; i++) {
      if (bits[i] != 0) {
        this.bits.set(bits.length - 1 - i);
      }
    }
  }

  /**
   * Creates a vector from a specified {@code long}
   *
   * @param length The number of bits to retain from {@code vector}. The least significant {@code
   *     length} bits are used.
   * @param vector A {@code long} to source bits from.
   */
  public BitSetBitVector(int length, long vector) {
    lengthRangeCheck(length);

    this.length = length;
    this.bits = BitSet.valueOf(new long[] {vector});
  }

  private static void lengthRangeCheck(int length) {
    if (length <= 0) {
      throw Exceptions.nonPositiveLength(length);
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
    return (byte) (bits.get(index) ? 1 : 0);
  }

  @Override
  public byte toByte() {
    if (length > Byte.SIZE) {
      throw Exceptions.vectorTruncation(length, "byte");
    } else if (length == 0 || bits.isEmpty()) {
      return 0;
    }

    return bits.toByteArray()[0];
  }

  @Override
  public short toShort() {
    if (length > Short.SIZE) {
      throw Exceptions.vectorTruncation(length, "short");
    } else if (length == 0 || bits.isEmpty()) {
      return 0;
    }

    return (short) (bits.toLongArray()[0] & SHORT_MASK);
  }

  @Override
  public int toInt() {
    if (length > Integer.SIZE) {
      throw Exceptions.vectorTruncation(length, "int");
    } else if (length == 0 || bits.isEmpty()) {
      return 0;
    }

    return (int) (bits.toLongArray()[0] & INT_MASK);
  }

  @Override
  public long toLong() {
    if (length > Long.SIZE) {
      throw Exceptions.vectorTruncation(length, "long");
    } else if (length == 0 || bits.isEmpty()) {
      return 0;
    }

    return bits.toLongArray()[0];
  }

  @Override
  public BitSet toBitSet() {
    return (BitSet) bits.clone();
  }

  @Override
  public BitSetBitVector reverse() {
    BitSet reversed = new BitSet(length);

    for (int i = 0; i < length; i++) {
      if (bits.get(length - i - 1)) {
        reversed.set(i);
      }
    }

    return new BitSetBitVector(length, reversed);
  }

  @Override
  public BitSetBitVector increment() {
    long[] longs = BitUtility.bitSetToLongArray(length, bits);

    // Loop as long as overflows continue to occur
    for (int i = 0; (i == 0 || longs[i - 1] == 0) && i < longs.length; i++) {
      // In general, the highest order long needs to be manually overflowed via a bitmask.
      if (i == longs.length - 1) {
        longs[i] = (longs[i] + 1) & BitUtility.lowerBitmask(length % Long.SIZE);
      } else {
        longs[i]++;
      }
    }

    return new BitSetBitVector(length, BitSet.valueOf(longs));
  }

  @Override
  public BitSetBitVector and(BitVector b) {

    if (b.getLength() != length) {
      throw Exceptions.invalidVectorLength(length, b.getLength());
    }

    BitSet result = b.toBitSet();
    result.and(bits);

    return new BitSetBitVector(length, result);
  }

  @Override
  public BitSetBitVector or(BitVector b) {
    if (b.getLength() != length) {
      throw Exceptions.invalidVectorLength(length, b.getLength());
    }

    BitSet result = b.toBitSet();
    result.or(bits);

    return new BitSetBitVector(length, result);
  }

  @Override
  public BitSetBitVector xor(BitVector b) {

    if (b.getLength() != length) {
      throw Exceptions.invalidVectorLength(length, b.getLength());
    }

    BitSet result = b.toBitSet();
    result.xor(bits);

    return new BitSetBitVector(length, result);
  }

  @Override
  public int hashCode() {
    return bits.hashCode();
  }

  @Override
  public BitVector not() {
    BitSet result = bits.get(0, length);
    result.flip(0, length);

    return new BitSetBitVector(length, result);
  }

  /**
   * Returns an {@link Iterable} that will allow iteration over all bit vectors of the given length.
   *
   * <p>This method enables syntax like the following: <br>
   * {@code for (BitSetBitVector v : BitSetBitVector.allBitVectors(5))}
   *
   * @param length The length of the vectors to be iterated over.
   * @return An {@link Iterable} that can produce {@link Iterator}s over all bit vectors of the
   *     given length.
   */
  public static Iterable<BitSetBitVector> allVectors(int length) {
    lengthRangeCheck(length);

    return () -> new BitSetBitVectorIterator(length);
  }

  /** Allows access to every {@link BitSetBitVector} of a given length */
  private static class BitSetBitVectorIterator implements Iterator<BitSetBitVector> {

    private final BitSetBitVector zeroVector;
    
    private boolean finished = false;   
    private BitSetBitVector currentVector;

    public BitSetBitVectorIterator(int length) {
      lengthRangeCheck(length);

      currentVector = new BitSetBitVector(length);
      zeroVector = new BitSetBitVector(length);
    }

    @Override
    public boolean hasNext() {
      return !finished;
    }

    @Override
    public BitSetBitVector next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      BitSetBitVector old = currentVector;
      currentVector = currentVector.increment();
      
      // TODO: Implement and use a bitCount() method to determine this quickly
      if (currentVector.equals(zeroVector)) {
        finished = true;
      }

      return old;
    }
  }
}
