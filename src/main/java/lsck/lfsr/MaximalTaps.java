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
 * convention is DEGREE_X_Y, where X is the length of the corresponding tap configuration and Y is
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
  public static final BitVector DEGREE_2_0 = BitVector.fromBits(1, 1);
  public static final BitVector DEGREE_3_0 = BitVector.fromBits(1, 0, 1);
  public static final BitVector DEGREE_4_0 = BitVector.fromBits(1, 0, 0, 1);
  public static final BitVector DEGREE_5_0 = BitVector.fromBits(1, 0, 0, 1, 0);
  public static final BitVector DEGREE_5_1 = BitVector.fromBits(1, 1, 0, 1, 1);
  public static final BitVector DEGREE_5_2 = BitVector.fromBits(1, 1, 1, 1, 0);
  public static final BitVector DEGREE_6_0 = BitVector.fromBits(1, 0, 0, 0, 0, 1);
  public static final BitVector DEGREE_6_1 = BitVector.fromBits(1, 1, 0, 0, 1, 1);
  public static final BitVector DEGREE_6_2 = BitVector.fromBits(1, 1, 0, 1, 1, 0);
  public static final BitVector DEGREE_7_0 = BitVector.fromBits(1, 0, 0, 0, 0, 0, 1);
  public static final BitVector DEGREE_7_1 = BitVector.fromBits(1, 0, 0, 0, 1, 0, 0);
  public static final BitVector DEGREE_7_2 = BitVector.fromBits(1, 0, 0, 0, 1, 1, 1);
  public static final BitVector DEGREE_7_3 = BitVector.fromBits(1, 0, 0, 1, 1, 1, 0);
  public static final BitVector DEGREE_7_4 = BitVector.fromBits(1, 0, 1, 1, 1, 1, 1);
  public static final BitVector DEGREE_7_5 = BitVector.fromBits(1, 1, 0, 0, 1, 0, 1);
  public static final BitVector DEGREE_7_6 = BitVector.fromBits(1, 1, 0, 1, 0, 1, 0);
  public static final BitVector DEGREE_7_7 = BitVector.fromBits(1, 1, 1, 0, 0, 1, 0);
  public static final BitVector DEGREE_7_8 = BitVector.fromBits(1, 1, 1, 1, 0, 1, 1);
  public static final BitVector DEGREE_8_0 = BitVector.fromBits(1, 0, 0, 0, 1, 1, 1, 0);
  public static final BitVector DEGREE_8_1 = BitVector.fromBits(1, 0, 0, 1, 0, 1, 0, 1);
  public static final BitVector DEGREE_8_2 = BitVector.fromBits(1, 0, 1, 0, 1, 1, 1, 1);
  public static final BitVector DEGREE_8_3 = BitVector.fromBits(1, 0, 1, 1, 0, 0, 0, 1);
  public static final BitVector DEGREE_8_4 = BitVector.fromBits(1, 0, 1, 1, 0, 0, 1, 0);
  public static final BitVector DEGREE_8_5 = BitVector.fromBits(1, 0, 1, 1, 0, 1, 0, 0);
  public static final BitVector DEGREE_8_6 = BitVector.fromBits(1, 1, 1, 0, 0, 0, 0, 1);
  public static final BitVector DEGREE_8_7 = BitVector.fromBits(1, 1, 1, 1, 0, 0, 1, 1);
  public static final BitVector DEGREE_9_0 = BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0);
  public static final BitVector DEGREE_9_1 = BitVector.fromBits(1, 0, 0, 0, 1, 0, 1, 1, 0);
  public static final BitVector DEGREE_9_2 = BitVector.fromBits(1, 0, 0, 1, 0, 1, 1, 0, 0);
  public static final BitVector DEGREE_9_3 = BitVector.fromBits(1, 0, 0, 1, 1, 0, 1, 1, 1);
  public static final BitVector DEGREE_9_4 = BitVector.fromBits(1, 0, 0, 1, 1, 1, 0, 1, 1);
  public static final BitVector DEGREE_9_5 = BitVector.fromBits(1, 0, 1, 1, 0, 1, 1, 0, 1);
  public static final BitVector DEGREE_9_6 = BitVector.fromBits(1, 1, 0, 0, 0, 1, 0, 0, 1);
  public static final BitVector DEGREE_9_7 = BitVector.fromBits(1, 1, 0, 0, 1, 1, 0, 0, 0);
  public static final BitVector DEGREE_9_8 = BitVector.fromBits(1, 1, 0, 1, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_9_9 = BitVector.fromBits(1, 1, 0, 1, 1, 0, 1, 0, 1);
  public static final BitVector DEGREE_9_10 = BitVector.fromBits(1, 1, 1, 0, 0, 0, 0, 1, 0);
  public static final BitVector DEGREE_9_11 = BitVector.fromBits(1, 1, 1, 0, 0, 0, 1, 1, 1);
  public static final BitVector DEGREE_9_12 = BitVector.fromBits(1, 1, 1, 1, 1, 0, 0, 0, 1);
  public static final BitVector DEGREE_9_13 = BitVector.fromBits(1, 1, 1, 1, 1, 0, 1, 0, 0);
  public static final BitVector DEGREE_10_0 = BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  public static final BitVector DEGREE_10_1 = BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 1, 0, 1);
  public static final BitVector DEGREE_10_2 = BitVector.fromBits(1, 0, 0, 0, 1, 1, 0, 1, 1, 1);
  public static final BitVector DEGREE_10_3 = BitVector.fromBits(1, 0, 1, 0, 0, 0, 0, 1, 1, 0);
  public static final BitVector DEGREE_10_4 = BitVector.fromBits(1, 0, 1, 0, 0, 0, 1, 1, 0, 0);
  public static final BitVector DEGREE_10_5 = BitVector.fromBits(1, 0, 1, 0, 0, 1, 0, 0, 0, 1);
  public static final BitVector DEGREE_10_6 = BitVector.fromBits(1, 0, 1, 0, 0, 1, 1, 0, 0, 0);
  public static final BitVector DEGREE_10_7 = BitVector.fromBits(1, 0, 1, 1, 1, 1, 0, 0, 1, 0);
  public static final BitVector DEGREE_10_8 = BitVector.fromBits(1, 0, 1, 1, 1, 1, 1, 1, 0, 1);
  public static final BitVector DEGREE_10_9 = BitVector.fromBits(1, 1, 0, 0, 0, 0, 1, 0, 0, 1);
  public static final BitVector DEGREE_10_10 = BitVector.fromBits(1, 1, 0, 0, 1, 1, 1, 1, 1, 1);
  public static final BitVector DEGREE_10_11 = BitVector.fromBits(1, 1, 1, 0, 1, 0, 0, 1, 1, 0);
  public static final BitVector DEGREE_10_12 = BitVector.fromBits(1, 1, 1, 0, 1, 1, 0, 0, 0, 1);
  public static final BitVector DEGREE_10_13 = BitVector.fromBits(1, 1, 1, 1, 1, 1, 1, 1, 0, 0);
  public static final BitVector DEGREE_11_0 = BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
  public static final BitVector DEGREE_11_1 = BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1);
  public static final BitVector DEGREE_11_2 = BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0);
  public static final BitVector DEGREE_11_3 = BitVector.fromBits(1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1);
  public static final BitVector DEGREE_11_4 = BitVector.fromBits(1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0);
  public static final BitVector DEGREE_11_5 = BitVector.fromBits(1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0);
  public static final BitVector DEGREE_11_6 = BitVector.fromBits(1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1);
  public static final BitVector DEGREE_11_7 = BitVector.fromBits(1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1);
  public static final BitVector DEGREE_11_8 = BitVector.fromBits(1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1);
  public static final BitVector DEGREE_11_9 = BitVector.fromBits(1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1);
  public static final BitVector DEGREE_11_10 = BitVector.fromBits(1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0);
  public static final BitVector DEGREE_11_11 = BitVector.fromBits(1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1);
  public static final BitVector DEGREE_11_12 = BitVector.fromBits(1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1);
  public static final BitVector DEGREE_11_13 = BitVector.fromBits(1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1);
  public static final BitVector DEGREE_12_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1);
  public static final BitVector DEGREE_12_1 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0);
  public static final BitVector DEGREE_12_2 =
      BitVector.fromBits(1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1);
  public static final BitVector DEGREE_12_3 =
      BitVector.fromBits(1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0);
  public static final BitVector DEGREE_12_4 =
      BitVector.fromBits(1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0);
  public static final BitVector DEGREE_12_5 =
      BitVector.fromBits(1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1);
  public static final BitVector DEGREE_12_6 =
      BitVector.fromBits(1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1);
  public static final BitVector DEGREE_12_7 =
      BitVector.fromBits(1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0);
  public static final BitVector DEGREE_12_8 =
      BitVector.fromBits(1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_12_9 =
      BitVector.fromBits(1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0);
  public static final BitVector DEGREE_12_10 =
      BitVector.fromBits(1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1);
  public static final BitVector DEGREE_12_11 =
      BitVector.fromBits(1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1);
  public static final BitVector DEGREE_12_12 =
      BitVector.fromBits(1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1);
  public static final BitVector DEGREE_12_13 =
      BitVector.fromBits(1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1);
  public static final BitVector DEGREE_13_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1);
  public static final BitVector DEGREE_13_1 =
      BitVector.fromBits(1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1);
  public static final BitVector DEGREE_13_2 =
      BitVector.fromBits(1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1);
  public static final BitVector DEGREE_13_3 =
      BitVector.fromBits(1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0);
  public static final BitVector DEGREE_13_4 =
      BitVector.fromBits(1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1);
  public static final BitVector DEGREE_13_5 =
      BitVector.fromBits(1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1);
  public static final BitVector DEGREE_13_6 =
      BitVector.fromBits(1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
  public static final BitVector DEGREE_13_7 =
      BitVector.fromBits(1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0);
  public static final BitVector DEGREE_13_8 =
      BitVector.fromBits(1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_13_9 =
      BitVector.fromBits(1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0);
  public static final BitVector DEGREE_13_10 =
      BitVector.fromBits(1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0);
  public static final BitVector DEGREE_13_11 =
      BitVector.fromBits(1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1);
  public static final BitVector DEGREE_13_12 =
      BitVector.fromBits(1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1);
  public static final BitVector DEGREE_13_13 =
      BitVector.fromBits(1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0);
  public static final BitVector DEGREE_14_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1);
  public static final BitVector DEGREE_14_1 =
      BitVector.fromBits(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1);
  public static final BitVector DEGREE_14_2 =
      BitVector.fromBits(1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1);
  public static final BitVector DEGREE_14_3 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
  public static final BitVector DEGREE_14_4 =
      BitVector.fromBits(1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0);
  public static final BitVector DEGREE_14_5 =
      BitVector.fromBits(1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0);
  public static final BitVector DEGREE_14_6 =
      BitVector.fromBits(1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1);
  public static final BitVector DEGREE_14_7 =
      BitVector.fromBits(1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0);
  public static final BitVector DEGREE_14_8 =
      BitVector.fromBits(1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1);
  public static final BitVector DEGREE_14_9 =
      BitVector.fromBits(1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1);
  public static final BitVector DEGREE_14_10 =
      BitVector.fromBits(1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1);
  public static final BitVector DEGREE_14_11 =
      BitVector.fromBits(1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1);
  public static final BitVector DEGREE_14_12 =
      BitVector.fromBits(1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1);
  public static final BitVector DEGREE_14_13 =
      BitVector.fromBits(1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_15_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
  public static final BitVector DEGREE_15_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
  public static final BitVector DEGREE_15_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
  public static final BitVector DEGREE_15_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1);
  public static final BitVector DEGREE_15_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1);
  public static final BitVector DEGREE_15_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0);
  public static final BitVector DEGREE_15_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1);
  public static final BitVector DEGREE_15_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0);
  public static final BitVector DEGREE_15_8 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0);
  public static final BitVector DEGREE_15_9 =
      BitVector.fromBits(1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1);
  public static final BitVector DEGREE_15_10 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1);
  public static final BitVector DEGREE_15_11 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0);
  public static final BitVector DEGREE_15_12 =
      BitVector.fromBits(1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0);
  public static final BitVector DEGREE_15_13 =
      BitVector.fromBits(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0);
  public static final BitVector DEGREE_16_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0);
  public static final BitVector DEGREE_16_1 =
      BitVector.fromBits(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1);
  public static final BitVector DEGREE_16_2 =
      BitVector.fromBits(1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0);
  public static final BitVector DEGREE_16_3 =
      BitVector.fromBits(1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1);
  public static final BitVector DEGREE_16_4 =
      BitVector.fromBits(1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1);
  public static final BitVector DEGREE_16_5 =
      BitVector.fromBits(1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1);
  public static final BitVector DEGREE_16_6 =
      BitVector.fromBits(1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1);
  public static final BitVector DEGREE_16_7 =
      BitVector.fromBits(1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0);
  public static final BitVector DEGREE_16_8 =
      BitVector.fromBits(1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0);
  public static final BitVector DEGREE_16_9 =
      BitVector.fromBits(1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1);
  public static final BitVector DEGREE_16_10 =
      BitVector.fromBits(1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1);
  public static final BitVector DEGREE_16_11 =
      BitVector.fromBits(1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1);
  public static final BitVector DEGREE_16_12 =
      BitVector.fromBits(1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1);
  public static final BitVector DEGREE_16_13 =
      BitVector.fromBits(1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1);
  public static final BitVector DEGREE_17_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  public static final BitVector DEGREE_17_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1);
  public static final BitVector DEGREE_17_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_17_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
  public static final BitVector DEGREE_17_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0);
  public static final BitVector DEGREE_17_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0);
  public static final BitVector DEGREE_17_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0);
  public static final BitVector DEGREE_17_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1);
  public static final BitVector DEGREE_17_8 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1);
  public static final BitVector DEGREE_17_9 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0);
  public static final BitVector DEGREE_17_10 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1);
  public static final BitVector DEGREE_17_11 =
      BitVector.fromBits(1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0);
  public static final BitVector DEGREE_17_12 =
      BitVector.fromBits(1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1);
  public static final BitVector DEGREE_17_13 =
      BitVector.fromBits(1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1);
  public static final BitVector DEGREE_18_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1);
  public static final BitVector DEGREE_18_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
  public static final BitVector DEGREE_18_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1);
  public static final BitVector DEGREE_18_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1);
  public static final BitVector DEGREE_18_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0);
  public static final BitVector DEGREE_18_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1);
  public static final BitVector DEGREE_18_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1);
  public static final BitVector DEGREE_18_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_18_8 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_18_9 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1);
  public static final BitVector DEGREE_18_10 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0);
  public static final BitVector DEGREE_18_11 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0);
  public static final BitVector DEGREE_18_12 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0);
  public static final BitVector DEGREE_18_13 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0);
  public static final BitVector DEGREE_19_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1);
  public static final BitVector DEGREE_19_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1);
  public static final BitVector DEGREE_19_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1);
  public static final BitVector DEGREE_19_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1);
  public static final BitVector DEGREE_19_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0);
  public static final BitVector DEGREE_19_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1);
  public static final BitVector DEGREE_19_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_19_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1);
  public static final BitVector DEGREE_19_8 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1);
  public static final BitVector DEGREE_19_9 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_19_10 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1);
  public static final BitVector DEGREE_19_11 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1);
  public static final BitVector DEGREE_19_12 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0);
  public static final BitVector DEGREE_19_13 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1);
  public static final BitVector DEGREE_19_14 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1);
  public static final BitVector DEGREE_20_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  public static final BitVector DEGREE_20_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0);
  public static final BitVector DEGREE_20_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0);
  public static final BitVector DEGREE_20_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0);
  public static final BitVector DEGREE_20_4 =
      BitVector.fromBits(1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0);
  public static final BitVector DEGREE_20_5 =
      BitVector.fromBits(1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0);
  public static final BitVector DEGREE_21_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
  public static final BitVector DEGREE_21_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0);
  public static final BitVector DEGREE_21_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0);
  public static final BitVector DEGREE_21_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0);
  public static final BitVector DEGREE_21_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0);
  public static final BitVector DEGREE_21_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0);
  public static final BitVector DEGREE_21_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0);
  public static final BitVector DEGREE_21_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0);
  public static final BitVector DEGREE_21_8 =
      BitVector.fromBits(1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0);
  public static final BitVector DEGREE_22_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
  public static final BitVector DEGREE_22_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1);
  public static final BitVector DEGREE_22_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1);
  public static final BitVector DEGREE_22_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1);
  public static final BitVector DEGREE_22_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1);
  public static final BitVector DEGREE_22_5 =
      BitVector.fromBits(1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1);
  public static final BitVector DEGREE_23_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_23_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1);
  public static final BitVector DEGREE_23_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_23_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0);
  public static final BitVector DEGREE_23_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0);
  public static final BitVector DEGREE_23_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0);
  public static final BitVector DEGREE_23_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_23_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1);
  public static final BitVector DEGREE_23_8 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0);
  public static final BitVector DEGREE_23_9 =
      BitVector.fromBits(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_24_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1);
  public static final BitVector DEGREE_24_1 =
      BitVector.fromBits(1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1);
  public static final BitVector DEGREE_24_2 =
      BitVector.fromBits(1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0);
  public static final BitVector DEGREE_25_0 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  public static final BitVector DEGREE_25_1 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1);
  public static final BitVector DEGREE_25_2 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0);
  public static final BitVector DEGREE_25_3 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0);
  public static final BitVector DEGREE_25_4 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0);
  public static final BitVector DEGREE_25_5 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1);
  public static final BitVector DEGREE_25_6 =
      BitVector.fromBits(1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0);
  public static final BitVector DEGREE_25_7 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0);
  public static final BitVector DEGREE_25_8 =
      BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1);
  public static final BitVector DEGREE_25_9 =
      BitVector.fromBits(1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0);
  public static final BitVector DEGREE_26_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1);
  public static final BitVector DEGREE_26_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0);
  public static final BitVector DEGREE_26_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0);
  public static final BitVector DEGREE_26_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1);
  public static final BitVector DEGREE_26_4 =
      BitVector.fromBits(
          1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1);
  public static final BitVector DEGREE_26_5 =
      BitVector.fromBits(
          1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0);
  public static final BitVector DEGREE_26_6 =
      BitVector.fromBits(
          1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1);
  public static final BitVector DEGREE_27_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1);
  public static final BitVector DEGREE_27_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0);
  public static final BitVector DEGREE_27_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0);
  public static final BitVector DEGREE_27_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
  public static final BitVector DEGREE_27_4 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0);
  public static final BitVector DEGREE_27_5 =
      BitVector.fromBits(
          1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1);
  public static final BitVector DEGREE_27_6 =
      BitVector.fromBits(
          1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0);
  public static final BitVector DEGREE_27_7 =
      BitVector.fromBits(
          1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1);
  public static final BitVector DEGREE_27_8 =
      BitVector.fromBits(
          1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0);
  public static final BitVector DEGREE_28_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
  public static final BitVector DEGREE_28_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0);
  public static final BitVector DEGREE_28_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0);
  public static final BitVector DEGREE_28_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0);
  public static final BitVector DEGREE_28_4 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0);
  public static final BitVector DEGREE_28_5 =
      BitVector.fromBits(
          1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0);
  public static final BitVector DEGREE_29_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
  public static final BitVector DEGREE_29_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0);
  public static final BitVector DEGREE_29_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0);
  public static final BitVector DEGREE_29_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0);
  public static final BitVector DEGREE_29_4 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
  public static final BitVector DEGREE_29_5 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0);
  public static final BitVector DEGREE_29_6 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0);
  public static final BitVector DEGREE_29_7 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0);
  public static final BitVector DEGREE_29_8 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0);
  public static final BitVector DEGREE_29_9 =
      BitVector.fromBits(
          1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0);
  public static final BitVector DEGREE_30_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1);
  public static final BitVector DEGREE_30_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1);
  public static final BitVector DEGREE_30_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1);
  public static final BitVector DEGREE_30_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1);
  public static final BitVector DEGREE_30_4 =
      BitVector.fromBits(
          1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1);
  public static final BitVector DEGREE_31_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
          0);
  public static final BitVector DEGREE_31_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
          1);
  public static final BitVector DEGREE_31_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0,
          0);
  public static final BitVector DEGREE_31_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1,
          0);
  public static final BitVector DEGREE_31_4 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0,
          0);
  public static final BitVector DEGREE_31_5 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0,
          0);
  public static final BitVector DEGREE_31_6 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
          1);
  public static final BitVector DEGREE_31_7 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0,
          0);
  public static final BitVector DEGREE_31_8 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1,
          1);
  public static final BitVector DEGREE_31_9 =
      BitVector.fromBits(
          1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0,
          0);
  public static final BitVector DEGREE_31_10 =
      BitVector.fromBits(
          1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1,
          1);
  public static final BitVector DEGREE_32_0 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          1, 1);
  public static final BitVector DEGREE_32_1 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0,
          0, 1);
  public static final BitVector DEGREE_32_2 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1,
          0, 0);
  public static final BitVector DEGREE_32_3 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0,
          1, 1);
  public static final BitVector DEGREE_32_4 =
      BitVector.fromBits(
          1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0,
          1, 1);
  public static final BitVector DEGREE_32_5 =
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
      throw Exceptions.indexOutOfBoundsException(index, MAXIMAL_TAPS[degreeIndex].length);
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
      DEGREE_2_0,
    },
    {
      DEGREE_3_0,
    },
    {
      DEGREE_4_0,
    },
    {
      DEGREE_5_0, DEGREE_5_1, DEGREE_5_2,
    },
    {
      DEGREE_6_0, DEGREE_6_1, DEGREE_6_2,
    },
    {
      DEGREE_7_0,
      DEGREE_7_1,
      DEGREE_7_2,
      DEGREE_7_3,
      DEGREE_7_4,
      DEGREE_7_5,
      DEGREE_7_6,
      DEGREE_7_7,
      DEGREE_7_8,
    },
    {
      DEGREE_8_0,
      DEGREE_8_1,
      DEGREE_8_2,
      DEGREE_8_3,
      DEGREE_8_4,
      DEGREE_8_5,
      DEGREE_8_6,
      DEGREE_8_7,
    },
    {
      DEGREE_9_0,
      DEGREE_9_1,
      DEGREE_9_2,
      DEGREE_9_3,
      DEGREE_9_4,
      DEGREE_9_5,
      DEGREE_9_6,
      DEGREE_9_7,
      DEGREE_9_8,
      DEGREE_9_9,
      DEGREE_9_10,
      DEGREE_9_11,
      DEGREE_9_12,
      DEGREE_9_13,
    },
    {
      DEGREE_10_0,
      DEGREE_10_1,
      DEGREE_10_2,
      DEGREE_10_3,
      DEGREE_10_4,
      DEGREE_10_5,
      DEGREE_10_6,
      DEGREE_10_7,
      DEGREE_10_8,
      DEGREE_10_9,
      DEGREE_10_10,
      DEGREE_10_11,
      DEGREE_10_12,
      DEGREE_10_13,
    },
    {
      DEGREE_11_0,
      DEGREE_11_1,
      DEGREE_11_2,
      DEGREE_11_3,
      DEGREE_11_4,
      DEGREE_11_5,
      DEGREE_11_6,
      DEGREE_11_7,
      DEGREE_11_8,
      DEGREE_11_9,
      DEGREE_11_10,
      DEGREE_11_11,
      DEGREE_11_12,
      DEGREE_11_13,
    },
    {
      DEGREE_12_0,
      DEGREE_12_1,
      DEGREE_12_2,
      DEGREE_12_3,
      DEGREE_12_4,
      DEGREE_12_5,
      DEGREE_12_6,
      DEGREE_12_7,
      DEGREE_12_8,
      DEGREE_12_9,
      DEGREE_12_10,
      DEGREE_12_11,
      DEGREE_12_12,
      DEGREE_12_13,
    },
    {
      DEGREE_13_0,
      DEGREE_13_1,
      DEGREE_13_2,
      DEGREE_13_3,
      DEGREE_13_4,
      DEGREE_13_5,
      DEGREE_13_6,
      DEGREE_13_7,
      DEGREE_13_8,
      DEGREE_13_9,
      DEGREE_13_10,
      DEGREE_13_11,
      DEGREE_13_12,
      DEGREE_13_13,
    },
    {
      DEGREE_14_0,
      DEGREE_14_1,
      DEGREE_14_2,
      DEGREE_14_3,
      DEGREE_14_4,
      DEGREE_14_5,
      DEGREE_14_6,
      DEGREE_14_7,
      DEGREE_14_8,
      DEGREE_14_9,
      DEGREE_14_10,
      DEGREE_14_11,
      DEGREE_14_12,
      DEGREE_14_13,
    },
    {
      DEGREE_15_0,
      DEGREE_15_1,
      DEGREE_15_2,
      DEGREE_15_3,
      DEGREE_15_4,
      DEGREE_15_5,
      DEGREE_15_6,
      DEGREE_15_7,
      DEGREE_15_8,
      DEGREE_15_9,
      DEGREE_15_10,
      DEGREE_15_11,
      DEGREE_15_12,
      DEGREE_15_13,
    },
    {
      DEGREE_16_0,
      DEGREE_16_1,
      DEGREE_16_2,
      DEGREE_16_3,
      DEGREE_16_4,
      DEGREE_16_5,
      DEGREE_16_6,
      DEGREE_16_7,
      DEGREE_16_8,
      DEGREE_16_9,
      DEGREE_16_10,
      DEGREE_16_11,
      DEGREE_16_12,
      DEGREE_16_13,
    },
    {
      DEGREE_17_0,
      DEGREE_17_1,
      DEGREE_17_2,
      DEGREE_17_3,
      DEGREE_17_4,
      DEGREE_17_5,
      DEGREE_17_6,
      DEGREE_17_7,
      DEGREE_17_8,
      DEGREE_17_9,
      DEGREE_17_10,
      DEGREE_17_11,
      DEGREE_17_12,
      DEGREE_17_13,
    },
    {
      DEGREE_18_0,
      DEGREE_18_1,
      DEGREE_18_2,
      DEGREE_18_3,
      DEGREE_18_4,
      DEGREE_18_5,
      DEGREE_18_6,
      DEGREE_18_7,
      DEGREE_18_8,
      DEGREE_18_9,
      DEGREE_18_10,
      DEGREE_18_11,
      DEGREE_18_12,
      DEGREE_18_13,
    },
    {
      DEGREE_19_0,
      DEGREE_19_1,
      DEGREE_19_2,
      DEGREE_19_3,
      DEGREE_19_4,
      DEGREE_19_5,
      DEGREE_19_6,
      DEGREE_19_7,
      DEGREE_19_8,
      DEGREE_19_9,
      DEGREE_19_10,
      DEGREE_19_11,
      DEGREE_19_12,
      DEGREE_19_13,
      DEGREE_19_14,
    },
    {
      DEGREE_20_0, DEGREE_20_1, DEGREE_20_2, DEGREE_20_3, DEGREE_20_4, DEGREE_20_5,
    },
    {
      DEGREE_21_0,
      DEGREE_21_1,
      DEGREE_21_2,
      DEGREE_21_3,
      DEGREE_21_4,
      DEGREE_21_5,
      DEGREE_21_6,
      DEGREE_21_7,
      DEGREE_21_8,
    },
    {
      DEGREE_22_0, DEGREE_22_1, DEGREE_22_2, DEGREE_22_3, DEGREE_22_4, DEGREE_22_5,
    },
    {
      DEGREE_23_0,
      DEGREE_23_1,
      DEGREE_23_2,
      DEGREE_23_3,
      DEGREE_23_4,
      DEGREE_23_5,
      DEGREE_23_6,
      DEGREE_23_7,
      DEGREE_23_8,
      DEGREE_23_9,
    },
    {
      DEGREE_24_0, DEGREE_24_1, DEGREE_24_2,
    },
    {
      DEGREE_25_0,
      DEGREE_25_1,
      DEGREE_25_2,
      DEGREE_25_3,
      DEGREE_25_4,
      DEGREE_25_5,
      DEGREE_25_6,
      DEGREE_25_7,
      DEGREE_25_8,
      DEGREE_25_9,
    },
    {
      DEGREE_26_0, DEGREE_26_1, DEGREE_26_2, DEGREE_26_3, DEGREE_26_4, DEGREE_26_5, DEGREE_26_6,
    },
    {
      DEGREE_27_0,
      DEGREE_27_1,
      DEGREE_27_2,
      DEGREE_27_3,
      DEGREE_27_4,
      DEGREE_27_5,
      DEGREE_27_6,
      DEGREE_27_7,
      DEGREE_27_8,
    },
    {
      DEGREE_28_0, DEGREE_28_1, DEGREE_28_2, DEGREE_28_3, DEGREE_28_4, DEGREE_28_5,
    },
    {
      DEGREE_29_0,
      DEGREE_29_1,
      DEGREE_29_2,
      DEGREE_29_3,
      DEGREE_29_4,
      DEGREE_29_5,
      DEGREE_29_6,
      DEGREE_29_7,
      DEGREE_29_8,
      DEGREE_29_9,
    },
    {
      DEGREE_30_0, DEGREE_30_1, DEGREE_30_2, DEGREE_30_3, DEGREE_30_4,
    },
    {
      DEGREE_31_0,
      DEGREE_31_1,
      DEGREE_31_2,
      DEGREE_31_3,
      DEGREE_31_4,
      DEGREE_31_5,
      DEGREE_31_6,
      DEGREE_31_7,
      DEGREE_31_8,
      DEGREE_31_9,
      DEGREE_31_10,
    },
    {
      DEGREE_32_0, DEGREE_32_1, DEGREE_32_2, DEGREE_32_3, DEGREE_32_4, DEGREE_32_5,
    }
  };
}
