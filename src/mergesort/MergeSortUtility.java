package mergesort;

import java.util.Arrays;

public class MergeSortUtility {

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


	System.out.println(Arrays.asList(array_to_sort));
	Comparable[] sorted = MergeSortUtility.nonRecSort(array_to_sort);
	System.out.println(Arrays.asList(sorted));

	Integer[] testArray = { 12, 60, 32, 44, 30, 99, 3, 39, 15, 18, 100, 5 };
	System.out.println(Arrays.asList(testArray));
	Comparable[] sortedTestArray = MergeSortUtility.nonRecSort(testArray);
	System.out.println(Arrays.asList(sortedTestArray));
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
		    "At least one paramter is required");
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

    private static Comparable[] merge(Comparable[] array, Comparable[] sorted,
	    int first, int mid, int last) {
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

    public static <T extends Comparable> Comparable[] sort(T[] array) {
	int size = array.length;
	Comparable[] sorted = new Comparable[size];
	for (int i = 1; i < size; i *= 2) {
	    for (int lo = 0; lo < size - i; lo += i) {
		// array = (T[]) merge(array, sorted, lo, lo + i - 1,
		// Math.min(lo + i + i - 1, size - 1));
	    }
	}
	return array;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable> T[] nonRecSort(T[] array) {
	int size = array.length;
	for (int partitionSize = 0; partitionSize <= size; partitionSize *= 2) {
	    partitionSize++;
	    Comparable[] temp = new Comparable[size];
	    int first = 0;
	    int last = 0; // Initialized here to handle extra elements not
			  // covered by partitions
	    int mid = 0;
	    while (first <= size) {
		last = first + partitionSize;
		if (last >= size) {
		    last = size - 1;
		}
		mid = (last + first) / 2;

		temp = (T[]) merge(array, temp, first, mid, last);

		first += partitionSize + 1;
	    }
	    array = (T[]) Arrays.copyOf(temp, size);

	    if (first != size) {
		mid = (first - partitionSize - 1) - 1; // set division to end of
						       // last complete
						       // partition
		first = first - 2 * partitionSize - 2; // reset to index at
						       // beginning of last
						       // complete partition
		last = size - 1; // set to the last array element
		if (first < 0) {
		    continue;
		}
		temp = (T[]) merge(array, temp, first, mid, last);
		array = (T[]) temp;
	    }
	}
	// array = temp;
	return array;
    }

    private static <T extends Comparable> boolean less(T v, T w) {
	return v.compareTo(w) < 0;
    }
}
