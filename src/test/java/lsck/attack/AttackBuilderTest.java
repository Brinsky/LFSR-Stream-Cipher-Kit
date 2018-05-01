package lsck.attack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import lsck.bitwise.BitList;
import lsck.combiner.BooleanFunction;
import lsck.combiner.Generator;
import lsck.lfsr.Lfsr;

public class AttackBuilderTest {

  private static final int NUM_REGISTERS = 10;
  private static final int REGISTER_LENGTHS = 8;

  private static final Lfsr[] registers = getRegisters();

  private static Lfsr[] getRegisters() {
    Lfsr[] registers = new Lfsr[NUM_REGISTERS];

    for (int i = 0; i < registers.length; i++) {
      registers[i] = Lfsr.create(REGISTER_LENGTHS);
    }

    return registers;
  }

  @Test
  void testSetRegistersToAttack_zeroIndexed() {
    AttackBuilder attack = new AttackBuilder(registers).setIndexFromZero();

    attack.setRegistersToAttack(IntStream.range(0, registers.length).toArray());
  }

  @Test
  void testSetRegistersToAttack_oneIndexed() {
    AttackBuilder attack = new AttackBuilder(registers).setIndexFromOne();

    attack.setRegistersToAttack(IntStream.range(1, registers.length + 1).toArray());
  }

  @Test
  void testSetCombiner_arityMismatch() {
    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                new AttackBuilder(registers)
                    .setCombiner(BooleanFunction.fromString(registers.length + 1, "1")));

    assertEquals(
        "Combiner arity of "
            + (registers.length + 1)
            + " does not match register count of "
            + registers.length,
        e.getMessage());
  }

  @Test
  void testConstructor_maxRegistersExceeded() {
    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () -> new AttackBuilder(new Lfsr[Generator.MAX_REGISTERS + 1]));

    assertEquals(
        "Number of registers provided ("
            + (Generator.MAX_REGISTERS + 1)
            + ") exceeds maximum generator size of "
            + Generator.MAX_REGISTERS,
        e.getMessage());
  }

  @Test
  void testSetRegistersToAttack_duplicates() {
    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () -> new AttackBuilder(registers).setRegistersToAttack(1, 1));

    assertEquals("Register with index " + 1 + " specified more than once", e.getMessage());
  }

  @Test
  void testSetRegistersToAttack_attackableLengthExceeded() {
    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                new AttackBuilder(Lfsr.create(Attack.MAX_ATTACKABLE_REGISTER_LENGTH + 1))
                    .setRegistersToAttack(0));

    assertEquals(
        "Register of length "
            + (Attack.MAX_ATTACKABLE_REGISTER_LENGTH + 1)
            + " exceeds max-attackable length of "
            + Attack.MAX_ATTACKABLE_REGISTER_LENGTH,
        e.getMessage());
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, NUM_REGISTERS})
  void testSetRegistersToAttack_invalidIndex_fromZero(int index) {
    Exception e =
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> new AttackBuilder(registers).setIndexFromZero().setRegistersToAttack(index));

    assertEquals(
        "Variable index should be between "
            + 0
            + " and "
            + (registers.length - 1)
            + " (inclusive); got "
            + index,
        e.getMessage());
  }

  @ParameterizedTest
  @ValueSource(ints = {0, NUM_REGISTERS + 1})
  void testSetRegistersToAttack_invalidIndex_fromOne(int index) {
    Exception e =
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> new AttackBuilder(registers).setIndexFromOne().setRegistersToAttack(index));

    assertEquals(
        "Variable index should be between "
            + 1
            + " and "
            + registers.length
            + " (inclusive); got "
            + index,
        e.getMessage());
  }

  @Test
  void testAttack_missingCombiner() {
    AttackBuilder attack =
        new AttackBuilder(registers).setKnownKeystream(new BitList()).setRegistersToAttack(0);

    Exception e = assertThrows(IllegalArgumentException.class, () -> attack.attack());

    assertEquals("Must specify correlation function q by calling setCombiner()", e.getMessage());
  }

  @Test
  void testAttack_missingKeystream() {
    AttackBuilder attack =
        new AttackBuilder(registers)
            .setCombiner(BooleanFunction.fromString(NUM_REGISTERS, "1"))
            .setRegistersToAttack(0);

    Exception e = assertThrows(IllegalArgumentException.class, () -> attack.attack());

    assertEquals("Must specify the known keystream via setKnownKeystream()", e.getMessage());
  }

  @Test
  void testAttack_missingRegistersToAttack() {
    AttackBuilder attack =
        new AttackBuilder(registers)
            .setKnownKeystream(new BitList())
            .setCombiner(BooleanFunction.fromString(NUM_REGISTERS, "1"));

    Exception e = assertThrows(IllegalArgumentException.class, () -> attack.attack());

    assertEquals(
        "Must specify the registers to attack by calling setRegistersToAttack()", e.getMessage());
  }
  
  // TODO: Add tests for attack() and getPreviousAttackDuration();
}
