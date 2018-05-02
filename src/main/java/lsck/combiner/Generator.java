package lsck.combiner;

import lsck.bitwise.BitList;
import lsck.bitwise.BitUtility;
import lsck.common.Exceptions;
import lsck.lfsr.Lfsr;

/**
 * A {@link Generator} combines the output of multiple {@link Lfsr} instances via a {@link
 * BooleanFunction}.
 */
public class Generator {

  public static final int MAX_REGISTERS = 30;

  private final BooleanFunction combiner;
  private final Lfsr[] registers;
  
  private Generator(BooleanFunction combiner, int registerCount) {
    if (combiner.getArity() > MAX_REGISTERS) {
      throw Exceptions.maxGeneratorLength(combiner.getArity());
    } else if (combiner.getArity() != registerCount) {
      throw Exceptions.arityRegisterCountMismatch(combiner.getArity(), registerCount);
    }

    this.combiner = combiner;
    registers = new Lfsr[registerCount];
  }

  /**
   * Creates a generator with the given combiner and registers of the specified lengths.
   *
   * <p>Registers are created by calling {@link Lfsr#create(int)}.
   *
   * @param combiner The function with which to combiner register outputs.
   * @param registerLengths The lengths of the desired LFSRs.
   */
  public Generator(BooleanFunction combiner, int... registerLengths) {
    this(combiner, registerLengths.length);

    for (int i = 0; i < registerLengths.length; i++) {
      registers[i] = Lfsr.create(registerLengths[i]);
    }
  }

  /**
   * Creates a generator with the given combiner and registers.
   *
   * @param combiner The function with which to combiner register outputs.
   * @param registers The registers to include in this generator.
   */
  public Generator(BooleanFunction combiner, Lfsr... registers) {
    this(combiner, registers.length);

    for (int i = 0; i < registers.length; i++) {
      this.registers[i] = registers[i];
    }
  }

  /**
   * Returns the combiner function used by this {@link Generator}.
   *
   * @return The {@link BooleanFunction} used by this {@link Generator} as a combiner function.
   */
  public BooleanFunction getCombiner() {
    return combiner;
  }

  /**
   * Returns the number of registers included in this generator.
   *
   * @return the number of registers included in this generator.
   */
  public int getRegisterCount() {
    return registers.length;
  }

  /**
   * Returns the specified register from this generator.
   *
   * <p>The registers are not immutable, so changes to their taps and fills will be reflected in the
   * generator.
   *
   * @param i The index of the register to be returned.
   * @return The specified register from this generator.
   */
  public Lfsr getRegister(int i) {
    if (i < 0 || i >= registers.length) {
      throw Exceptions.registerIndexOutOfBounds(i);
    }

    return registers[i];
  }

  /**
   * Returns the next output bit without mutating any of the registers.
   *
   * @return The bit output by a single shift.
   */
  public byte peek() {
    int argVector = 0;

    for (int i = 0; i < registers.length; i++) {
      argVector = BitUtility.setBit(argVector, registers.length - 1 - i, registers[i].peek());
    }

    return combiner.atInteger(argVector);
  }

  /**
   * Returns a sequence of output bits without mutating any of the registers.
   *
   * @param terms The number of bits to be generated.
   * @return A list of bits output during the specified number of shifts.
   */
  public BitList peek(int terms) {
    return shift(i -> registers[i].peek(terms), terms);
  }

  /**
   * Returns the specified bit in the generator's output stream
   *
   * <p>The registers are not mutated by this operation. Bits in the output sequence are indexed
   * from 0.
   *
   * @param term The index of the desired bit in the output stream.
   * @return The specified bit in the output stream.
   */
  public byte peekAt(int term) {
    // Generate argument vector using each register's output
    int argVector = 0;
    for (int i = 0; i < registers.length; i++) {
      argVector = BitUtility.setBit(argVector, registers.length - 1 - i, registers[i].peekAt(term));
    }

    return combiner.atInteger(argVector);
  }

  /**
   * Shifts the register and returns the output bit.
   *
   * @return The bit output during a single shift.
   */
  public byte shift() {
    int argVector = 0;

    for (int i = 0; i < registers.length; i++) {
      argVector = BitUtility.setBit(argVector, registers.length - 1 - i, registers[i].shift());
    }

    return combiner.atInteger(argVector);
  }

  /**
   * Performs the specified number of shifts and returns the output bits.
   *
   * @param terms The number of bits to be generated.
   * @return A list of bits output during the specified number of shifts.
   */
  public BitList shift(int terms) {
    return shift(i -> registers[i].shift(terms), terms);
  }

  /**
   * Performs {@code term + 1} shifts and returns the last resulting bit.
   *
   * <p>Bits in the output sequence are indexed from 0. Calling {@code shiftTo(i)} leaves the
   * registers in the same state as calling {@code shift(i + 1)}.
   *
   * @param term The index of the desired bit in the output stream.
   * @return The specified bit in the output stream.
   */
  public byte shiftTo(int term) {
    // Generate argument vector using each register's output
    int argVector = 0;
    for (int i = 0; i < registers.length; i++) {
      argVector =
          BitUtility.setBit(argVector, registers.length - 1 - i, registers[i].shiftTo(term));
    }

    return combiner.atInteger(argVector);
  }

  @FunctionalInterface
  private static interface BitListGetter {
    BitList get(int register);
  }

  private BitList shift(BitListGetter shiftLists, int terms) {
    int[] args = new int[terms];

    // Generate argument vectors from each register's output
    for (int i = 0; i < registers.length; i++) {
      BitList registerOutput = shiftLists.get(i);

      for (int j = 0; j < terms; j++) {
        args[j] = BitUtility.setBit(args[j], registers.length - 1 - i, registerOutput.get(j));
      }
    }

    // Pass the argument vectors to the combiner
    BitList output = new BitList(terms);
    for (int i = 0; i < terms; i++) {
      output.add(combiner.atInteger(args[i]));
    }

    return output;
  }
}
