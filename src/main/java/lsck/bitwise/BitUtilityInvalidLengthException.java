package lsck.bitwise;

/** Indicates an invalid length parameter in a {@link BitUtility} context. */
public class BitUtilityInvalidLengthException extends IllegalArgumentException {

	public static final String OUT_OF_RANGE = 
			"Expected length to be nonnegative and no more than %d, got %d";
	
	public BitUtilityInvalidLengthException(int maxLength, int actualLength) {
		super(String.format(OUT_OF_RANGE, maxLength, actualLength));
	}
}
