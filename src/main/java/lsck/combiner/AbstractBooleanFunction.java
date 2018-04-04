package lsck.combiner;

public abstract class AbstractBooleanFunction implements BooleanFunction {

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BooleanFunction)) {
      return false;
    }
    
    BooleanFunction other = (BooleanFunction) o;
    
    return getArity() == other.getArity() && getTruthTable().equals(other.getTruthTable());
  }
}
