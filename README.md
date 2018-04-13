# LFSR Stream Cipher Kit
An open-source library supporting simulation and cryptanalysis of [stream ciphers](https://en.wikipedia.org/wiki/Stream_cipher) based on [LFSRs (linear feedback shift registers)](https://en.wikipedia.org/wiki/Linear-feedback_shift_register).

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
