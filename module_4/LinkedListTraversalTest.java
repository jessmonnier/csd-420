/* 
 * Jess Monnier
 * CSD-420 Module 4 Programming Assignment
 * 15 June 2025
 * This program builds and tests LinkedLists of 50k & 500k
 * integers to see the timing difference between using
 * an iterator versus the .get() method
 */

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListTraversalTest {

    public static void main(String[] args) {
        
        // Welcome message
        System.out.println("\nThis program compares the timing of an Iterator versus .get() on large LinkedLists.");
        System.out.println("Please note that the 500,000 integer test will likely take a few minutes to complete.");
        pause();
        
        System.out.println("\nFirst test, 50,000 integers:");
        testTraversal(50_000);
        pause();

        System.out.println("\nSecond test, 500,000 integers:");
        testTraversal(500_000);
        pause();

        // Summary statement
        System.out.println("\nIterator is typically faster because get(index) on LinkedList is O(n), making the total time O(n^2).");
    }

    private static void testTraversal(int size) {
        LinkedList<Integer> list = new LinkedList<>();

        // Fill the list with integers
        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        // Test traversal using iterator
        long iteratorStart = System.nanoTime();
        int iteratorSum = 0;
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            iteratorSum += iterator.next();
        }
        long iteratorEnd = System.nanoTime();
        double iteratorTime = (iteratorEnd - iteratorStart) / 1_000_000.0;
        System.out.println("Iterator traversal time: " + iteratorTime + " ms");

        // Test traversal using get(index)
        long getStart = System.nanoTime();
        int getSum = 0;
        for (int i = 0; i < list.size(); i++) {
            getSum += list.get(i);
        }
        long getEnd = System.nanoTime();
        double getTime = (getEnd - getStart) / 1_000_000.0;
        System.out.println("get(index) traversal time: " + getTime + " ms");

        // Summary of performance
        double difference = (getTime - iteratorTime) / 1_000;
        System.out.println("The iterator was " + difference + " seconds faster!");
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
