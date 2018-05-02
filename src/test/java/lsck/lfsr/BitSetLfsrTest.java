package lsck.lfsr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lsck.bitwise.BitList;
import lsck.bitwise.BitVector;

public class BitSetLfsrTest extends BaseLfsrTest {

  private static final int TEST_LENGTH = 30;
  private static final BitVector TEST_FILL =
      BitVector.fromBits(
          0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1);
  private static final BitVector TEST_TAPS =
      BitVector.fromBits(
          0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1);
  private static final BitList EXPECTED_OUTPUT =
      BitList.fromBits(
          0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1,
          0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1);
  private static final BitVector EXPECTED_FINAL_FILL =
      BitVector.fromBits(
          1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
  private Lfsr TEST_LFSR = new BitSetLfsr(TEST_TAPS, TEST_FILL);

  @Override
  Lfsr createLfsr(int length) {
    return new BitSetLfsr(length);
  }

  @Override
  Lfsr getLfsr() {
    return TEST_LFSR;
  }

  @Override
  int getExpectedLength() {
    return TEST_LENGTH;
  }

  @Override
  BitVector getTestFill() {
    return TEST_FILL;
  }

  @Override
  BitVector getTestTaps() {
    return TEST_TAPS;
  }

  @Override
  BitList getExpectedOutput() {
    return EXPECTED_OUTPUT;
  }

  @Override
  BitVector getExpectedFinalFill() {
    return EXPECTED_FINAL_FILL;
  }

  @Test
  void testConstructorEquivalence() {
    assertEquals(
        new BitSetLfsr(TEST_TAPS, BitVector.zeroVector(TEST_LENGTH)), new BitSetLfsr(TEST_TAPS));
    assertEquals(
        new BitSetLfsr(BitVector.zeroVector(TEST_LENGTH), BitVector.zeroVector(TEST_LENGTH)),
        new BitSetLfsr(TEST_LENGTH));
  }
}
