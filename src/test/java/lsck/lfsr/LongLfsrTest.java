package lsck.lfsr;

import lsck.bitwise.BitList;
import lsck.bitwise.BitVector;

public class LongLfsrTest extends BaseLfsrTest {

  private static final int TEST_LENGTH = 64;
  private static final BitVector TEST_FILL =
      BitVector.fromBits(
          1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0,
          0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1,
          1, 0, 1, 0);
  private static final BitVector TEST_TAPS =
      BitVector.fromBits(
          1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0,
          1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1,
          0, 1, 0, 1);
  private static final BitList EXPECTED_OUTPUT =
      BitList.fromBits(
          1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0,
          0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1,
          1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1,
          0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1,
          1, 0, 1, 0, 0, 1, 1, 0);
  private static final BitVector EXPECTED_FINAL_FILL =
      BitVector.fromBits(
          1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0,
          0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1,
          1, 1, 0, 0);
  private Lfsr TEST_LFSR = new LongLfsr(TEST_LENGTH, TEST_TAPS, TEST_FILL);

  @Override
  Lfsr createLfsr(int length) {
    return new LongLfsr(length);
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
}
