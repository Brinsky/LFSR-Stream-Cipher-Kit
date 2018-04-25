package lsck.bitwise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/** Tests for subclasses of {@link BitVector}. */
@TestInstance(Lifecycle.PER_CLASS)
public abstract class BaseBitVectorTest {

  /*
   * MSB to LSB:
   * 1011 0011 0101 1011
   * 0011 0101 1011 0011
   * 0101 1011 0011 0101
   * 1011 0011 0101 1011
   */
  protected static final int[] TEST_BITS =
      new int[] {
        1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1,
        1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0,
        1, 1
      };
  protected static final int[] TEST_BITS_REVERSED =
      new int[] {
        1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1,
        0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1,
        0, 1
      };
  protected static final long TEST_LONG = 0xB35B35B35B35B35BL;

  protected static final BitSet TEST_BITSET = BitSet.valueOf(new long[] {TEST_LONG});
  protected static final int NUM_BITS = TEST_BITS.length;

  // Instance provider methods for subclasses to implement

  protected abstract BitVector newVector();

  protected abstract BitVector newTruncatedVector(int length);

  protected abstract BitVector newVectorFromBits();

  protected abstract BitVector newVectorFromLong();

  protected abstract BitVector newZeroVector();

  protected abstract BitVector newReversedVector();

  // Proxy for static method
  protected abstract Iterable<? extends BitVector> allVectors(int length);

  /** Creates a new {@link BitVector} representing the given bits. */
  protected abstract BitVector create(int... bits);

  @Test
  /** Verifies that our differently-formatted test bits are equivalent. */
  final void matchingBitsTest() {
    assertEquals(Long.SIZE, TEST_BITS.length);

    for (int i = 0; i < Long.SIZE; i++) {
      assertEquals((TEST_LONG >>> i) & 1, TEST_BITS[TEST_BITS.length - 1 - i]);
    }
  }

  @Test
  void testGetLength() {
    assertEquals(NUM_BITS, newZeroVector().getLength());
    assertEquals(NUM_BITS, newVector().getLength());
    assertEquals(NUM_BITS, newVectorFromBits().getLength());
    assertEquals(NUM_BITS, newVectorFromLong().getLength());
  }

  @Test
  void testZeroVector() {
    BitVector emptyVector = newZeroVector();

    assertEquals(new BitSet(0), emptyVector.toBitSet());
    assertEquals(0, newZeroVector().toLong());
  }

  @Test
  void testConstructorEquivalence() {
    assertEquals(TEST_BITSET, newVector().toBitSet());
    assertEquals(TEST_BITSET, newVectorFromBits().toBitSet());
    assertEquals(TEST_BITSET, newVectorFromLong().toBitSet());
  }

  @Test
  void testGetBit_outOfBounds_below() {
    assertThrows(
        IndexOutOfBoundsException.class,
        () -> newVector().get(-1),
        "Expected index between 0 and " + NUM_BITS + ", got -1");
  }

  @Test
  void testGetBit_outOfBounds_above() {
    assertThrows(
        IndexOutOfBoundsException.class,
        () -> newVector().get(NUM_BITS),
        "Expected index between 0 and " + NUM_BITS + ", got " + NUM_BITS);
  }

  @Test
  void testGetBit() {
    BitVector vector = newVector();

    for (int i = 0; i < vector.getLength(); i++) {
      assertEquals(TEST_BITS[vector.getLength() - 1 - i], vector.get(i));
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 6, 8})
  void testToByte_valid(int length) {
    assertEquals((byte) (lowerMask(length) & TEST_LONG), newTruncatedVector(length).toByte());
  }

