/* CS121 A'11
 * HW1: Opinion Dynamics
 * 
 * Class for testing hasConverged
 *
 * Anne Rogers
 * Sept 2011
 */

public class TestHasConverged {
    /* error: generate an error message
     *  String input: describes input for this test
     *  boolean expected: the expected output
     *  boolean actual: the actual output
     */
    private static void error(String input, boolean expected, boolean actual) {
	int n = input.length();
	System.out.println("Error: " + input);
	System.out.println("  Expected: " + expected);
	System.out.print("  Got     : " + actual);
	System.out.println();
	System.exit(0);
    }


    /* test: do one test
     *  String input: describes input for this test
     *  boolean expected: the expected output
     */
    private static void test(String input, boolean output) {
	int n = input.length();
	boolean[] voters = new boolean[n];	
	
	/* convert input string into a boolean array */
	for (int i = 0; i < n; i++) {
	    voters[i] = (input.charAt(i) == 'A');
	}

	boolean rv = Opinion.hasConverged(voters);

	if (rv != output) {
	    error(input, output, rv);
	} else {
	    System.out.printf("Success: %s => %s\n", input, output);
	}
    }


    public static void main(String[] args) {
	test("AAAAAA", true);
	test("BBBBBB", true);
	test("ABABAB", false);

	/* boundary cases */
	test("AAAAAB", false);
	test("BAAAAA", false);
    }
}