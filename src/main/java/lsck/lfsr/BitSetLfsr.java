package lsck.lfsr;

import java.util.BitSet;

import lsck.bitwise.BitList;
import lsck.bitwise.BitUtility;
import lsck.bitwise.BitVector;
import lsck.common.Exceptions;

/**
 * An {@link Lfsr} implementation backed by {@link BitSet}.
 *
 * <p>Supports registers of arbitrary length.
 */
public class BitSetLfsr extends AbstractLfsr {

  private final int length;
  
  // Bitmask for the uppermost (length % 64) bits in the highest-order long from the underlying
  // long[] array representing the fill.
  private final long upperLongMask;
  
  private BitSet fill;
  private BitSet taps;

  /**
   * Creates a {@code BitSetLfsr} with the specified taps and fill.
   *
   * @param length The length of the register to be created.
   * @param taps A {@code BitVector} representing the tap configuration. See {@link
   *     BitSetLfsr#setTaps(BitVector)}.
   * @param fill A {@code BitVector} representing the fill. See {@link
   *     BitSetLfsr#setFill(BitVector)}.
   */
  public BitSetLfsr(int length, BitVector taps, BitVector fill) {
    if (length <= 0) {
      throw Exceptions.nonPositiveLengthException(length);
    }

    this.length = length;
    upperLongMask = BitUtility.lowerBitmask(length % Long.SIZE);
    
    setFill(fill);
    setTaps(taps);
  }

  /**
   * Creates a {@code BitSetLfsr} with the specified taps and all-zero fill.
   *
   * @param length The length of the register to be created.
   * @param taps A {@code BitVector} representing the tap configuration. See {@link
   *     BitSetLfsr#setTaps(BitVector)}.
   */
  public BitSetLfsr(int length, BitVector taps) {
    if (length <= 0) {
      throw Exceptions.nonPositiveLengthException(length);
    }

    this.length = length;
    upperLongMask = BitUtility.lowerBitmask(length % Long.SIZE);
    
    this.fill = new BitSet(length);
    setTaps(taps);
  }

  /**
   * Creates a {@code BitSetLfsr} with all-zero taps and fill.
   *
   * @param length The length of the register to be created.
   */
  public BitSetLfsr(int length) {
    if (length <= 0) {
      throw Exceptions.nonPositiveLengthException(length);
    }

    this.length = length;
    upperLongMask = BitUtility.lowerBitmask(length % Long.SIZE);
    
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
      throw Exceptions.indexOutOfBoundsException(index, length);
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
      throw Exceptions.indexOutOfBoundsException(index, length);
    }

    return (byte) (taps.get(index) ? 1 : 0);
  }

  @Override
  public void setFill(BitVector fill) {
    if (fill.getLength() != length) {
      throw Exceptions.invalidVectorLengthException(length, fill.getLength());
    }

    this.fill = fill.toBitSet();
  }

  @Override
  public void setFillAt(int index, int value) {
    if (index < 0 || index >= length) {
      throw Exceptions.indexOutOfBoundsException(index, length);
    }

    fill.set(index, value != 0);
  }

  @Override
  public void setTaps(BitVector taps) {
    if (taps.getLength() != length) {
      throw Exceptions.invalidVectorLengthException(length, taps.getLength());
    }

    this.taps = taps.toBitSet();
  }

  @Override
  public void setTapsAt(int index, int value) {
    if (index < 0 || index >= length) {
      throw Exceptions.indexOutOfBoundsException(index, length);
    }

    taps.set(index, value != 0);
  }

  @Override
  public byte peek() {
    return getFillAt(length - 1);
  }

  @Override
  public BitList peek(int terms) {
    // Perform a regular shift and then reset the fill
    BitSet oldFill = (BitSet) fill.clone();
    BitList output = shift(terms);
    fill = oldFill;

    return output;
  }

  @Override
  public byte peekAt(int term) {
    // Perform a regular shiftAt and then reset the fill
    BitSet oldFill = (BitSet) fill.clone();
    byte result = shiftTo(term);
    fill = oldFill;

    return result;
  }

  @Override
  public byte shift() {
    long[] fillVectors = fill.toLongArray();

    byte output = shiftVectors(fillVectors, taps.toLongArray());

    // Retain the final fill state
    fill = BitSet.valueOf(fillVectors);

    return output;
  }

  @Override
  public BitList shift(int terms) {
    BitList output = new BitList(terms);

    long[] fillVectors = fill.toLongArray();

    for (int i = 0; i < terms; i++) {
      output.add(shiftVectors(fillVectors, taps.toLongArray()));
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
      shiftVectors(fillVectors, tapVectors);
    }

    byte output = shiftVectors(fillVectors, tapVectors);
    fill = BitSet.valueOf(fillVectors); // Retain the final fill state

    return output;
  }

  /** An LFSR shift operation on the underlying long[] representations */
  private byte shiftVectors(long[] fillVectors, long[] tapVectors) {
    byte outputBit = BitUtility.getBit(fillVectors, length - 1);

    // Perform a single shift
    byte inputBit = nextBit(fillVectors, tapVectors);
    leftShift(fillVectors);
    fillVectors[0] |= inputBit;

    return outputBit;
  }

  private void leftShift(long[] fillVectors) {
    // Left-shift the most-significant long and truncate the output bit
    fillVectors[fillVectors.length - 1] <<= 1;
    fillVectors[fillVectors.length - 1] &= upperLongMask;
    
    for (int i = fillVectors.length - 2; i >= 0; i++) {
      // Set the lowest bit in the previously-shifted long equal to the highest bit in this long
      fillVectors[i + 1] |= fillVectors[i] >>> Long.SIZE - 1;
      fillVectors[i] <<= 1;
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
