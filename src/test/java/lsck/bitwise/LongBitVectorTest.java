package lsck.bitwise;

import lsck.bitwise.BitVector;
import lsck.bitwise.LongBitVector;

public class LongBitVectorTest extends BaseBitVectorTest {

  @Override
  protected BitVector newVector() {
    return new LongBitVector(NUM_BITS, TEST_BITSET);
  }

  @Override
  protected BitVector newTruncatedVector(int length) {
    return new LongBitVector(length, TEST_BITSET);
  }

  @Override
  protected BitVector newVectorFromBits() {
    return new LongBitVector(TEST_BITS);
  }

  @Override
  protected BitVector newVectorFromLong() {
    return new LongBitVector(NUM_BITS, TEST_LONG);
  }

  @Override
  protected BitVector newEmptyVector() {
    return new LongBitVector(NUM_BITS);
  }

  @Override
  protected BitVector newReversedVector() {
    return new LongBitVector(TEST_BITS_REVERSED);
  }
}
