package lsck.lfsr;

import lsck.bitwise.BitList;
import lsck.bitwise.BitUtility;
import lsck.bitwise.BitVector;
import lsck.bitwise.LongBitVector;
import lsck.common.Exceptions;

/**
 * An {@link Lfsr} implementation backed by {@code long}.
 *
 * <p>Supports registers up to 64 bits in length.
 */
public class LongLfsr extends AbstractLfsr {

  public static final int MAX_LENGTH = Long.SIZE;
  
  private final int length;
  
  // Bitmask covering the length-many bits in the fill vector
  private final long registerMask;

  private long fill;
  private long taps;

  /**
   * Creates a {@link LongLfsr} with the specified taps and fill.
   *
   * @param length The length of the register to be created.
   * @param taps A {@code BitVector} representing the tap configuration. See {@link
   *     Lfsr#setTaps(BitVector)}.
   * @param fill A {@code BitVector} representing the fill. See {@link Lfsr#setFill(BitVector)}.
   */
  public LongLfsr(int length, BitVector taps, BitVector fill) {
    if (length <= 0) {
      throw Exceptions.nonPositiveLengthException(length);
    }

    this.length = length;
    this.registerMask = BitUtility.lowerBitmask(length);

    setFill(fill);
    setTaps(taps);
  }

  /**
   * Creates a {@link LongLfsr} with the specified taps and fill.
   *
   * @param length The length of the register to be created.
   * @param taps A {@code long} representing the tap configuration. See {@link Lfsr#setTapsFromInteger(long)}.
   * @param fill A {@code long} representing the fill. See {@link Lfsr#setFillFromInteger(long)}.
   */
  public LongLfsr(int length, long taps, long fill) {
    if (length <= 0) {
      throw Exceptions.nonPositiveLengthException(length);
    }

    this.length = length;
    this.registerMask = BitUtility.lowerBitmask(length);

    setFillFromInteger(fill);
    setTapsFromInteger(taps);
  }

  /**
   * Creates a {@link LongLfsr} with the specified taps and all-zero fill.
   *
   * @param length The length of the register to be created.
   * @param taps A {@code BitVector} representing the tap configuration. See {@link
   *     Lfsr#setTaps(BitVector)}.
   */
  public LongLfsr(int length, BitVector taps) {
    if (length <= 0) {
      throw Exceptions.nonPositiveLengthException(length);
    }

    this.length = length;
    this.registerMask = BitUtility.lowerBitmask(length);

    this.fill = 0;
    setTaps(taps);
  }

  /**
   * Creates a {@link LongLfsr} with the specified taps and all-zero fill.
   *
   * @param length The length of the register to be created.
   * @param taps A {@code long} representing the tap configuration. See {@link Lfsr#setTapsFromInteger(long)}.
   */
  public LongLfsr(int length, long taps) {
    if (length <= 0) {
      throw Exceptions.nonPositiveLengthException(length);
    }

    this.length = length;
    this.registerMask = BitUtility.lowerBitmask(length);

    this.fill = 0;
    setTapsFromInteger(taps);
  }

  /**
   * Creates a {@link LongLfsr} with all-zero taps and fill.
   *
   * @param length The length of the register to be created.
   */
  public LongLfsr(int length) {
    if (length <= 0) {
      throw Exceptions.nonPositiveLengthException(length);
    } else if (length > Long.SIZE) {
      throw Exceptions.unsupportedLengthException(length, Long.SIZE);
    }

    this.length = length;
    this.registerMask = BitUtility.lowerBitmask(length);

    this.fill = 0;
    this.taps = 0;
  }

  @Override
  public int getLength() {
    return length;
  }

  @Override
  public BitVector getFill() {
    return new LongBitVector(length, fill);
  }

  @Override
  public byte getFillAt(int index) {
    if (index < 0 || index >= length) {
      throw Exceptions.indexOutOfBoundsException(index, length);
    }
    
    return BitUtility.getBit(fill, index);
  }

  @Override
  public BitVector getTaps() {
    return new LongBitVector(length, taps);
  }

  @Override
  public byte getTapsAt(int index) {
    if (index < 0 || index >= length) {
      throw Exceptions.indexOutOfBoundsException(index, length);
    }
    
    return BitUtility.getBit(taps, index);
  }

  @Override
  public void setFill(BitVector fill) {
    if (fill.getLength() != length) {
      throw Exceptions.invalidVectorLengthException(length, fill.getLength());
    }
    
    this.fill = fill.toLong();
  }

  @Override
  public void setFillAt(int index, int value) {
    if (index < 0 || index >= length) {
      throw Exceptions.indexOutOfBoundsException(index, length);
    }
    
    fill = BitUtility.setBit(fill, index, value);
  }

  @Override
  public void setTaps(BitVector taps) {
    if (taps.getLength() != length) {
      throw Exceptions.invalidVectorLengthException(length, taps.getLength());
    }
    
    this.taps = taps.toLong();
  }

  @Override
  public void setTapsAt(int index, int value) {
    if (index < 0 || index >= length) {
      throw Exceptions.indexOutOfBoundsException(index, length);
    }
    
    taps = BitUtility.setBit(taps, index, value);
  }

  @Override
  public byte peek() {
    long oldFill = fill;
    byte output = shift();
    fill = oldFill;

    return output;
  }

  @Override
  public BitList peek(int terms) {
    long oldFill = fill;
    BitList output = shift(terms);
    fill = oldFill;

    return output;
  }

  @Override
  public byte peekAt(int term) {
    long oldFill = fill;
    byte output = shiftTo(term);
    fill = oldFill;

    return output;
  }

  @Override
  public byte shift() {
    byte output = (byte) ((fill >>> length - 1) & 1L);

    long inputBit = Long.bitCount(taps & fill) % 2;

    fill = (fill << 1L) & registerMask; // Left shift by one position and truncate the output bit
    fill |= inputBit;

    return output;
  }

  @Override
  public BitList shift(int terms) {
    BitList output = new BitList(terms);

    for (int i = 0; i < terms; i++) {
      output.add(shift());
    }

    return output;
  }

  @Override
  public byte shiftTo(int term) {
    for (int i = 0; i < term; i++) {
      shift();
    }

    return shift();
  }
  
  @Override
  public void incrementFill() {
    // Manage overflow manually using a bitmask
    fill = (fill + 1) & registerMask;
  }
}
