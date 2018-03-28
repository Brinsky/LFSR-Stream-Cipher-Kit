package lsck.combiner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import lsck.bitwise.BitUtility;
import lsck.bitwise.BitVector;

/** Tests for {@link IntegerTruthTable}. */
public class IntegerTruthTableTest {

  private static final int ARITY = 6;
  private static final int[] ARRAY =
      new int[] {
        0, 1, 0, 0, 1, 1, 0, 1,
        1, 1, 0, 1, 0, 0, 0, 1,
        0, 1, 0, 0, 1, 1, 0, 0,
        1, 1, 1, 0, 0, 1, 0, 0,
        0, 0, 1, 1, 0, 1, 0, 1,
        1, 1, 1, 0, 0, 1, 1, 0,
        1, 0, 1, 0, 1, 1, 0, 0,
        1, 0, 0, 0, 1, 0, 0, 1
      };
  private static final TruthTable TRUTH_TABLE = new IntegerTruthTable(ARITY, ARRAY);
  private static final int[] SUPPORT =
      new int[] {
        1, 4, 5, 7, 8, 9, 11, 15, 17, 20, 21, 24, 25, 26, 29, 34, 35, 37, 39, 40, 41, 42, 45, 46,
        48, 50, 52, 53, 56, 60, 63
      };

  @ParameterizedTest
  @ValueSource(ints = {-1, 0, IntegerTruthTable.MAX_ARITY + 1})
  void constructorTest_invalidArity(int arity) {
    Exception e =
        assertThrows(
            IllegalArgumentException.class, () -> new IntegerTruthTable(arity, new int[0]));

    assertEquals(
        "Expected arity between 1 and " + IntegerTruthTable.MAX_ARITY + ", inclusive; got " + arity,
        e.getMessage());
  }

  @Test
  void constructorTest_incorrectIntArrayLength() {
    int wrongLength = (1 << ARITY) - 1;

    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () -> new IntegerTruthTable(ARITY, new int[wrongLength]));

    assertEquals(
        "Table should be of length 2^arity (" + (1 << ARITY) + "); got " + wrongLength,
        e.getMessage());
  }

  @Test
  void constructorTest_incorrectByteArrayLength() {
    int wrongLength = (1 << ARITY) - 1;

    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () -> new IntegerTruthTable(ARITY, new byte[wrongLength]));

    assertEquals(
        "Table should be of length 2^arity (" + (1 << ARITY) + "); got " + wrongLength,
        e.getMessage());
  }

  @Test
  void getArityTest() {
    assertEquals(ARITY, TRUTH_TABLE.getArity());
  }

  @Test
  void getLengthTest() {
    assertEquals(1 << ARITY, TRUTH_TABLE.getLength());
    assertEquals(ARRAY.length, TRUTH_TABLE.getLength());
  }

  @Test
  void atTest() {
    for (int i = 0; i < TRUTH_TABLE.getLength(); i++) {
      assertEquals(BitUtility.asBit(ARRAY[i]), TRUTH_TABLE.at(BitVector.fromInteger(ARITY, i)));
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {0, ARITY - 1, ARITY + 1})
  void atTest_wrongVectorLength() {
    BitVector vector = BitVector.nullVector(ARITY + 1);

    Exception e = assertThrows(IllegalArgumentException.class, () -> TRUTH_TABLE.at(vector));

    assertEquals(
        "Expected vector length to match arity of " + ARITY + "; got " + vector.getLength(),
        e.getMessage());
  }

  @Test
  void getSupportTest() {
    List<BitVector> support = TRUTH_TABLE.getSupport();
    assertEquals(SUPPORT.length, support.size());

    for (int argument : SUPPORT) {
      assertTrue(support.contains(BitVector.fromInteger(ARITY, argument)));
    }
  }

  @Test
  void getWeight() {
    int weight = 0;

    for (int i = 0; i < ARRAY.length; i++) {
      weight += ARRAY[i];
    }

    assertEquals(weight, TRUTH_TABLE.getWeight());
  }

  @Test
  void isConstantTest_nonConstant() {
    assertFalse(TRUTH_TABLE.isConstant());
  }

  @Test
  void isConstantTest_constantOne() {
    int[] table = new int[16];
    Arrays.fill(table, 1);

    assertTrue(new IntegerTruthTable(4, table).isConstant());
  }

  @Test
  void isConstantTest_constantZero() {
    int[] table = new int[16];
    Arrays.fill(table, 0);

    assertTrue(new IntegerTruthTable(4, table).isConstant());
  }
}