package lsck.bitwise;

import java.util.ArrayList;
import java.util.List;

import lsck.common.Exceptions;

/** A collection of static helper methods for bit manipulation */
public class BitUtility {
  
  /**
   * Returns the value of a given bit in the provided {@code long}.
   *
   * @param vector A {@code long} to extract a bit from.
   * @param index The index of the bit being retrieved. The least significant bit has index 0, with
   *     the remaining bits indexed consecutively.
   * @return The value of the requested bit - always 0 or 1.
   */
  public static byte getBit(long vector, int index) {
    if (index < 0 || index >= Long.SIZE) {
      throw Exceptions.indexOutOfBoundsException(index, Long.SIZE);
    }

    return (byte) ((vector >>> index) & 1L);
  }

  /**
   * Sets the value of a given bit in the provided {@code long}.
   *
   * @param vector A {@code long} to alter.
   * @param index The index of the bit being set. The least significant bit has index 0, with the
   *     remaining bits indexed consecutively.
   * @param value The value to which the specified bit should be set. A value of 0 sets the bit to
   *     0, and any other value sets it to 1.
   * @return A copy of {@code vector} with the desired bit set.
   */
  public static long setBit(long vector, int index, int value) {
    if (index < 0 || index >= Long.SIZE) {
      throw Exceptions.indexOutOfBoundsException(index, Long.SIZE);
    }

    if (value == 0) {
      return vector & ~(1L << index);
    } else {
      return vector | (1L << index);
    }
  }

  /**
   * Returns the value of a given bit in an array of {@code long} values.
   *
   * @param vectors An array of {@code long} values to extract a bit from.
   * @param index The index of the bit being retrieved. The least significant bit in {@code
   *     vectors[0]} has index 0, with the remaining bits indexed in ascending order of array index
   *     and significance.
   * @return The value of the requested bit - always 0 or 1.
   */
  public static byte getBit(long[] vectors, int index) {
    if (index < 0 || index >= vectors.length * Long.SIZE) {
      throw Exceptions.indexOutOfBoundsException(index, vectors.length * Long.SIZE);
    }

    return getBit(vectors[index / Long.SIZE], index % Long.SIZE);
  }

  /**
   * Sets the value of a given bit in an array of {@code long} values.
   *
   * @param vectors An array of {@code long} values to alter.
   * @param index The index of the bit being set. The least significant bit in {@code vectors[0]}
   *     has index 0, with the remaining bits indexed in ascending order of array index and
   *     significance.
   * @param value The value to which the specified bit should be set. A value of 0 sets the bit to
   *     0, and any other value sets it to 1.
   */
  public static void setBit(long[] vectors, int index, int value) {
    if (index < 0 || index >= vectors.length * Long.SIZE) {
      throw Exceptions.indexOutOfBoundsException(index, vectors.length * Long.SIZE);
    }

    int i = index / Long.SIZE;
    vectors[i] = setBit(vectors[i], index % Long.SIZE, value);
  }

  /**
   * Converts a sequence of bits into a {@code List<Byte>}
   *
   * @param bits A sequence of bits to be stored in a list. Any nonzero value will result in a 1
   *     being stored, while a value of 0 will be stored directly.
   * @return A {@code List<Byte>} containing the bits represented by {@code bits}.
   */
  public static List<Byte> listFromBits(int... bits) {
    List<Byte> list = new ArrayList<>(bits.length);

    for (int bit : bits) {
      list.add((byte) (bit == 0 ? 0 : 1));
    }

    return list;
  }

  /**
   * Converts a bit represented by an {@code int} into a {@code char}.
   *
   * @param bit The bit to be converted into a {@code char}.
   * @return {@code '0'} if {@code bit == 0}, {@code '1'} otherwise.
   */
  public static char bitToChar(int bit) {
    return bit == 0 ? '0' : '1';
  }

  /**
   * Converts an {@code int} to a {@code byte} representing a single bit.
   *
   * @param value An integer to be converted to a bit.
   * @return 0 if {@code value == 0}, 1 otherwise.
   */
  public static byte asBit(int value) {
    return (byte) ((value == 0) ? 0 : 1);
  }

  /**
   * Returns a String representation of a {@link BitVector}.
   *
   * @param vector The {@link BitVector} to create a String representation for.
   * @return A String representation of {@code vector} with no delimiters and bits in descending
   *     order (most significant bit first).
   */
  public static String bitString(BitVector vector) {
    return bitString(vector, false, "");
  }

  /**
   * Returns a customized String representation of a {@link BitVector}.
   *
   * @param vector The {@link BitVector} to create a String representation for.
   * @param ascending If true, bits are ordered from lowest to highest index (least significant bit
   *     first). If {@code false}, bits are ordered from highest to lowest index (most significant
   *     bit first).
   * @param delimiter A delimiter to be inserted after all but the last bit in the String
   *     representation.
   * @return A customized String representation of {@code vector}.
   */
  public static String bitString(BitVector vector, boolean ascending, String delimiter) {
    return bitString(i -> vector.get(i), vector.getLength(), ascending, delimiter);
  }

  /**
   * Returns a String representation of a list of bits.
   *
   * @param bitList The {@code List<Byte>} to create a String representation for. Character
   *     representations for bytes are determined by calling {@link BitUtility#bitToChar(int)}.
   * @return A String representation of {@code bitList} with no delimiters.
   */
  public static String bitString(List<Byte> bitList) {
    return bitString(bitList, true, "");
  }

  /**
   * Returns a customized String representation of a {@link BitVector}.
   *
   * @param bitList The {@code List<Byte>} to create a String representation for. Character
   *     representations for bytes are determined by calling {@link BitUtility#bitToChar(int)}.
   * @param ascending If true, bits are ordered from lowest to highest index. If {@code false}, bits
   *     are ordered from highest to lowest index.
   * @param delimiter A delimiter to be inserted after all but the last bit in the String
   *     representation.
   * @return A customized String representation of {@code bitList}.
   */
  public static String bitString(List<Byte> bitList, boolean ascending, String delimiter) {
    return bitString(i -> bitList.get(i), bitList.size(), ascending, delimiter);
  }

  @FunctionalInterface
  private static interface BitGetter {
    byte get(int index);
  }

  private static String bitString(
      BitGetter getter, int length, boolean ascending, String delimiter) {
    StringBuilder builder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      if (ascending) {
        builder.append(bitToChar(getter.get(i)));
      } else {
        builder.append(bitToChar(getter.get(length - i - 1)));
      }

      // Append delimiter on all but the last bit
      if (i < length - 1) {
        builder.append(delimiter);
      }
    }

    return builder.toString();
  }
  
  /** Reverses the bit vector represented by a {@code long}.
   * 
   * The lower {@code length} bits of {@code vector} are reversed. Any higher-order bits are truncated
   * 
   * @param length The number of bits in the given vector.
   * @param vector A bit vector represented by a {@code long}.
   * @return A reversed copy of {@code vector}.
   */
  public static long reverse(int length, long vector) {
    long reversed = 0;
    
    for (int i = 0; i < length; i++) {
      reversed <<= 1; // (First shift has no effect)
      reversed |= (vector >>> i) & 1L;
    }
    
    return reversed;
  }
}
