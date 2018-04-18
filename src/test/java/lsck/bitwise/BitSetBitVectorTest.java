package lsck.bitwise;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.BitSet;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
  protected BitVector newZeroVector() {
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

  protected static Stream<Arguments> getTestIncrementArgs() {
    int largeSize = 70;

    BitSet allOnes = new BitSet(largeSize);
    allOnes.set(0, largeSize);

    BitSet allOnesButLeading = new BitSet(largeSize);
    allOnesButLeading.set(0, largeSize - 1);

    BitSet leadingOne = new BitSet(largeSize);
    leadingOne.set(largeSize - 1);

    return Stream.of(
        Arguments.of(BitVector.fromBits(0, 0, 0, 0, 0, 0), BitVector.fromBits(0, 0, 0, 0, 0, 1)),
        Arguments.of(BitVector.fromBits(0, 1, 1, 1, 1, 1), BitVector.fromBits(1, 0, 0, 0, 0, 0)),
        Arguments.of(BitVector.fromBits(1, 1, 1, 1, 1, 1), BitVector.fromBits(0, 0, 0, 0, 0, 0)),
        Arguments.of(BitVector.fromBitSet(largeSize, allOnes), BitVector.zeroVector(largeSize)),
        Arguments.of(
            BitVector.fromBitSet(largeSize, allOnesButLeading),
            BitVector.fromBitSet(largeSize, leadingOne)));
  }

  @Override
  protected BitVector create(int... bits) {
    return new BitSetBitVector(bits);
  }

  @Override
  protected Iterable<? extends BitVector> allVectors(int length) {
    return BitSetBitVector.allVectors(length);
  }
}
