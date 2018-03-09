package lsck.bitwise;

import java.util.ArrayList;
import java.util.List;

/** A collection of static helper methods for bit manipulation */
public class BitUtility {
	
	/** Returns the value of a given bit in the provided {@code long}.
	 * 
	 * @param vector A {@code long} to extract a bit from.
	 * @param index The index of the bit being retrieved. The least significant 
	 * 	bit	has index 0, with the remaining bits indexed consecutively.
	 * @return The value of the requested bit - always 0 or 1.
	 */
	public static byte getBit(long vector, int index) {
		if (index < 0 || index >= Long.SIZE) {
			throw new BitUtilityIndexOutOfBoundsException(index, Long.SIZE);
		}
		
		return (byte) ((vector >>> index) & 1L);
	}
	
	/** Sets the value of a given bit in the provided {@code long}.
	 * 
	 * @param vector A {@code long} to alter.
	 * @param index The index of the bit being set. The least significant bit
	 * 	has index 0, with the remaining bits indexed consecutively.
	 * @param value The value to which the specified bit should be set. A value
	 * 	of 0 sets the bit to 0, and any other value sets it to 1.
	 * @return A copy of {@code vector} with the desired bit set.
	 */
	public static long setBit(long vector, int index, int value) {
		if (index < 0 || index >= Long.SIZE) {
			throw new BitUtilityIndexOutOfBoundsException(index, Long.SIZE);
		}
		
		if (value == 0) {
			return vector & ~(1L << index);
		} else {
			return vector | (1L << index);
		}
	}
	
	/** Returns the value of a given bit in an array of {@code long} values.
	 * 
	 * @param vectors An array of {@code long} values to extract a bit from.
	 * @param index The index of the bit being retrieved. The least significant 
	 * 	bit in {@code vectors[0]} has index 0, with the remaining bits indexed
	 * 	in ascending order of array index and significance.
	 * @return The value of the requested bit - always 0 or 1.
	 */
	public static byte getBit(long[] vectors, int index) {
		return getBit(vectors[index / Long.SIZE], index % Long.SIZE);
	}
	
	/** Sets the value of a given bit in an array of {@code long} values.
	 * 
	 * @param vectors An array of {@code long} values to alter.
	 * @param index The index of the bit being set. The least significant bit
	 * 	in {@code vectors[0]} has index 0, with the remaining bits indexed in
	 * 	ascending order of array index and significance.
	 * @param value The value to which the specified bit should be set. A value
	 * 	of 0 sets the bit to 0, and any other value sets it to 1.
	 */
	public static void setBit(long[] vectors, int index, int value) {
		int i = index / Long.SIZE;
		vectors[i] = setBit(vectors[i], index % Long.SIZE, value);
	}
	
	/** Converts a sequence of bits into a {@code List<Byte>}
	 * 
	 * @param bits A sequence of bits to be stored in a list. Any nonzero value
	 * 	will result in a 1 being stored, while a value of 0 will be stored
	 * 	directly.
	 * @return A {@code List<Byte>} containing the bits represented by
	 * 	{@code bits}.
	 */
	public static List<Byte> listFromBits(int ... bits) {
		List<Byte> list = new ArrayList<>(bits.length);
		
		for (int bit : bits) {
			list.add((byte) (bit == 0 ? 0 : 1));
		}
		
		return list;
	}
	
	/** Converts a bit represented by an {@code int} into a {@code char}.
	 * 
	 * @param bit The bit to be converted into a {@code char}.
	 * @return {@code '0'} if {@code bit == 0}, {@code '1'} otherwise.
	 */
	public static char bitToChar(int bit) {
		return bit == 0 ? '0' : '1';
	}
	
	/** Returns a String representation of a {@link BitVector}.
	 * 
	 * @param vector The {@link BitVector} to create a String representation
	 * 	for.
	 * @return A String representation of {@code vector} with no delimiters and
	 * 	bits in descending order (most significant bit first).
	 */
	public static String bitString(BitVector vector) {
		return bitString(vector.getLength(), vector, false, "");
	}
	
	/** Returns a customized String representation of a {@link BitVector}.
	 * 
	 * @param length The number of bits to include in the String
	 * 	representation. Bits with indices 0 through {@code length - 1} 
	 * 	(inclusive) will be included.
	 * @param vector The {@link BitVector} to create a String representation
	 * 	for.
	 * @param ascending If true, bits are ordered from lowest to highest index
	 * 	(least significant bit first). If {@code false}, bits are ordered from
	 * 	highest to lowest index (most significant bit first).
	 * @param delimiter A delimiter to be inserted after all but the last bit
	 * 	in the String representation.
	 * @return A customized String representation of {@code vector}.
	 */
	public static String bitString(int length, BitVector vector,
			boolean ascending, String delimiter) {
		
		if (length < 0 || length > vector.getLength()) {
			throw new BitUtilityInvalidLengthException(length, 
					vector.getLength());
		}
		
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			if (ascending) {
				builder.append(bitToChar(vector.get(i)));
			} else {
				builder.append(bitToChar(vector.get(length - i - 1)));
			}
			
			// Append delimiter on all but the last bit
			if (i < length - 1) {
				builder.append(delimiter);
			}
		}
		
		return builder.toString();
	}
}
