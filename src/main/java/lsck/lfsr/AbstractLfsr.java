package lsck.lfsr;

import lsck.bitwise.BitVector;

/** A base class with common code for implementations of {@link Lfsr}. */
public abstract class AbstractLfsr implements Lfsr {
  
  @Override
  public void setFill(int ... bits) {
    setFill(BitVector.fromBits(bits));
  }

  @Override
  public void setFillFromInteger(long fill) {
    setFill(BitVector.fromInteger(getLength(), fill));
  }

  @Override
  public void setFillFromInteger(int fill) {
    setFill(BitVector.fromInteger(getLength(), fill));
  }

  @Override
  public void setFillFromInteger(short fill) {
    setFill(BitVector.fromInteger(getLength(), fill));
  }

  @Override
  public void setFillFromInteger(byte fill) {
    setFill(BitVector.fromInteger(getLength(), fill));
  }

  @Override
  public void setTaps(int ... bits) {
    setTaps(BitVector.fromBits(bits));
  }

  @Override
  public void setTapsFromInteger(long taps) {
    setTaps(BitVector.fromInteger(getLength(), taps));
  }

  @Override
  public void setTapsFromInteger(int taps) {
    setTaps(BitVector.fromInteger(getLength(), taps));
  }

  @Override
  public void setTapsFromInteger(short taps) {
    setTaps(BitVector.fromInteger(getLength(), taps));
  }

  @Override
  public void setTapsFromInteger(byte taps) {
    setTaps(BitVector.fromInteger(getLength(), taps));
  }
}
