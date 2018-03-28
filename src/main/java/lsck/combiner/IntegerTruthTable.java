package lsck.combiner;

import java.util.ArrayList;
import java.util.List;

import lsck.bitwise.BitUtility;
import lsck.bitwise.BitVector;
import lsck.bitwise.IntegerSequence;
import lsck.common.Exceptions;

// Supports 30-argument functions
public class IntegerTruthTable implements TruthTable {

  public static final int MAX_ARITY = 30;

  private final byte[] truthTable;
  private final int arity;

  private int weight = 0;

  /**
   * Creates a truth table from the given array.
   *
   * @param arity The number of variables for the represented function.
   * @param truthTable An array representing the truth table. {@link BitUtility#asBit(int)} is
   *     called on each element of this array to determine the corresponding binary value.
   */
  public IntegerTruthTable(int arity, int[] truthTable) {
    this(arity, truthTable.length, i -> truthTable[i]);
  }

  /**
   * Creates a truth table from the given array.
   *
   * @param arity The number of variables for the represented function.
   * @param truthTable An array representing the truth table. {@link BitUtility#asBit(int)} is
   *     called on each element of this array to determine the corresponding binary value.
   */
  public IntegerTruthTable(int arity, byte[] truthTable) {
    this(arity, truthTable.length, i -> truthTable[i]);
  }

  private IntegerTruthTable(int arity, int length, IntegerSequence data) {
    if (arity < 1 || arity > MAX_ARITY) {
      throw Exceptions.invalidArityException(arity, MAX_ARITY);
    } else if (length != 1 << arity) {
      throw Exceptions.tableLengthException(arity, length);
    }

    this.arity = arity;
    truthTable = new byte[1 << arity];

    for (int i = 0; i < truthTable.length; i++) {
      truthTable[i] = BitUtility.asBit(data.get(i));
      weight += truthTable[i];
    }
  }

  @Override
  public int getArity() {
    return arity;
  }

  @Override
  public int getLength() {
    return truthTable.length;
  }

  @Override
  public byte at(BitVector args) {
    if (args.getLength() != arity) {
      throw Exceptions.vectorLengthException(arity, args.getLength());
    }

    return truthTable[args.toInt()];
  }

  @Override
  public List<BitVector> getSupport() {
    List<BitVector> support = new ArrayList<>(weight);

    for (int i = 0; i < truthTable.length; i++) {
      if (truthTable[i] != 0) {
        support.add(BitVector.fromInteger(arity, i));
      }
    }

    return support;
  }

  @Override
  public int getWeight() {
    return weight;
  }

  @Override
  public boolean isConstant() {
    return weight == 0 || weight == truthTable.length;
  }

  @Override
  public TermTable buildTermTable() {
    // TODO Auto-generated method stub
    return null;
  }
}
