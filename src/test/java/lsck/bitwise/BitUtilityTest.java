package lsck.bitwise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class BitUtilityTest {
	
	@ParameterizedTest
	@MethodSource("setBitProvider")
	void testSetBit_validIndex(long vector, int index, int value, long expected) {
		assertEquals(expected, BitUtility.setBit(vector, index, value));
	}
	
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
	@ValueSource(ints = {-10, -1, 64, 100})
	void testSetBit_invalidIndex(int index) {
		BitUtilityIndexOutOfBoundsException e = 
				assertThrows(BitUtilityIndexOutOfBoundsException.class,
						() -> BitUtility.setBit(0L, index, 1));
		
		assertEquals("Expected a nonnegative value less than " + 64 
				+ ", got " + index, e.getMessage());
	}
	

	@ParameterizedTest
	@MethodSource("getBitProvider")
	void testGetBit_validIndex(long vector, int index, int expected) {
		assertEquals(expected, BitUtility.getBit(vector, index));
	}
	
	static Stream<Arguments> getBitProvider() {
		return Stream.of(
				Arguments.of(1L, 0, 1),
				Arguments.of(1L << 30, 30, 1),
				Arguments.of(0xABCD1234ABCD1234L, 29, 1),
				Arguments.of(0xABCD1234ABCD1234L, 30, 0),
				Arguments.of(1L << 63, 63, 1),
				Arguments.of(~0L >>> 1, 63, 0));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-10, -1, 64, 100})
	void testGetBit_invalidIndex(int index) {
		BitUtilityIndexOutOfBoundsException e = 
				assertThrows(BitUtilityIndexOutOfBoundsException.class,
						() -> BitUtility.getBit(~0L, index));
		
		assertEquals("Expected a nonnegative value less than " + 64 
				+ ", got " + index, e.getMessage());
	}
}
