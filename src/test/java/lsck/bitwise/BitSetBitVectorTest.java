package lsck.bitwise;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BitSetBitVectorTest extends BaseBitVectorTest {

  @Override
  protected BitVector newVector() {
    return new BitSetBitVector(NUM_BITS, TEST_BITSET);
  }

  @Override
  protected BitVector newTruncatedVector(int length) {
    return new BitSetBitVector(length, TEST_BITSET);
  }

  @Override
  protected BitVector newVectorFromBits() {
    return new BitSetBitVector(TEST_BITS);
  }

  @Override
  protected BitVector newVectorFromLong() {
    return new BitSetBitVector(NUM_BITS, TEST_LONG);
  }

  @Override
  protected BitVector newEmptyVector() {
    return new BitSetBitVector(NUM_BITS);
  }

  @Override
  protected BitVector newReversedVector() {
    return new BitSetBitVector(TEST_BITS_REVERSED);
  }
  
  @ParameterizedTest
  @ValueSource(ints = {65})
  void toLongTest_invalid(int length) {
    assertThrows(
        IllegalArgumentException.class,
        () -> newTruncatedVector(length).toLong(),
        "Vector of length " + length + " exceeds length of long");
  }
}
