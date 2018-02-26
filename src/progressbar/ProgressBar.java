package progressbar;

import java.awt.Point;

/**
 * Represents a circular progress bar on a screen. The screen constants adjust
 * the background boundaries of the progress bar as a square. The screen has its
 * origin in the bottom left and its maximum at the top right. For the progress
 * bar, the polar coordinate system is used to simplify calculations. The
 * standard pole for Polar coordinates is on the x-axis of the Cartesian
 * coordinate system. Positive angles are measured counterclockwise. These
 * constants adjust the start angle (0% progress) of the progress circle and the
 * angle multiplier (-1 means clockwise without scaling).
 */
public class ProgressBar {
    // Screen constants
    public static int SCREEN_SIZE = 100;
    public static Point SCREEN_ORIGIN = new Point(0, 0);
    public static Point SCREEN_MAXIMUM = new Point(SCREEN_SIZE, SCREEN_SIZE);
    public static Point SCREEN_CENTER =
	    new Point(SCREEN_MAXIMUM.x / 2, SCREEN_MAXIMUM.y / 2);

    // Progress bar constants
    public static int PROGRESS_RADIUS = SCREEN_SIZE / 2;
    public static int ANGLE_MULTIPLIER = -1;
    public static double PROGRESS_START_ANGLE = -90;
    public static int PROGRESS_MINIMUM = 0;
    public static int PROGRESS_MAXIMUM = 100;
    public static String COMPLETED_PROGRESS_COLOR = "RED";
    public static String BACKGROUND_COLOR = "BLUE";

    // Other constants
    public static int EXPECTED_ARGS = 3;
    public static Point CARTESIAN_ORIGIN = new Point(0, 0);
    /*
     * Adjusts output of inverse tangent (degrees) to be in the range of [0,
     * 360) rather than [-180, 180)
     */
    public static int ATAN_RANGE_ADJUSTMENT = 180;

    /**
     * Runs the getColor function using input from the command line as the
     * parameters for the function. Prints the result of getColor.
     * 
     * @param args
     *            three integers to represent the percentage completed, the x
     *            coordinate of the point on screen, and the y coordinate of the
     *            point on screen.
     */
    public static void main(String[] args) {
	int progressCompleted;
	Point pointOnScreen;

	// Validate input
	try {
	    int[] valid_args = validateArgs(args);
	    progressCompleted = valid_args[0];
	    pointOnScreen = new Point(valid_args[1], valid_args[2]);
	} catch (NumberFormatException nfe) {
	    System.err.println("Arguments given must be of type int.");
	    return;
	} catch (IllegalArgumentException iae) {
	    System.err.println(iae.getMessage());
	    return;
	}

	// Gets the color of the specified point and prints the result
	String pointColor;
	try {
	    pointColor = getColor(progressCompleted, pointOnScreen);
	} catch (IllegalArgumentException iae) {
	    System.err.println(iae.getMessage());
	    return;
	}
	System.out.println(pointColor);
    }

    /**
     * Verifies the inputs given are all ints.
     * 
     * @param args
     *            three integers to represent the percentage completed, the x
     *            coordinate of the point on screen, and the y coordinate of the
     *            point on screen.
     * @return an int array consisting of the command-line input
     */
    private static int[] validateArgs(String[] args) {
	// Create point to hold point on screen
	int[] valid_args = new int[EXPECTED_ARGS];

	// Verify correct command-line argument quantity
	if (args.length != EXPECTED_ARGS) {
	    throw new IllegalArgumentException(
		    "Exactly " + EXPECTED_ARGS + " paramters required");
	}

	// Verify arguments are positive ints and store into 'valid_args'
	for (int argsIndex = 0; argsIndex < EXPECTED_ARGS; argsIndex++) {
	    int intValue = Integer.parseInt(args[argsIndex]);
	    valid_args[argsIndex] = intValue;
	}

	return valid_args;

    }

