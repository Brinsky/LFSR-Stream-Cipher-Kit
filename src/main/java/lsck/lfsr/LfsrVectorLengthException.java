package lsck.lfsr;

public class LfsrVectorLengthException extends IllegalArgumentException {

	private static final String MESSAGE = "Expected vector of length %d, got %d";
	
	public LfsrVectorLengthException(int registerLength, int vectorLength) {
		super(String.format(MESSAGE, registerLength, vectorLength));
	}
}
