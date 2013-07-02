/* CS121 A'11
 * HW1: Opinion Dynamics
 * 
 * Class for testing update function 
 *
 * Anne Rogers
 * Sept 2011
 */

public class TestUpdate {
    /* error: generate an error message 
     *  String input: describes voters
     *  String output: describes the expected output
     *  int v: voter to use as the lead
     *  boolean[] voters: the actual output
     *  String dir: should up the update happen on the left or the right, if at all
     */
    private static void error(String input, String output, int v, boolean voters[], 
			      String dir) {
	int n = input.length();
	System.out.println("Error: " + input + " @ " + v + " " + dir);
	System.out.println("  Expected: " + output);
	System.out.print("  Got     : ");
	Utility.printVotersOnOneLine(voters);
    }


    /* test: do one test
     *  String input: describes voters
     *  String output: describes the expected output
     *  int v: voter to use as the lead
     *  String dir: should up the update happen on the left or the right, if at all
     */
    private static void test(String input, String output, int v, String dir) {
	int n = input.length();
	boolean[] voters = new boolean[n];	
	int i;
	
	/* convert the string into an array of booleans */
	for (i = 0; i < n; i++) {
	    voters[i] = (input.charAt(i) == 'A');
	}

	Opinion.update(voters, v);

	/* check the output */
	for (i = 0; i < n; i++) {
	    if ((output.charAt(i) == 'A') && (!voters[i])) {
		error(input, output, v, voters, dir);
		break;
	    } else if ((output.charAt(i) == 'B') && (voters[i])) {
		error(input, output, v, voters, dir);
		break;
	    }
	}
	
	if (i == n)
	    System.out.printf("Success: %s @ %d %s => %s\n", input, v, dir, output);

	System.out.println();
    }


    public static void main(String[] args) {
	double[] rands = new double[10];

	LocalRandom.initRandom(rands);

	/* no change: middle */
	test("BABB", "BABB", 1, "");

	/* no change: right edge. Tests wrap-around */
	test("BBBA", "BBBA", 3, "");

	/* change middle, left */
	rands[LocalRandom.next] = 0.25; // left
	test("BAAB", "AAAB", 1, "choose left");

	/* change middle, right */
	rands[LocalRandom.next] = 0.75; // right
	test("BAAB", "BAAA", 1, "choose right");

	/* change middle, right w/ random value of .5 */
	rands[LocalRandom.next] = 0.5; // right
	test("BAAB", "BAAA", 1, "choose right w/ random value of .5");

	/* change @ zero, left.  Tests wrap-around to the left. */
	rands[LocalRandom.next] = 0.05; // left
	test("AABB", "AABA", 0, "choose left");

	/* change @ zero, right. For completeness. */
	rands[LocalRandom.next] = 0.85; // right
	test("AABB", "AAAB", 0, "choose right");

	/* change @ N-1, right.  Test wrap-around to the right */
	rands[LocalRandom.next] = 0.85; // right
	test("ABBA", "AABA", 3, "choose right");

	/* change @ N-1, left. For completeness. */
	rands[LocalRandom.next] = 0.09; // left
	test("ABBA", "ABAA", 3, "choose left");

	rands[LocalRandom.next] = 0.09; // left
	test("AAAABBAA", "AAABBBAA", 4, "choose left");

	rands[LocalRandom.next] = 0.9; // right
	test("AAAABBAA", "AAAABBBA", 4, "choose right");
   }
}