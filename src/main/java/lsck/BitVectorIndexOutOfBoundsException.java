package lsck;

/** Indicates an invalid index parameter in a BitVector context. */
public class BitVectorIndexOutOfBoundsException extends IndexOutOfBoundsException {

	public static final String MESSAGE =
			"Expected index between 0 and %d, got %d";
	
	public BitVectorIndexOutOfBoundsException(int index, int length) {
		super(String.format(MESSAGE, index, length - 1));
	}
}
