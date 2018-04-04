package lsck.combiner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lsck.bitwise.BitVector;

public class SimpleBooleanFunctionTest {

  private static final TruthTable TRUTH_TABLE =
      new IntegerTruthTable(3, new int[] {0, 1, 1, 0, 1, 0, 0, 1});
  private static final TermTable TERM_TABLE = 
      new IntegerTermTable(3, new int[] {0, 1, 1, 0, 1, 0, 0, 0});
  
  private static final SimpleBooleanFunction FUNCTION = new SimpleBooleanFunction(TRUTH_TABLE);
  
  @Test
  void testConstructor_equivalence() {
    assertEquals(new SimpleBooleanFunction(TRUTH_TABLE), new SimpleBooleanFunction(TERM_TABLE));
  }
  
  @Test
  void testAt() {
    for (int i = 0; i < TRUTH_TABLE.getLength(); i++) {
      BitVector args = BitVector.fromInteger(FUNCTION.getArity(), i);
      assertEquals(TRUTH_TABLE.at(args), FUNCTION.at(args));
    }
  }

  @Test
  void testGetTruthTable() {
    assertEquals(TRUTH_TABLE, FUNCTION.getTruthTable());
  }
  
  @Test
  void testGetTermTable() {
    assertEquals(TERM_TABLE, FUNCTION.getTermTable());
  }
}
