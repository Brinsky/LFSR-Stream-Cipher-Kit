package lsck.combiner;

import java.util.List;

import lsck.bitwise.BitVector;

/** A list of terms present in an algebraic Boolean expression.
 * 
 * <p>A term table is a list of terms present in the
 * <a href=http://wikipedia.org/wiki/Algebraic_normal_form>algebraic normal
 * form (ANF)</a> representation of a Boolean function. In particular, an
 * individual term is product of some or all of a function's arguments with
 * 1. As examples, consider the term "x0 * x1 * x3" and the ANF expression
 * "x0 + x0 x2 + x1 x2 x3 + 1".
 * 
 * <p>{@code TermTable} and {@code TruthTable} instances are both sufficient
 * to fully describe a Boolean function, and each can be used to build /
 * recover the other.
 * 
 * @see TruthTable
 */
public abstract class TermTable {

	/** Returns the number of arguments to this term table / function. 
	 *
	 * <p>Note that a term table may have more arguments than appear in its
	 * representation. For example, "x0 + 1" can still be treated as a function
	 * of x0, x1, and x2.
	 * 
	 * @return the number of arguments taken by this term table / function.
	 */
	public abstract int getArity();
	
	/** Indicates whether or not the specified term is in this table.
	 * 
	 * <p>The bit vector provided as an argument indicates which algebraic
	 * term is being requested. The lowest order bit in the vector indicates
	 * the presence of the first argument "x0", and the remaining bits refer
	 * to the remaining consecutive arguments. The term in question is the
	 * product of 1 and all present arguments.
	 * 
	 * <p>E.g. 01101 = "x0 * x2 * x3"
	 * 
	 * <p>The constant term 1 is represented by a vector of zeros.
	 * 
	 * @param A vector of length {@code getArity()} representing a term.
	 * @return A bit indicating the presence of the specified term.
	 */
	public abstract byte at(BitVector term);
	
	/** Returns a list of all terms present in this term table.
	 * 
	 * <p>Each bit vector in the list returned indicates the presence of a
	 * single algebraic term. The lowest-order bit in a vector indicates that
	 * it contains the first argument "x0", and the remaining bits refer to the
	 * remaining consecutive arguments. The term in question is the product of
	 * 1 and all present arguments.
	 * 
	 * <p>E.g. 01101 = "x0 * x2 * x3"
	 * 
	 * <p>The constant term 1 is represented by a vector of zeros.
	 * 
	 * @return A list of bit vectors (each of length {@code getArity()}
	 * representing the terms present in this table.
	 */
	public abstract List<BitVector> getTerms();
	
	/** Whether or not this term table represents a constant function.
	 * 
	 * @return Whether or not this term table represents a constant function.
	 */
	public abstract boolean isConstant();
	
	/** Returns the number of terms present in this term table.
	 * 
	 * @return the number of terms present in this term table.
	 */
	public abstract int getTermCount();
	
	/** Constructs a {@code TruthTable} corresponding to this term table.
	 * 
	 * <p>The resulting truth table corresponds to the values of this term
	 * table evaluated for every possible argument value. Note that this
	 * operation is expensive - it runs in n-squared time, where n is the
	 * length of the truth table.
	 * 
	 * @return a {@code TruthTable} corresponding to this term table.
	 */
	public abstract TruthTable buildTruthTable();
}
