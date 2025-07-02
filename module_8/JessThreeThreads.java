/* 
 * Jess Monnier
 * CSD-420 Module 8 Programming Assignment
 * 1 July 2025
 * A program to utilize three concurrent threads to write random
 * symbols to a text area. I added a pause/resume functionality for
 * each thread. Thought it might be fun/improve my understanding.
 */

import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class JessThreeThreads extends Application {

    // Assignment calls for 10_000 but that's a lot; I did most  
    // testing with 1000 & upped it at the end.
    private static final int CHARACTER_COUNT = 10_000;

    // This one controls the pause in milliseconds for the threads
    // It helps control output flow. 1 is fast but choppy; 10 is slower but smoother etc.
    private static final int PAUSE_LENGTH = 5;

    // TextArea to display the results as they are generated
    private TextArea textArea;

    // Instantiate the three thread objects we will be using
    private CharacterGenerator letterGen, digitGen, symbolGen;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        
        // Define some parameters for the TextArea
        textArea = new TextArea();
        textArea.setEditable(false); // Don't want user trying to type in here
        textArea.setWrapText(true); // A ton of vertical scrolling is better than horizontal

        // Create a label for the description
        String description = String.format("""
    A program to generate three different types of characters with three concurrent threads:
    • Letters (a–z)
    • Digits (0–9)
    • Symbols (! @ # $ %% & *)

    Each generator runs in its own thread and generates %d characters. Use the buttons below to pause/resume each stream. Each button will be disabled when its associated thread finishes.
    """, CHARACTER_COUNT);
        Label descriptionLabel = new Label(description);
        descriptionLabel.setPadding(new Insets(10));
        descriptionLabel.setWrapText(true);
        
        // Create pause/resume buttons for each thread
        Button lettersBtn = new Button("Pause Letter Generation");
        Button digitsBtn = new Button("Pause Digit Generation");
        Button symbolsBtn = new Button("Pause Symbol Generation");

        // Create CharacterGenerator objects, tie them to associated buttons
        letterGen = new CharacterGenerator("letters", textArea, () -> disableButton(lettersBtn));
        digitGen = new CharacterGenerator("digits", textArea, () -> disableButton(digitsBtn));
        symbolGen = new CharacterGenerator("symbols", textArea, () -> disableButton(symbolsBtn));

        // Handle the button clicks with our helper createToggleHandler method
        lettersBtn.setOnAction(createToggleHandler(letterGen, lettersBtn, "Letter"));
        digitsBtn.setOnAction(createToggleHandler(digitGen, digitsBtn, "Digit"));
        symbolsBtn.setOnAction(createToggleHandler(symbolGen, symbolsBtn, "Symbol"));

        // Create an HBox to contain the controls
        HBox controls = new HBox(10, lettersBtn, digitsBtn, symbolsBtn);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER);

        // Use a BorderPane to position our controls/layout and make sure it auto-resizes
        BorderPane root = new BorderPane();
        root.setTop(descriptionLabel);
        root.setCenter(textArea);
        root.setBottom(controls);
        Scene scene = new Scene(root, 800, 600);

        // Get this GUI up, titled, and ready to go
        stage.setTitle("Jess Three Threads - JavaFX");
        stage.setScene(scene);
        stage.show();

        // Start the threads for each CharacterGenerator object
        new Thread(letterGen).start();
        new Thread(digitGen).start();
        new Thread(symbolGen).start();
    }

    // Event handler method to reduce code redundancy; checks if thread is paused and pauses/resumes/updates button text accordingly
    private EventHandler<ActionEvent> createToggleHandler(CharacterGenerator generator, Button button, String label) {
        return event -> {
            if (generator.isPaused()) {
                generator.resume();
                button.setText("Pause " + label + " Generation");
            } else {
                generator.pause();
                button.setText("Resume " + label + " Generation");
            }
        };
    }

    // method to disable a button, including graying it out
    private void disableButton(Button button) {
        button.setDisable(true);
        button.setStyle("-fx-opacity: 0.5; -fx-text-fill: gray;");
    }

    /* 
     * A Runnable class that can take a String type and a TextArea object, and use these
     * to generate CHARACTER_COUNT random characters of the specified type in the specified
     * TextArea object. It can also be toggled to pause/resume.
     */
    class CharacterGenerator implements Runnable {
        private final String type;
        private final TextArea textArea;
        private final Random random = new Random();
        private volatile boolean paused = false;
        private final Object pauseLock = new Object();
        private final Runnable onFinish; // let app know when it's done to disable button

        // Constructor ensuring our type and TextArea arguments can be utilized
        public CharacterGenerator(String type, TextArea textArea, Runnable onFinish) {
            this.type = type;
            this.textArea = textArea;
            this.onFinish = onFinish;
        }

        // Check if it's paused; used in the pause/resume button event handlers
        public boolean isPaused() {
            return paused;
        }
        
        // Setting the volatile paused variable to true lets all threads know this one is paused
        public void pause() {
            paused = true;
        }

        // Allows this thread to resume after being paused
        public void resume() {
            synchronized (pauseLock) {
                paused = false;
                pauseLock.notifyAll();
            }
        }

        @Override
        public void run() {
            // Define the characters to use in the thread.
            // Note I added a space to each to help break up the display a bit.
            String letters = "abcdefghijklmnopqrstuvwxyz ";
            String digits = "0123456789 ";
            String symbols = "!@#$%&* ";

            for (int i = 0; i < JessThreeThreads.CHARACTER_COUNT; i++) {
                // Pause mechanism per run
                synchronized (pauseLock) {
                    while (paused) {
                        try {
                            pauseLock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }

                // Switch to grab a random character of the appropriate type
                char ch;
                switch (type) {
                    case "letters":
                        ch = letters.charAt(random.nextInt(letters.length()));
                        break;
                    case "digits":
                        ch = digits.charAt(random.nextInt(digits.length()));
                        break;
                    case "symbols":
                        ch = symbols.charAt(random.nextInt(symbols.length()));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown type: " + type);
                }

                final char outputChar = ch;

                // Output the selected character to the text area
                Platform.runLater(() -> textArea.appendText(String.valueOf(outputChar)));

                // This try/catch slows the output down a bit so it's easier on the GUI & the user
                try {
                    Thread.sleep(PAUSE_LENGTH); // Helps UI stay responsive
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            // Notify when finished so we can disable the associated button
            if (onFinish != null) {
                Platform.runLater(onFinish);
            }
        }
    }
}