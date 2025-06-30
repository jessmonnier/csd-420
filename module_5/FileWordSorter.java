/* 
 * Jess Monnier
 * CSD-420 M5 Programming Assignment
 * 22 June 2025
 * A program to sort and check the frequency of words from an input file.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class FileWordSorter {

    public static final String FILE = "collection_of_words.txt";
    public static TreeMap<String, Integer> wordMap = new TreeMap<>();
    public static int lineCount = 0;

    public static void main (String[] args) {

        // Welcome message
        System.out.println("\nThis program will read the contents of " + FILE +
        ", a poem titled 'If Feeling Isn't In It' by John Brehm.");
        pause();
        // The two second pause described below is far from perfect but at least feels less  
        // overwhelming than the walls of text that would result otherwise.
        System.out.println("\nNote that lines will be printed ~ 5 at a time with a 2 second pause.");
        pause();
        System.out.println("\nFirst, let's see the poem in its entirety:");
        pause();

        // Try block to read the file, print initial poem, and populate the TreeMap
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line; // initialize var to hold each line of poem
            System.out.println("\nIf Feeling Isn't In It by John Brehm\n");
            
            // While loop to print each line & store its words in wordMap TreeMap
            while ((line = br.readLine()) != null) {
                
                // Print the line of the poem
                System.out.println(line);
                lineCount++;

                // Pause briefly after every 5 lines
                if (lineCount > 0 && lineCount % 5 == 0) {
                    pause();
                }
                
                // Split the line into an array, using whitespace as the divider
                List<String> words = Arrays.asList(line.split("\\s+"));
                // Iterate over each element of the words array
                for (String word: words) {
                    // Ensure we're only getting words & apostrophes, no commas or periods, & it's all lowercase
                    word = word.replaceAll("[^a-zA-Zé']", "").toLowerCase();
                    // Ignore empty "words"
                    if (word.isEmpty()) continue;
                    // If the word is in the map, increment count, otherwise add it with count 1
                    wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

        // Reset lineCount
        lineCount = 0;

        // Print words alphabetically
        pause();
        System.out.println("\nNow we'll print the unique words from the poem in alphabetical order, including count:\n");
        pause();
        for (String w : wordMap.keySet()) {
            System.out.println(w + ": " + wordMap.get(w));
            lineCount++;
            // Pause briefly after every 5 lines
            if (lineCount > 0 && lineCount % 5 == 0) {
                pause();
            }
        }
        
        // Reset lineCount
        lineCount = 0;
        
        // Print in reverse order
        pause();
        System.out.println("\nNow we'll print the unique words from the poem in reverse alphabetical order, including count:\n");
        pause();
        NavigableMap<String, Integer> reverseMap = wordMap.descendingMap();
        for (String key : reverseMap.keySet()) {
            System.out.println(key + ": " + reverseMap.get(key));
            lineCount++;
            // Pause briefly after every 5 lines
            if (lineCount > 0 && lineCount % 5 == 0) {
                pause();
            }
        }

        // Exit message
        pause();
        System.out.println("\nThat's it! Have a nice day!");
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