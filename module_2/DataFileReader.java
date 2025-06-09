/* 
 * Jess Monnier
 * CSD-420 Programming Assignment 2
 * 8 June 2025
 * A program to read and display arrays from a text file.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataFileReader {
    public static void main(String[] args) {
        
        // Establish filename as a variable for ease of later change if desired
        String myName = "jmonnier";
        String filename = myName + "_datafile.dat";

        // Read the file, or if that fails print the associated error
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("\nContents of " + filename + ":");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }
}