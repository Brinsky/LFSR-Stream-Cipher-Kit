package lsck.bitwise;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.BitSet;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class BitUtilityTest {

  private static final int[] TEST_BITS =
      new int[] {0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1};
  private static final BitVector TEST_VECTOR = BitVector.fromBits(TEST_BITS);
  private static final BitList TEST_BIT_LIST = buildBitList(TEST_BITS);
  private static final String MSB_STRING = "000101101101101001110101101";
  private static final String LSB_STRING = new StringBuilder(MSB_STRING).reverse().toString();

  private final long[] TEST_LONG_ARRAY = new long[] {0xABCD1234ABCD1234L, 0x9876FEDC9876FEDCL};

  private static final long LONG_PRE_REVERSE = 0x1234ABCD1234ABCDL;
  private static final long LONG_POST_REVERSE = 0xB3D52C48B3D52C48L;

  private static BitList buildBitList(int... bits) {
    BitList list = new BitList(bits.length);

    for (int i = 0; i < bits.length; i++) {
      list.add(bits[i]);
    }

    return list;
  }

  /** Test values for {@link BitUtility#getBit}. */
  static Stream<Arguments> getBitProvider() {
    return Stream.of(
        Arguments.of(1L, 0, 1),
        Arguments.of(1L << 30, 30, 1),
        Arguments.of(0xABCD1234ABCD1234L, 29, 1),
        Arguments.of(0xABCD1234ABCD1234L, 30, 0),
        Arguments.of(1L << 63, 63, 1),
        Arguments.of(~0L >>> 1, 63, 0));
  }

  /** Test values for {@link BitUtility#setBit}. */
  static Stream<Arguments> setBitProvider() {
    return Stream.of(
        Arguments.of(0L, 0, 1, 1L),
        Arguments.of(0L, 0, 0, 0L),
        Arguments.of(0L, 63, 1, 1L << 63),
        Arguments.of(0L, 63, 0, 0L),
        Arguments.of(1L, 0, 1, 1L),
        Arguments.of(1L, 0, 0, 0L),
        Arguments.of(~0L, 30, 1, ~0L),
        Arguments.of(~0L, 30, 0, ~(1L << 30)),
        Arguments.of(~0L, 63, 1, ~0L),
        Arguments.of(~0L, 63, 0, ~0L >>> 1));
  }

  @ParameterizedTest
  @MethodSource("getBitProvider")
  void testGetBit_validIndex(long vector, int index, int expected) {
    assertEquals(expected, BitUtility.getBit(vector, index));
  }

  @ParameterizedTest
  @MethodSource("setBitProvider")
  void testSetBit_validIndex(long vector, int index, int value, long expected) {
    assertEquals(expected, BitUtility.setBit(vector, index, value));
  }

  @ParameterizedTest
  @ValueSource(ints = {-10, -1, 64, 100})
  void testGetBit_invalidIndex(int index) {
    IndexOutOfBoundsException e =
        assertThrows(IndexOutOfBoundsException.class, () -> BitUtility.getBit(~0L, index));

    assertEquals("Expected a nonnegative index less than " + 64 + "; got " + index, e.getMessage());
  }

  @ParameterizedTest
  @ValueSource(ints = {-10, -1, 64, 100})
  void testSetBit_invalidIndex(int index) {
    IndexOutOfBoundsException e =
        assertThrows(IndexOutOfBoundsException.class, () -> BitUtility.setBit(0L, index, 1));

    assertEquals("Expected a nonnegative index less than " + 64 + "; got " + index, e.getMessage());
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 128, 200})
  void testGetBitArray_invalidIndex(int index) {
    IndexOutOfBoundsException e =
        assertThrows(
            IndexOutOfBoundsException.class, () -> BitUtility.getBit(TEST_LONG_ARRAY, index));

    assertEquals(
        "Expected a nonnegative index less than "
            + (TEST_LONG_ARRAY.length * Long.SIZE)
            + "; got "
            + index,
        e.getMessage());
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 128, 200})
  void testSetBitArray_invalidIndex(int index) {
    IndexOutOfBoundsException e =
        assertThrows(
            IndexOutOfBoundsException.class, () -> BitUtility.setBit(TEST_LONG_ARRAY, index, 1));

    assertEquals(
        "Expected a nonnegative index less than "
            + (TEST_LONG_ARRAY.length * Long.SIZE)
            + "; got "
            + index,
        e.getMessage());
  }

  @Test
  void testGetBit_array_validIndex() {
    for (int i = 0; i < TEST_LONG_ARRAY.length; i++) {
      for (int j = 0; j < Long.SIZE; j++) {
        assertEquals(
            (TEST_LONG_ARRAY[i] >>> j) & 1, BitUtility.getBit(TEST_LONG_ARRAY, i * Long.SIZE + j));
      }
    }
  }

  /** Test values for {@link BitUtility#setBit(long[],int)}. */
  static Stream<Arguments> setBitArrayProvider() {
    return Stream.of(
        Arguments.of(0, 1, new long[] {0xABCD1234ABCD1235L, 0x9876FEDC9876FEDCL}),
        Arguments.of(63, 0, new long[] {0x2BCD1234ABCD1234L, 0x9876FEDC9876FEDCL}),
        Arguments.of(64, 1, new long[] {0xABCD1234ABCD1234L, 0x9876FEDC9876FEDDL}),
        Arguments.of(127, 0, new long[] {0xABCD1234ABCD1234L, 0x1876FEDC9876FEDCL}));
  }

  @ParameterizedTest
  @MethodSource("setBitArrayProvider")
  void testSetBit_array_validIndex(int index, int value, long[] expected) {
    BitUtility.setBit(TEST_LONG_ARRAY, index, value);
    assertArrayEquals(expected, TEST_LONG_ARRAY);
  }

  @Test
  void testListFromBits_nonEmpty() {
    List<Byte> list = BitUtility.listFromBits(TEST_BITS);

    for (int i = 0; i < TEST_BITS.length; i++) {
      assertEquals(TEST_BITS[i], (int) list.get(i), "Difference at index " + i);
    }
  }

  @Test
  void testListFromBits_empty() {
    List<Byte> list = BitUtility.listFromBits();
    assertTrue(list.isEmpty());
  }

  @Test
  void testBitToChar_zero() {
    assertEquals('0', BitUtility.bitToChar(0));
  }

  @ParameterizedTest
  @ValueSource(ints = {1, -1, -100, 100})
  void testBitToChar_nonZero(int value) {
    assertEquals('1', BitUtility.bitToChar(value));
  }

  @Test
  void testAsBit_zero() {
    assertEquals(0, BitUtility.asBit(0));
  }

  @ParameterizedTest
  @ValueSource(ints = {1, -1, -100, 100})
  void testAsBit_nonZero(int value) {
    assertEquals(1, BitUtility.asBit(value));
  }

  @Test
  void testBitString_bitVector_default() {
    assertEquals(MSB_STRING, BitUtility.bitString(TEST_VECTOR));
  }

  @Test
  void testBitString_bitList_default() {
    assertEquals(MSB_STRING, BitUtility.bitString(TEST_BIT_LIST));
  }

  @Test
  void testBitString_bitVector_ascending() {
    assertEquals(LSB_STRING, BitUtility.bitString(TEST_VECTOR, true, ""));
  }

  @Test
  void testBitString_bitList_ascending() {
    assertEquals(MSB_STRING, BitUtility.bitString(TEST_BIT_LIST, true, ""));
  }

  @Test
  void testBitString_bitVector_descending() {
    assertEquals(MSB_STRING, BitUtility.bitString(TEST_VECTOR, false, ""));
  }

  @Test
  void testBitString_bitList_descending() {
    assertEquals(LSB_STRING, BitUtility.bitString(TEST_BIT_LIST, false, ""));
  }

  @Test
  void testBitString_bitVector_ascendingDelimiter() {
    assertEquals(
        LSB_STRING.replaceAll("(\\d)(?=\\d)", "$1-"), BitUtility.bitString(TEST_VECTOR, true, "-"));
  }

  @Test
  void testBitString_bitList_ascendingDelimiter() {
    assertEquals(
        MSB_STRING.replaceAll("(\\d)(?=\\d)", "$1-"),
        BitUtility.bitString(TEST_BIT_LIST, true, "-"));
  }

  @Test
  void testBitString_bitVector_descendingDelimiter() {
    assertEquals(
        MSB_STRING.replaceAll("(\\d)(?=\\d)", "$1-"),
        BitUtility.bitString(TEST_VECTOR, false, "-"));
  }

  @Test
  void testBitString_bitList_descendingDelimiter() {
    assertEquals(
        LSB_STRING.replaceAll("(\\d)(?=\\d)", "$1-"),
        BitUtility.bitString(TEST_BIT_LIST, false, "-"));
  }

  @Test
  void testBitString_bitList_emptyVector() {
    assertTrue(BitUtility.bitString(new BitList()).isEmpty());
  }

  @Test
  void testReverse_fullLength() {
    assertEquals(LONG_POST_REVERSE, BitUtility.reverse(Long.SIZE, LONG_PRE_REVERSE));
  }

  @Test
  void testReverse_halfLength() {
    int length = 32;
    long halfMask = 0xFFFFFFFF;
    long postReverse = LONG_POST_REVERSE >>> length;
    assertEquals(postReverse & halfMask, BitUtility.reverse(length, LONG_PRE_REVERSE & halfMask));
  }

  @Test
  void testBitSetToLongArray_allZeros() {
    int numZeros = 1000;

    BitSet b = new BitSet(numZeros);
    long[] array = BitUtility.bitSetToLongArray(numZeros, b);

    assertEquals(divideAndRoundUp(numZeros, Long.SIZE), array.length);

    for (int i = 0; i < array.length; i++) {
      assertEquals(0, array[i], "Long at index " + i + " is nonzero");
    }
  }

  @Test
  void testBitSetToLongArray() {
    for (int i = 1; i < TEST_LONG_ARRAY.length * Long.SIZE; i += 8) {
      assertUnchangedByBitSetToLongArray(i, TEST_LONG_ARRAY);
    }
  }

  void assertUnchangedByBitSetToLongArray(int numBits, long[] source) {
    // Use a BitVector to truncate higher-order bits
    BitSet b = BitVector.fromBitSet(numBits, BitSet.valueOf(source)).toBitSet();
    long[] result = BitUtility.bitSetToLongArray(numBits, b);

    assertEquals(divideAndRoundUp(numBits, Long.SIZE), result.length);

    for (int i = 0; i < numBits; i++) {
      assertEquals(
          BitUtility.getBit(source, i), BitUtility.getBit(result, i), "Differs at index " + i);
    }
  }

  private static int divideAndRoundUp(int a, int b) {
    return a / b + (a % b == 0 ? 0 : 1);
  }
}
