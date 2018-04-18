package lsck.bitwise;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** Tests for the static methods in {@link BitVector}. */
public class BitVectorTest {
  
  @ParameterizedTest
  @ValueSource(ints = {1, 10, 100})
  void testZeroVector(int length) {
    BitVector vector = BitVector.zeroVector(length);
    
    for (int i = 0; i < length; i++) {
      assertEquals(0, vector.get(i), "1 at index " + i);
    }
  }
  
  @ParameterizedTest
  @ValueSource(ints = {1, 10, 100})
  void testOneVector(int length) {
    BitVector vector = BitVector.oneVector(length);
    
    for (int i = 0; i < length; i++) {
      assertEquals(1, vector.get(i), "0 at index " + i);
    }
  }
}
