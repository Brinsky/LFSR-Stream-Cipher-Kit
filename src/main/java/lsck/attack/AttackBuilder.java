package lsck.attack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lsck.bitwise.BitList;
import lsck.bitwise.BitVector;
import lsck.combiner.BooleanFunction;
import lsck.combiner.Generator;
import lsck.common.Exceptions;
import lsck.lfsr.Lfsr;

/** Allows correlation attacks to be initialized and performed. */
public class AttackBuilder {

  private final Lfsr[] allRegisters;

  private boolean indexFromZero = true;
  private BooleanFunction q = null;
  private Lfsr[] registersToAttack = null; // Subset of the registers
  private BitList knownKeystream = null;
  private Attack.TestStatistic statistic = Attack.SIEGANTHALER_STATISTIC;
  private double cutoff = 0;
  private int numCombinations;

  private long previousAttackDuration = 0; // In nanoseconds

  /**
   * Creates an {@link AttackBuilder} by specifying all registers in the generator being attacked.
   *
   * @param registers A list of all registers contained in the generator being attacked. This
   *     registers are not copied, and will be affected by external modification.
   */
  public AttackBuilder(Lfsr... registers) {
    if (registers.length > Generator.MAX_REGISTERS) {
      throw new IllegalArgumentException(
          "Number of registers provided ("
              + registers.length
              + ") exceeds maximum generator size of "
              + Generator.MAX_REGISTERS);
    }

    this.allRegisters = registers;
  }

  /** Causes {@link #setRegistersToAttack(int...)} to subsequently expect zero-based indices. */
  public AttackBuilder setIndexFromZero() {
    indexFromZero = true;
    return this;
  }

  /** Causes {@link #setRegistersToAttack(int...)} to subsequently expect one-based indices. */
  public AttackBuilder setIndexFromOne() {
    indexFromZero = false;
    return this;
  }

  /**
   * Sets the combiner to use during the next attack.
   *
   * <p>This function must be called prior to calling {@link #attack()}. The specified combiner
   * should depend only on the registers being attacked and those of known fill being leveraged in
   * the attack.
   */
  public AttackBuilder setCombiner(BooleanFunction q) {
    if (q.getArity() != allRegisters.length) {
      throw Exceptions.arityRegisterCountMismatch(q.getArity(), allRegisters.length);
    }

    this.q = q;
    return this;
  }

  /**
   * Sets the registers whose fills should be recovered in the subsequent attack.
   *
   * <p>This function must be called prior to calling {@link #attack()}.
   *
   * @param registerIndices The index of the registers to be attacked within the initially-provided
   *     array of registers. This list should not contain duplicates, and should be either
   *     zero-indexed (by default, or if {@link #setIndexFromZero()} has been called) or one-indexed
   *     (if @link {@link #setIndexFromOne()} has been called).
   */
  public AttackBuilder setRegistersToAttack(int... registerIndices) {
    registersToAttack = new Lfsr[registerIndices.length];
    numCombinations = 1;

    // Use a set to check for duplicate register indices
    Set<Integer> indexSet = new HashSet<>(registerIndices.length);

    for (int i = 0; i < registerIndices.length; i++) {
      int registerIndex = registerIndices[i];

      // Ensure valid index and normalize index
      rangeCheck(registerIndex);
      if (!indexFromZero) {
        registerIndex--;
      }

      // Check for duplicates
      if (indexSet.contains(registerIndex)) {
        throw Exceptions.duplicateRegister(registerIndex + (indexFromZero ? 0 : 1));
      }

      indexSet.add(registerIndex);
      registersToAttack[i] = allRegisters[registerIndex];

      // Ensure selected register is within max attackable length
      if (registersToAttack[i].getLength() > Attack.MAX_ATTACKABLE_REGISTER_LENGTH) {
        throw Exceptions.registerExceedsAttackableLength(registersToAttack[i].getLength());
      }

      // TODO: Deal with potential overflows of this quantity
      // -1 because zero fills are not included
      numCombinations *= (1 << registersToAttack[i].getLength()) - 1;
    }

    return this;
  }

  /**
   * Sets the known keystream to be correlated against during the subsequent attack.
   *
   * <p>All bits of the provided keystream will be used in computing statistic values for each set
   * of fills.
   */
  public AttackBuilder setKnownKeystream(BitList keystream) {
    this.knownKeystream = new BitList(keystream);
    return this;
  }

  /**
   * Sets the test statistic used to evaluate each set of fills during the subsequent attack.
   *
   * <p>The default test statistic is {@link Attack#SIEGANTHALER_STATISTIC}.
   */
  public AttackBuilder setTestStatistic(Attack.TestStatistic statistic) {
    this.statistic = statistic;
    return this;
  }

  /**
   * Sets a cutoff value. Fills with smaller scores (in magnitude) will not be saved.
   *
   * <p>The default cutoff value is 0.
   */
  public AttackBuilder setCutoff(double cutoff) {
    this.cutoff = cutoff;
    return this;
  }

  /**
   * Performs a correlation attack according to the previously specified parameters.
   *
   * @return A list of {@link ScoredFills} objects associating fills with test statistic values.
   * @throws IllegalArgumentException Thrown if non-optional parameters (the combiner, the registers
   *     to attack, or the known keystream) have not been specified.
   */
  public List<ScoredFills> attack() {
    if (q == null) {
      throw new IllegalArgumentException(
          "Must specify correlation function q by calling setCombiner()");
    } else if (registersToAttack == null) {
      throw new IllegalArgumentException(
          "Must specify the registers to attack by calling setRegistersToAttack()");
    } else if (knownKeystream == null) {
      throw new IllegalArgumentException(
          "Must specify the known keystream via setKnownKeystream()");
    }

    long startTime = System.nanoTime();

    BitVector[] fills = new BitVector[registersToAttack.length];
    List<ScoredFills> scores = new ArrayList<>(numCombinations);

    Attack.attack(
        new Generator(q, allRegisters),
        knownKeystream,
        registersToAttack,
        fills,
        statistic,
        scores,
        cutoff,
        0);

    previousAttackDuration = System.nanoTime() - startTime;

    return scores;
  }

  /**
   * The duration of the previous attack in nanoseconds.
   *
   * <p>This duration is recomputed for each call to {@link #attack()}.
   *
   * @return The duration of the previous attack in nanoseconds or 0 if no prior attack occurred.
   */
  public long getPreviousAttackDuration() {
    return previousAttackDuration;
  }

  private void rangeCheck(int registerIndex) {
    if (indexFromZero && (registerIndex < 0 || registerIndex >= allRegisters.length)) {
      throw Exceptions.invalidIndexRange(0, allRegisters.length - 1, registerIndex);
    } else if (!indexFromZero && (registerIndex < 1 || registerIndex > allRegisters.length)) {
      throw Exceptions.invalidIndexRange(1, allRegisters.length, registerIndex);
    }
  }
}
