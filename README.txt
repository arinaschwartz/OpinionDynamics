CS121 A'11
HW1: Opinion Dynamics

Anne Rogers
Sept 2011

This directory contains the distribution.

Opinion.java: You will modify this file.  Do not modify any of the other files.

TestUpdate.java: Class for testing the update function in
  Opinion.java.  To compile and run it, use the commands:

    javac TestUpdate.java
    java TestUpdate

TestHasConverged.java: Class for testing the hasConverged function in
  Opinion.java.  To compile and run it, use the commands:

    javac TestHasConverged.java		       
    java TestHasConverged

TestOneSimulation.java: Class for testing the doOneSimulation function
  in Opinion.java.  To compile and run it, use the commands:

    javac TestOneSimulation.java
    javac TestOneSimulation <test file>

  where testfile is the name of a file from the test sub-directory.

TestComputeExitProbability.java: Class for testing the
  computeExitProbability function in Opinion.java.  To compile and run
  it, use the commands:

    javac TestComputeExitProbability.java
    javac TestComputeExitProbability


Utility.java: Utility functions

LocalRandom.java: Wrapper for the standard random number generator
  that lets us control the sequence of numbers generated.

GenDraw.java: Drawing functions used in Utility.java

test: a directory of test files to be used with TestOneSimulation.
