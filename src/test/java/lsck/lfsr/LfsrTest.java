package lsck.lfsr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.exceptions.base.MockitoException;

import utility.TestUtilities;

public class LfsrTest {
	
	@ParameterizedTest
	@ValueSource(ints = {1, 10}) // Valid lengths
	void lengthTest_valid(int length) {
		Lfsr lfsr = TestUtilities.mockAbstractClass(Lfsr.class, length);
		assertEquals(length, lfsr.getLength());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-10, 0}) // Invalid lengths
	void lengthTest_invalid(int length) {
		try {
			TestUtilities.mockAbstractClass(Lfsr.class, length);
		} catch (MockitoException e) {
			LfsrInvalidLengthException exception =
					(LfsrInvalidLengthException) extractConstructorException(e);
			
			assertEquals("Expected a positive length, got " + length,
					exception.getMessage());
			return;
		}
		
		fail("Expected nonpostive lengths to cause an exception");
	}

	// TODO: Determine a better method of testing abstract constructors with
	// exceptions.
	/** Extracts the cause of an exception throw from a mock constructor */
	Throwable extractConstructorException(MockitoException exception) {
		return exception.getCause().getCause().getCause();
	}
}
