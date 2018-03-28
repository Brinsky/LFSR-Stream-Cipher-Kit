package lsck.common;

/** Helper class for common exceptions and messages */
public final class Exceptions {
  
  public static final String INVALID_INDEX = "Expected a nonnegative index less than %d; got %d";
  public static final String NEGATIVE_LENGTH = "Expected a nonnegative length; got %d";
  public static final String EXCESSIVE_LENGTH =
      "Expected a positive length no greater than %d; got %d";
  public static final String NONPOSTIIVE_LENGTH = "Expected a length greater than 0; got %d";
  public static final String INVALID_VECTOR_LENGTH = "Expected vector of length %d; got %d";
  public static final String VECTOR_TRUNCATION = "Vector of length %d exceeds length of %s";
  
  public static IndexOutOfBoundsException indexOutOfBoundsException(int index, int length) {
    return new IndexOutOfBoundsException(String.format(INVALID_INDEX, length, index));
  }
  
  public static IllegalArgumentException negativeLengthException(int length) {
    return new IllegalArgumentException(String.format(NEGATIVE_LENGTH, length));
  }
  
  public static IllegalArgumentException nonPositiveLengthException(int length) {
    return new IllegalArgumentException(String.format(NONPOSTIIVE_LENGTH, length));
  }
  
  public static IllegalArgumentException unsupportedLengthException(int length, int maxLength) {
    return new IllegalArgumentException(String.format(EXCESSIVE_LENGTH, maxLength, length));
  }
  
  public static IllegalArgumentException invalidVectorLengthException(int expected, int actual) {
    return new IllegalArgumentException(String.format(INVALID_VECTOR_LENGTH, expected, actual));
  }
  
  public static IllegalArgumentException vectorTruncationException(int length, String type) {
    return new IllegalArgumentException(String.format(VECTOR_TRUNCATION, length, type));
  }
}
