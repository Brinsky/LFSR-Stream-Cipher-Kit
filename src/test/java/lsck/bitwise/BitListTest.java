package lsck.bitwise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class BitListTest {

  private static final int TEST_BITS_LENGTH = 160;
  private static final int[] TEST_BITS =
      new int[] {
        0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0,
        1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1,
        1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1,
        1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0,
        1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0,
        1, 1, 0, 1, 0
      };

  private BitList testList = BitList.fromBits(TEST_BITS);

  private static List<Integer> toList(int[] array) {
    List<Integer> list = new ArrayList<>(array.length);

    for (int i = 0; i < array.length; i++) {
      list.add(array[i]);
    }

    return list;
  }

  @Test
  void testAdd() {
    BitList list = new BitList();

    for (int i = 0; i < TEST_BITS.length; i++) {
      list.add(TEST_BITS[i]);
      assertEquals(i + 1, list.size());
      assertEquals(TEST_BITS[i], (int) list.get(i));
    }

    // Check that no earlier bits were changed by adding later ones
    for (int i = 0; i < TEST_BITS.length; i++) {
      assertEquals(TEST_BITS[i], (int) list.get(i));
    }
  }

  @Test
  void testAdd_atIndex() {
    BitList list = new BitList();

    int numZeros = 100;
    int numOnes = 10;

    for (int i = 0; i < numZeros; i++) {
      list.add(0);
    }

    for (int i = 0; i < numOnes; i++) {
      list.add(0, 1);
    }

    for (int i = 0; i < numOnes; i++) {
      assertEquals(1, (int) list.get(i), "Differs at index " + i);
    }

    for (int i = numOnes; i < numOnes + numZeros; i++) {
      assertEquals(0, (int) list.get(i), "Differs at index " + i);
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {-10, -1, TEST_BITS_LENGTH + 1})
  void testAdd_invalidIndex(int index) {
    Exception e = assertThrows(IndexOutOfBoundsException.class, () -> testList.add(index, 0));

    assertEquals(
        "Expected a nonnegative index less than " + (testList.size() + 1) + "; got " + index,
        e.getMessage());
  }

  @Test
  void testAddAll() {
    int numZeros = 10;
    BitList list = new BitList();

    for (int i = 0; i < numZeros; i++) {
      list.add(i, 0);
      testList.add(i, 0);
    }

    list.addAll(toList(TEST_BITS));

    assertEquals(testList, list);
  }

  @Test
  void testAddAll_atIndex() {
    BitList list = new BitList();

    int numZeros = 100;
    int numOnes = 10;

    for (int i = 0; i < numZeros; i++) {
      list.add(0);
    }

    list.addAll(0, Collections.nCopies(numOnes, (Integer) 1));

    for (int i = 0; i < numOnes; i++) {
      assertEquals(1, (int) list.get(i), "Differs at index " + i);
    }

    for (int i = numOnes; i < numOnes + numZeros; i++) {
      assertEquals(0, (int) list.get(i), "Differs at index " + i);
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {-10, -1, TEST_BITS_LENGTH + 1})
  void testAddAll_invalidIndex(int index) {
    Exception e =
        assertThrows(IndexOutOfBoundsException.class, () -> testList.addAll(index, new BitList()));

    assertEquals(
        "Expected a nonnegative index less than " + (testList.size() + 1) + "; got " + index,
        e.getMessage());
  }

  @Test
  void testClear() {
    assertNotEquals(0, testList.size());

    testList.clear();

    assertEquals(0, testList.size());
  }

  @Test
  void testContains_1_present() {
    BitList list = new BitList();

    list.addAll(Collections.nCopies(100, (Integer) 0));
    list.add(1);
    list.addAll(Collections.nCopies(100, (Integer) 0));

    assertTrue(list.contains(1));
  }

  @Test
  void testContains_1_notPresent() {
    BitList list = new BitList();

    list.addAll(Collections.nCopies(200, (Integer) 0));

    assertFalse(list.contains(1));
  }

  @Test
  void testContains_0_present() {
    BitList list = new BitList();

    list.addAll(Collections.nCopies(100, (Integer) 1));
    list.add(0);
    list.addAll(Collections.nCopies(100, (Integer) 1));

    assertTrue(list.contains(0));
  }

  @Test
  void testContains_0_notPresent() {
    BitList list = new BitList();

    list.addAll(Collections.nCopies(200, (Integer) 1));

    assertFalse(list.contains(0));
  }

  @Test
  void testGet_validIndex() {
    for (int i = 0; i < TEST_BITS.length; i++) {
      assertEquals(TEST_BITS[i], (int) testList.get(i));
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {-10, -1, TEST_BITS_LENGTH})
  void testGet_invalidIndex(int index) {
    Exception e = assertThrows(IndexOutOfBoundsException.class, () -> testList.get(index));

    assertEquals(
        "Expected a nonnegative index less than " + testList.size() + "; got " + index,
        e.getMessage());
  }

  @Test
  void testIndexOf_1_present() {
    BitList list = new BitList();

    list.addAll(Collections.nCopies(100, (Integer) 0));
    list.add(1);
    list.addAll(Collections.nCopies(100, (Integer) 0));

    assertEquals(100, list.indexOf(1));
  }

  @Test
  void testIndexOf_1_notPresent() {
    BitList list = new BitList();

    list.addAll(Collections.nCopies(200, (Integer) 0));

    assertEquals(-1, list.indexOf(1));
  }

  @Test
  void testIndexOf_0_present() {
    BitList list = new BitList();

    list.addAll(Collections.nCopies(100, (Integer) 1));
    list.add(0);
    list.addAll(Collections.nCopies(100, (Integer) 1));

    assertEquals(100, list.indexOf(0));
  }

  @Test
  void testIndexOf_0_notPresent() {
    BitList list = new BitList();

    list.addAll(Collections.nCopies(200, (Integer) 1));

    assertEquals(-1, list.indexOf(0));
  }

  @Test
  void testIsEmpty() {
    assertFalse(testList.isEmpty());

    testList.clear();

    assertTrue(testList.isEmpty());
  }

  @Test
  void testLastIndexOf_1_present() {
    BitList list = new BitList();

    addCopies(list, 100, 0);
    list.add(1);
    addCopies(list, 99, 0);
    list.add(1);
    addCopies(list, 100, 0);

    assertEquals(200, list.lastIndexOf(1));
  }

  @Test
  void testLastIndexOf_1_notPresent() {
    BitList list = new BitList();

    addCopies(list, 100, 0);

    assertEquals(-1, list.lastIndexOf(1));
  }

  @Test
  void testLastIndexOf_0_present() {
    BitList list = new BitList();

    addCopies(list, 100, 1);
    list.add(0);
    addCopies(list, 99, 1);
    list.add(0);
    addCopies(list, 100, 1);

    assertEquals(200, list.lastIndexOf(0));
  }

  @Test
  void testLastIndexOf_0_notPresent() {
    BitList list = new BitList();

    addCopies(list, 100, 1);

    assertEquals(-1, list.lastIndexOf(0));
  }

  @Test
  void testRemoveValue_1_present() {
    BitList list = new BitList();

    addCopies(list, 100, 0);
    list.add(1);
    addCopies(list, 100, 0);

    assertTrue(list.contains(1));

    list.remove((Integer) 1);

    for (int i = 0; i < list.size(); i++) {
      assertEquals(0, (int) list.get(i));
    }
  }

  @Test
  void testRemoveValue_1_notPresent() {
    BitList list = new BitList();

    addCopies(list, 100, 0);

    assertFalse(list.remove((Integer) 1));
  }

  @Test
  void testRemoveValue_0_present() {
    BitList list = new BitList();

    addCopies(list, 100, 1);
    list.add(0);
    addCopies(list, 100, 1);

    list.remove((Integer) 0);

    for (int i = 0; i < list.size(); i++) {
      assertEquals(1, (int) list.get(i));
    }
  }

  @Test
  void testRemoveValue_0_notPresent() {
    BitList list = new BitList();

    addCopies(list, 100, 1);

    assertFalse(list.remove((Integer) 0));
  }

  @Test
  void testRemoveIndex() {
    BitList list = new BitList();

    addCopies(list, 100, 0);
    list.add(1);
    addCopies(list, 100, 0);

    list.remove(100);

    for (int i = 0; i < list.size(); i++) {
      assertEquals(0, (int) list.get(i));
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {-10, -1, TEST_BITS_LENGTH})
  void testRemoveIndex_invalidIndex(int index) {
    Exception e = assertThrows(IndexOutOfBoundsException.class, () -> testList.remove(index));

    assertEquals(
        "Expected a nonnegative index less than " + testList.size() + "; got " + index,
        e.getMessage());
  }

  @Test
  void testSet() {
    BitList list = new BitList();

    addCopies(list, 100, 0);

    for (int i = 0; i < 100; i += 2) {
      list.set(i, 0);
      list.set(i + 1, 1);
    }

    for (int i = 0; i < 100; i += 2) {
      assertEquals(0, (int) list.get(i), "Differs at index " + i);
      assertEquals(1, (int) list.get(i + 1), "Differs at index " + (i + 1));
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {-10, -1, TEST_BITS_LENGTH})
  void testSet_invalidIndex(int index) {
    Exception e = assertThrows(IndexOutOfBoundsException.class, () -> testList.set(index, 0));

    assertEquals(
        "Expected a nonnegative index less than " + testList.size() + "; got " + index,
        e.getMessage());
  }

  @Test
  void testSize() {
    assertEquals(TEST_BITS.length, testList.size());

    testList.clear();

    assertEquals(0, testList.size());
  }

  @Test
  void testSubList_all() {
    assertEquals(testList, testList.subList(0, testList.size()));
  }

  @Test
  void testSubList_partial() {
    BitList list = new BitList();

    addCopies(list, 100, 0);
    addCopies(list, 100, 1);
    addCopies(list, 100, 0);

    BitList sublist = new BitList();

    addCopies(sublist, 100, 1);

    assertEquals(sublist, list.subList(100, 200));
  }
  
  @Test
  void testSubList_empty() {
    BitList list = new BitList();

    addCopies(list, 100, 0);

    assertEquals(new BitList(), list.subList(50, 50));
  }

  static Stream<Arguments> getSubListArgs() {
    return Stream.of(Arguments.of(10, 0, 11), Arguments.of(10, -1, 10), Arguments.of(10, 6, 5));
  }

  @ParameterizedTest
  @MethodSource("getSubListArgs")
  void testSubList_invalidIndices(int size, int fromIndex, int toIndex) {
    BitList list = new BitList();
    addCopies(list, size, 0);

    Exception e =
        assertThrows(IndexOutOfBoundsException.class, () -> list.subList(fromIndex, toIndex));

    assertEquals(
        "Expected 0 <= fromIndex <= startIndex <= "
            + size
            + "; got fromIndex: "
            + fromIndex
            + ", toIndex: "
            + toIndex,
        e.getMessage());
  }
  
  @Test
  void testToArray() {
    Integer[] array = (Integer[]) testList.toArray();
    
    for (int i = 0; i < TEST_BITS.length; i++) {
      assertEquals(TEST_BITS[i], (int) array[i]);
    }
  }
  
  @Test
  void testToArrayT_equalLength() {
    Integer[] array = new Integer[testList.size()];
    
    array = testList.toArray(array);
    
    for (int i = 0; i < TEST_BITS.length; i++) {
      assertEquals(TEST_BITS[i], (int) array[i]);
    }
  }
  
  @Test
  void testToArrayT_shortedLength() {
    Integer[] array = new Integer[0];
    
    array = testList.toArray(array);
    
    for (int i = 0; i < TEST_BITS.length; i++) {
      assertEquals(TEST_BITS[i], (int) array[i]);
    }
  }
  
  @Test
  void testToArrayT_longerLength() {
    Integer[] array = new Integer[testList.size() + 10];
    
    array = testList.toArray(array);
    
    for (int i = 0; i < TEST_BITS.length; i++) {
      assertEquals(TEST_BITS[i], (int) array[i]);
    }
    assertEquals(null, array[testList.size()]);
  }
 
  @Test
  void testToByteArray() {
    byte[] array = testList.toByteArray();
    
    for (int i = 0; i < TEST_BITS.length; i++) {
      assertEquals(TEST_BITS[i], array[i]);
    }
  }

  @Test
  void testToIntArray() {
    int[] array = testList.toIntArray();
    
    for (int i = 0; i < TEST_BITS.length; i++) {
      assertEquals(TEST_BITS[i], array[i]);
    }
  }
  
  @ParameterizedTest
  @ValueSource(ints = {0, 10, 100, 1000})
  void testGetCapacity(int size) {
    assertTrue(new BitList(size).getCapacity() >= size);
  }

  private static void addCopies(BitList list, int copies, int value) {
    list.addAll(Collections.nCopies(copies, value));
  }
}
