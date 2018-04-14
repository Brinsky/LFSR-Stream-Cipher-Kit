package lsck.attack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import lsck.bitwise.BitList;
import lsck.combiner.BooleanFunction;
import lsck.combiner.Generator;
import lsck.lfsr.Lfsr;

public class AttackTest {

  @SuppressWarnings("unused")
  private static Stream<Arguments> getTestSieganthalerStatisticArgs() {
    return Stream.of(
        Arguments.of(
            BitList.fromBits(1, 1, 1, 1, 1, 1, 1, 1),
            BitList.fromBits(0, 0, 0, 0, 0, 0, 0, 0),
            -8),
        Arguments.of(
            BitList.fromBits(0, 0, 0, 0, 0, 0, 0, 0),
            BitList.fromBits(1, 1, 1, 1, 1, 1, 1, 1),
            -8),
        Arguments.of(
            BitList.fromBits(1, 1, 1, 1, 1, 1, 1, 1),
            BitList.fromBits(1, 1, 1, 1, 1, 1, 1, 1),
            8),
        Arguments.of(
            BitList.fromBits(1, 0, 1, 1, 0, 1, 0, 0),
            BitList.fromBits(1, 1, 1, 0, 0, 1, 0, 1),
            2),
        Arguments.of(
            BitList.fromBits(1, 1, 1, 0, 0, 1, 0, 1),
            BitList.fromBits(1, 0, 1, 1, 0, 1, 0, 0),
            2),
        Arguments.of(
            BitList.fromBits(1, 1, 1, 1, 1, 1, 1, 1),
            BitList.fromBits(1, 1, 1, 1, 0, 0, 0, 0),
            0));
  }

  @ParameterizedTest
  @MethodSource("getTestSieganthalerStatisticArgs")
  void testSieganthalerStatistic(BitList known, BitList observed, int statistic) {
    assertEquals(Attack.SIEGANTHALER_STATISTIC.compute(known, observed), statistic);
  }

  @Test
  void testAttack_invalidRegisterIndices() {
    Generator g = Mockito.mock(Generator.class);
    Mockito.when(g.getRegisterCount()).thenReturn(3);
    Mockito.when(g.getRegister(Mockito.anyInt())).thenReturn(Lfsr.create(8));

    Exception e =
        assertThrows(
            IndexOutOfBoundsException.class,
            () ->
                Attack.attack(g, new BitList(), Attack.SIEGANTHALER_STATISTIC, 0, 1, 2, 3));

    assertEquals("Expected a valid register index; got " + 3, e.getMessage());
  }

  @Test
  void testAttack_registerLengthExceedsMax() {
    BooleanFunction f = Mockito.mock(BooleanFunction.class);
    Mockito.when(f.getArity()).thenReturn(1);

    Generator g = new Generator(f, Lfsr.create(Attack.MAX_ATTACKABLE_REGISTER_LENGTH + 1));

    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () -> Attack.attack(g, new BitList(), Attack.SIEGANTHALER_STATISTIC, 0));

    assertEquals(
        "Register of length "
            + (Attack.MAX_ATTACKABLE_REGISTER_LENGTH + 1)
            + " exceeds max-attackable length of "
            + Attack.MAX_ATTACKABLE_REGISTER_LENGTH,
        e.getMessage());
  }

  @Test
  void testAttack_duplicateRegisters() {
    Generator g = Mockito.mock(Generator.class);
    Mockito.when(g.getRegisterCount()).thenReturn(5);
    Mockito.when(g.getRegister(Mockito.anyInt())).thenReturn(Lfsr.create(8));

    Exception e =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                Attack.attack(
                    g, new BitList(), Attack.SIEGANTHALER_STATISTIC, 0, 1, 2, 3, 1));

    assertEquals("Register with index " + 1 + " specified more than once", e.getMessage());
  }

  // TODO: Implement non-exception attack test
}
