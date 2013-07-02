public class TestEstimateExitProbability {
    public static void test(int numTrials, int numVoters, double percentA, double expected) {
	LocalRandom.initRandom(1316535669118L);

	double result = Opinion.estimateExitProbability(numTrials, numVoters, percentA);

	if (expected == result) {
	    System.out.printf("Success: call to computeExitProbability(%d, %d, %f) generated %f as expected.\n", numTrials,
			       numVoters, percentA, result);
	} else {
	    System.out.printf("Failure: on call to computeExitProbability(%d, %d, %f).  Expected %f.  Got %f.\n", numTrials,
		numVoters, percentA, expected, result);
	}
    }


    public static void main(String[] args) {
	int numVoters = 50;
	int numTrials = 100;
	double percentA;

	    test(numTrials, numVoters, 0.0, 0.0);
	    test(numTrials, numVoters, 0.1, 0.01);
	    test(numTrials, numVoters, 0.5, 0.47);
	    test(numTrials, numVoters, 0.95, 1.0);
    }
}

