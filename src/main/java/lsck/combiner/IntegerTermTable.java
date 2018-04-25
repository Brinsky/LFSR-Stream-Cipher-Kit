package lsck.combiner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lsck.bitwise.BitUtility;
import lsck.bitwise.BitVector;
import lsck.bitwise.IntegerSequence;
import lsck.common.Exceptions;

public class IntegerTermTable implements TermTable {

  public static final int MAX_ARITY = 30;

  private final byte[] termTable;
  private final int arity;

  private int termCount = 0;

  /**
   * Creates a term table from a string representation of a Boolean expression.
   *
   * @param arity The number of variables for the represented function.
   * @param expression A string representation of a Boolean function. See {@link
   *     BooleanFunction#fromString(int, String, boolean)} for details.
   * @param indexFromZero If {@code true}, variables in the string representation are assumed to be
   *     indexed from zero. Otherwise, they are assumed to be indexed from 1.
   */
  public IntegerTermTable(int arity, String expression, boolean indexFromZero) {
    this(arity, BooleanFunctionParser.expand(arity, expression, indexFromZero));
  }

  /**
   * Creates a term table from the given set of terms.
   *
   * @param arity The number of variables for the represented function.
   * @param terms A set of terms that should be added to the table, represented by {@link
   *     BitVector}s. See {@link TermTable#at(BitVector)} for a detailed description of the
   *     representation.
   */
  public IntegerTermTable(int arity, Set<BitVector> terms) {
    if (arity < 1 || arity > MAX_ARITY) {
      throw Exceptions.invalidArity(arity, MAX_ARITY);
    }

    this.arity = arity;
    termTable = new byte[1 << arity];

    for (BitVector term : terms) {
      if (term.getLength() != arity) {
        throw Exceptions.vectorArityMismatch(arity, term.getLength());
      }

      termTable[term.toInt()] = 1;
      termCount++;
    }
  }

  /**
   * Creates a term table from the given array.
   *
   * @param arity The number of variables for the represented function.
   * @param termTable An array representing the term table. {@link BitUtility#asBit(int)} is called
   *     on each element of this array to determine the corresponding binary value.
   */
  public IntegerTermTable(int arity, int[] termTable) {
    this(arity, termTable.length, i -> termTable[i]);
  }

  /**
   * Creates a term table from the given array.
   *
   * @param arity The number of variables for the represented function.
   * @param termTable An array representing the term table. {@link BitUtility#asBit(int)} is called
   *     on each element of this array to determine the corresponding binary value.
   */
  public IntegerTermTable(int arity, byte[] termTable) {
    this(arity, termTable.length, i -> termTable[i]);
  }

  private IntegerTermTable(int arity, int length, IntegerSequence data) {
    if (arity < 1 || arity > MAX_ARITY) {
      throw Exceptions.invalidArity(arity, MAX_ARITY);
    } else if (length != 1 << arity) {
      throw Exceptions.tableLength(arity, length);
    }

    this.arity = arity;
    termTable = new byte[1 << arity];

    for (int i = 0; i < termTable.length; i++) {
      termTable[i] = BitUtility.asBit(data.get(i));
      termCount += termTable[i];
    }
  }

  @Override
  public int getArity() {
    return arity;
  }

  @Override
  public int getLength() {
    return termTable.length;
  }

  @Override
  public byte at(BitVector term) {
    if (term.getLength() != arity) {
      throw Exceptions.vectorArityMismatch(arity, term.getLength());
    }

    return termTable[term.toInt()];
  }

  @Override
  public List<BitVector> getTerms() {
    List<BitVector> terms = new ArrayList<>(termCount);

    for (int i = 0; i < termTable.length; i++) {
      if (termTable[i] != 0) {
        terms.add(BitVector.fromInteger(arity, i));
      }
    }

    return terms;
  }

  @Override
  public boolean isConstant() {
    return termCount == 0 || (termCount == 1 && termTable[0] == 1);
  }

  @Override
  public int getTermCount() {
    return termCount;
  }

  @Override
  public TruthTable buildTruthTable() {
    return new IntegerTruthTable(arity, IntegerTruthTable.switchBasis(termTable));
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof TermTable)) {
      return false;
    }

    TermTable other = (TermTable) o;

    if (other.getArity() == arity) {
      // Check for term table equivalence
      for (int i = 0; i < termTable.length; i++) {
        BitVector argument = BitVector.fromInteger(arity, i);
        if (at(argument) != other.at(argument)) {
          return false;
        }
      }

      return true;
    }

    return false;
  }
}
