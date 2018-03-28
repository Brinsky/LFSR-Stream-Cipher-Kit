package lsck.lfsr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import lsck.bitwise.BitVector;

/** Tests for subclasses of {@link Lfsr}. */
public abstract class BaseLfsrTest {

  // Data provider methods for subclasses to implement

  abstract Lfsr getLfsr();

  abstract int getExpectedLength();

  abstract BitVector getTestFill();

  abstract BitVector getTestTaps();

  abstract List<Byte> getExpectedOutput();

  abstract BitVector getExpectedFinalFill();

  @Test
  void getLengthTest() {
    assertEquals(getExpectedLength(), getLfsr().getLength());
  }

  @Test
  void getFillTest() {
    assertEquals(getTestFill(), getLfsr().getFill());
  }

  @Test
  void getFillAtTest() {
    Lfsr lfsr = getLfsr();

    for (int i = 0; i < lfsr.getLength(); i++) {
      assertEquals(getTestFill().get(i), lfsr.getFillAt(i));
    }
  }

  @Test
  void getTapsTest() {
    assertEquals(getTestTaps(), getLfsr().getTaps());
  }

  @Test
  void getTapsAtTest() {
    Lfsr lfsr = getLfsr();

    for (int i = 0; i < lfsr.getLength(); i++) {
      assertEquals(getTestTaps().get(i), lfsr.getTapsAt(i));
    }
  }

  @Test
  void setFillTest() {
    Lfsr lfsr = getLfsr();
    BitVector nullVector = BitVector.nullVector(lfsr.getLength());

    // Clear fill with an empty vector
    lfsr.setFill(nullVector);
    assertEquals(nullVector, lfsr.getFill());

    // Reset to original fill
    lfsr.setFill(getTestFill());
    assertEquals(getTestFill(), lfsr.getFill());
  }

  @Test
  void setFillAtTest() {
    Lfsr lfsr = getLfsr();
    BitVector nullVector = BitVector.nullVector(lfsr.getLength());

    // Clear fill with an empty vector
    for (int i = 0; i < lfsr.getLength(); i++) {
      lfsr.setFillAt(i, 0);
    }
    assertEquals(nullVector, lfsr.getFill());

    // Reset to original fill
    for (int i = 0; i < lfsr.getLength(); i++) {
      lfsr.setFillAt(i, getTestFill().get(i));
    }
    assertEquals(getTestFill(), lfsr.getFill());
  }

  @Test
  void setTapsTest() {
    Lfsr lfsr = getLfsr();
    BitVector nullVector = BitVector.nullVector(lfsr.getLength());

    // Clear fill with an empty vector
    lfsr.setTaps(nullVector);
    assertEquals(nullVector, lfsr.getTaps());

    // Reset to original fill
    lfsr.setTaps(getTestTaps());
    assertEquals(getTestTaps(), lfsr.getTaps());
  }

  @Test
  void setTapsAtTest() {
    Lfsr lfsr = getLfsr();
    BitVector nullVector = BitVector.nullVector(lfsr.getLength());

    // Clear fill with an empty vector
    for (int i = 0; i < lfsr.getLength(); i++) {
      lfsr.setTapsAt(i, 0);
    }
    assertEquals(nullVector, lfsr.getTaps());

    // Reset to original fill
    for (int i = 0; i < lfsr.getLength(); i++) {
      lfsr.setTapsAt(i, getTestTaps().get(i));
    }
    assertEquals(getTestTaps(), lfsr.getTaps());
  }

  @Test
  void shiftTest_single() {
    Lfsr lfsr = getLfsr();
    List<Byte> expected = getExpectedOutput();

    for (int i = 0; i < expected.size(); i++) {
      assertEquals((byte) expected.get(i), lfsr.shift(), "Unequal at index " + i);
    }

    assertEquals(getExpectedFinalFill(), lfsr.getFill());
  }

  @Test
  void shiftTest_all() {
    Lfsr lfsr = getLfsr();
    List<Byte> expected = getExpectedOutput();

    assertEquals(expected, lfsr.shift(expected.size()));
    assertEquals(getExpectedFinalFill(), lfsr.getFill());
  }

  @Test
  void shiftToTest() {
    Lfsr lfsr = getLfsr();
    List<Byte> expected = getExpectedOutput();
    int last = expected.size() - 1;

    assertEquals((byte) expected.get(last), lfsr.shiftTo(last));
    assertEquals(getExpectedFinalFill(), lfsr.getFill());
  }

  @Test
  void peekTest_single() {
    Lfsr lfsr = getLfsr();
    List<Byte> expected = getExpectedOutput();

    assertEquals((byte) expected.get(0), lfsr.peek());
    assertEquals(getTestFill(), lfsr.getFill());
  }

  @Test
  void peekTest_all() {
    Lfsr lfsr = getLfsr();
    List<Byte> expected = getExpectedOutput();

    assertEquals(expected, lfsr.peek(expected.size()));
    assertEquals(getTestFill(), lfsr.getFill());
  }

  @Test
  void peekAtTest() {
    Lfsr lfsr = getLfsr();
    List<Byte> expected = getExpectedOutput();

    // Try a sane number of peekAt() operations
    int step = expected.size() / 10;
    for (int i = 0; i < expected.size(); i += step) {
      assertEquals((byte) expected.get(i), lfsr.peekAt(i), "Unequal at index " + i);
      assertEquals(getTestFill(), lfsr.getFill());
    }
  }
}
