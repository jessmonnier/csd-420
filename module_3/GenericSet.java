/* 
 * Jess Monnier
 * CSD-420 Programming Assignment 3
 * 12 June 2025
 * A program to essentially create a set from an ArrayList
 */

 import java.util.ArrayList;
 import java.util.Random;

public class GenericSet {
    public static void main(String[] args) {
        
        // Welcome message
        System.out.println("\nThis program generates an ArrayList of 50 random numbers within a set range");
        System.out.println("and then removes duplicates and prints the de-duplicated result.");
        pause();

        // 50 random integers
        System.out.println("\nFirst we'll use 50 integers between 1 & 20 inclusive.");
        pause();
        
        ArrayList<Integer> intNumbers = buildArray(50, 1, 20);
        ArrayList<Integer> uniqueInts = removeDuplicates(intNumbers);
        System.out.println("\nDe-duplicated integers:");
        System.out.println(uniqueInts);
        pause();

        // 50 semi-random doubles (see buildArray method for randomization control)
        System.out.println("\nNext we'll use 50 doubles between 0.1 & 2.0 inclusive, rounded to the nearest tenth.");
        pause();

        ArrayList<Double> doubleNumbers = buildArray(20, 0.1, 2.0);
        ArrayList<Double> uniqueDoubles = removeDuplicates(doubleNumbers);
        System.out.println("\nDe-duplicated doubles:");
        System.out.println(uniqueDoubles);
    }

    // Build a generic array; elements must be of type Number
    @SuppressWarnings("unchecked") // We check & handle within the method
    public static <E extends Number> ArrayList<E> buildArray(int numElements, Number minValue, Number maxValue) {
        Random random = new Random();

        // For doubles, ensure the array falls within the min and max values
        // and that all values are rounded to the nearest tenth
        if (minValue instanceof Double && maxValue instanceof Double) {
            ArrayList<E> doubleArray = new ArrayList<>();
            double min = minValue.doubleValue();
            double max = maxValue.doubleValue();
            for (int i=0; i<numElements; i++) {
                double raw = min + (max - min) * random.nextDouble();
                double rounded = Math.round(raw * 10.0) / 10.0;
                doubleArray.add((E) Double.valueOf(rounded));
            }
            return doubleArray;
        } else if (minValue instanceof Integer && maxValue instanceof Integer) {
            ArrayList<E> intArray = new ArrayList<>();
            int min = minValue.intValue();
            int max = maxValue.intValue();
            for (int i=0; i<numElements; i++) {
                int value = random.nextInt((max - min) + 1) + min;
                intArray.add((E) Integer.valueOf(value));
            }
            return intArray;
        } else {
            // Error if not doubles/ints or mismatched; return empty ArrayList
            System.out.println("At this time only Doubles and Integers are allowed.");
            return new ArrayList<>();
        }
    }

    // Method to return generic ArrayList of only unique values from the provided list
    public static <E> ArrayList<E> removeDuplicates(ArrayList<E> list) {
        ArrayList<E> result = new ArrayList<>();
        for (E item : list) {
            if (!result.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }

    // Method to pause briefly for terminal flow control
    public static void pause () {
        
        try {
            Thread.sleep(2000); // Pause for two seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