  @ParameterizedTest
  @ValueSource(ints = {9})
  void testToByte_invalid(int length) {
    assertThrows(
        IllegalArgumentException.class,
        () -> newTruncatedVector(length).toByte(),
        "Vector of length " + length + " exceeds length of byte");
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 14, 16})
  void testToShort_valid(int length) {
    assertEquals((short) (lowerMask(length) & TEST_LONG), newTruncatedVector(length).toShort());
  }

  @ParameterizedTest
  @ValueSource(ints = {17})
  void testToShort_invalid(int length) {
    assertThrows(
        IllegalArgumentException.class,
        () -> newTruncatedVector(length).toShort(),
        "Vector of length " + length + " exceeds length of short");
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 30, 32})
  void testToInt_valid(int length) {
    assertEquals((int) (lowerMask(length) & TEST_LONG), newTruncatedVector(length).toInt());
  }

  @ParameterizedTest
  @ValueSource(ints = {33})
  void testToInt_invalid(int length) {
    assertThrows(
        IllegalArgumentException.class,
        () -> newTruncatedVector(length).toInt(),
        "Vector of length " + length + " exceeds length of int");
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 62, 64})
  void testToLong_valid(int length) {
    assertEquals(lowerMask(length) & TEST_LONG, newTruncatedVector(length).toLong());
  }

  @Test
  void testToBitSet() {
    assertEquals(TEST_BITSET.get(0, NUM_BITS), newVector().toBitSet());
  }

  @Test
  void testReverse() {
    assertEquals(newReversedVector(), newVector().reverse());
  }

  @ParameterizedTest
  @MethodSource("getTestIncrementArgs")
  void testIncrement(BitVector preIncrement, BitVector postIncrement) {
    assertEquals(postIncrement, preIncrement.increment());
  }

  Stream<Arguments> getTestAndArgs() {
    return Stream.of(
        Arguments.of(create(0, 0, 0, 0, 0), create(1, 0, 1, 0, 1), create(0, 1, 0, 1, 0)),
        Arguments.of(create(0, 0, 1, 1, 1), create(0, 0, 1, 1, 1), create(1, 1, 1, 1, 1)),
        Arguments.of(create(0, 1, 0, 1, 0), create(1, 1, 1, 1, 1), create(0, 1, 0, 1, 0)));
  }

  @ParameterizedTest
  @MethodSource("getTestAndArgs")
  void testAnd(BitVector expected, BitVector a, BitVector b) {
    assertEquals(expected, a.and(b));
    assertEquals(expected, b.and(a));
  }

  Stream<Arguments> getTestOrArgs() {
    return Stream.of(
        Arguments.of(create(1, 1, 1, 1, 1), create(1, 0, 1, 0, 1), create(0, 1, 0, 1, 0)),
        Arguments.of(create(0, 1, 0, 1, 0), create(0, 0, 0, 0, 0), create(0, 1, 0, 1, 0)),
        Arguments.of(create(1, 1, 1, 1, 1), create(1, 1, 1, 1, 1), create(0, 1, 0, 1, 0)));
  }

  @ParameterizedTest
  @MethodSource("getTestOrArgs")
  void testOr(BitVector expected, BitVector a, BitVector b) {
    assertEquals(expected, a.or(b));
    assertEquals(expected, b.or(a));
  }

  Stream<Arguments> getTestXorArgs() {
    return Stream.of(
        Arguments.of(create(1, 1, 1, 1, 1), create(1, 0, 1, 0, 1), create(0, 1, 0, 1, 0)),
        Arguments.of(create(1, 1, 0, 0, 0), create(0, 0, 1, 1, 1), create(1, 1, 1, 1, 1)),
        Arguments.of(create(1, 0, 1, 0, 1), create(1, 1, 1, 1, 1), create(0, 1, 0, 1, 0)));
  }

  @ParameterizedTest
  @MethodSource("getTestXorArgs")
  void testXor(BitVector expected, BitVector a, BitVector b) {
    assertEquals(expected, a.xor(b));
    assertEquals(expected, b.xor(a));
  }

  Stream<Arguments> getTestNotArgs() {
    return Stream.of(
        Arguments.of(create(0, 1, 0, 1, 0), create(1, 0, 1, 0, 1)),
        Arguments.of(create(1, 1, 0, 0, 0), create(0, 0, 1, 1, 1)),
        Arguments.of(create(0, 0, 0, 0, 0), create(1, 1, 1, 1, 1)));
  }

  @ParameterizedTest
  @MethodSource("getTestNotArgs")
  void testNot(BitVector expected, BitVector toNegate) {
    assertEquals(expected, toNegate.not());
    assertEquals(toNegate, expected.not());
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 5, 10})
  void testAllVectors(int length) {
    Set<BitVector> vectors = new HashSet<>(1 << length);

    int i = 0;
    for (BitVector vector : allVectors(length)) {
      vectors.add(vector);
      i++;
    }

    // Assert we found 2^length unique vectors with no repeats
    assertEquals(1 << length, i);
    assertEquals(1 << length, vectors.size());
  }

  protected final long lowerMask(int bits) {
    // Java mods shift sizes by Type.SIZE, so shifts >= Type.SIZE don't work.
    if (bits >= Long.SIZE) {
      return ~0L;
    } else {
      return ~((~0L) << bits);
    }
  }
}
