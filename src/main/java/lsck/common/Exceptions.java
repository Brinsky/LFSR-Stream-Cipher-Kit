package lsck.common;

import lsck.attack.Attack;
import lsck.combiner.Generator;

/** Helper class for common exceptions and messages */
public final class Exceptions {

  public static final String INVALID_INDEX = "Expected a nonnegative index less than %d; got %d";

  // General purpose length-related messages
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

  // Generator-related messages
  public static final String MAX_GENERATOR_LENGTH =
      "Combiner arity of %d exceeds maximum number of registers (%d)";
  public static final String ARITY_REGISTER_MISMATCH =
      "Combiner arity of %d does not match register count of %d";
  public static final String REGISTER_INDEX = "Expected a valid register index; got %d";

  // Attack-related messages
  public static final String REGISTER_EXCEEDS_ATTACK_LENGTH =
      "Register of length %d exceeds max-attackable length of %d";
  public static final String DUPLICATE_REGISTER = "Register with index %d specified more than once";
  public static final String VECTOR_EXCEEDS_ITERABLE_LENGTH =
      "Vector of length %d exceeds max-iterable length of %d";

  // Boolean function parsing messages
  public static final String INVALID_INDEX_RANGE =
      "Variable index should be between %d and %d (inclusive); got %d";

  // BitList
  public static final String INVALID_CAPACITY = "Capacity must be nonnegative; got %d";
  public static final String INVALID_SUBLIST_INDICES =
      "Expected 0 <= fromIndex <= startIndex <= %d; got fromIndex: %d, toIndex: %d";

  public static IndexOutOfBoundsException indexOutOfBoundsException(int index, int length) {
    return new IndexOutOfBoundsException(String.format(INVALID_INDEX, length, index));
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

  public static IllegalArgumentException vectorArityMismatchException(int arity, int length) {
    return new IllegalArgumentException(String.format(VECTOR_ARITY_MISMATCH, arity, length));
  }

  public static IllegalArgumentException tableLengthException(int arity, int length) {
    return new IllegalArgumentException(String.format(TABLE_LENGTH, 1 << arity, length));
  }

  public static IllegalArgumentException maxGeneratorLengthException(int length) {
    return new IllegalArgumentException(
        String.format(MAX_GENERATOR_LENGTH, length, Generator.MAX_REGISTERS));
  }

  public static IllegalArgumentException arityRegisterCountMismatchException(
      int arity, int registerCount) {
    return new IllegalArgumentException(
        String.format(ARITY_REGISTER_MISMATCH, arity, registerCount));
  }

  public static IndexOutOfBoundsException registerIndexOutOfBoundsException(int index) {
    return new IndexOutOfBoundsException(String.format(REGISTER_INDEX, index));
  }

  public static IllegalArgumentException registerExceedsAttackableLengthException(
      int registerLength) {
    return new IllegalArgumentException(
        String.format(
            REGISTER_EXCEEDS_ATTACK_LENGTH, registerLength, Attack.MAX_ATTACKABLE_REGISTER_LENGTH));
  }

  public static IllegalArgumentException vectorExceedsIterableLengthException(int vectorLength) {
    return new IllegalArgumentException(
        String.format(
            VECTOR_EXCEEDS_ITERABLE_LENGTH, vectorLength, Attack.MAX_ITERABLE_VECTOR_LENGTH));
  }

  public static IllegalArgumentException duplicateRegisterException(int index) {
    return new IllegalArgumentException(String.format(DUPLICATE_REGISTER, index));
  }

  public static IndexOutOfBoundsException invalidIndexRangeException(int min, int max, int given) {
    return new IndexOutOfBoundsException(String.format(INVALID_INDEX_RANGE, min, max, given));
  }

  public static IllegalArgumentException invalidCapacity(int capacity) {
    throw new IllegalArgumentException(String.format(INVALID_CAPACITY, capacity));
  }

  public static IllegalArgumentException invalidSublistIndices(
      int fromIndex, int toIndex, int size) {
    throw new IndexOutOfBoundsException(
        String.format(INVALID_SUBLIST_INDICES, size, fromIndex, toIndex));
  }
}
