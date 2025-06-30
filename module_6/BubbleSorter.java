/* 
 * Jess Monnier
 * CSD-420 Programming Assignment M6
 * 29 June 2025
 * A program to bubble sort using Comparable and Comparator.
 */

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Random;

public class BubbleSorter {

   public static void main (String[] args) {
         
      // Generate an Integer array using the ArrayGenerator class, specifying length, class, min, and max
      Integer[] intArray = ArrayGenerator.generateArray(10, Integer.class, 0, 100);
      System.out.println("Original Random Integer array:");
      printArray(intArray);

      // Use our bubbleSort method to sort the Integer array, then print the results
      bubbleSort(intArray);
      System.out.println("Sorted using Comparable:");
      printArray(intArray);

      // Generate a Double array using the ArrayGenerator class, specifying length, class, min, and max
      Double[] doubleArray = ArrayGenerator.generateArray(5, Double.class, 1.5, 9.5);
      System.out.println("\nOriginal Random Double array:");
      printArray(doubleArray);

      // Use our bubbleSort method to sort the Double array, then print the results
      bubbleSort(doubleArray, Comparator.reverseOrder());
      System.out.println("Sorted using Comparator (reverse order):");
      printArray(doubleArray);
   }

   // Method to bubble sort using Comparable interface
   public static <E extends Comparable<E>> void bubbleSort (E[] list) {
      // Iterate over the elements. End 1 before the final element bc we'll get it with our +1 arguments
      for (int i=0; i<list.length-1; i++) {
         for (int j=0; j<list.length-1; j++) {
            // Compare current & next element. Swap if next element is smaller.
            if (list[j].compareTo(list[j+1]) > 0) {
               E temp = list[j];
               list[j] = list[j+1];
               list[j+1] = temp;
            }
         }
      }
   }

   // Method to bubble sort using Comparator interface
   // This is essentially the same as Comparable except we compare using comparator.compare instead of .compareTo()
   public static <E> void bubbleSort (E[] list, Comparator<? super E> comparator) {
      for (int i=0; i<list.length-1; i++) {
         for (int j=0; j<list.length-1; j++) {
            if (comparator.compare(list[j], list[j+1]) > 0) {
               E temp = list[j];
               list[j] = list[j+1];
               list[j+1] = temp;
            }
         }
      }
   }

   // Method to print an array with elements of flexible class.
   public static <E> void printArray(E[] array) {
        System.out.print("{ ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
               System.out.print(", ");
            }
        }
        System.out.println(" }");
    }

   // An ArrayGenerator class to randomly generate an array with length, Class, min, and max as arguments
   public class ArrayGenerator {

      // Create our random variable
      private static final Random random = new Random();
      
      // Constructor taking length, Class, min, and max arguments
      public static <T> T[] generateArray(int length, Class<T> type, double min, double max) {
         // Suppress warnings because the type/Class isn't checked; for an example of this scope, that doesn't matter
         @SuppressWarnings("unchecked")
         // Create a new array with the specified Class and length
         T[] array = (T[]) Array.newInstance(type, length);
         // Iterate over the required length to add random elements
         for (int i = 0; i < length; i++) {
            // Generate random elements in the event that Class is Integer (respecting min/max)
            if (type == Integer.class) {
                int value = (int) (random.nextDouble() * (max - min) + min);
                array[i] = type.cast(value);
            // Generate random elements in the event Class is Double (respecting min/max)
            } else if (type == Double.class) {
                double raw = random.nextDouble() * (max - min) + min;
                double rounded = Math.round(raw * 100.0) / 100.0; // Round to nearest hundredth
                array[i] = type.cast(rounded);
            // Catch potential exception of unsupported Class (not Integer or Double)
            } else {
                throw new IllegalArgumentException("Unsupported type: " + type.getName());
            }
        }
         return array;
      }
   }
}