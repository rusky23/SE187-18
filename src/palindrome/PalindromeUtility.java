package palindrome;

import java.util.Stack;

/**
 * Utility for checking and evaluating palindromes.
 *
 */
public class PalindromeUtility {

    public static int MIN_EXPECTED_ARGS = 1;
    public static int MIN_PALINDROME_LENGTH = 1;

    /**
     * Runs the isPalindrome function using input from the command line as the
     * input for the function. Prints the result of isPalindrome.
     * 
     * @param args
     *            any string that may be a palindrome; ignores non-alphabetical
     *            characters
     */
    public static void main(String[] args) {
	String possiblePalindrome;

	// Validate input
	try {
	    possiblePalindrome = validateArgs(args);
	} catch (IllegalArgumentException iae) {
	    System.err.println(iae.getMessage());
	    return;
	}

	// Gets the length of the given string
	int palindromeLength = possiblePalindrome.length();

	String output =
		"'" + possiblePalindrome + "' has a length of "
			+ palindromeLength + " and is ";
	// Checks if the string is a palindrome and prints the result
	if (isPalindrome(possiblePalindrome)) {
	    output += "a palindrome";
	} else {
	    output += "not a palindrome";
	}
	System.out.println(output);
    }

    /**
     * Verifies the input given is a single string.
     * 
     * @param args
     *            a single string that may be a palindrome; ignores
     *            non-alphabetical characters
     * @return the string given
     */
    private static String validateArgs(String[] args) {
	// Create empty string to add alpha characters to
	String possiblePalindrome = "";
	// Verify correct command-line argument quantity
	if (args.length < MIN_EXPECTED_ARGS) {
	    throw new IllegalArgumentException("More than " + MIN_EXPECTED_ARGS
		    + " parameter(s) required");
	}
	// Loop through each string in args
	for (int argsIndex = 0; argsIndex < args.length; argsIndex++) {
	    String someString = args[argsIndex];
	    // Loop through each character in the string
	    for (int characterIndex = 0; characterIndex < someString.length();
		    characterIndex++) {
		// Append alphabetical characters to 'possiblePalindrome'
		if (Character.isAlphabetic(
			someString.codePointAt(characterIndex))) {
		    possiblePalindrome +=
			    someString.substring(characterIndex,
				    characterIndex + 1);
		}
	    }
	}

	// Verify 'possiblePalindrome' is long enough
	if (possiblePalindrome.length() < MIN_PALINDROME_LENGTH) {
	    throw new IllegalArgumentException(
		    "More than " + MIN_PALINDROME_LENGTH
			    + " alphabetical character(s) required");
	}

	return possiblePalindrome;

    }

    /**
     * Checks if the given string is a palindrome. Relies on the String equals
     * method for comparisons
     * 
     * @param possiblePalindrome
     *            a string that might be a palindrome
     * @return true if the string is a palindrome, false otherwise
     */
    private static boolean isPalindrome(String possiblePalindrome) {
	boolean isPalindrome = true;
	Stack<String> reversed = new Stack<>();
	// Reverse the string by adding each character to a stack
	for (int characterIndex = 0;
		characterIndex < possiblePalindrome.length();
		characterIndex++) {
	    reversed.push(possiblePalindrome.substring(characterIndex,
		    characterIndex + 1));
	}

	// Check if stack order matches string order (is a palindrome)
	for (int characterIndex = 0; !reversed.empty(); characterIndex++) {
	    String stackCharacter = reversed.pop();
	    if (!stackCharacter.equals(possiblePalindrome.substring(
		    characterIndex, characterIndex + 1))) {
		// Break and return false if a character does not match
		isPalindrome = false;
		break;
	    }
	}

	return isPalindrome;
    }
}
