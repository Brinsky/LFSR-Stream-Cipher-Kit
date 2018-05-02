package lsck.combiner;

import lsck.bitwise.BitVector;

public abstract class AbstractBooleanFunction implements BooleanFunction {

  @Override
  public byte at(int... args) {
    return at(BitVector.fromBits(args));
  }

  @Override
  public byte atInteger(long args) {
    return at(BitVector.fromInteger(getArity(), args));
  }

  @Override
  public byte atInteger(int args) {
    return at(BitVector.fromInteger(getArity(), args));
  }

  @Override
  public byte atInteger(short args) {
    return at(BitVector.fromInteger(getArity(), args));
  }

  @Override
  public byte atInteger(byte args) {
    return at(BitVector.fromInteger(getArity(), args));
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BooleanFunction)) {
      return false;
    }

    BooleanFunction other = (BooleanFunction) o;

    return getArity() == other.getArity() && getTruthTable().equals(other.getTruthTable());
  }
}
