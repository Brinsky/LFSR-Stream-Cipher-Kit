package lsck.bitwise;

/** A functional interface for providing an indexed sequence of integers */
@FunctionalInterface
public interface IntegerSequence {
  
  /** Gets the {@code int} at the specified index.
   * 
   * @param index The index of the {@code int} to retrieve.
   * @return The value of the retrieved {@code int}.
   */
  int get(int index);
}
