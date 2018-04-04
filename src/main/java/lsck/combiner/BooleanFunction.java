package lsck.combiner;

import lsck.bitwise.BitVector;

/**
 * Represents a Boolean function.
 *
 * <p>A {@code BooleanFunction} object represents and computes the values of a Boolean function with
 * a fixed number of parameters. Underlying details of the function are made available both as a
 * truth table and as a "term table" - a list of algebraic terms describing a Boolean expression.
 *
 * @see TruthTable
 * @see TermTable
 */
public interface BooleanFunction {

  /**
   * Returns the number of arguments taken by this function.
   *
   * @return the number of arguments taken by this function.
   */
  int getArity();

  /**
   * Returns the value of the function given the specified arguments.
   *
   * <p>The provided bit vector indicates which arguments to evaluate this function for. The least
   * significant bit is treated as the first argument "x0", and the remaining bits correspond to the
   * remaining consecutive arguments.
   *
   * <p>E.g. 01101 implies x0 = 1, x1 = 0, x2 = 1, x3 = 1, and x4 = 0.
   *
   * @param An argument vector of length {@code getArity()}.
   * @return The value of this function evaluated for the specified argument.
   */
  byte at(BitVector args);

  /**
   * Returns a copy of the truth table for this function.
   *
   * @return a copy of the truth table for this function.
   */
  TruthTable getTruthTable();

  /**
   * Returns a copy of the term table for this function.
   *
   * @see TermTable#buildTruthTable()
   */
  TermTable getTermTable();
}
