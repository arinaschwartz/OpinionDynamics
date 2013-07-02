/* CS121 A'11
 * HW1: Opinion Dynamics
 * Anne Rogers
 * Sept 2011
 *
 * Class for testing OneSimulation
 *
 * The program takes the name of a test file as an argument.  A test
 * file contains a seed for the random number generator, the number of
 * voters, initial opinions for those voters, the expected number of
 * steps in the simulation and A or B, if the simulation converged.
 *
 * The program outputs a success or failure message as appropriate.
 */


public class TestOneSimulation {	
    public static void main(String[] args) {
	if (args.length != 1) {
	    System.err.println("usage: java TestOneSimulation <test filename>");
	    System.exit(0);
	}

	boolean voters[] = Utility.setupVoters(args[0]);
	int steps = Opinion.doOneSimulation(voters);

	if (steps != Utility.numSteps) {
	    System.out.printf("Error: expected %d steps, got %d steps\n", 
			      Utility.numSteps, steps);
	} else if (steps != Opinion.MAX_STEPS) {
	    /* Does the output converge to the expected opinion? */
	    boolean c = (Utility.convergedTo == 'A') ? true : false;
	    for (int i = 0; i < voters.length; i++) {
		if (voters[i] != c) {
		    char d0 = (voters[i] ? 'A' : 'B');
		    System.out.printf("Error: expected convergence to %c, %c @ array location %d\n",
				      Utility.convergedTo, d0, i);
		    System.exit(0);
		}
	    }
	    System.out.println("Success!");
	} else {
	    System.out.println("Success!");
	}
    }
}