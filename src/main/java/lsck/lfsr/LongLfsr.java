package lsck.lfsr;

import java.util.ArrayList;
import java.util.List;

import lsck.bitwise.BitVector;
import lsck.bitwise.BitUtility;
import lsck.bitwise.BitVector;
import lsck.bitwise.LongBitVector;

/**
 * An {@link Lfsr} implementation backed by {@code long}.
 *
 * <p>Supports registers up to 64 bits in length.
 */
public class LongLfsr extends AbstractLfsr {

  private final int length;
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
      throw new LfsrInvalidLengthException(length);
    }

    this.length = length;
    setFill(fill);
    setTaps(taps);
  }

  /**
   * Creates a {@link LongLfsr} with the specified taps and fill.
   *
   * @param length The length of the register to be created.
   * @param taps A {@code long} representing the tap configuration. See {@link Lfsr#setTaps(long)}.
   * @param fill A {@code long} representing the fill. See {@link Lfsr#setFill(long)}.
   */
  public LongLfsr(int length, long taps, long fill) {
    if (length <= 0) {
      throw new LfsrInvalidLengthException(length);
    }

    this.length = length;
    setFill(fill);
    setTaps(taps);
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
      throw new LfsrInvalidLengthException(length);
    }

    this.length = length;
    this.fill = 0;
    setTaps(taps);
  }

  /**
   * Creates a {@link LongLfsr} with the specified taps and all-zero fill.
   *
   * @param length The length of the register to be created.
   * @param taps A {@code long} representing the tap configuration. See {@link Lfsr#setTaps(long)}.
   */
  public LongLfsr(int length, long taps) {
    if (length <= 0) {
      throw new LfsrInvalidLengthException(length);
    }

    this.length = length;
    this.fill = 0;
    setTaps(taps);
  }

  /**
   * Creates a {@link LongLfsr} with all-zero taps and fill.
   *
   * @param length The length of the register to be created.
   */
  public LongLfsr(int length) {
    if (length <= 0) {
      throw new LfsrInvalidLengthException(length);
    } else if (length > Long.SIZE) {
      throw new LfsrInvalidLengthException(0, Long.SIZE, length);
    }

    this.length = length;
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
    return BitUtility.getBit(fill, index);
  }

  @Override
  public BitVector getTaps() {
    return new LongBitVector(length, taps);
  }

  @Override
  public byte getTapsAt(int index) {
    return BitUtility.getBit(taps, index);
  }

  @Override
  public void setFill(BitVector fill) {
    this.fill = fill.toLong();
  }

  @Override
  public void setFillAt(int index, int value) {
    fill = BitUtility.setBit(fill, index, value);
  }

  @Override
  public void setTaps(BitVector taps) {
    this.taps = taps.toLong();
  }

  @Override
  public void setTapsAt(int index, int value) {
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
  public List<Byte> peek(int terms) {
    long oldFill = fill;
    List<Byte> output = shift(terms);
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
    byte output = (byte) (fill & 1L);

    long inputBit = ((long) (Long.bitCount(taps & fill) % 2)) << (length - 1);
    fill >>>= 1;
    fill |= inputBit;

    return output;
  }

  @Override
  public List<Byte> shift(int terms) {
    List<Byte> output = new ArrayList<>(terms);

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
}
