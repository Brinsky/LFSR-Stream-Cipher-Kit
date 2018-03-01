package lsck;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import lsck.bitwise.BitSetBitVector;
import lsck.bitwise.BitVector;
import lsck.bitwise.BitVectorTruncationException;

public class BitSetBitVectorTest extends BitVectorTest {

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
	
	@ParameterizedTest
	@ValueSource(ints = {65})
	void toLongTest_invalid(int length) {
		assertThrows(BitVectorTruncationException.class,
				() -> newTruncatedVector(length).toLong(),
				"Vector of length " + length + " exceeds length of long");
	}
}
