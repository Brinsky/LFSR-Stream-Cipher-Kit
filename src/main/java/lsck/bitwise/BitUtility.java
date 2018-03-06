package lsck.bitwise;

/** A collection of static helper methods for bit manipulation */
public class BitUtility {
	
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
}
