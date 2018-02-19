package lsck.lfsr;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

public class LfsrTest {
	
	private static int VALID_LENGTH = 10;
	private static int[] INVALID_LENGTH = new int[] {-10, 0};
	
	@Test
	void lengthTest_valid() {
		Lfsr lfsr = mock(Lfsr.class, withSettings()
				.useConstructor(VALID_LENGTH).defaultAnswer(CALLS_REAL_METHODS));
		
		assertEquals(VALID_LENGTH, lfsr.getLength());
	}
}
