package lsck.bitwise;

/** Indicates truncation of a BitVector into a type of insufficient size. */
public class BitVectorTruncationException extends IllegalArgumentException {

  public static final String EXCEEDED_LIMIT = "Vector of length %d exceeds length of %s";

  public BitVectorTruncationException(int length, String type) {
    super(String.format(EXCEEDED_LIMIT, length, type));
  }
}
