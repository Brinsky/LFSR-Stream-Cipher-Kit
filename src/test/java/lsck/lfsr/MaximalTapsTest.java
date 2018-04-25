package lsck.lfsr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import lsck.bitwise.BitVector;

public class MaximalTapsTest {

  @ParameterizedTest
  @ValueSource(ints = {-1, MaximalTaps.MIN_LENGTH - 1, MaximalTaps.MAX_LENGTH + 1})
  void testMaximalTapsCount_invalidLength(int length) {
    Exception e =
        assertThrows(IllegalArgumentException.class, () -> MaximalTaps.maximalTapsCount(length));

    assertEquals(
        "Length should be between "
            + MaximalTaps.MIN_LENGTH
            + " and "
            + MaximalTaps.MAX_LENGTH
            + ", inclusive; got "
            + length,
        e.getMessage());
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, MaximalTaps.MIN_LENGTH - 1, MaximalTaps.MAX_LENGTH + 1})
  void testGetMaximalTaps_invalidLength(int length) {
    Exception e =
        assertThrows(IllegalArgumentException.class, () -> MaximalTaps.getMaximalTaps(length, 0));

    assertEquals(
        "Length should be between "
            + MaximalTaps.MIN_LENGTH
            + " and "
            + MaximalTaps.MAX_LENGTH
            + ", inclusive; got "
            + length,
        e.getMessage());
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 1000})
  void testMaximalTapsCount_invalidIndex(int index) {
    int length = MaximalTaps.MIN_LENGTH;

    Exception e =
        assertThrows(
            IndexOutOfBoundsException.class, () -> MaximalTaps.getMaximalTaps(length, index));

    assertEquals(
        "Expected a nonnegative index less than "
            + MaximalTaps.maximalTapsCount(length)
            + "; got "
            + index,
        e.getMessage());
  }

  static Stream<Arguments> testGetMaximalTaps_args() {
    return Stream.of(
        Arguments.of(2, 0, MaximalTaps.DEGREE_2_0),
        Arguments.of(3, 0, MaximalTaps.DEGREE_3_0),
        Arguments.of(4, 0, MaximalTaps.DEGREE_4_0),
        Arguments.of(5, 0, MaximalTaps.DEGREE_5_0),
        Arguments.of(6, 0, MaximalTaps.DEGREE_6_0),
        Arguments.of(7, 0, MaximalTaps.DEGREE_7_0),
        Arguments.of(8, 0, MaximalTaps.DEGREE_8_0),
        Arguments.of(9, 0, MaximalTaps.DEGREE_9_0),
        Arguments.of(10, 0, MaximalTaps.DEGREE_10_0),
        Arguments.of(11, 0, MaximalTaps.DEGREE_11_0),
        Arguments.of(12, 0, MaximalTaps.DEGREE_12_0),
        Arguments.of(13, 0, MaximalTaps.DEGREE_13_0),
        Arguments.of(14, 0, MaximalTaps.DEGREE_14_0),
        Arguments.of(15, 0, MaximalTaps.DEGREE_15_0),
        Arguments.of(16, 0, MaximalTaps.DEGREE_16_0),
        Arguments.of(17, 0, MaximalTaps.DEGREE_17_0),
        Arguments.of(18, 0, MaximalTaps.DEGREE_18_0),
        Arguments.of(19, 0, MaximalTaps.DEGREE_19_0),
        Arguments.of(20, 0, MaximalTaps.DEGREE_20_0),
        Arguments.of(21, 0, MaximalTaps.DEGREE_21_0),
        Arguments.of(22, 0, MaximalTaps.DEGREE_22_0),
        Arguments.of(23, 0, MaximalTaps.DEGREE_23_0),
        Arguments.of(24, 0, MaximalTaps.DEGREE_24_0),
        Arguments.of(25, 0, MaximalTaps.DEGREE_25_0),
        Arguments.of(26, 0, MaximalTaps.DEGREE_26_0),
        Arguments.of(27, 0, MaximalTaps.DEGREE_27_0),
        Arguments.of(28, 0, MaximalTaps.DEGREE_28_0),
        Arguments.of(29, 0, MaximalTaps.DEGREE_29_0),
        Arguments.of(30, 0, MaximalTaps.DEGREE_30_0),
        Arguments.of(31, 0, MaximalTaps.DEGREE_31_0),
        Arguments.of(32, 0, MaximalTaps.DEGREE_32_0));
  }

  @ParameterizedTest
  @MethodSource("testGetMaximalTaps_args")
  void testGetMaximalTaps(int length, int index, BitVector taps) {
    assertEquals(taps, MaximalTaps.getMaximalTaps(length, index));
  }
}
