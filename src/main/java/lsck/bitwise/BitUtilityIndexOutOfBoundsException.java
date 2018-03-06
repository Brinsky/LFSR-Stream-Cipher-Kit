package lsck.bitwise;

public class BitUtilityIndexOutOfBoundsException extends IndexOutOfBoundsException {

	public static final String MESSAGE =
			"Expected a nonnegative value less than %d, got %d";
	
	public BitUtilityIndexOutOfBoundsException(int index, int length) {
		super(String.format(MESSAGE, length, index));
	}
}
