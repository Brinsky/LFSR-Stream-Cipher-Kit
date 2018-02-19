package lsck.lfsr;

/** Indicates an invalid length parameter in an LFSR-related context. */
public class LfsrInvalidLengthException extends IllegalArgumentException {

	public static final String MESSAGE = "Expected a positive length, got %d";
	
	public LfsrInvalidLengthException(int length) {
		super(String.format(MESSAGE, length));
	}
}
