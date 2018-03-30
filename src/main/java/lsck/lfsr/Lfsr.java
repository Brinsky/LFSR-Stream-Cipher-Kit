package lsck.lfsr;

import java.util.List;

import lsck.bitwise.BitVector;

/**
 * Represents a linear feedback shift register (LFSR).
 *
 * <p>In particular, this interface represents a Fibonacci LFSR. Basic information on the workings
 * and mathematics of LFSRs can be found at <a
 * href=http://wikipedia.org/wiki/Linear-feedback_shift_register>
 * http://wikipedia.org/wiki/Linear-feedback_shift_register</a>.
 *
 * <p>An {@code Lfsr} can be created with arbitrary length, taps, and fill. It can also generate
 * output streams of arbitrary length, both with and without permanently mutating the state of the
 * register.
 */
public interface Lfsr {

  /**
   * Returns the length of this register.
   *
   * @return The length of this register.
   */
  int getLength();

  /**
   * Returns the fill of this register.
   *
   * <p>The most significant bit in the returned vector represents the bit that will be output by
   * the next shift operation. The remaining lower-order bits follow consecutively.
   *
   * @return The fill of this register.
   */
  BitVector getFill();

  /**
   * Returns the specified bit within the fill of this register.
   *
   * <p>The bit with the an index of {@code getLength() - 1} is the bit that will be output by the
   * next shift operation. The remaining lower-order bits are indexed consecutively.
   *
   * @param index The position of the desired bit within the fill.
   * @return The specified bit within the fill of this register.
   */
  byte getFillAt(int index);

  /**
   * Returns the tap configuration of this register.
   *
   * <p>The bits in the returned vector indicate whether or not the corresponding bit position in
   * the register is tapped. A value of 1 indicates that the position is tapped and a value of 0
   * indicates that it is not.
   *
   * <p>The most significant bit in this vector corresponds to the position whose bit will be output
   * by the next shift operation. The remaining lower-order bits correspond to the remaining
   * consecutive positions.
   *
   * @return The fill of this register.
   */
  BitVector getTaps();

  /**
   * Returns the specified bit of this register's tap configuration.
   *
   * <p>A value of 1 indicates that the specified position is tapped and a value of 0 indicates that
   * it is not.
   *
   * <p>The bit with an index of {@code getLength() - 1} corresponds to the position whose bit will
   * be output by the next shift operation. The remaining lower-order bits are indexed
   * consecutively.
   *
   * @param index The position of the desired tap.
   * @return The fill of this register.
   */
  byte getTapsAt(int index);

  /**
   * Sets the fill of this register.
   *
   * <p>The most significant bit in the vector should describe the bit that will be output by the
   * next shift operation. The remaining lower-order bits should follow consecutively.
   *
   * @param fill A {@code BitVector} representing the fill of this register.
   */
  void setFill(BitVector fill);

  /**
   * Sets the fill of this register.
   *
   * <p>Equivalent to calling {@link BitVector#fromBits(int...)} and passing the result to {@link
   * #setFill(BitVector)}.
   *
   * @param bits A sequence of bits representing the fill of this register.
   */
  void setFill(int... bits);

  /**
   * Sets the fill of this register using a {@code long}.
   *
   * <p>The length of the {@code long} vector is assumed to be equal to that of the LFSR - any
   * higher order bits are truncated. Behavior-wise, this method should be equivalent to calling
   * {@code setFill(BitVector.fromInteger(getLength(), fill))}.
   *
   * @param fill A {@code long} representing the fill of this register.
   */
  void setFillFromInteger(long fill);

  /**
   * Sets the fill of this register using an {@code int}.
   *
   * <p>The length of the {@code int} vector is assumed to be equal to that of the LFSR - any higher
   * order bits are truncated. Behavior-wise, this method should be equivalent to calling {@code
   * setFill(BitVector.fromInteger(getLength(), fill))}.
   *
   * @param fill An {@code int} representing the fill of this register.
   */
  void setFillFromInteger(int fill);

  /**
   * Sets the fill of this register using a {@code short}.
   *
   * <p>The length of the {@code short} vector is assumed to be equal to that of the LFSR - any
   * higher order bits are truncated. Behavior-wise, this method should be equivalent to calling
   * {@code setFill(BitVector.fromInteger(getLength(), fill))}.
   *
   * @param fill A {@code short} representing the fill of this register.
   */
  void setFillFromInteger(short fill);

  /**
   * Sets the fill of this register using a {@code byte}.
   *
   * <p>The length of the {@code byte} vector is assumed to be equal to that of the LFSR - any
   * higher order bits are truncated. Behavior-wise, this method should be equivalent to calling
   * {@code setFill(BitVector.fromInteger(getLength(), fill))}.
   *
   * @param fill A {@code byte} representing the fill of this register.
   */
  void setFillFromInteger(byte fill);

  /**
   * Sets the specified bit in this register's fill.
   *
   * <p>The bit with an index of {@code getLength() - 1} corresponds to the bit that will be output
   * by the next shift operation. The remaining lower-order bits are indexed consecutively.
   *
   * @param index The position of the desired bit within the fill.
   * @param value The value to store into the specified position.
   */
  void setFillAt(int index, int value);

  /**
   * Sets the tap configuration for this register.
   *
   * <p>The most significant bit in the vector should correspond to the position in the register of
   * the bit that will be output by the next shift operation. The remaining lower-order bits should
   * follow consecutively.
   *
   * <p>A value of 1 indicates that the specified position should be tapped, and a value of 0
   * indicates that it should not be.
   *
   * @param taps A {@code BitVector} representing the tap configuration for this register.
   */
  void setTaps(BitVector taps);

