package lsck.bitwise;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lsck.common.Exceptions;

/**
 * A space-efficient list of bits.
 *
 * <p>For convenience, bits are accepted and returned as {@code int} values, where the "bit" in
 * question is 0 if and only if the corresponding {@code int} is 0.
 */
public class BitList extends AbstractList<Integer> {

  private static final int INITIAL_CAPACITY = 2; // In terms of longs

  private long[] bits;
  private int capacity = INITIAL_CAPACITY;
  private int size = 0;

  /** Creates an empty {@link BitList}. */
  public BitList() {
    bits = new long[capacity];
  }

  /**
   * Creates a {@link BitList} with the specified initial capacity.
   *
   * @param initialCapacity The number of bits this list will initially be able to store without
   *     resizing.
   */
  public BitList(int initialCapacity) {
    if (initialCapacity < 0) {
      throw Exceptions.invalidCapacity(initialCapacity);
    }

    // To get capacity in terms of longs, divide by 64 and round up
    capacity = minimumCapacity(initialCapacity);
    bits = new long[capacity];
  }
  
  /** Creates a copy of the provided list of bits.
   * 
   * @param list A list of bits to created a {@link BitList}-based copy of.
   */
  public BitList(List<Integer> list) {
    this(list.size());
    addAll(list);
  }

  /** A convenient proxy for {@link #add(Integer)}. */
  public boolean add(byte b) {
    return add((int) b);
  }

  /** A convenient proxy for {@link #add(Integer)}. */
  public boolean add(int b) {
    if (capacity <= size) {
      increaseCapacity();
    }

    BitUtility.setBit(bits, size++, b);

    return true;
  }

  @Override
  public boolean add(Integer b) {
    return add((int) b);
  }

  @Override
  public void add(int index, Integer bit) {
    rangeCheckForAdd(index);

    // Make sure capacity is at least size + 1
    if (size >= capacity) {
      increaseCapacity();
    }

    // Right shift all bits at/above index
    for (int i = size - 1; i >= index; i--) {
      BitUtility.setBit(bits, i + 1, BitUtility.getBit(bits, i));
    }
    size++;

    BitUtility.setBit(bits, index, bit);
  }

