package triangleproblem;

public class Triangles {
    private static int SIDES_IN_TRIANGLE = 3;
    private static int EXPECTED_ARGS = 3;

    /**
     * Determines if a valid triangle can be created, given three integer values
     * that represent the lengths of the sides of the triangle. If the values
     * form a valid, triangle, determines the triangle type (scalene, isosceles,
     * or equilateral).
     * 
     * @param args
     *            three positive integers to represent the sides of a possible
     *            triangle
     */
    public static void main(String[] args) {
	int[] sides;
	// Validate input
	try {
	    sides = validateArgs(args);
	} catch (NumberFormatException nfe) {
	    System.err.println("Arguments given must be of type int.");
	    return;
	} catch (IllegalArgumentException iae) {
	    System.err.println(iae.getMessage());
	    return;
	}
	// Determine if valid triangle
	boolean isValidTriangle = isTriangle(sides);

	// Determine triangle type or report as not a triangle
	if (isValidTriangle) {
	    String triangleType = getTriangleType(sides);
	    System.out.println("Side lengths " + arrayToString(sides)
		    + " represent a triangle of type " + triangleType);
	} else {
	    System.out.println("Side lengths " + arrayToString(sides)
		    + " do not represent a valid triangle.");
	}

    }

    /**
     * Verifies the side lengths given are positive ints.
     * 
     * @param args
     *            three positive integers to represent the sides of a possible
     *            triangle
     * @return an int array containing the three triangle sides
     */
    private static int[] validateArgs(String[] args) {
	// Create array to hold triangle sides
	int sides[] = new int[SIDES_IN_TRIANGLE];

	// Verify correct command-line argument quantity
	if (args.length != EXPECTED_ARGS) {
	    throw new IllegalArgumentException(
		    "Exactly " + EXPECTED_ARGS + " paramters required");
	}

	// Verify arguments are positive ints and store into 'sides'
	for (int argsIndex = 0; argsIndex < EXPECTED_ARGS; argsIndex++) {
	    int intValue = Integer.parseInt(args[argsIndex]);
	    if (intValue <= 0) {
		throw new IllegalArgumentException(
			"Integers given must be greater than zero.");
	    }
	    sides[argsIndex] = intValue;
	}

	return sides;
    }

    /**
     * Checks if the side lengths given represent a valid triangle. Uses the
     * Triangle Inequality Theorem.
     * 
     * @param sides
     *            three positive integers to represent the sides of a possible
     *            triangle
     * @return if the sides form a valid triangle
     */
    private static boolean isTriangle(int[] sides) {
	boolean isValid = true;

	/*
	 * If any one side is not larger than the other two sides combined, the
	 * triangle is invalid
	 */
	if (sides[0] + sides[1] <= sides[2] || sides[1] + sides[2] <= sides[0]
		|| sides[2] + sides[0] <= sides[1]) {
	    isValid = false;
	}

	return isValid;
    }

    /**
     * Checks the type of the triangle represented by the side lengths given.
     * 
     */
    private static String getTriangleType(int[] sides) {
	String triangleType = "";
	int x = sides[0];
	int y = sides[1];
	int z = sides[2];

	if (x == y && y == z) {
	    // Check to see if it is equilateral
	    triangleType = "equilateral";
	} else if ((x == y && y != z) || (x != y && z == x)
		|| (z == y && z != x)) {
	    // Check to see if it is isosceles
	    triangleType = "isosceles";
	} else if (x != y && y != z && z != x) {
	    // Check to see if it is scalene
	    triangleType = "scalene";
	}
	return triangleType;
    }

    private static String arrayToString(int[] array) {
	String arrayAsString = "[";
	for (int element : array) {
	    arrayAsString += element + ", ";
	}
	arrayAsString =
		arrayAsString.substring(0,
			arrayAsString.length() - ", ".length());

	return arrayAsString + "]";
    }
}
