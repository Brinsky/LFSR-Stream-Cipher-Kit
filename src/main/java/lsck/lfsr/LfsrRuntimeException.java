package lsck.lfsr;

/** A superclass for any unchecked LFSR-related exception */
public class LfsrRuntimeException extends RuntimeException {
	
	public LfsrRuntimeException(String message) {
		super(message);
	}
}
