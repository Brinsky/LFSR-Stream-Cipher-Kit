package lsck.lfsr;

import lsck.bitwise.BitVector;
import lsck.common.Exceptions;

/**
 * Contains tap configurations that generate sequences of maximal period.
 *
 * <p>The {@link BitVector}s contained in this class represent {@link Lfsr} tap-configurations that,
 * given a non-zero initial fill, will generate an output sequence of maximum-possible length. That
 * is, for a register of length k, the sequences generated using these taps will be of length 2^k -
 * 1.
 *
 * <p>The {@link BitVector}s can be accessed directly as members of this class. The naming
 * convention is TAPS_X_Y, where X is the length of the corresponding tap configuration and Y is
 * the index of that tap configuration among all those of length Y. There is also a helper method
 * {@link #getMaximalTaps(int, int)} to allow programmatic access to these tap configurations.
 *
 * <p>Tap-configurations can be represented as "characteristic polynomials," of which only the
 * primitive polynomials will produce maximal-period output sequences. For a more detailed
 * description of this relationship, see <a
 * href="https://en.wikipedia.org/wiki/Linear-feedback_shift_register#Fibonacci_LFSRs">Fibonacci
 * LFSRs</a> on Wikipedia. The list of primitive polynomials used to generate the {@link BitVector}s
 * in this class was retrieved from Arash Partow's website at <a
 * href="http://www.partow.net/programming/polynomials/index.html">
 * http://www.partow.net/programming/polynomials/index.html</a>.
 */
