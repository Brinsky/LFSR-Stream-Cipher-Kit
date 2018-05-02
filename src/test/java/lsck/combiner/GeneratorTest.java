package lsck.combiner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import lsck.bitwise.BitList;
import lsck.bitwise.BitVector;
import lsck.lfsr.Lfsr;

public class GeneratorTest {

  private static final int LFSR_COUNT = 4;
  private static final BitVector[] taps =
      new BitVector[] {
        BitVector.fromBits(1, 0, 0, 0, 0, 1, 0, 0, 0),
        BitVector.fromBits(1, 0, 1, 1, 1, 0, 0, 0),
        BitVector.fromBits(1, 0, 1, 1, 1, 0, 0, 0),
        BitVector.fromBits(1, 1, 0, 0, 0, 0, 0)
      };
  private static final BitVector[] fills =
      new BitVector[] {
        BitVector.fromBits(0, 1, 1, 0, 0, 0, 0, 0, 1),
        BitVector.fromBits(0, 0, 0, 0, 1, 1, 0, 1),
        BitVector.fromBits(1, 1, 1, 1, 0, 1, 1, 1),
        BitVector.fromBits(1, 0, 1, 0, 1, 1, 1)
      };
  private static final BooleanFunction combiner =
      new SimpleBooleanFunction(
          new IntegerTruthTable(4, new int[] {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0}));

  private static final BitList expectedBits =
      BitList.fromBits(
          1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1,
          0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0,
          1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0,
          0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0,
          0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0,
          1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0,
          1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1);

  private final Generator generator = newGenerator();

  // Recreate LFSRs from scratch to undo shifts
  private static Generator newGenerator() {
    Lfsr[] lfsrs = new Lfsr[LFSR_COUNT];
    for (int i = 0; i < lfsrs.length; i++) {
      lfsrs[i] = Lfsr.create(taps[i], fills[i]);
    }

    return new Generator(combiner, lfsrs);
  }
  
  @Test
  void testGetCombiner()
  {
    assertEquals(combiner, generator.getCombiner());
  }

  @Test
  void testGetRegisterCount() {
    assertEquals(LFSR_COUNT, generator.getRegisterCount());
  }

  @Test
  void testGetRegister_validIndex() {
    for (int i = 0; i < LFSR_COUNT; i++) {
      assertEquals(Lfsr.create(taps[i], fills[i]), generator.getRegister(i));
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {-10, -1, LFSR_COUNT})
  void testGetRegister_invalidIndex(int index) {
    Exception e = assertThrows(IndexOutOfBoundsException.class, () -> generator.getRegister(index));
    assertEquals("Expected a valid register index; got " + index, e.getMessage());
  }

  @Test
  void testPeek() {
    for (int i = 0; i < expectedBits.size(); i++) {
      assertEquals((int) expectedBits.get(i), generator.peek(), "Mismatch at " + i);
      generator.shift();
    }
  }

  @Test
  void testPeek_terms() {
    assertEquals(expectedBits, generator.peek(expectedBits.size()));
  }

  @Test
  void testPeekAt() {
    for (int i = 0; i < expectedBits.size(); i += 10) {
      assertEquals((int) expectedBits.get(i), generator.peekAt(i), "Mismatch at " + i);
    }
  }
  
  @Test
  void testShift() {
    for (int i = 0; i < expectedBits.size(); i++) {
      assertEquals((int) expectedBits.get(i), generator.shift(), "Mismatch at " + i);
    }
  }

  @Test
  void testShift_terms() {
    assertEquals(expectedBits, generator.shift(expectedBits.size()));
  }

  @Test
  void testShiftAt() {
    for (int i = 0; i < expectedBits.size(); i += 1) {
      assertEquals((int) expectedBits.get(i), generator.shiftTo(0), "Mismatch at " + i);
    }
  }
}
