package lsck.bitwise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.BitSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import lsck.bitwise.BitVector;
import lsck.bitwise.BitVectorIndexOutOfBoundsException;
import lsck.bitwise.BitVectorTruncationException;

public abstract class BitVectorTest {

	/* 
	 * MSB to LSB:
	 * 1011 0011 0101 1011
	 * 0011 0101 1011 0011
	 * 0101 1011 0011 0101
	 * 1011 0011 0101 1011
	 */
	protected static final int[] TEST_BITS =
			new int[] {1, 1, 0, 1, 1, 0, 1, 0,
					1, 1, 0, 0, 1, 1, 0, 1,
					1, 0, 1, 0, 1, 1, 0, 0,
					1, 1, 0, 1, 1, 0, 1, 0,
					1, 1, 0, 0, 1, 1, 0, 1,
					1, 0, 1, 0, 1, 1, 0, 0,
					1, 1, 0, 1, 1, 0, 1, 0,
					1, 1, 0, 0, 1, 1, 0, 1};
	protected static final long TEST_LONG = 0xB35B35B35B35B35BL;

	protected static final BitSet TEST_BITSET = BitSet.valueOf(new long[] {TEST_LONG});
	protected static final int NUM_BITS = TEST_BITS.length;
	
	// Instance provider methods for subclasses to implement
	
	protected abstract BitVector newVector();
	protected abstract BitVector newTruncatedVector(int length);
	protected abstract BitVector newVectorFromBits();
	protected abstract BitVector newVectorFromLong();
	protected abstract BitVector newEmptyVector();
	
	@Test
	/** Verifies that our differently-formatted test bits are equivalent. */
	final void matchingBitsTest() {
		assertEquals(Long.SIZE, TEST_BITS.length);
		
		for (int i = 0; i < Long.SIZE; i++) {
			assertEquals((TEST_LONG >>> i) & 1, TEST_BITS[i]);
		}
	}
	
	@Test
	void getLengthTest() {
		assertEquals(NUM_BITS, newEmptyVector().getLength());
		assertEquals(NUM_BITS, newVector().getLength());
		assertEquals(NUM_BITS, newVectorFromBits().getLength());
		assertEquals(NUM_BITS, newVectorFromLong().getLength());
	}
	
	@Test
	void emptyVectorTest() {
		BitVector emptyVector = newEmptyVector();
		
		assertEquals(new BitSet(0), emptyVector.toBitSet());
		assertEquals(0, newEmptyVector().toLong());
	}
	
	@Test
	void constructorEquivalenceTest() {
		assertEquals(TEST_BITSET, newVector().toBitSet());
		assertEquals(TEST_BITSET, newVectorFromBits().toBitSet());
		assertEquals(TEST_BITSET, newVectorFromLong().toBitSet());
	}

	@Test
	void getBitTest_outOfBounds_below() {
		assertThrows(BitVectorIndexOutOfBoundsException.class,
				() -> newVector().get(-1),
				"Expected index between 0 and " + NUM_BITS + ", got -1");
	}

	@Test
	void getBitTest_outOfBounds_above() {
		assertThrows(BitVectorIndexOutOfBoundsException.class,
				() -> newVector().get(NUM_BITS),
				"Expected index between 0 and " + NUM_BITS + ", got " + NUM_BITS);
	}

	@Test
	void getBitTest() {
		BitVector vector = newVector();
		
		for (int i = 0; i < vector.getLength(); i++) {
			assertEquals(TEST_BITS[i], vector.get(i));
		}
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 6, 8})
	void toByteTest_valid(int length) {
		assertEquals((byte) (lowerMask(length) & TEST_LONG),
				newTruncatedVector(length).toByte());
	}

	@ParameterizedTest
	@ValueSource(ints = {9})
	void toByteTest_invalid(int length) {
		assertThrows(BitVectorTruncationException.class,
				() -> newTruncatedVector(length).toByte(),
				"Vector of length " + length + " exceeds length of byte");
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 14, 16})
	void toShortTest_valid(int length) {
		assertEquals((short) (lowerMask(length) & TEST_LONG),
				newTruncatedVector(length).toShort());
	}

	@ParameterizedTest
	@ValueSource(ints = {17})
	void toShortTest_invalid(int length) {
		assertThrows(BitVectorTruncationException.class,
				() -> newTruncatedVector(length).toShort(),
				"Vector of length " + length + " exceeds length of short");
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 30, 32})
	void toIntTest_valid(int length) {
		assertEquals((int) (lowerMask(length) & TEST_LONG),
				newTruncatedVector(length).toInt());
	}

	@ParameterizedTest
	@ValueSource(ints = {33})
	void toIntTest_invalid(int length) {
		assertThrows(BitVectorTruncationException.class,
				() -> newTruncatedVector(length).toInt(),
				"Vector of length " + length + " exceeds length of int");
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 62, 64})
	void toLongTest_valid(int length) {
		assertEquals(lowerMask(length) & TEST_LONG,
				newTruncatedVector(length).toLong());
	}
	
	@Test
	void toBitSetTest() {
		assertEquals(TEST_BITSET.get(0, NUM_BITS), newVector().toBitSet());
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

