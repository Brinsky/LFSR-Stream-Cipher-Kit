package lsck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.BitSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BitSetBitVectorTest {

	/* 
	 * MSB to LSB:
	 * 1011 0011 0101 1011
	 * 0011 0101 1011 0011
	 * 0101 1011 0011 0101
	 * 1011 0011 0101 1011
	 */
	private static final int[] TEST_BITS =
			new int[] {1, 1, 0, 1, 1, 0, 1, 0,
					1, 1, 0, 0, 1, 1, 0, 1,
					1, 0, 1, 0, 1, 1, 0, 0,
					1, 1, 0, 1, 1, 0, 1, 0,
					1, 1, 0, 0, 1, 1, 0, 1,
					1, 0, 1, 0, 1, 1, 0, 0,
					1, 1, 0, 1, 1, 0, 1, 0,
					1, 1, 0, 0, 1, 1, 0, 1};
	private static final long TEST_LONG = 0xB35B35B35B35B35BL;

	private static final BitSet TEST_BITSET = BitSet.valueOf(new long[] {TEST_LONG});
	private static int NUM_BITS = TEST_BITS.length;

	private final BitSetBitVector EMPTY_VECTOR = new BitSetBitVector(NUM_BITS);
	private final BitSetBitVector VECTOR = new BitSetBitVector(NUM_BITS, TEST_BITSET);
	private final BitSetBitVector VECTOR_FROM_BITS = new BitSetBitVector(TEST_BITS);
	private final BitSetBitVector VECTOR_FROM_LONG = new BitSetBitVector(NUM_BITS, TEST_LONG);
	
	@Test
	/** Verifies that our differently-formatted test bits are equivalent. */
	void matchingBitsTest() {
		assertEquals(Long.SIZE, TEST_BITS.length);
		
		for (int i = 0; i < Long.SIZE; i++) {
			assertEquals((TEST_LONG >>> i) & 1, TEST_BITS[i]);
		}
	}
	
	@Test
	void getLengthTest() {
		assertEquals(NUM_BITS, EMPTY_VECTOR.getLength());
		assertEquals(NUM_BITS, VECTOR.getLength());
		assertEquals(NUM_BITS, VECTOR_FROM_BITS.getLength());
		assertEquals(NUM_BITS, VECTOR_FROM_LONG.getLength());
	}
	
	@Test
	void emptyVectorTest() {
		assertEquals(new BitSet(0), EMPTY_VECTOR.toBitSet());
		assertEquals(0, EMPTY_VECTOR.toLong());
	}
	
	@Test
	void constructorEquivalenceTest() {
		assertEquals(TEST_BITSET, VECTOR.toBitSet());
		assertEquals(TEST_BITSET, VECTOR_FROM_BITS.toBitSet());
		assertEquals(TEST_BITSET, VECTOR_FROM_LONG.toBitSet());
	}

	@Test
	void getBitTest_outOfBounds_below() {
		assertThrows(BitVectorIndexOutOfBoundsException.class,
				() -> VECTOR.get(-1),
				"Expected index between 0 and " + NUM_BITS + ", got -1");
	}

	@Test
	void getBitTest_outOfBounds_above() {
		assertThrows(BitVectorIndexOutOfBoundsException.class,
				() -> VECTOR.get(NUM_BITS),
				"Expected index between 0 and " + NUM_BITS + ", got " + NUM_BITS);
	}

	@Test
	void getBitTest() {
		for (int i = 0; i < VECTOR.getLength(); i++) {
			assertEquals(TEST_BITS[i], VECTOR.get(i));
		}
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 6, 8})
	void toByteTest_valid(int length) {
		BitSetBitVector vector = new BitSetBitVector(length, TEST_BITSET);

		assertEquals((byte) (lowerMask(length) & TEST_LONG), vector.toByte());
	}

	@ParameterizedTest
	@ValueSource(ints = {9})
	void toByteTest_invalid(int length) {
		BitSetBitVector vector = new BitSetBitVector(length, TEST_BITSET);

		assertThrows(BitVectorTruncationException.class,
				() -> vector.toByte(),
				"Vector of length " + length + " exceeds length of byte");
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 14, 16})
	void toShortTest_valid(int length) {
		BitSetBitVector vector = new BitSetBitVector(length, TEST_BITSET);

		assertEquals((short) (lowerMask(length) & TEST_LONG), vector.toShort());
	}

	@ParameterizedTest
	@ValueSource(ints = {17})
	void toShortTest_invalid(int length) {
		BitSetBitVector vector = new BitSetBitVector(length, TEST_BITSET);

		assertThrows(BitVectorTruncationException.class,
				() -> vector.toShort(),
				"Vector of length " + length + " exceeds length of short");
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 30, 32})
	void toIntTest_valid(int length) {
		BitSetBitVector vector = new BitSetBitVector(length, TEST_BITSET);

		assertEquals((int) (lowerMask(length) & TEST_LONG), vector.toInt());
	}

	@ParameterizedTest
	@ValueSource(ints = {33})
	void toIntTest_invalid(int length) {
		BitSetBitVector vector = new BitSetBitVector(length, TEST_BITSET);

		assertThrows(BitVectorTruncationException.class,
				() -> vector.toInt(),
				"Vector of length " + length + " exceeds length of int");
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 62, 64})
	void toLongTest_valid(int length) {
		BitSetBitVector vector = new BitSetBitVector(length, TEST_BITSET);

		assertEquals(lowerMask(length) & TEST_LONG, vector.toLong());
	}

	@ParameterizedTest
	@ValueSource(ints = {65})
	void toLongTest_invalid(int length) {
		BitSetBitVector vector = new BitSetBitVector(length, TEST_BITSET);

		assertThrows(BitVectorTruncationException.class,
				() -> vector.toLong(),
				"Vector of length " + length + " exceeds length of long");
	}
	
	void toBitSetTest() {
		assertEquals(TEST_BITSET.get(0, NUM_BITS), VECTOR.toBitSet());
	}

	private long lowerMask(int bits) {
		// Java mods shift sizes by Type.SIZE, so shifts >= Type.SIZE don't work.
		if (bits >= Long.SIZE) {
			return ~0L;
		} else {
			return ~((~0L) << bits);
		}
	}
}
