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
    return new IntegerTermTable(arity, switchBasis(truthTable));
  }

  /**
   * Converts truth tables to term tables and vice versa.
   *
   * @param table An array of bits representing either a truth table or a term table.
   * @return An array of bits representing the transformed table - either a truth table if a term
   *     table was given, or a term table if a truth table was given.
   */
  static byte[] switchBasis(byte[] table) {
    // If the truth tables and term tables for a given function are considered as binary vectors,
    // it is possible to compute one from the other using a change a of basis - i.e. multiplying
    // by a square matrix of basis vectors. The matrix in question can be assembled from the truth
    // tables of each possible ANF term (e.g. the truth tables for 1, x3, x2, x2 x3, x1, x1 x3,
    // x1 x2, and x1 x2 x3 for a 3-variable function) treated as column vectors and joined
    // together. This matrix is also its own inverse, so it can be used to transform between truth
    // tables and term tables in both directions.

    // For implementation purposes, it is easier to describe this matrix as the matrix M where
    // every entry M[i][j] where (i & j) == j.

    byte[] result = new byte[table.length];

    for (int row = 0; row < table.length; row++) {
      for (int col = 0; col <= row; col++) {
        // The below is conceptually equivalent to:
        // result[row] = (result[row] + table[col] * M[row][col]) % 2
        if ((row & col) == col) {
          result[row] ^= table[col];
        }
      }
    }

    return result;
  }
  
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof TruthTable)) {
      return false;
    }
    
    TruthTable other = (TruthTable) o;
    
    if (other.getArity() == arity) {
      // Check for truth table equivalence
      for (int i = 0; i < truthTable.length; i++) {
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