  @Override
  public boolean addAll(Collection<? extends Integer> c) {
    return addAll(size, c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends Integer> c) {
    rangeCheckForAdd(index);

    int deltaSize = c.size();

    if (deltaSize == 0) {
      return false;
    }

    if ((size + deltaSize) >= capacity) {
      increaseCapacity(size + deltaSize);
    }

    // Right shift all bits at/above index
    for (int i = size - 1; i >= index; i--) {
      BitUtility.setBit(bits, index + deltaSize, BitUtility.getBit(bits, index));
    }

    int j = index;
    for (int bit : c) {
      BitUtility.setBit(bits, j, bit);
      j++;
    }
    size += deltaSize;

    return true;
  }

  @Override
  public void clear() {
    // No need to clear the longs, since their old contents will get overwritten with each add()
    size = 0;
  }

  @Override
  public boolean contains(Object o) {
    if (!(o instanceof Integer)) {
      return false;
    }

    byte value = BitUtility.asBit((Integer) o);

    for (int i = 0; i < size; i++) {
      if (BitUtility.getBit(bits, i) == value) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Integer get(int index) {
    rangeCheck(index);

    return (int) BitUtility.getBit(bits, index);
  }

  @Override
  public int indexOf(Object o) {
    if (!(o instanceof Integer)) {
      return -1;
    }

    byte value = BitUtility.asBit((Integer) o);

    for (int i = 0; i < size; i++) {
      if (BitUtility.getBit(bits, i) == value) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int lastIndexOf(Object o) {
    if (!(o instanceof Integer)) {
      return -1;
    }

    byte value = BitUtility.asBit((Integer) o);

    for (int i = size - 1; i >= 0; i--) {
      if (BitUtility.getBit(bits, i) == value) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public boolean remove(Object o) {
    if (!(o instanceof Integer)) {
      return false;
    }

    byte value = BitUtility.asBit((Integer) o);

    for (int i = 0; i < size; i++) {
      if (BitUtility.getBit(bits, i) == value) {
        remove(i);
        return true;
      }
    }

    return false;
  }

  @Override
  public Integer remove(int index) {
    rangeCheck(index);

    int old = get(index);

    // Left shift all higher-order bits
    size--;
    for (int i = index; i < size; i++) {
      BitUtility.setBit(bits, i, BitUtility.getBit(bits, i + 1));
    }

    return old;
  }
  
  /** Proxy for {@link #remove(Object)} to help clarify ambiguity with {@link #remove(int)}. */
  public boolean removeBit(int bit) {
    return remove((Integer) bit);
  }

  @Override
  public Integer set(int index, Integer bit) {
    rangeCheck(index);

    int old = get(index);
    BitUtility.setBit(bits, index, bit);
    return old;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public BitList subList(int fromIndex, int toIndex) {
    if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
      throw Exceptions.invalidSublistIndices(fromIndex, toIndex, size);
    }

    BitList other = new BitList(toIndex - fromIndex);

    for (int i = fromIndex; i < toIndex; i++) {
      other.add((int) BitUtility.getBit(bits, i));
    }

    return other;
  }

  @Override
  public Object[] toArray() {
    Integer[] array = new Integer[size];

    for (int i = 0; i < size; i++) {
      array[i] = (int) BitUtility.getBit(bits, i);
    }

    return array;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T[] toArray(T[] a) {
    if (a.length < size) {
      a = Arrays.copyOf(a, size); // Contents don't matter
    }

    for (int i = 0; i < size; i++) {
      a[i] = (T) (Integer) (int) BitUtility.getBit(bits, i);
    }

    if (a.length > size) {
      a[size] = null;
    }

    return a;
  }

  /**
   * Returns a copy of data in this {@link BitList} as a {@code byte} array.
   *
   * <p>A given value in the {@code byte} array is 1 if the corresponding bit is 1, and 0 if the
   * corresponding bit is 0.
   *
   * @return A {@code byte} array containing a copy of the data in this {@link BitList}.
   */
  public byte[] toByteArray() {
    byte[] array = new byte[size];

    for (int i = 0; i < size; i++) {
      array[i] = BitUtility.getBit(bits, i);
    }

    return array;
  }

  /**
   * Returns a copy of data in this {@link BitList} as an {@code int} array.
   *
   * <p>A given value in the {@code int} array is 1 if the corresponding bit is 1, and 0 if the
   * corresponding bit is 0.
   *
   * @return An {@code int} array containing a copy of the data in this {@link BitList}.
   */
  public int[] toIntArray() {
    int[] array = new int[size];

    for (int i = 0; i < size; i++) {
      array[i] = BitUtility.getBit(bits, i);
    }

    return array;
  }

  /** Returns the maximum number of bits this list can hold before resizing takes place. */
  public int getCapacity() {
    return capacity * Long.SIZE;
  }

  private void rangeCheck(int index) {
    if (index < 0 || index >= size) {
      throw Exceptions.indexOutOfBounds(index, size);
    }
  }

  private void rangeCheckForAdd(int index) {
    if (index < 0 || index > size) {
      throw Exceptions.indexOutOfBounds(index, size + 1);
    }
  }

  /** If it was non-zero, the capacity of the list is doubled. Otherwise, it is set to 1. */
  private void increaseCapacity() {
    if (capacity == 0) {
      capacity = 1;
    } else {
      capacity *= 2;
    }

    long[] temp = new long[capacity];
    for (int i = 0; i < bits.length; i++) {
      temp[i] = bits[i];
    }

    bits = temp;
  }

  /** Sets the capacity to the smallest power of 2 such that {@code capacity >= minimum}. */
  private void increaseCapacity(int minimum) {
    // Find the smallest power of two greater than or equal to the specified minimum
    capacity = Integer.highestOneBit(minimum);
    if (capacity != minimum) {
      capacity *= 2;
    }

    long[] temp = new long[capacity];
    for (int i = 0; i < bits.length; i++) {
      temp[i] = bits[i];
    }

    bits = temp;
  }

  /** Returns the minimum number of longs needed to store {@code numBits}-many bits. */
  private static int minimumCapacity(int numBits) {
    return (numBits / Long.SIZE) + (numBits % Long.SIZE == 0 ? 0 : 1);
  }

  public static BitList fromBits(int... bits) {
    BitList list = new BitList(bits.length);

    for (int i = 0; i < bits.length; i++) {
      list.add(bits[i]);
    }

    return list;
  }
}
