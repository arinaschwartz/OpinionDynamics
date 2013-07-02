/* CS121 A'11
 *
 * This class contains a wrapper for the standard random number
 * generator that makes it possible to dictate the numbers that get
 * returned.
 *
 * Anne Rogers
 * August 2011
 */

import java.util.*;
import java.io.File;

public class LocalRandom {
    private static Random randGen = null;
    private static long seed = 0;
    private static double[] randValues = null;
    public static int next;

    /* Initialize the random number generator to use s as a seed */
    public static void initRandom(long s) {
	seed = s;
	randGen = new Random(seed);		    
    }

    /* Initialize the random number generator to use time as a seed */
    public static void initRandom() {
	seed = System.currentTimeMillis();	
	randGen = new Random(seed);
    }

    /* Initialize the random number generator to return a specific set 
       of values */
    public static void initRandom(double[] values) {
	randValues = values;
	next = 0;
    }

    public static long getSeed() {
	return seed;
    }

    /* rand: generate a random number between [0.0..1.0) */
    public static double rand() {
	if (randGen == null)
	    initRandom();

	if (randValues != null) {
	    /* use the specified values.  Wrap around if necessary */
	    double rv = randValues[next];
	    next = (next + 1) % randValues.length;
	    return rv;
	}

	return randGen.nextDouble();
    }
}