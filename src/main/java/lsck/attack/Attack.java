package lsck.attack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lsck.bitwise.BitVector;
import lsck.combiner.Generator;
import lsck.common.Exceptions;
import lsck.lfsr.Lfsr;

/**
 * A collection of tools and methods used to perform correlation attacks on generators.
 *
 * <p>All correlation attacks assume access to the register lengths, taps, and the combiner function
 * of the targeted generator, as well as access to an excerpt of keystream produced using unknown
 * fills.
 */
public class Attack {

  public static final int MAX_ATTACKABLE_REGISTER_LENGTH = Long.SIZE - 2;

  /**
   * Performs a correlation attack against the specified registers and {@link Generator}.
   *
   * @param generator The generator to attack, with known LFSR fills already set
   * @param knownOutput The known output bits produced by the original generator.
   * @param statistic The test statistic used to compare observed keystreams with the given
   *     keystream.
   * @param registersToAttack A list of register indices within the generator. These are the
   *     registers whose fills are being searched/tested for. Repeated values are ignored.
   * @return A list of {@link ScoredFills} object associating fills with test statistic values.
   */
  public static List<ScoredFills> attack(
      Generator generator,
      List<Byte> knownOutput,
      TestStatistic statistic,
      int... registersToAttack) {

    Lfsr[] registers = new Lfsr[registersToAttack.length];
    int numCombinations = 1;

    // Use a set to check for duplicate register indices
    Set<Integer> indexSet = new HashSet<>(registersToAttack.length);

    // Extract registers from generator, calculate number of fill combinations
    for (int i = 0; i < registers.length; i++) {
      int registerIndex = registersToAttack[i];
      
      // Check for duplicates
      if (indexSet.contains(registerIndex)) {
        throw Exceptions.duplicateRegisterException(registerIndex);
      }
      
      indexSet.add(registerIndex);
      
      if (registerIndex < 0 || registerIndex >= generator.getRegisterCount()) {
        throw Exceptions.registerIndexOutOfBoundsException(registerIndex);
      }
      
      registers[i] = generator.getRegister(registerIndex);
      
      if (registers[i].getLength() > MAX_ATTACKABLE_REGISTER_LENGTH) {
        throw Exceptions.registerExceedsAttackableLengthException(registers[i].getLength());
      }

      // TODO: Deal with potential overflows of this quantity
      // -1 because zero fills are not included
      numCombinations *= (1 << registers[i].getLength()) - 1;
    }

    BitVector[] fills = new BitVector[registers.length];
    List<ScoredFills> scores = new ArrayList<>(numCombinations);

    attack(generator, knownOutput, registers, fills, statistic, scores, 0);

    return scores;
  }

  private static void attack(
      Generator generator,
      List<Byte> knownOutput,
      Lfsr[] registers,
      BitVector[] fills,
      TestStatistic statistic,
      List<ScoredFills> scores,
      int index) {
    // Once all fills have been chosen, generate output and perform the test
    if (index == registers.length) {
      List<Byte> testOutput = generator.peek(knownOutput.size());
      scores.add(new ScoredFills(statistic.compute(knownOutput, testOutput), fills));
      return;
    }

    // Increment the fill (never 0) and recurse
    for (long i = 1; i < (1L << registers[index].getLength()); i++) {
      fills[index] = BitVector.fromInteger(registers[index].getLength(), i);
      registers[index].setFill(fills[index]);
      attack(generator, knownOutput, registers, fills, statistic, scores, index + 1);
    }
  }

  /** The classical test statistic proposed by T. Sieganthaler in 1985 */
  public static final TestStatistic SIEGANTHALER_STATISTIC =
      (expected, observed) -> {
        int N = expected.size();
        int sum = 0;

        for (int i = 0; i < N; i++) {
          sum += expected.get(i) ^ observed.get(i);
        }

        return N - 2 * sum;
      };

  /**
   * Computes a numerical the value of a test statistic involving a known output stream and an
   * observed output stream.
   */
  @FunctionalInterface
  public static interface TestStatistic {
    double compute(List<Byte> expected, List<Byte> observed);
  }
}
