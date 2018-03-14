package lsck.lfsr;

import java.util.List;

import lsck.bitwise.BitUtility;
import lsck.bitwise.BitVector;

public class LongLfsrTest extends LfsrTest {

	private static final int TEST_LENGTH = 64;
	private static final BitVector TEST_FILL = 
			BitVector.fromBits(
					1,0,1,0,1,1,0,1,
					0,0,0,1,1,0,1,1,
					0,1,0,0,0,1,1,1,
					1,1,1,0,1,0,0,1,
					1,1,0,1,0,0,0,0,
					1,1,0,1,1,0,1,0,
					0,0,1,0,1,1,1,0,
					0,0,0,1,1,0,1,0);
	private static final BitVector TEST_TAPS =
			BitVector.fromBits(
					1,0,1,0,0,0,1,0,
					0,0,0,1,0,0,1,0,
					0,0,1,0,0,0,0,0,
					1,0,1,0,0,0,1,0,
					0,0,0,0,1,0,0,0,
					1,0,0,1,0,0,0,0,
					1,0,1,0,0,0,1,1,
					0,0,0,1,0,1,0,1);
	private static final List<Byte> EXPECTED_OUTPUT =
			BitUtility.listFromBits(
					0,1,0,1,1,0,0,0,
					0,1,1,1,0,1,0,0,
					0,1,0,1,1,0,1,1,
					0,0,0,0,1,0,1,1,
					1,0,0,1,0,1,1,1,
					1,1,1,0,0,0,1,0,
					1,1,0,1,1,0,0,0,
					1,0,1,1,0,1,0,1,
					1,0,1,0,0,0,0,1,
					0,0,0,1,1,1,1,1,
					1,0,1,1,0,0,0,0,
					0,0,1,1,1,1,0,0,
					0,1,1,0,0,1,1,0,
					0,1,1,1,0,0,0,1,
					1,0,1,1,1,1,0,0,
					1,0,0,0,1,0,0,1);
	private static final BitVector EXPECTED_FINAL_FILL =
			BitVector.fromBits(
					0,0,1,0,1,0,0,1,
					1,1,0,1,1,0,0,0,
					1,0,1,0,1,0,0,0,
					0,0,0,0,0,1,1,1,
					0,1,1,0,0,1,0,1,
					1,0,0,1,1,0,1,0,
					0,0,1,0,0,0,0,0,
					1,0,0,0,1,0,0,1);
	private Lfsr TEST_LFSR = new LongLfsr(TEST_LENGTH, TEST_TAPS, TEST_FILL);
	
	@Override
	Lfsr getLfsr() {
		return TEST_LFSR;
	}

	@Override
	int getExpectedLength() {
		return TEST_LENGTH;
	}

	@Override
	BitVector getTestFill() {
		return TEST_FILL;
	}

	@Override
	BitVector getTestTaps() {
		return TEST_TAPS;
	}

	@Override
	List<Byte> getExpectedOutput() {
		return EXPECTED_OUTPUT;
	}
	
	@Override
	BitVector getExpectedFinalFill() {
		return EXPECTED_FINAL_FILL;
	}

}
