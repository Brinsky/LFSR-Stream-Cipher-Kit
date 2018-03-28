package lsck.common;

/** Helper class for common exceptions and messages */
public final class Exceptions {

  public static final String INVALID_INDEX = "Expected a nonnegative index less than %d; got %d";

  // General purpose length-related messages
  public static final String NEGATIVE_LENGTH = "Expected a nonnegative length; got %d";
  public static final String EXCESSIVE_LENGTH =
      "Expected a positive length no greater than %d; got %d";
  public static final String NONPOSTIIVE_LENGTH = "Expected a length greater than 0; got %d";

  // Vector manipulation related messages
  public static final String INVALID_VECTOR_LENGTH = "Expected vector of length %d; got %d";
  public static final String VECTOR_TRUNCATION = "Vector of length %d exceeds length of %s";

  // BooleanFunction-related messages
  public static final String INVALID_ARITY = "Expected arity between 1 and %d, inclusive; got %d";
  public static final String VECTOR_ARITY_MISMATCH =
      "Expected vector length to match arity of %d; got %d";
  public static final String TABLE_LENGTH = "Table should be of length 2^arity (%d); got %d";

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

  public static IllegalArgumentException invalidArityException(int arity, int maxArity) {
    return new IllegalArgumentException(String.format(INVALID_ARITY, maxArity, arity));
  }

  public static IllegalArgumentException vectorLengthException(int arity, int length) {
    return new IllegalArgumentException(String.format(VECTOR_ARITY_MISMATCH, arity, length));
  }

  public static IllegalArgumentException tableLengthException(int arity, int length) {
    return new IllegalArgumentException(String.format(TABLE_LENGTH, 1 << arity, length));
  }
}
