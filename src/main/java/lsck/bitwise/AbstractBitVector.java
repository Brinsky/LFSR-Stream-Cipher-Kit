package lsck.bitwise;

/** A base class with common code for implementations of {@link BitVector}. */
public abstract class AbstractBitVector implements BitVector {

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BitVector)) {
      return false;
    }

    BitVector other = (BitVector) o;

    if (getLength() != other.getLength()) {
      return false;
    }

    // Bit-by-bit comparison
    for (int i = 0; i < getLength(); i++) {
      if (get(i) != other.get(i)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public String toString() {
    return BitUtility.bitString(this);
  }
}
