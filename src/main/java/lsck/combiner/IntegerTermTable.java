package lsck.combiner;

import java.util.ArrayList;
import java.util.List;

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
      throw Exceptions.invalidArityException(arity, MAX_ARITY);
    } else if (length != 1 << arity) {
      throw Exceptions.tableLengthException(arity, length);
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
      throw Exceptions.vectorLengthException(arity, term.getLength());
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
    // TODO Auto-generated method stub
    return null;
  }
}
