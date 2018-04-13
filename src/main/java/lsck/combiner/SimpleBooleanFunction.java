package lsck.combiner;

import lsck.bitwise.BitVector;

/** An implementation of {@link BooleanFunction} */
public class SimpleBooleanFunction extends AbstractBooleanFunction {

  private final int arity;
  private final TruthTable truthTable;
  private final TermTable termTable;

  public SimpleBooleanFunction(TruthTable truthTable) {
    this.arity = truthTable.getArity();

    this.truthTable = truthTable;
    this.termTable = truthTable.buildTermTable();
  }

  public SimpleBooleanFunction(TermTable termTable) {
    this.arity = termTable.getArity();

    this.truthTable = termTable.buildTruthTable();
    this.termTable = termTable;
  }

  @Override
  public int getArity() {
    return arity;
  }

  @Override
  public byte at(BitVector args) {
    return truthTable.at(args);
  }

  @Override
  public TruthTable getTruthTable() {
    return truthTable;
  }

  @Override
  public TermTable getTermTable() {
    return termTable;
  }
}