public class MaximalTaps {
  public static final BitVector TAPS_2_0 = BitVector.fromBits(1, 1);
  public static final BitVector TAPS_3_0 = BitVector.fromBits(1, 0, 1);
  public static final BitVector TAPS_4_0 = BitVector.fromBits(1, 0, 0, 1);
  public static final BitVector TAPS_5_0 = BitVector.fromBits(1, 0, 0, 1, 0);
  public static final BitVector TAPS_5_1 = BitVector.fromBits(1, 1, 0, 1, 1);
  public static final BitVector TAPS_5_2 = BitVector.fromBits(1, 1, 1, 1, 0);
  public static final BitVector TAPS_6_0 = BitVector.fromBits(1, 0, 0, 0, 0, 1);
  public static final BitVector TAPS_6_1 = BitVector.fromBits(1, 1, 0, 0, 1, 1);
  public static final BitVector TAPS_6_2 = BitVector.fromBits(1, 1, 0, 1, 1, 0);
  public static final BitVector TAPS_7_0 = BitVector.fromBits(1, 0, 0, 0, 0, 0, 1);
  public static final BitVector TAPS_7_1 = BitVector.fromBits(1, 0, 0, 0, 1, 0, 0);
  public static final BitVector TAPS_7_2 = BitVector.fromBits(1, 0, 0, 0, 1, 1, 1);
  public static final BitVector TAPS_7_3 = BitVector.fromBits(1, 0, 0, 1, 1, 1, 0);
  public static final BitVector TAPS_7_4 = BitVector.fromBits(1, 0, 1, 1, 1, 1, 1);
  public static final BitVector TAPS_7_5 = BitVector.fromBits(1, 1, 0, 0, 1, 0, 1);
  public static final BitVector TAPS_7_6 = BitVector.fromBits(1, 1, 0, 1, 0, 1, 0);
  public static final BitVector TAPS_7_7 = BitVector.fromBits(1, 1, 1, 0, 0, 1, 0);
  public static final BitVector TAPS_7_8 = BitVector.fromBits(1, 1, 1, 1, 0, 1, 1);
  public static final BitVector TAPS_8_0 = BitVector.fromBits(1, 0, 0, 0, 1, 1, 1, 0);
  public static final BitVector TAPS_8_1 = BitVector.fromBits(1, 0, 0, 1, 0, 1, 0, 1);
  public static final BitVector TAPS_8_2 = BitVector.fromBits(1, 0, 1, 0, 1, 1, 1, 1);
  public static final BitVector TAPS_8_3 = BitVector.fromBits(1, 0, 1, 1, 0, 0, 0, 1);
  public static final BitVector TAPS_8_4 = BitVector.fromBits(1, 0, 1, 1, 0, 0, 1, 0);
  public static final BitVector TAPS_8_5 = BitVector.fromBits(1, 0, 1, 1, 0, 1, 0, 0);
  public static final BitVector TAPS_8_6 = BitVector.fromBits(1, 1, 1, 0, 0, 0, 0, 1);
  public static final BitVector TAPS_8_7 = BitVector.fromBits(1, 1, 1, 1, 0, 0, 1, 1);
  public static final BitVector TAPS_9_0 = BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0);
  public static final BitVector TAPS_9_1 = BitVector.fromBits(1, 0, 0, 0, 1, 0, 1, 1, 0);
  public static final BitVector TAPS_9_2 = BitVector.fromBits(1, 0, 0, 1, 0, 1, 1, 0, 0);
  public static final BitVector TAPS_9_3 = BitVector.fromBits(1, 0, 0, 1, 1, 0, 1, 1, 1);
  public static final BitVector TAPS_9_4 = BitVector.fromBits(1, 0, 0, 1, 1, 1, 0, 1, 1);
  public static final BitVector TAPS_9_5 = BitVector.fromBits(1, 0, 1, 1, 0, 1, 1, 0, 1);
  public static final BitVector TAPS_9_6 = BitVector.fromBits(1, 1, 0, 0, 0, 1, 0, 0, 1);
  public static final BitVector TAPS_9_7 = BitVector.fromBits(1, 1, 0, 0, 1, 1, 0, 0, 0);
  public static final BitVector TAPS_9_8 = BitVector.fromBits(1, 1, 0, 1, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_9_9 = BitVector.fromBits(1, 1, 0, 1, 1, 0, 1, 0, 1);
  public static final BitVector TAPS_9_10 = BitVector.fromBits(1, 1, 1, 0, 0, 0, 0, 1, 0);
  public static final BitVector TAPS_9_11 = BitVector.fromBits(1, 1, 1, 0, 0, 0, 1, 1, 1);
  public static final BitVector TAPS_9_12 = BitVector.fromBits(1, 1, 1, 1, 1, 0, 0, 0, 1);
  public static final BitVector TAPS_9_13 = BitVector.fromBits(1, 1, 1, 1, 1, 0, 1, 0, 0);
  public static final BitVector TAPS_10_0 = BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  public static final BitVector TAPS_10_1 = BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 1, 0, 1);
  public static final BitVector TAPS_10_2 = BitVector.fromBits(1, 0, 0, 0, 1, 1, 0, 1, 1, 1);
  public static final BitVector TAPS_10_3 = BitVector.fromBits(1, 0, 1, 0, 0, 0, 0, 1, 1, 0);
  public static final BitVector TAPS_10_4 = BitVector.fromBits(1, 0, 1, 0, 0, 0, 1, 1, 0, 0);
  public static final BitVector TAPS_10_5 = BitVector.fromBits(1, 0, 1, 0, 0, 1, 0, 0, 0, 1);
  public static final BitVector TAPS_10_6 = BitVector.fromBits(1, 0, 1, 0, 0, 1, 1, 0, 0, 0);
  public static final BitVector TAPS_10_7 = BitVector.fromBits(1, 0, 1, 1, 1, 1, 0, 0, 1, 0);
  public static final BitVector TAPS_10_8 = BitVector.fromBits(1, 0, 1, 1, 1, 1, 1, 1, 0, 1);
  public static final BitVector TAPS_10_9 = BitVector.fromBits(1, 1, 0, 0, 0, 0, 1, 0, 0, 1);
  public static final BitVector TAPS_10_10 = BitVector.fromBits(1, 1, 0, 0, 1, 1, 1, 1, 1, 1);
  public static final BitVector TAPS_10_11 = BitVector.fromBits(1, 1, 1, 0, 1, 0, 0, 1, 1, 0);
  public static final BitVector TAPS_10_12 = BitVector.fromBits(1, 1, 1, 0, 1, 1, 0, 0, 0, 1);
  public static final BitVector TAPS_10_13 = BitVector.fromBits(1, 1, 1, 1, 1, 1, 1, 1, 0, 0);
  public static final BitVector TAPS_11_0 = BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
  public static final BitVector TAPS_11_1 = BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1);
  public static final BitVector TAPS_11_2 = BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0);
  public static final BitVector TAPS_11_3 = BitVector.fromBits(1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1);
  public static final BitVector TAPS_11_4 = BitVector.fromBits(1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0);
  public static final BitVector TAPS_11_5 = BitVector.fromBits(1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0);
  public static final BitVector TAPS_11_6 = BitVector.fromBits(1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1);
  public static final BitVector TAPS_11_7 = BitVector.fromBits(1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1);
  public static final BitVector TAPS_11_8 = BitVector.fromBits(1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1);
  public static final BitVector TAPS_11_9 = BitVector.fromBits(1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1);
  public static final BitVector TAPS_11_10 = BitVector.fromBits(1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0);
  public static final BitVector TAPS_11_11 = BitVector.fromBits(1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1);
  public static final BitVector TAPS_11_12 = BitVector.fromBits(1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1);
  public static final BitVector TAPS_11_13 = BitVector.fromBits(1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1);
  public static final BitVector TAPS_12_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1);
  public static final BitVector TAPS_12_1 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0);
  public static final BitVector TAPS_12_2 =
      BitVector.fromBits(1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1);
  public static final BitVector TAPS_12_3 =
      BitVector.fromBits(1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0);
  public static final BitVector TAPS_12_4 =
      BitVector.fromBits(1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0);
  public static final BitVector TAPS_12_5 =
      BitVector.fromBits(1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1);
  public static final BitVector TAPS_12_6 =
      BitVector.fromBits(1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1);
  public static final BitVector TAPS_12_7 =
      BitVector.fromBits(1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0);
  public static final BitVector TAPS_12_8 =
      BitVector.fromBits(1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_12_9 =
      BitVector.fromBits(1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0);
  public static final BitVector TAPS_12_10 =
      BitVector.fromBits(1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1);
  public static final BitVector TAPS_12_11 =
      BitVector.fromBits(1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1);
  public static final BitVector TAPS_12_12 =
      BitVector.fromBits(1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1);
  public static final BitVector TAPS_12_13 =
      BitVector.fromBits(1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1);
  public static final BitVector TAPS_13_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1);
  public static final BitVector TAPS_13_1 =
      BitVector.fromBits(1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1);
  public static final BitVector TAPS_13_2 =
      BitVector.fromBits(1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1);
  public static final BitVector TAPS_13_3 =
      BitVector.fromBits(1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0);
  public static final BitVector TAPS_13_4 =
      BitVector.fromBits(1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1);
  public static final BitVector TAPS_13_5 =
      BitVector.fromBits(1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1);
  public static final BitVector TAPS_13_6 =
      BitVector.fromBits(1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
  public static final BitVector TAPS_13_7 =
      BitVector.fromBits(1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0);
  public static final BitVector TAPS_13_8 =
      BitVector.fromBits(1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_13_9 =
      BitVector.fromBits(1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0);
  public static final BitVector TAPS_13_10 =
      BitVector.fromBits(1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0);
  public static final BitVector TAPS_13_11 =
      BitVector.fromBits(1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1);
  public static final BitVector TAPS_13_12 =
      BitVector.fromBits(1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1);
  public static final BitVector TAPS_13_13 =
      BitVector.fromBits(1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0);
  public static final BitVector TAPS_14_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1);
  public static final BitVector TAPS_14_1 =
      BitVector.fromBits(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1);
  public static final BitVector TAPS_14_2 =
      BitVector.fromBits(1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1);
  public static final BitVector TAPS_14_3 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
  public static final BitVector TAPS_14_4 =
      BitVector.fromBits(1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0);
  public static final BitVector TAPS_14_5 =
      BitVector.fromBits(1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0);
  public static final BitVector TAPS_14_6 =
      BitVector.fromBits(1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1);
  public static final BitVector TAPS_14_7 =
      BitVector.fromBits(1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0);
  public static final BitVector TAPS_14_8 =
      BitVector.fromBits(1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1);
  public static final BitVector TAPS_14_9 =
      BitVector.fromBits(1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1);
  public static final BitVector TAPS_14_10 =
      BitVector.fromBits(1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1);
  public static final BitVector TAPS_14_11 =
      BitVector.fromBits(1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1);
  public static final BitVector TAPS_14_12 =
      BitVector.fromBits(1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1);
  public static final BitVector TAPS_14_13 =
      BitVector.fromBits(1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_15_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
  public static final BitVector TAPS_15_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
  public static final BitVector TAPS_15_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
  public static final BitVector TAPS_15_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1);
  public static final BitVector TAPS_15_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1);
  public static final BitVector TAPS_15_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0);
  public static final BitVector TAPS_15_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1);
  public static final BitVector TAPS_15_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0);
  public static final BitVector TAPS_15_8 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0);
  public static final BitVector TAPS_15_9 =
      BitVector.fromBits(1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1);
  public static final BitVector TAPS_15_10 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1);
  public static final BitVector TAPS_15_11 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0);
  public static final BitVector TAPS_15_12 =
      BitVector.fromBits(1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0);
  public static final BitVector TAPS_15_13 =
      BitVector.fromBits(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0);
  public static final BitVector TAPS_16_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0);
  public static final BitVector TAPS_16_1 =
      BitVector.fromBits(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1);
  public static final BitVector TAPS_16_2 =
      BitVector.fromBits(1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0);
  public static final BitVector TAPS_16_3 =
      BitVector.fromBits(1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1);
  public static final BitVector TAPS_16_4 =
      BitVector.fromBits(1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1);
  public static final BitVector TAPS_16_5 =
      BitVector.fromBits(1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1);
  public static final BitVector TAPS_16_6 =
      BitVector.fromBits(1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1);
  public static final BitVector TAPS_16_7 =
      BitVector.fromBits(1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0);
  public static final BitVector TAPS_16_8 =
      BitVector.fromBits(1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0);
  public static final BitVector TAPS_16_9 =
      BitVector.fromBits(1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1);
  public static final BitVector TAPS_16_10 =
      BitVector.fromBits(1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1);
  public static final BitVector TAPS_16_11 =
      BitVector.fromBits(1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1);
  public static final BitVector TAPS_16_12 =
      BitVector.fromBits(1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1);
  public static final BitVector TAPS_16_13 =
      BitVector.fromBits(1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1);
  public static final BitVector TAPS_17_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  public static final BitVector TAPS_17_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1);
  public static final BitVector TAPS_17_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_17_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
  public static final BitVector TAPS_17_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0);
  public static final BitVector TAPS_17_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0);
  public static final BitVector TAPS_17_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0);
  public static final BitVector TAPS_17_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1);
  public static final BitVector TAPS_17_8 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1);
  public static final BitVector TAPS_17_9 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0);
  public static final BitVector TAPS_17_10 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1);
  public static final BitVector TAPS_17_11 =
      BitVector.fromBits(1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0);
  public static final BitVector TAPS_17_12 =
      BitVector.fromBits(1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1);
  public static final BitVector TAPS_17_13 =
      BitVector.fromBits(1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1);
  public static final BitVector TAPS_18_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1);
  public static final BitVector TAPS_18_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
  public static final BitVector TAPS_18_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1);
  public static final BitVector TAPS_18_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1);
  public static final BitVector TAPS_18_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0);
  public static final BitVector TAPS_18_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1);
  public static final BitVector TAPS_18_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1);
  public static final BitVector TAPS_18_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_18_8 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_18_9 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1);
  public static final BitVector TAPS_18_10 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0);
  public static final BitVector TAPS_18_11 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0);
  public static final BitVector TAPS_18_12 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0);
  public static final BitVector TAPS_18_13 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0);
  public static final BitVector TAPS_19_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1);
  public static final BitVector TAPS_19_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1);
  public static final BitVector TAPS_19_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1);
  public static final BitVector TAPS_19_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1);
  public static final BitVector TAPS_19_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0);
  public static final BitVector TAPS_19_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1);
  public static final BitVector TAPS_19_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_19_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1);
  public static final BitVector TAPS_19_8 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1);
  public static final BitVector TAPS_19_9 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_19_10 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1);
  public static final BitVector TAPS_19_11 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1);
  public static final BitVector TAPS_19_12 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0);
  public static final BitVector TAPS_19_13 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1);
  public static final BitVector TAPS_19_14 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1);
  public static final BitVector TAPS_20_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  public static final BitVector TAPS_20_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0);
  public static final BitVector TAPS_20_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0);
  public static final BitVector TAPS_20_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0);
  public static final BitVector TAPS_20_4 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0);
  public static final BitVector TAPS_20_5 =
      BitVector.fromBits(1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0);
  public static final BitVector TAPS_21_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
  public static final BitVector TAPS_21_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0);
  public static final BitVector TAPS_21_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0);
  public static final BitVector TAPS_21_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0);
  public static final BitVector TAPS_21_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0);
  public static final BitVector TAPS_21_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0);
  public static final BitVector TAPS_21_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0);
  public static final BitVector TAPS_21_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0);
  public static final BitVector TAPS_21_8 =
      BitVector.fromBits(1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0);
  public static final BitVector TAPS_22_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
  public static final BitVector TAPS_22_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1);
  public static final BitVector TAPS_22_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1);
  public static final BitVector TAPS_22_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1);
  public static final BitVector TAPS_22_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1);
  public static final BitVector TAPS_22_5 =
      BitVector.fromBits(1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1);
  public static final BitVector TAPS_23_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_23_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1);
  public static final BitVector TAPS_23_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_23_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0);
  public static final BitVector TAPS_23_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0);
  public static final BitVector TAPS_23_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0);
  public static final BitVector TAPS_23_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_23_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1);
  public static final BitVector TAPS_23_8 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0);
  public static final BitVector TAPS_23_9 =
      BitVector.fromBits(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_24_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1);
  public static final BitVector TAPS_24_1 =
      BitVector.fromBits(1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1);
  public static final BitVector TAPS_24_2 =
      BitVector.fromBits(1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0);
  public static final BitVector TAPS_25_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  public static final BitVector TAPS_25_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1);
  public static final BitVector TAPS_25_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0);
  public static final BitVector TAPS_25_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0);
  public static final BitVector TAPS_25_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0);
  public static final BitVector TAPS_25_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1);
  public static final BitVector TAPS_25_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0);
  public static final BitVector TAPS_25_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0);
  public static final BitVector TAPS_25_8 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1);
  public static final BitVector TAPS_25_9 =
      BitVector.fromBits(1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0);
  public static final BitVector TAPS_26_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1);
  public static final BitVector TAPS_26_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0);
  public static final BitVector TAPS_26_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0);
  public static final BitVector TAPS_26_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1);
  public static final BitVector TAPS_26_4 =
      BitVector.fromBits(
          1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1);
  public static final BitVector TAPS_26_5 =
      BitVector.fromBits(
          1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0);
  public static final BitVector TAPS_26_6 =
      BitVector.fromBits(
          1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1);
  public static final BitVector TAPS_27_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1);
  public static final BitVector TAPS_27_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0);
  public static final BitVector TAPS_27_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0);
  public static final BitVector TAPS_27_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
  public static final BitVector TAPS_27_4 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0);
  public static final BitVector TAPS_27_5 =
      BitVector.fromBits(
          1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1);
  public static final BitVector TAPS_27_6 =
      BitVector.fromBits(
          1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0);
  public static final BitVector TAPS_27_7 =
      BitVector.fromBits(
          1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1);
  public static final BitVector TAPS_27_8 =
      BitVector.fromBits(
          1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0);
  public static final BitVector TAPS_28_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  public static final BitVector TAPS_28_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0);
  public static final BitVector TAPS_28_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0);
  public static final BitVector TAPS_28_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0);
  public static final BitVector TAPS_28_4 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0);
  public static final BitVector TAPS_28_5 =
      BitVector.fromBits(
          1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0);
  public static final BitVector TAPS_29_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
  public static final BitVector TAPS_29_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0);
  public static final BitVector TAPS_29_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0);
  public static final BitVector TAPS_29_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0);
  public static final BitVector TAPS_29_4 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
  public static final BitVector TAPS_29_5 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0);
  public static final BitVector TAPS_29_6 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0);
  public static final BitVector TAPS_29_7 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0);
  public static final BitVector TAPS_29_8 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0);
  public static final BitVector TAPS_29_9 =
      BitVector.fromBits(
          1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0);
  public static final BitVector TAPS_30_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1);
  public static final BitVector TAPS_30_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1);
  public static final BitVector TAPS_30_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1);
  public static final BitVector TAPS_30_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1);
  public static final BitVector TAPS_30_4 =
      BitVector.fromBits(
          1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1);
  public static final BitVector TAPS_31_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
          0);
  public static final BitVector TAPS_31_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
          1);
  public static final BitVector TAPS_31_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0,
          0);
  public static final BitVector TAPS_31_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1,
          0);
  public static final BitVector TAPS_31_4 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0,
          0);
  public static final BitVector TAPS_31_5 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0,
          0);
  public static final BitVector TAPS_31_6 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
          1);
  public static final BitVector TAPS_31_7 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0,
          0);
  public static final BitVector TAPS_31_8 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1,
          1);
  public static final BitVector TAPS_31_9 =
      BitVector.fromBits(
          1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0,
          0);
  public static final BitVector TAPS_31_10 =
      BitVector.fromBits(
          1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1,
          1);
  public static final BitVector TAPS_32_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          1, 1);
  public static final BitVector TAPS_32_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0,
          0, 1);
  public static final BitVector TAPS_32_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1,
          0, 0);
  public static final BitVector TAPS_32_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0,
          1, 1);
  public static final BitVector TAPS_32_4 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0,
          1, 1);
  public static final BitVector TAPS_32_5 =
      BitVector.fromBits(
          1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0,
          0, 1);

  public static final int MIN_LENGTH = 2;
  public static final int MAX_LENGTH = 32;

  /**
   * Returns a tap-configuration of maximal-period for the given register length.
   *
   * @param length The length of the register in question.
   * @param index The index of the tap-configuration among all other tap-configurations of the
   *     specified length. To determine how many such tap-configurations exist for a given length,
   *     call {@link #maximalTapsCount(int)}.
   * @return A {@link BitVector} representing the specified tap-configuration.
   */
  public static BitVector getMaximalTaps(int length, int index) {
    if (length < MIN_LENGTH || length > MAX_LENGTH) {
      throw Exceptions.invalidLength(MIN_LENGTH, MAX_LENGTH, length);
    }

    int degreeIndex = length - MIN_LENGTH;

    if (index < 0 || index > MAXIMAL_TAPS[degreeIndex].length) {
      throw Exceptions.indexOutOfBounds(index, MAXIMAL_TAPS[degreeIndex].length);
    }

    return MAXIMAL_TAPS[degreeIndex][index];
  }

  /**
   * Returns the number of maximal-period tap configurations that exist for a given register length.
   *
   * <p>Length must be between {@link MIN_LENGTH} and {@link MAX_LENGTH}, inclusive.
   *
   * <p>See {@link MaximalTaps} for more details on maximal-period tap configurations.
   *
   * @param length A register length for which the number of distinct maximal-period tap
   *     configurations is desired.
   * @return The number of maximal-period tap configurations that exist for the given register
   *     length.
   */
  public static int maximalTapsCount(int length) {
    if (length < MIN_LENGTH || length > MAX_LENGTH) {
      throw Exceptions.invalidLength(MIN_LENGTH, MAX_LENGTH, length);
    }

    return MAXIMAL_TAPS[length - MIN_LENGTH].length;
  }

  private static final BitVector[][] MAXIMAL_TAPS = {
    {
      TAPS_2_0,
    },
    {
      TAPS_3_0,
    },
    {
      TAPS_4_0,
    },
    {
      TAPS_5_0, TAPS_5_1, TAPS_5_2,
    },
    {
      TAPS_6_0, TAPS_6_1, TAPS_6_2,
    },
    {
      TAPS_7_0,
      TAPS_7_1,
      TAPS_7_2,
      TAPS_7_3,
      TAPS_7_4,
      TAPS_7_5,
      TAPS_7_6,
      TAPS_7_7,
      TAPS_7_8,
    },
    {
      TAPS_8_0,
      TAPS_8_1,
      TAPS_8_2,
      TAPS_8_3,
      TAPS_8_4,
      TAPS_8_5,
      TAPS_8_6,
      TAPS_8_7,
    },
    {
      TAPS_9_0,
      TAPS_9_1,
      TAPS_9_2,
      TAPS_9_3,
      TAPS_9_4,
      TAPS_9_5,
      TAPS_9_6,
      TAPS_9_7,
      TAPS_9_8,
      TAPS_9_9,
      TAPS_9_10,
      TAPS_9_11,
      TAPS_9_12,
      TAPS_9_13,
    },
    {
      TAPS_10_0,
      TAPS_10_1,
      TAPS_10_2,
      TAPS_10_3,
      TAPS_10_4,
      TAPS_10_5,
      TAPS_10_6,
      TAPS_10_7,
      TAPS_10_8,
      TAPS_10_9,
      TAPS_10_10,
      TAPS_10_11,
      TAPS_10_12,
      TAPS_10_13,
    },
    {
      TAPS_11_0,
      TAPS_11_1,
      TAPS_11_2,
      TAPS_11_3,
      TAPS_11_4,
      TAPS_11_5,
      TAPS_11_6,
      TAPS_11_7,
      TAPS_11_8,
      TAPS_11_9,
      TAPS_11_10,
      TAPS_11_11,
      TAPS_11_12,
      TAPS_11_13,
    },
    {
      TAPS_12_0,
      TAPS_12_1,
      TAPS_12_2,
      TAPS_12_3,
      TAPS_12_4,
      TAPS_12_5,
      TAPS_12_6,
      TAPS_12_7,
      TAPS_12_8,
      TAPS_12_9,
      TAPS_12_10,
      TAPS_12_11,
      TAPS_12_12,
      TAPS_12_13,
    },
    {
      TAPS_13_0,
      TAPS_13_1,
      TAPS_13_2,
      TAPS_13_3,
      TAPS_13_4,
      TAPS_13_5,
      TAPS_13_6,
      TAPS_13_7,
      TAPS_13_8,
      TAPS_13_9,
      TAPS_13_10,
      TAPS_13_11,
      TAPS_13_12,
      TAPS_13_13,
    },
    {
      TAPS_14_0,
      TAPS_14_1,
      TAPS_14_2,
      TAPS_14_3,
      TAPS_14_4,
      TAPS_14_5,
      TAPS_14_6,
      TAPS_14_7,
      TAPS_14_8,
      TAPS_14_9,
      TAPS_14_10,
      TAPS_14_11,
      TAPS_14_12,
      TAPS_14_13,
    },
    {
      TAPS_15_0,
      TAPS_15_1,
      TAPS_15_2,
      TAPS_15_3,
      TAPS_15_4,
      TAPS_15_5,
      TAPS_15_6,
      TAPS_15_7,
      TAPS_15_8,
      TAPS_15_9,
      TAPS_15_10,
      TAPS_15_11,
      TAPS_15_12,
      TAPS_15_13,
    },
    {
      TAPS_16_0,
      TAPS_16_1,
      TAPS_16_2,
      TAPS_16_3,
      TAPS_16_4,
      TAPS_16_5,
      TAPS_16_6,
      TAPS_16_7,
      TAPS_16_8,
      TAPS_16_9,
      TAPS_16_10,
      TAPS_16_11,
      TAPS_16_12,
      TAPS_16_13,
    },
    {
      TAPS_17_0,
      TAPS_17_1,
      TAPS_17_2,
      TAPS_17_3,
      TAPS_17_4,
      TAPS_17_5,
      TAPS_17_6,
      TAPS_17_7,
      TAPS_17_8,
      TAPS_17_9,
      TAPS_17_10,
      TAPS_17_11,
      TAPS_17_12,
      TAPS_17_13,
    },
    {
      TAPS_18_0,
      TAPS_18_1,
      TAPS_18_2,
      TAPS_18_3,
      TAPS_18_4,
      TAPS_18_5,
      TAPS_18_6,
      TAPS_18_7,
      TAPS_18_8,
      TAPS_18_9,
      TAPS_18_10,
      TAPS_18_11,
      TAPS_18_12,
      TAPS_18_13,
    },
    {
      TAPS_19_0,
      TAPS_19_1,
      TAPS_19_2,
      TAPS_19_3,
      TAPS_19_4,
      TAPS_19_5,
      TAPS_19_6,
      TAPS_19_7,
      TAPS_19_8,
      TAPS_19_9,
      TAPS_19_10,
      TAPS_19_11,
      TAPS_19_12,
      TAPS_19_13,
      TAPS_19_14,
    },
    {
      TAPS_20_0, TAPS_20_1, TAPS_20_2, TAPS_20_3, TAPS_20_4, TAPS_20_5,
    },
    {
      TAPS_21_0,
      TAPS_21_1,
      TAPS_21_2,
      TAPS_21_3,
      TAPS_21_4,
      TAPS_21_5,
      TAPS_21_6,
      TAPS_21_7,
      TAPS_21_8,
    },
    {
      TAPS_22_0, TAPS_22_1, TAPS_22_2, TAPS_22_3, TAPS_22_4, TAPS_22_5,
    },
    {
      TAPS_23_0,
      TAPS_23_1,
      TAPS_23_2,
      TAPS_23_3,
      TAPS_23_4,
      TAPS_23_5,
      TAPS_23_6,
      TAPS_23_7,
      TAPS_23_8,
      TAPS_23_9,
    },
    {
      TAPS_24_0, TAPS_24_1, TAPS_24_2,
    },
    {
      TAPS_25_0,
      TAPS_25_1,
      TAPS_25_2,
      TAPS_25_3,
      TAPS_25_4,
      TAPS_25_5,
      TAPS_25_6,
      TAPS_25_7,
      TAPS_25_8,
      TAPS_25_9,
    },
    {
      TAPS_26_0, TAPS_26_1, TAPS_26_2, TAPS_26_3, TAPS_26_4, TAPS_26_5, TAPS_26_6,
    },
    {
      TAPS_27_0,
      TAPS_27_1,
      TAPS_27_2,
      TAPS_27_3,
      TAPS_27_4,
      TAPS_27_5,
      TAPS_27_6,
      TAPS_27_7,
      TAPS_27_8,
    },
    {
      TAPS_28_0, TAPS_28_1, TAPS_28_2, TAPS_28_3, TAPS_28_4, TAPS_28_5,
    },
    {
      TAPS_29_0,
      TAPS_29_1,
      TAPS_29_2,
      TAPS_29_3,
      TAPS_29_4,
      TAPS_29_5,
      TAPS_29_6,
      TAPS_29_7,
      TAPS_29_8,
      TAPS_29_9,
    },
    {
      TAPS_30_0, TAPS_30_1, TAPS_30_2, TAPS_30_3, TAPS_30_4,
    },
    {
      TAPS_31_0,
      TAPS_31_1,
      TAPS_31_2,
      TAPS_31_3,
      TAPS_31_4,
      TAPS_31_5,
      TAPS_31_6,
      TAPS_31_7,
      TAPS_31_8,
      TAPS_31_9,
      TAPS_31_10,
    },
    {
      TAPS_32_0, TAPS_32_1, TAPS_32_2, TAPS_32_3, TAPS_32_4, TAPS_32_5,
    }
  };
}
