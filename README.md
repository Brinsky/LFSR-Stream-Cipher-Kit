# *LSCK*: LFSR Stream Cipher Kit
An open-source library supporting simulation and cryptanalysis of [stream ciphers](https://en.wikipedia.org/wiki/Stream_cipher) based on [linear feedback shift registers (LFSRs)](https://en.wikipedia.org/wiki/Linear-feedback_shift_register).

## Installation and Build Instructions
Build and dependency management are provided via Gradle. The project can be built by running one of the following commands in the source directory:

On Windows:
```
$ gradlew.bat build
```

On Linux:
```
$ ./gradlew build
```

The resulting JAR is placed in the `build/libs` subdirectory.

**Note** For the remainder of this document, the command `gradle` will be substituted for the appropriate `gradlew` invocation shown above.

To build without running unit tests, execute
```
$ gradle build -x test
```

### Generating IDE Files
If you wish to view or develop the LSCK source code in an IDE, you may find it convenient to have Gradle generate the project files for you. To generate Eclipse project files, run
```
$ gradle eclipse
```
To generate IntelliJ IDEA project files, run
```
$ gradle idea
```
In both cases, it should then be possible to import the source directory into the IDE as a project.

## Usage

### BitVector

The data type for representing fixed-width, immutable bit vectors in LSCK is provided by the `BitVector` interface and its implementations. Bit vectors can be conveniently created via the static methods in the `BitVector` interface, which will select the appropriate backing implementation based on the length of the vector:

```Java
BitVector a = BitVector.fromBits(1, 0, 0, 1, 0, 1, 1, 0); // Creates an 8-bit vector representing 10010110
BitVector b = BitVector.fromInteger(8, 150); // Creates a bit vector from the lowest 8 bits of the int 150
```

A number of useful operations can be performed on bit vectors, including access to individual bits, conversion to integer types, and standard bitwise operations:

```Java
BitVector a = BitVector.fromBits(1, 0, 0, 1, 0, 1, 1, 0);
BitVector b = BitVector.fromBits(0, 1, 1, 1, 0, 0, 1, 0);

a.toInt(); // Produces the int "150"

a.not(); // Returns the bit vector 01101001

a.and(b); // Returns the bit vector 00010010
a.or(b); // Returns the bit vector 11110110
a.xor(b); // Returns the bit vector 11100100
```

### BitList

The `BitList` class provides a mutable, arbitrary-length list of bits. In particular, BitLists are used to store the output streams of LFSRs and generators. A `BitList` implements `List<Integer>`, so standard iteration and streaming methods can be used to process data:
  
```Java
BitList bits = getSomeBits();

int numOnes = bits.stream().filter(bit -> bit == 1).count(); // Counts the number of 1 bits in 'bits'
```

### Lfsr

Perhaps the most important components of LSCK are the LFSRs represented by the `Lfsr` interface and its implementations. As with `BitVector`, LFSRs can be created from static methods in the LFSR interface, which will automatically select an appropriate implementation based on the size of the register:

```Java
// Creates an Lfsr with taps specified by the first bit vector and initial fill specified by the second
Lfsr register = Lfsr.create(BitVector.fromBits(1, 0, 0, 1, 0, 1, 1, 0), BitVector.fromBits(0, 1, 1, 1, 0, 0, 1, 0));
```

An LFSR can be shifted to produce output, with the option to save or discard the resulting state:

```Java
BitList output1 = register.shift(100); // Register is shifted 100 times and retains the resulting state
BitList output2 = register.peek(100); // Same output as above, but register retains its initial state

int x = register.shift(); // Performs a single shift
int y = register.peek();
```

If the register specified above is shifted 25 times, the following output is produced:

```Java
System.out.println(register.shift(25));
```
> \[0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0\]

### Maximal-period taps

Each register can only take on a finite number of states. Since each state completely determines the next, this output must eventually become periodic. In general, we desire registers to have the maximum possible period in order to reduce the exploitability of patterns in our keystream. Whether or not a register produces maximal-period output is dependent on its tap configuration. The mathematics behind choosing a maximal-period tap configuration are nontrivial and can be found [here](https://en.wikipedia.org/wiki/Linear-feedback_shift_register#Fibonacci_LFSRs).

For convenience, we provide bit vectors containing all such configurations for registers of lengths 2 up to 32. These `BitVector` instances are static members of `MaximalTaps` with names of the form `TAPS_X_Y`, where X is the length of the bit vector / corresponding register and Y is the index of that tap configuration among all those of length X. These can be easily passed to LFSRs at construction time:

```Java
Lfsr register = Lfsr.create(MaximalTaps.TAPS_10_0);
```

They can also be accessed programmatically as follows:

```Java
Lfsr register = Lfsr.create(MaximalTaps.getMaximalTaps(10, 0));
```

For each register length, the number of different, maximal-period tap configurations available can be found via `MaximalTaps.maximalTapsCount(length)`.

### BooleanFunction

In order to combine output from multiple LFSRs, Boolean functions are made available via the `BooleanFunction` interface and the `SimpleBooleanFunction` implementation. Boolean functions can be created directly from symbolic string representations:

```Java
BooleanFunction f = BooleanFunction.fromString(3, "x1 x2 + x3"); // Three-variable Boolean function

int y = f.at(1, 1, 0); // Computes f(1, 1, 0)
```

String representations can include constants and nested expressions:

```Java
BooleanFunction g = BooleanFunction.fromString(4, "x1 + x2 (1 + x1) + (x2 + x3) (x4 + x1 + 1) + 1");
```

Behind the scenes, each `BooleanFunction` contains both a `TruthTable` object and a `TermTable` object (a list of all expanded terms present in the function definition). These can be retrieved and manipulated directly:

```Java
TruthTable truthTable = f.getTruthTable();
TermTable termTable = f.getTermTable();

int weight = truthTable.getWeight(); // Returns the number of 1 values in the truth table

// Returns the number of terms in the algebraic expansion of the function
// For the f shown above, this would be 2 ("x1 x2" and "x3")
int terms = termTable.getTermCount(); 
```

### Generator

The `Generator` class can be used to easily pipe the output of multiple LFSRs into a Boolean "combiner" function:

```Java
Lfsr lfsr1 = Lfsr.create(5);
Lfsr lfsr2 = Lfsr.create(6);
Lfsr lfsr3 = Lfsr.create(7);

lfsr1.setTaps(1, 1, 0, 1, 0);
lfsr2.setTaps(1, 0, 1, 1, 1, 0);
lfsr3.setTaps(1, 0, 1, 0, 1, 1, 1);
      
lfsr1.setFill(0, 1, 0, 1, 0);
lfsr2.setFill(0, 1, 1, 0, 1, 0);
lfsr3.setFill(1, 1, 0, 1, 0, 1, 0);
      
BooleanFunction f = BooleanFunction.fromString(3, "x1 x2 + x3");
      
Generator generator = new Generator(f, lfsr1, lfsr2, lfsr3);

// Shifts all three registers 100 times, each time feeding their inputs into f and returning the result
BitList output = generator.shift(100);
```

### Performing correlation attacks

A central feature of LSCK is the ability to perform [correlation attacks](https://en.wikipedia.org/wiki/Correlation_attack) on LFSR-based cryptosystems. The currently supported attacks require knowledge a generator's combiner function, the taps of its LFSRs, and an excerpt of its keystream. The attacks then seek to recover the initial fills of the registers used to generate the keystream excerpt.

It is often useful to perform a Walsh transform on a combiner function in order to detect susceptibility to correlation attacks. Aside from the combiner function, each Walsh transform computation requires a bit vector indicating which registers/variables of the function are intended for attack:

```Java
BooleanFunction f = BooleanFunction.fromString(4, "x1 x2 + x3 + x4");

// Determine if x2 and x3 are jointly susceptible to a correlation attack
int walshValue = Attack.walshTransform(f, BitVector.fromBits(0, 1, 1, 0));
```

Each attack operation requires a test statistic by which to "score" groups of fills. As each combination of fills is tested for the specified registers, a corresponding segment of keystream is generated and compared to the given keystream excerpt using the statistic. The resulting groups of fills can then be sorted by score (the computed statistic) to find the most viable candidates for the original fills. The default statistic in LSCK is `Attack.SIEGENTHALER_STATISTIC`, although others can be created by implementing the `Attack.TestStatistic` interface.

The following is an example multi-stage attack in which we are provided with the LFSRs (minus fills), the combiner function, and a keystream excerpt from an adversary's generator. Console output is interspersed:

```Java
/* Establish knowns and generate keystream using secret fills */

int N = 10000;

BooleanFunction f = BooleanFunction.fromString(4, "x1 x2 + x3 + x4");

Lfsr lfsr1 = Lfsr.create(MaximalTaps.TAPS_9_0);
Lfsr lfsr2 = Lfsr.create(MaximalTaps.TAPS_8_0);
Lfsr lfsr3 = Lfsr.create(MaximalTaps.TAPS_8_1);
Lfsr lfsr4 = Lfsr.create(MaximalTaps.TAPS_7_0);

BitList knownKeystream = generateSecretKey(N, f, lfsr1, lfsr2, lfsr3, lfsr4);
/* Prepare for and perform attack */

// Walsh Transform - register groups with nonzero values are candidates for attack
for (BitVector v : BitVector.allVectors(f.getArity())) {
  System.out.println(v + " " + Attack.walshTransform(f, v));
}
```
> 0000 0</br>
> 0001 0</br>
> 0010 0</br>
> 0011 8</br>
> 0100 0</br>
> 0101 0</br>
> 0110 0</br>
> 0111 8</br>
> 1000 0</br>
> 1001 0</br>
> 1010 0</br>
> 1011 8</br>
> 1100 0</br>
> 1101 0</br>
> 1110 0</br>
> 1111 -8</br>
```
This output suggests that we can first jointly attack registers 3 and 4 (indicated by 0011) and then use their recovered fills to attack registers 1  (1011) and 2 (0111) separately.
```Java
AttackBuilder attack =
    new AttackBuilder(lfsr1, lfsr2, lfsr3, lfsr4) // The generator being attacked depends on these registers
        .setIndexFromOne() // For individual attacks, we will specify registers with one-based indexing
        .setKnownKeystream(knownKeystream)
        .setTestStatistic(Attack.SIEGENTHALER_STATISTIC)
        .setCutoff(1000); // Fill groups with scores less than 1000 in magnitude will be discarded

/* First, we attack x3 and x4 simultaneously */

// Using the linear function x3 + x4 is standard practice 
List<ScoredFills> potentialFills1 =
    attack
        .setCombiner(BooleanFunction.fromString(4, "x3 + x4"))
        .setRegistersToAttack(3, 4)
        .attack();
        
System.out.printf("Attack took %.3f seconds\n", attack.getPreviousAttackDuration() / 1e9);
```
> Attack took 17.037 seconds
```Java
// Sort fills by magnitude of scores, since Siegenthaler statistic is centered at 0
Collections.sort(potentialFills1, new ScoredFills.AbsComparator());

System.out.println(potentialFills1.get(0));
```
> 4934.0: 11000010 0101101
```Java
// Set our registers' fills to the highest-scoring fills
lfsr3.setFill(potentialFills1.get(0).getFill(0));
lfsr4.setFill(potentialFills1.get(0).getFill(1));

/* Next, we attack x1 using the known fills for x3 and x4 */

List<ScoredFills> potentialFills2 =
    attack
        .setCombiner(BooleanFunction.fromString(4, "x1 + x3 + x4"))
        .setRegistersToAttack(1)
        .attack();
        
System.out.printf("Attack took %.3f seconds\n", attack.getPreviousAttackDuration() / 1e9);
```
> Attack took 0.292 seconds
```Java
Collections.sort(potentialFills2, new ScoredFills.AbsComparator());

System.out.println(potentialFills2.get(0));
```
> 5058.0: 100101101
```Java
// Set the fill of lfsr1 from the top-scoring fill
lfsr1.setFill(potentialFills2.get(0).getFill(0));

/* Finally, we attack x2 using the known fills for x1, x3, and x4 */

List<ScoredFills> potentialFills3 =
    attack
        .setCombiner(BooleanFunction.fromString(4, "x1 + x2 + x3 + x4"))
        .setRegistersToAttack(2)
        .attack();
        
System.out.printf("Attack took %.3f seconds\n", attack.getPreviousAttackDuration() / 1e9);
```
> Attack took 0.152 seconds
```Java
Collections.sort(potentialFills3, new ScoredFills.AbsComparator());

System.out.println(potentialFills3.get(0));
```
> -4986.0: 01100111
```Java
// Set the fill of lfsr1 from the top-scoring fill
lfsr2.setFill(potentialFills3.get(0).getFill(0));

/* Test if we've gotten the correct fills by generating a new keystream and comparing it to the
 * given keystream.
 */

BitList putativeKeystream = new Generator(f, lfsr1, lfsr2, lfsr3, lfsr4).peek(N);

int diffCount = 0;
for (int i = 0; i < N; i++) {
  diffCount += putativeKeystream.get(i) ^ knownKeystream.get(i);
}

System.out.println("Differences: " + diffCount);
```
> Differences: 0

Since we matched the keystream completely, our choices of fills must have been correct.
