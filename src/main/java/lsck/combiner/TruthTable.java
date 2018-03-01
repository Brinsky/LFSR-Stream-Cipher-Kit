package lsck.combiner;

import java.util.List;

import lsck.bitwise.BitVector;

/** A table of the values taken by a Boolean function. 
 * 
 * <p>{@code TermTable} and {@code TruthTable} instances are both sufficient
 * to fully describe a Boolean function, and each can be used to build /
 * recover the other.
 * 
 * @see TermTable
 */
public abstract class TruthTable {
	
	/** Returns the number of arguments taken by this truth table / function.
	 * 
	 * @return the number of arguments taken by this truth table / function.
	 */
	public abstract int getArity();
	
	/** Returns the total length of this truth table.
	 * 
	 * <p>This value is always 2 raised to the power {@code getArity()}. It
	 * represents the number of distinct values contained in this truth table.
	 * 
	 * @return the total length of this truth table.
	 */
	public abstract int getLength();

	/** Returns the value of this truth table specified by the arguments.
	 * 
	 * @param args Values of the arguments to this truth table / function.
	 * @return The value of the truth table specified by the arguments.
	 */
	public abstract byte at(BitVector args);
	
	/** Returns a list of all argument vectors corresponding to a value of 1.
	 * 
	 * @return a list of all argument vectors corresponding to a value of 1.
	 */
	public abstract List<BitVector> getSupport();
	
	/** Returns a list of all argument vectors corresponding to a value of 1.
	 * 
	 * @return a list of all argument vectors corresponding to a value of 1.
	 */
	public abstract int getWeight();
	
	/** Whether or not this truth table represents a constant function.
	 * 
	 * @return Whether or not this truth table represents a constant function.
	 */
	public abstract boolean isConstant();
	
	/** Constructs a {@code TermTable} corresponding to this truth table.
	 * 
	 * <p>If the algebraic expression described by the resulting term table is
	 * evaluated at every possible argument value, it will produce the contents
	 * of this truth table. Note that this operation is expensive - it runs in
	 * n-squared time, where n is the length of the truth table.
	 * 
	 * @return a {@code TermTable} corresponding to this truth table.
	 */
	public abstract TermTable buildTermTable();
}
