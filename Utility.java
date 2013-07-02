/* CS121 A'11
 *
 * This class contains utility functions used by other classes
 *
 * Anne Rogers
 * August 2011
 */

import java.util.*;
import java.io.File;

public class Utility {
    private static GenDraw canvas = null;
    private static Random randGen = null;
    private static long seed = 0;
    private static double[] randValues = null;
    public static int next;
    public static int numSteps;
    public static char convergedTo;

    /* generateVoters: generate a voter sample
     *   int numVoters - number of voters in the sample
     *   double pctA - probability that a voter will hold Opinion A initially
     *
     * returns: a randomly computed boolean array of size n.
     */
    public static boolean[] generateVoters(int numVoters, double pctA) {
	boolean[] rv = new boolean[numVoters];
	for (int i = 0; i < numVoters; i++) {
	    double r = LocalRandom.rand();
	    if (r < pctA) {
		/* choose Opinion A */
		rv[i] = true;
	    } else {
		/* choose Opinion B */
		rv[i] = false;
	    }
	}
	return rv;
    }


    /* printVoters: Utility function that prints the contents of the
     * voter array to stdout.
     */
    public static void printVoters(boolean[] voters) {
	System.out.println(voters.length);
	for (int i = 0; i < voters.length; i++) 
	    if (voters[i]) {
		System.out.println('A');
	    } else {
		System.out.println('B');
	    }
    }

    /* printVotersOneLine: Utility function that prints the contents of the
     * voter array to stdout.
     */
    public static void printVotersOnOneLine(boolean[] voters) {
	for (int i = 0; i < voters.length; i++) 
	    if (voters[i]) {
		System.out.print('A');
	    } else {
		System.out.print('B');
	    }
	System.out.println();
    }


    /* drawVoters: Utility function that displays a visual
     *   representation of the voters.  Each voter is represented as a
     *   thin vertical bar, where blue means that the voter holds
     *   Opinion A and red means the voter holds Opinion B
     */
    public static void drawVoters(boolean voters[]) {
	double delta = 1.0/voters.length;
	double x = delta/2;
	double y = .5;

	if (canvas == null) {
	    // set up canvas on the first call to drawVoters
	    canvas = new GenDraw("Square Example");
	    canvas.setXscale(0.0, 1.0);
	    canvas.setYscale(-1.0, 1.0);
	    canvas.show();
	}

	// remove the previous drawing
	canvas.clear();

	for (int i = 0; i < voters.length; i++) {
	    if (voters[i])
		canvas.setPenColor(canvas.BLUE);
	    else
		canvas.setPenColor(canvas.RED);
	    canvas.line(x, .25, x, .75);
	    x = x + delta;
	}

	// show the new drawing
	canvas.show();

	try {
	    /* Wait 10 seconds before returning. */
	    Thread.sleep(1000);
	} catch(Exception e) {
	    e.printStackTrace();
	}
    }


    /* setupVoters: Utility function to load initial voter opinions
     * from a file along with the seed for the random number
     * generator.
     *
     * Returns a boolean array, where true corresponds to Opinion A
     * and false corresponds to Opinion B.
     */
    public static boolean[] setupVoters(String fileName) {
	Scanner scanner = null;
	
	// attempt to open the file                                                       
	try {
	    scanner = new Scanner(new File(fileName), "UTF-8");
	} catch (NullPointerException e) {
	    System.err.println("Bad file name:" + fileName);
	    System.exit(0);
	} catch (java.io.FileNotFoundException e) {
	    System.err.println("File " + fileName + " not found.");
	    System.exit(0);
	}

	int numVoters;
	int lineNum = 0;
	String line = "";
	boolean[] voters = null;

	try {
	    // parse the seed and initialize the random number
	    // generator.
	    line = scanner.nextLine();
	    long seed = Long.parseLong(line);
	    LocalRandom.initRandom(seed);

	    // parse the number of voters
	    line = scanner.nextLine();
	    numVoters = Integer.parseInt(line);
	    voters = new boolean[numVoters];
	    int i=0;

	    while (scanner.hasNextLine() && (lineNum < numVoters)) {
		line = scanner.nextLine();
		char c = line.charAt(0);
		if ((line.length() == 1) &&
		    ((c == 'A') || (c == 'B'))) {
		    voters[i] = (c == 'A');
		    i++;
		} else {
		    System.err.println("Format error on line #" + lineNum);
		    System.exit(0);
		}
		lineNum++;
	    }

	    if (lineNum != numVoters) {
		System.err.printf("Format error: not enough voters.  Expected %d.  Got %f\n",  numVoters, lineNum);
	    }

	    line = scanner.nextLine();
	    numSteps = Integer.parseInt(line);

	    if (numSteps < Opinion.MAX_STEPS) {
		if (scanner.hasNextLine()) {
		    line = scanner.nextLine();
		} else {
		    System.err.println("Expected outcome missing on line:" + lineNum);
		    System.exit(0);
		}
		char c = line.charAt(0);
		if ((c == 'A') || (c == 'B')) {
		    convergedTo = line.charAt(0);
		} else {
		    System.err.println("Expected A or B.  Got " + c);
		    System.exit(0);
		}
		    

	    }
	} catch (Exception e) {
	    System.err.println("Format error on line #" + lineNum);
	    System.err.println(":" + line + ":");
	    System.exit(0);
	}
	
	return voters;
    }
}