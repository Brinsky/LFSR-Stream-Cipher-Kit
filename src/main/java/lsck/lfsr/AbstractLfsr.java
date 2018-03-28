package lsck.lfsr;

import lsck.bitwise.BitVector;
import lsck.common.Exceptions;

/** A base class with common code for implementations of {@link Lfsr}. */
public abstract class AbstractLfsr implements Lfsr {

  public void setFill(long fill) {
    if (Long.SIZE < getLength()) {
      throw Exceptions.invalidVectorLengthException(getLength(), Long.SIZE);
    }

    setFill(BitVector.fromInteger(getLength(), fill));
  }

  public void setTaps(long taps) {
    if (Long.SIZE < getLength()) {
      throw Exceptions.invalidVectorLengthException(getLength(), Long.SIZE);
    }

    setTaps(BitVector.fromInteger(getLength(), taps));
  }
}
