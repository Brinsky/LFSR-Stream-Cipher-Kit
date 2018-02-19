package lsck.lfsr;

/** Indicates an invalid length parameter in an LFSR-related context. */
public class InvalidLengthException extends LfsrRuntimeException {

	public static final String MESSAGE = "Expected %d to be greater than 0";
	
	public InvalidLengthException(int length) {
		super(String.format(MESSAGE, length));
	}
}
