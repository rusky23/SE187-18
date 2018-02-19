package mergesort;

import java.util.Arrays;

/**
 * Class that runs Merge Sort on a list of integers passed in via command line.
 *
 */
public class MergeSortUtility {
    /**
     * Sorts the given list of integers via Merge Sort. Any integer is a valid
     * input. If any non-integer input is given, the program will not be run.
     * 
     * @param args
     *            the array containing the console input
     */
    public static void main(String[] args) {
	Integer[] array_to_sort;

	// Validate input
	try {
	    array_to_sort = validateArgs(args);
	} catch (NumberFormatException nfe) {
	    System.err.println("Arguments given must be of type int.");
	    return;
	} catch (IllegalArgumentException iae) {
	    System.err.println(iae.getMessage());
	    return;
	}

	// Sort array
	Integer[] sorted = MergeSortUtility.nonRecSort(array_to_sort);
	// Print sorted array
	System.out.println(Arrays.asList(sorted));
    }

    /**
     * Verifies the inputs given are all ints.
     * 
     * @param args
     *            any number of integers to represent an array to be sorted
     * @return an int array consisting of the command-line input
     */
    private static Integer[] validateArgs(String[] args) {
	// Verify that there is at least one command-line argument
	if (args.length < 1) {
	    throw new IllegalArgumentException(
		    "At least one parameter is required.");
	}

	// Create array to hold triangle sides
	Integer[] array_to_sort = new Integer[args.length];

	// Verify arguments are ints and store into 'array_to_sort'
	for (int argsIndex = 0; argsIndex < args.length; argsIndex++) {
	    int intValue = Integer.parseInt(args[argsIndex]);
	    array_to_sort[argsIndex] = intValue;
	}

	return array_to_sort;
    }

    /**
     * Helper method for the Merge Sort algorithm. Sorts a section of the array
     * using the given indexes.
     * 
     * @param array
     *            the array being sorted
     * @param sorted
     *            the sorted version of 'array'
     * @param first
     *            the beginning index of the sort
     * @param mid
     *            the midpoint index of the sort
     * @param last
     *            the last index of the sort
     * @return the 'sorted' array with the given section sorted
     */
    private static Integer[] merge(Integer[] array, Integer[] sorted, int first,
	    int mid, int last) {
	int beginHalf1 = first;
	int endHalf1 = mid;
	int beginHalf2 = mid + 1;
	int endHalf2 = last;

	for (int sortedIndex = first; sortedIndex <= last; sortedIndex++) {
	    if (beginHalf1 > endHalf1)
		sorted[sortedIndex] = array[beginHalf2++];
	    else if (beginHalf2 > endHalf2)
		sorted[sortedIndex] = array[beginHalf1++];
	    else if (array[beginHalf1].compareTo(array[beginHalf2]) <= 0)
		sorted[sortedIndex] = array[beginHalf1++];
	    else if (array[beginHalf1].compareTo(array[beginHalf2]) > 0)
		sorted[sortedIndex] = array[beginHalf2++];
	    else {
		throw new RuntimeException();
	    }
	}
	return sorted;
    }

    /**
     * Sorts the given array using the merge sort algorithm
     * 
     * @param array
     *            the array to sort
     * @return the sorted array
     */
    public static Integer[] nonRecSort(Integer[] array) {
	int size = array.length;
	for (int partitionSize = 0; partitionSize <= size; partitionSize *= 2) {
	    partitionSize++;
	    Integer[] temp = new Integer[size];
	    int first = 0;
	    /*
	     * 'last' initialized here to handle extra elements not covered by
	     * partitions
	     */
	    int last = 0;
	    int mid = 0;
	    while (first <= size) {
		last = first + partitionSize;
		if (last >= size) {
		    last = size - 1;
		}
		mid = (last + first) / 2;

		temp = merge(array, temp, first, mid, last);

		first += partitionSize + 1;
	    }
	    array = Arrays.copyOf(temp, size);

	    if (first != size) {
		// Set division to end of last complete partition
		mid = (first - partitionSize - 1) - 1;
		// Reset to index at beginning of last complete partition
		first = first - 2 * partitionSize - 2;
		// Set to the last array element
		last = size - 1;
		if (first < 0) {
		    continue;
		}
		temp = merge(array, temp, first, mid, last);
		array = temp;
	    }
	}
	return array;
    }
}
