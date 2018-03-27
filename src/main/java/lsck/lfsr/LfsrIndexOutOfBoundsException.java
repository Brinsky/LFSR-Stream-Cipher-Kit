package lsck.lfsr;

/** Indicates an invalid index parameter in an {@code Lfsr} context. */
public class LfsrIndexOutOfBoundsException extends IndexOutOfBoundsException {

  public static final String MESSAGE = "Expected index between 0 and %d, got %d";

  public LfsrIndexOutOfBoundsException(int index, int length) {
    super(String.format(MESSAGE, index, length - 1));
  }
}
