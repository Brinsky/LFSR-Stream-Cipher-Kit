package lsck;

import java.util.BitSet;

/** An immutable, fixed-length vector of bits */
public abstract class BitVector {

	/** Returns the length of this bit vector */
	public abstract int getLength();
	
	/** Returns the specified bit in this vector
	 * 
	 * @param index The position of the desired bit.
	 * @return The specified bit in this vector.
	 */
	public abstract byte get(int index);
	
	// TODO: Have these methods throw runtime exceptions if length > type size
	// and write corresponding documentation.
	public abstract byte toByte();
	public abstract short toShort();
	public abstract int toInt();
	public abstract long toLong();
	public abstract BitSet toBitSet();
}
