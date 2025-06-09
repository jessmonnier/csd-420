/* 
 * Jess Monnier
 * CSD-420 Programming Assignment 2
 * 8 June 2025
 * A program to create arrays of random doubles or integers
 * and store these arrays in a text file.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataFileWriter {
    public static void main(String[] args) {
        
        // Establish starting variables
        int arrayLength = 5;
        int pauseLength = 1000;
        String myName = "jmonnier";
        int[] randomInts = new int[arrayLength];
        double[] randomDoubles = new double[5];
        Random rand = new Random();

        // Generate random integers and doubles
        for (int i = 0; i < arrayLength; i++) {
            randomInts[i] = rand.nextInt(100); // random int between 0-99
            randomDoubles[i] = rand.nextDouble() * 100; // random double between 0.0-99.99
        }

        // File to write
        String filename = myName + "_datafile.dat";

        // Welcome message
        System.out.println("\nThis program will generate a random array of " +
        arrayLength + " integers and another of \nthe same number of doubles, " +
        "and store these in a file named " + filename);

        // Use our pause method to control terminal output flow
        pause(pauseLength);

        // Show progress
        System.out.println("\nGenerating arrays...");
        pause(pauseLength);

        // Write or append to the file
        try (FileWriter fw = new FileWriter(filename, true)) {
            System.out.println("\nInteger array generated:");
            fw.write("Integers: \n");
            for (int i : randomInts) {
                fw.write(i + " ");
                System.out.print(i + " ");
            }

            pause(pauseLength);
            System.out.println("\n\nDouble array generated:");
            fw.write("\nDoubles: \n");
            for (double d : randomDoubles) {
                fw.write(String.format("%.2f ", d));
                System.out.print(String.format("%.2f ", d));
            }
            fw.write("\n---\n"); // separator for clarity

            pause(pauseLength);

            // Let user know write was successful
            System.out.println("\n\nData written successfully to " + filename);

        } catch (IOException e) {
            System.out.println("\n\nAn error occurred while writing the file.");
            e.printStackTrace();
        }
    }

    // Method to pause for easier to follow terminal output
    public static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // reset interrupted status
            e.printStackTrace();
        }
    }

}