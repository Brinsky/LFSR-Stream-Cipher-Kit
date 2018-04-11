package lsck.bitwise;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public class LongBitVectorTest extends BaseBitVectorTest {

  private static final long MAX_UNSIGNED = BitUtility.lowerBitmask(Long.SIZE);

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

  protected static Stream<Arguments> getTestIncrementArgs() {
    return Stream.of(
        Arguments.of(BitVector.fromBits(0, 0, 0, 0, 0, 0), BitVector.fromBits(0, 0, 0, 0, 0, 1)),
        Arguments.of(BitVector.fromBits(0, 1, 1, 1, 1, 1), BitVector.fromBits(1, 0, 0, 0, 0, 0)),
        Arguments.of(BitVector.fromBits(1, 1, 1, 1, 1, 1), BitVector.fromBits(0, 0, 0, 0, 0, 0)),
        Arguments.of(BitVector.fromInteger(64, MAX_UNSIGNED), BitVector.fromInteger(64, 0)));
  }
}
