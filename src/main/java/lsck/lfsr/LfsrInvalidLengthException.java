package lsck.lfsr;

/** Indicates an invalid length parameter in an LFSR-related context. */
public class LfsrInvalidLengthException extends IllegalArgumentException {

	public static final String MESSAGE =
			"Expected a positive length, got %d";
	public static final String INCORRECT_VECTOR_LENGTH = 
			"Expected vector length to be %d, got %d";
	public static final String OUT_OF_RANGE = 
			"Expected length to be greater than %d and no more than %d, got %d";
	
	public LfsrInvalidLengthException(int length) {
		super(String.format(MESSAGE, length));
	}
	
	public LfsrInvalidLengthException(int expectedLength, int actualLength) {
		super(String.format(INCORRECT_VECTOR_LENGTH, expectedLength, actualLength));
	}
	
	public LfsrInvalidLengthException(int minLength, int maxLength, int actualLength) {
		super(String.format(OUT_OF_RANGE, minLength, maxLength, actualLength));
	}
}
