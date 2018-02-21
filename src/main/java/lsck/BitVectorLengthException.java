package lsck;

/** Indicates an invalid length parameter in a BitVector context. */
public class BitVectorLengthException extends IllegalArgumentException {

	public static final String WAS_NEGATIVE = 
			"Expected a nonnegative length, got %d";
	public static final String EXCEEDED_LIMIT =
			"Expected a length between 0 and %d, got %d";
	
	public BitVectorLengthException(int length) {
		super(String.format(WAS_NEGATIVE, length));
	}
	
	public BitVectorLengthException(int length, int upperLimit) {
		super(String.format(EXCEEDED_LIMIT, length, upperLimit));
	}
}
