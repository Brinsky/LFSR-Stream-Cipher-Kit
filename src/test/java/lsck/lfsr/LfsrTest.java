package lsck.lfsr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import lsck.bitwise.BitVector;

public abstract class LfsrTest {
	
	abstract Lfsr getLfsr();
	abstract int getExpectedLength();
	abstract BitVector getTestFill();
	abstract BitVector getTestTaps();
	abstract List<Byte> getExpectedOutput();
	abstract BitVector getExpectedFinalFill();
	
	@Test
	void getLengthTest() {
		assertEquals(getExpectedLength(), getLfsr().getLength());
	}
	
	@Test
	void shiftTest_single() {
		Lfsr lfsr = getLfsr();
		List<Byte> expected = getExpectedOutput();
		
		for (int i = 0; i < expected.size(); i++) {
			assertEquals((byte) expected.get(i), lfsr.shift(),
					"Unequal at index " + i);
		}
		
		assertEquals(getExpectedFinalFill(), lfsr.getFill());
	}
	
	@Test
	void shiftTest_all() {
		Lfsr lfsr = getLfsr();
		List<Byte> expected = getExpectedOutput();
		
		assertEquals(expected, lfsr.shift(expected.size()));
		assertEquals(getExpectedFinalFill(), lfsr.getFill());
	}
	
	@Test
	void peekTest_single() {
		Lfsr lfsr = getLfsr();
		List<Byte> expected = getExpectedOutput();
		
		assertEquals((byte) expected.get(0), lfsr.peek());
		assertEquals(getTestFill(), lfsr.getFill());
	}
	
	@Test
	void peekTest_all() {
		Lfsr lfsr = getLfsr();
		List<Byte> expected = getExpectedOutput();
		
		assertEquals(expected, lfsr.peek(expected.size()));
		assertEquals(getTestFill(), lfsr.getFill());
	}
}