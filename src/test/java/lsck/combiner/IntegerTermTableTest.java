package lsck.combiner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import lsck.bitwise.BitUtility;
import lsck.bitwise.BitVector;

/** Tests for {@link IntegerTermTable}. */
public class IntegerTermTableTest {

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
  private static final TermTable TERM_TABLE = new IntegerTermTable(ARITY, ARRAY);
  private static final int[] TERMS =
      new int[] {
        1, 4, 5, 7, 8, 9, 11, 15, 17, 20, 21, 24, 25, 26, 29, 34, 35, 37, 39, 40, 41, 42, 45, 46,
        48, 50, 52, 53, 56, 60, 63
      };

  @ParameterizedTest
  @ValueSource(ints = {-1, 0, IntegerTermTable.MAX_ARITY + 1})
  void constructorTest_invalidArity(int arity) {
    Exception e =
        assertThrows(IllegalArgumentException.class, () -> new IntegerTermTable(arity, new int[0]));

    assertEquals(
        "Expected arity between 1 and " + IntegerTermTable.MAX_ARITY + ", inclusive; got " + arity,
        e.getMessage());
  }

  @Test
  void constructorTest_incorrectIntArrayLength() {
    int wrongLength = (1 << ARITY) - 1;

    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () -> new IntegerTermTable(ARITY, new int[wrongLength]));

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
            () -> new IntegerTermTable(ARITY, new byte[wrongLength]));

    assertEquals(
        "Table should be of length 2^arity (" + (1 << ARITY) + "); got " + wrongLength,
        e.getMessage());
  }

  @Test
  void getArityTest() {
    assertEquals(ARITY, TERM_TABLE.getArity());
  }

  @Test
  void getLengthTest() {
    assertEquals(1 << ARITY, TERM_TABLE.getLength());
    assertEquals(ARRAY.length, TERM_TABLE.getLength());
  }

  @Test
  void atTest() {
    for (int i = 0; i < TERM_TABLE.getLength(); i++) {
      assertEquals(BitUtility.asBit(ARRAY[i]), TERM_TABLE.at(BitVector.fromInteger(ARITY, i)));
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {0, ARITY - 1, ARITY + 1})
  void atTest_wrongVectorLength() {
    BitVector vector = BitVector.nullVector(ARITY + 1);

    Exception e = assertThrows(IllegalArgumentException.class, () -> TERM_TABLE.at(vector));

    assertEquals(
        "Expected vector length to match arity of " + ARITY + "; got " + vector.getLength(),
        e.getMessage());
  }

  @Test
  void getSupportTest() {
    List<BitVector> terms = TERM_TABLE.getTerms();
    assertEquals(TERMS.length, terms.size());

    for (int argument : TERMS) {
      assertTrue(terms.contains(BitVector.fromInteger(ARITY, argument)));
    }
  }

  @Test
  void getWeight() {
    int weight = 0;

    for (int i = 0; i < ARRAY.length; i++) {
      weight += ARRAY[i];
    }

    assertEquals(weight, TERM_TABLE.getTermCount());
  }

  @Test
  void isConstantTest_nonConstant() {
    assertFalse(TERM_TABLE.isConstant());
  }

  @Test
  void isConstantTest_constantOne() {
    // Term table of the function "1"
    int[] table = new int[16];
    table[0] = 1;

    assertTrue(new IntegerTermTable(4, table).isConstant());
  }

  @Test
  void isConstantTest_constantZero() {
    int[] table = new int[16]; // All zeros

    assertTrue(new IntegerTermTable(4, table).isConstant());
  }
  

  /** Test values for {@link #buildTruthTableTest(TruthTable, TermTable)}. */
  static Stream<Arguments> buildTruthTableProvider() {
    return Stream.of(
        // x1 + x2 + x3
        Arguments.of(
            new IntegerTermTable(3, new byte[] {0,1,1,0,1,0,0,0}),
            new IntegerTruthTable(3, new byte[] {0,1,1,0,1,0,0,1})),

        // 1
        Arguments.of(
            new IntegerTermTable(3, new byte[] {1,0,0,0,0,0,0,0}),
            new IntegerTruthTable(3, new byte[] {1,1,1,1,1,1,1,1})),
        
        // 1 + x1 + x2 + x3 + x1 x2 + x1 x3 + x2 x3 + x1 x2 x3
        Arguments.of(
            new IntegerTermTable(3, new byte[] {1,1,1,1,1,1,1,1}),
            new IntegerTruthTable(3, new byte[] {1,0,0,0,0,0,0,0})),
        
        // x1 + x2 + x3 x4
        Arguments.of(
            new IntegerTermTable(4, new byte[] {0,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0}),
            new IntegerTruthTable(4, new byte[] {0,0,0,1,1,1,1,0,1,1,1,0,0,0,0,1})));
  }
  
  @ParameterizedTest
  @MethodSource("buildTruthTableProvider")
  void buildTruthTableTest(TermTable given, TruthTable expected) {
    assertEquals(expected, given.buildTruthTable());
  }
}
