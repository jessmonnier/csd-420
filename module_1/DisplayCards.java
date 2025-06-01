/* 
 * Jess Monnier
 * CSD-420 M1 Programming Assignment
 * 1 June 2025
 * A program to randomly display 4 cards with a refresh button
 * to display 4 new cards in a GUI window using JavaFX
 */

 // Import like a bajillion necessary classes
import java.util.ArrayList;
import java.util.Collections;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DisplayCards extends Application {
    
    // Set up some static variables that I need all methods to be able to access

    // Card file names for the regular deck
    protected static final String[] deck = {
        "1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png", "10.png",
        "11.png", "12.png", "13.png", "14.png", "15.png", "16.png", "17.png", "18.png", "19.png", 
        "20.png", "21.png", "22.png", "23.png", "24.png", "25.png", "26.png", "27.png", "28.png", 
        "29.png", "30.png", "31.png", "32.png", "33.png", "34.png", "35.png", "36.png", "37.png", 
        "38.png", "39.png", "40.png", "41.png", "42.png", "43.png", "44.png", "45.png", "46.png", 
        "47.png", "48.png", "49.png", "50.png", "51.png", "52.png"
    };

    // ArrayList to store indices 0-51 for accessing the card file names, also to track which have been drawn
    protected static ArrayList<Integer> availableIndices = new ArrayList<>();
    
    // Card back for use in starting view, shuffling, and if an image error were to occur
    protected static final Image cardBack = new Image("file:cards/b2fv.png");
    
    // Sizing (for ImageView nodes)
    protected static final int cardWidth = 72;
    protected static final int cardHeight = 96;
    
    // Initialize starting card views
    public static ImageView cardA = new ImageView(cardBack);
    public static ImageView cardB = new ImageView();
    public static ImageView cardC = new ImageView();
    public static ImageView cardD = new ImageView();
    
    // PauseTransition to allow a brief pause after shuffling
    public static final PauseTransition pause = new PauseTransition(Duration.seconds(1));
    
    // Track if it's the first shuffle bc we don't want the pause in that case
    public static boolean firstShuffle = true;
    
    @Override
	public void start(Stage stage) {

        // Initialize buttons
        Button drawButton = new Button("Draw 4 cards");
        Button shuffleButton = new Button("Shuffle all cards");

        /* 
         Set draw button to shuffle if there are fewer than 4 indices left in 
         availableIndices, and if it's not the first shuffle to pause for a second 
         after shuffling and before drawing cards; in all cases cards are drawn as
         the last step
         */
        drawButton.setOnAction(e -> {
            if (availableIndices.size() < 4 && !firstShuffle) {
                shuffleDeck();
                pause.setOnFinished(o -> {
                    drawCards();
                });
                pause.play();
            } else if (firstShuffle) {
                shuffleDeck();
                drawCards();
            } else {
                drawCards();
            }
        });

        // Set shuffle button to, well, shuffle
        shuffleButton.setOnAction(d -> {
            shuffleDeck();
        });

        // Set the four image viewers to the static card heights/widths
        cardA.setFitHeight(cardHeight);
        cardA.setFitWidth(cardWidth);
        cardB.setFitHeight(cardHeight);
        cardB.setFitWidth(cardWidth);
        cardC.setFitHeight(cardHeight);
        cardC.setFitWidth(cardWidth);
        cardD.setFitHeight(cardHeight);
        cardD.setFitWidth(cardWidth);

        // Create a vbox with centered content and spacing of 10
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        
        // Create hboxes (1 for cards, 1 for buttons)
        HBox cardBox = new HBox();
        HBox buttonBox = new HBox();

        // Set card hbox's spacing & alignment
        cardBox.setSpacing(10);
        cardBox.setAlignment(Pos.CENTER);

        // Set button hbox's spacing & alignment
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);

        // Add child nodes to each box
        vbox.getChildren().addAll(cardBox, buttonBox);
        cardBox.getChildren().addAll(cardA, cardB, cardC, cardD);
        buttonBox.getChildren().addAll(drawButton, shuffleButton);

        // Create scene
        Scene scene = new Scene(vbox, 375, 175);

        // Set stage title & add the scene to it
        stage.setTitle("Card Drawing Application");
        stage.setScene(scene);
        
        // Show the stage
        stage.show();

    }

    // Method to draw 4 cards
    public static void drawCards() {
        cardA.setImage(getCard());
        cardB.setImage(getCard());
        cardC.setImage(getCard());
        cardD.setImage(getCard());
    }
    
    // Method to get a new card image
    public static Image getCard() {
        
        // Because availableIndices gets shuffled we can use index 0 to get the file path
        String newCardPath = "file:cards\\" + deck[availableIndices.get(0)];
        
        // Remove the used index
        availableIndices.remove(0);
        
        // Create a newCard Image with the assigned file path
        Image newCard = new Image(newCardPath);
        
        // Make sure it worked; if not, return the static card back image
        if (newCard.isError()) {
            return cardBack;
        } else {
            return newCard;
        }
    }

    // Method to shuffle the deck
    public static void shuffleDeck() {
        
        // Revert to the starting image setup
        cardA.setImage(cardBack);
        cardB.setImage(null);
        cardC.setImage(null);
        cardD.setImage(null);
        
        // Empty out any remaining indices
        availableIndices.clear();
        
        // Repopulate availableIndices with 0-51
        for (int i=0; i<deck.length; i++) {
            availableIndices.add(i);
        }
        
        // Shuffle the indices
        Collections.shuffle(availableIndices);
        
        // This is what allows for the pause after shuffles other than the very first one
        firstShuffle = false;
    }

    public static void main(String[] args) {
		launch(args);
	}
}
