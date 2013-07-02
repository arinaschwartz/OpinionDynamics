/* CS121 A'11
 *
 * Opinion dynamics using Sznajd model.
 * 
 * Arin Schwartz-- arinschwartz
 *
 * This class implements one simulation of the voter model for the
 * evolution of voter opinions from Sznajd-Weron and Sznajd's paper
 * "Opinion evolution in closed communities".
 *
 * The program takes the name of a test file as an argument.  A test
 * file contains a seed for the random number generator, the number of
 * voters, and initial opinions for those voters.
 *
 * The program outputs either that the configuration in the test file
 * (1) converged with Opinion A (or B) in some number of steps or (2)
 * did not converge.
 */

import java.util.*;
import java.io.File;


public class Opinion {
    public static final int MAX_STEPS = 10000000;

    /* vote: update the voter array using voter v as the lead voter.
     *
     * The update rule is: given a randomly chosen an anchor voter i,
     * if voter v and voter v+1 agree, then randomly pick one of their
     * immediate neightbors (v-1) and (v+2) and make that voter agree
     * with voter v.  The voters are assumed to be arranged in a ring:
     * voter 0's left neighbor is voter n-1 and voter n-1's right
     * neighbor is 0.
     */
    public static void update(boolean[] voters, int i) 
    { 
        if (i == voters.length - 1)
        {
            if (voters[i] == voters[0])
            {
                double z = LocalRandom.rand();
                if (z < 0.5)
                {
                    voters[i-1] = voters[i];
                }
                else
                {
                    voters[1] = voters[i];
                }
            }
            return;
        }
        if (i == voters.length - 2)
        {
            if (voters[i] == voters[i+1])
            {
                double z = LocalRandom.rand();
                if (z < 0.5)
                {
                    voters[i-1] = voters[i];
                }
                else
                {
                    voters[0] = voters[i];
                }
            }
            return;
        }
        if (i == 0)
        {
            if (voters[i] == voters[i+1])
            {
                double z = LocalRandom.rand();
                if (z < 0.5)
                {
                    voters[voters.length - 1] = voters[i];
                }
                else
                {
                    voters[i+2] = voters[i];
                }
            }
            return;
        }
        
        if (voters[i] == voters[i+1])
        {
            double z = LocalRandom.rand();
            
            if (z < 0.5)
            {
                voters[i-1] = voters[i];
            }
            else
            {
                voters[i+2] = voters[i];
            }
            return;
        }
    }


    /* doStep: do N updates at randomly chosen locations in the voter
     * array, where N is the length of the voters array.  Note an
     * array may be changed more than once in a step.
     */
    public static void doStep(boolean[] voters) 
    {
	    for (int i = 0; i < voters.length; i++)
	    {
	        double x = LocalRandom.rand();
	        double y = x * voters.length;
	        int q = (int)y;
	        update(voters, q);
	    }
    }


    /* hasConverged: determines whether the voters have converged a
     *   single opinion
     *     
     *   Returns true if all the voters share the same opinion, false
     *   otherwise.
     */
    public static boolean hasConverged(boolean[] voters) 
    {
        for(int i = 1; i < voters.length; i++)
        {
            if(voters[i] != voters[i-1])
            {
                return false;
            }
        }
        return true;
    }



    /* doOneSimulation: Simulate steps until voters converge on a
     *   single opinion or MAX_STEPS steps have been taken.
     *
     *   Returns: the number of steps executed.
     */
    public static int doOneSimulation(boolean[] voters) 
    {
	    for(int i = 1; i <= MAX_STEPS; i++)
	    {
	        doStep(voters);
	        boolean z = hasConverged(voters);
	        
	        if(z == true)
	        {
	            return i;
	        }
	    }
	    return MAX_STEPS;
    }

    /* estimateExitProbability: run numTrials simulations
     *
     *   int numTrials: the number of trials to run
     *   int numVoters - number of voters in the sample
     *   double pctA - probability that a voter will hold Opinion A
     *     initially
     *
     *   Returns the percentage of trials that ended with all the voters holding
     *    Opinion A, aka, the exit probability.
     */
    public static double estimateExitProbability(int numTrials, int numVoters, double pctA){
        double successCount = 0; //Counts sucesses, so we can get a percentage
        for(int i = 0; i < numTrials; i++)
        {
            boolean isTrue = true;
            boolean[] voters = Utility.generateVoters(numVoters, pctA);
            int a = doOneSimulation(voters);

            for(int w = 0; w < voters.length; w++)
            {
                
                if(voters[w] == false)
                {
                    isTrue = false;
                    break;
                }
                else
                {
                    isTrue = true;
                }
                
            }
            if(isTrue == true)
            {
                successCount++;
            }
        }
        double q = successCount/numTrials;
	    return q;
    }


    public static void main(String[] args) {
	String usage = "usage: java Opinion [-s] <number of trials> <number of voters>  <initial percentage of A voters>";
	int argIndex = 0;
	int numVoters = 0;
	int numTrials = 0;
	double percentA = 0.0;


	// parse the arguments
	try {
	    if (args.length == 3) {
		LocalRandom.initRandom();
		numTrials = Integer.parseInt(args[0]);
		numVoters = Integer.parseInt(args[1]);
		percentA = Double.parseDouble(args[2]);
	    } else if ((args.length == 4) && (args[0].equals("-s"))) {
		LocalRandom.initRandom(1316535669118L);
		numTrials = Integer.parseInt(args[1]);
		numVoters = Integer.parseInt(args[2]);
		percentA = Double.parseDouble(args[3]);
	    } else {
		System.err.println(usage);
		System.exit(0);
	    }


	} catch (Exception e) {
	    System.err.println(usage);
	}

	System.out.printf("%.2f\n", estimateExitProbability(numTrials, numVoters, percentA));

    }
}