    /**
     * Returns the color of a specified point on the screen, given a percentage
     * of progress completed on the progress bar. All points are blue unless
     * they are within the sector of the progress bar that represents the
     * completed percentage. The progress sector shown is red.
     * 
     * @param progressCompleted
     *            an integer between 0 and 100, inclusive, representing the
     *            percent of progress completed.
     * @param pointOnScreen
     *            the point on the screen to determine the color of. Must have x
     *            and y values within the screen boundaries
     * @return a string indicating the color of the given point
     */
    private static String getColor(int progressCompleted, Point pointOnScreen) {
	// Defines colors of specified areas

	// Validate input
	if (progressCompleted < PROGRESS_MINIMUM
		|| progressCompleted > PROGRESS_MAXIMUM) {
	    throw new IllegalArgumentException("Progress given must be between "
		    + PROGRESS_MINIMUM + " and " + PROGRESS_MAXIMUM + ".");
	}

	if (pointOnScreen.x < SCREEN_ORIGIN.x
		|| pointOnScreen.x > SCREEN_MAXIMUM.x
		|| pointOnScreen.y < SCREEN_ORIGIN.y
		|| pointOnScreen.y > SCREEN_MAXIMUM.y) {
	    throw new IllegalArgumentException("Point given must be between "
		    + pointAsCoordinate(SCREEN_ORIGIN) + " and "
		    + pointAsCoordinate(SCREEN_MAXIMUM) + ".");
	}

	// Check special conditions
	if (progressCompleted == 0) {
	    return BACKGROUND_COLOR;
	}
	if (progressCompleted > 0 && pointOnScreen.equals(SCREEN_CENTER)) {
	    return COMPLETED_PROGRESS_COLOR;
	}

	/*
	 * Find angle, in degrees, for progress sector, adjusted to follow the
	 * standard references for polar coordinates.
	 */
	double progressCompleteAngle = (progressCompleted / 100.0) * 360;
	// Get angle and radius of given point on the screen
	Point cartesianPoint = screenToCartesian(pointOnScreen);
	double polarRadius = cartesianPoint.distance(CARTESIAN_ORIGIN);
	double polarAngle =
		adjustAngle((Math.toDegrees(
			Math.atan2(cartesianPoint.y, cartesianPoint.x)
				* ANGLE_MULTIPLIER))
			+ ATAN_RANGE_ADJUSTMENT + PROGRESS_START_ANGLE);

	if (polarRadius <= PROGRESS_RADIUS) {
	    // the point on screen is within the progress bar circle
	    if (polarAngle <= progressCompleteAngle) {
		// the point on screen is within the circle completed
		return COMPLETED_PROGRESS_COLOR;
	    } else {
		// the point on screen is in the circle not yet completed
		return BACKGROUND_COLOR;
	    }
	} else {
	    // the point on screen is in the background
	    return BACKGROUND_COLOR;
	}
    }

    /**
     * Returns the Cartesian point for the given point on screen. Translates the
     * point on screen to make the center of the screen the origin of the
     * Cartesian system.
     * 
     * @param pointOnScreen
     *            the point on the screen to translate to the Cartesian system
     * @return the Cartesian point translated from the point on screen
     */
    private static Point screenToCartesian(Point pointOnScreen) {
	Point cartesianPoint = new Point(pointOnScreen);
	cartesianPoint.translate(-(SCREEN_MAXIMUM.x / 2),
		-(SCREEN_MAXIMUM.y / 2));
	return cartesianPoint;
    }

    /**
     * Adjusts a given angle (in degrees) to be within the range [0, 360]. Uses
     * the mod funtion.
     * 
     * @param angle
     *            the angle to adjust
     * @return the new angle within [0, 360]
     */
    private static double adjustAngle(double angle) {
	double adjustedAngle = angle;
	while (adjustedAngle < 0) {
	    adjustedAngle += 360;
	}

	while (adjustedAngle > 360) {
	    adjustedAngle -= 360;
	}

	return adjustedAngle;
    }

    /**
     * Returns a coordinate representation of a point.
     * 
     * @param point
     *            the point to represent as a coordinate.
     * @return the coordinate of the given point in the form "(x, y)"
     */
    private static String pointAsCoordinate(Point point) {
	return "(" + point.x + ", " + point.y + ")";
    }
}