  /**
   * Sets the tap configuration for this register.
   *
   * <p>Equivalent to calling {@link BitVector#fromBits(int...)} and passing the result to {@link
   * #setTaps(BitVector)}.
   *
   * @param bits A sequence of bits representing the tap configuration for this register.
   */
  void setTaps(int... bits);

  /**
   * Sets the tap configuration of this register using a {@code long}.
   *
   * <p>The length of the {@code long} vector is assumed to be equal to that of the LFSR - any
   * higher order bits are truncated. Behavior-wise, this method should be equivalent to calling
   * {@code setTaps(BitVector.fromInteger(getLength(), taps))}.
   *
   * @param taps A {@code long} representing the tap configuration of this register.
   */
  void setTapsFromInteger(long taps);

  /**
   * Sets the tap configuration of this register using an {@code int}.
   *
   * <p>The length of the {@code int} vector is assumed to be equal to that of the LFSR - any higher
   * order bits are truncated. Behavior-wise, this method should be equivalent to calling {@code
   * setTaps(BitVector.fromInteger(getLength(), taps))}.
   *
   * @param taps An {@code int} representing the tap configuration of this register.
   */
  void setTapsFromInteger(int taps);

  /**
   * Sets the tap configuration of this register using a {@code byte}.
   *
   * <p>The length of the {@code short} vector is assumed to be equal to that of the LFSR - any
   * higher order bits are truncated. Behavior-wise, this method should be equivalent to calling
   * {@code setTaps(BitVector.fromInteger(getLength(), taps))}.
   *
   * @param taps A {@code short} representing the tap configuration of this register.
   */
  void setTapsFromInteger(short taps);

  /**
   * Sets the tap configuration of this register using a {@code byte}.
   *
   * <p>The length of the {@code byte} vector is assumed to be equal to that of the LFSR - any
   * higher order bits are truncated. Behavior-wise, this method should be equivalent to calling
   * {@code setTaps(BitVector.fromInteger(getLength(), taps))}.
   *
   * @param taps A {@code byte} representing the tap configuration of this register.
   */
  void setTapsFromInteger(byte taps);

  /**
   * Sets the specified bit in this register's tap configuration.
   *
   * <p>The bit with an index of {@code getLength() - 1} corresponds to the position of the bit that
   * will be output by the next shift operation. The remaining lower- order bits are indexed
   * consecutively.
   *
   * <p>A value of 1 indicates that the specified position should be tapped, and a value of 0
   * indicates that it should not be.
   *
   * @param index The position of the desired bit within the tap configuration.
   * @param value The value to store into the specified position.
   */
  void setTapsAt(int index, int value);

  /**
   * Returns the next output bit without mutating the register's fill.
   *
   * @return The bit output during a single shift.
   */
  byte peek();

  /**
   * Returns a sequence of output bits without mutating the register's fill.
   *
   * @param terms The number of bits to be generated.
   * @return A list of bits output during the specified number of shifts.
   */
  List<Byte> peek(int terms);

  /**
   * Returns the specified bit in the register's output stream
   *
   * <p>The register's fill is not mutated by this operation. Bits in the output sequence are
   * indexed from 0.
   *
   * @param term The index of the desired bit in the output stream.
   * @return The specified bit in the output stream.
   */
  byte peekAt(int term);

  /**
   * Shifts the register and returns the output bit.
   *
   * @return The bit output during a single shift.
   */
  byte shift();

  /**
   * Performs the specified number of shifts and returns the output bits.
   *
   * @param terms The number of bits to be generated.
   * @return A list of bits output during the specified number of shifts.
   */
  List<Byte> shift(int terms);

  /**
   * Performs {@code term + 1} shifts and returns the last resulting bit.
   *
   * <p>Bits in the output sequence are indexed from 0. Calling {@code shiftTo(i)} leaves the
   * register in the same state as calling {@code shift(i + 1)}.
   *
   * @param term The index of the desired bit in the output stream.
   * @return The specified bit in the output stream.
   */
  byte shiftTo(int term);

  /**
   * Creates an {@link Lfsr} of the given length
   *
   * <p>This method selects an appropriate implementation of {@link Lfsr} based on the length
   * provided.
   *
   * @param length The length of the desired {@link Lfsr}.
   * @return An instance of {@link Lfsr} of the given length.
   */
  static Lfsr create(int length) {
    if (length <= LongLfsr.MAX_LENGTH) {
      return new LongLfsr(length);
    } else {
      return new BitSetLfsr(length);
    }
  }

  /**
   * Creates an {@link Lfsr} with the given length, taps, and fill.
   *
   * <p>This method selects an appropriate implementation of {@link Lfsr} based on the length
   * provided.
   *
   * @param length The length of the desired {@link Lfsr}.
   * @param taps See {@link #setTaps(BitVector)}.
   * @param fill See {@link #setFill(BitVector)}.
   * @return An instance of {@link Lfsr} with the given length, taps, and fill.
   */
  static Lfsr create(int length, BitVector taps, BitVector fill) {
    if (length <= LongLfsr.MAX_LENGTH) {
      return new LongLfsr(length, taps, fill);
    } else {
      return new BitSetLfsr(length, taps, fill);
    }
  }
}
