/* Jess Monnier
 * CSD-420 Module 7 Programming Assignment
 * 30 June 2025
 * A program to create a GUI displaying circles with their
 * color scheme defined in a separate CSS file
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CircleApp extends Application {

    @Override
    public void start(Stage stage) {
        
        // Create vbox as primary layout container
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        // Create two circles with the style classes
        Circle circle1 = new Circle(40);
        circle1.getStyleClass().add("stroke-black");
        circle1.getStyleClass().add("fill-white");

        // Note this circle will be centered below the other three
        Circle circle2 = new Circle(40);
        circle2.getStyleClass().add("stroke-black");
        circle2.getStyleClass().add("fill-white");

        // Create two circles with styles defined by ID
        Circle circle3 = new Circle(40);
        circle3.setId("circle-green");

        Circle circle4 = new Circle(40);
        circle4.setId("circle-red");

        // Create an enum to handle colors
        enum ColorOption {
            RED("Red", "red"),
            GREEN("Green", "green"),
            BLUE("Blue", "blue"),
            YELLOW("Yellow", "yellow"),
            PURPLE("Purple", "purple"),
            WHITE("White", "white"),
            BLACK("Black", "black");

            private final String displayName;
            private final String cssName;

            // Constructor defining first string as display name, second as css name
            ColorOption(String displayName, String cssName) {
                this.displayName = displayName;
                this.cssName = cssName;
            }

            public String getCssName() {
                return cssName;
            }

            @Override
            public String toString() {
                return displayName;
            }
        }
        
        // Create drop-down boxes to select colors for the fourth circle
        ComboBox<ColorOption> strokeComboBox = new ComboBox<>();
        strokeComboBox.getItems().addAll(ColorOption.values());
        strokeComboBox.setPromptText("Select a stroke color");
        
        ComboBox<ColorOption> fillComboBox = new ComboBox<>();
        fillComboBox.getItems().addAll(ColorOption.values());
        fillComboBox.setPromptText("Select a fill color");

        // Add event listeners to drop-down lists to actually change the color of the circle
        strokeComboBox.setOnAction(event -> {
            if (strokeComboBox.getValue() != null) {
                ColorOption selectedStroke = strokeComboBox.getValue();
                circle2.getStyleClass().removeIf(style -> style.startsWith("stroke-"));
                circle2.getStyleClass().add("stroke-" + selectedStroke.getCssName());
            }
        });

        fillComboBox.setOnAction(event -> {
            if (fillComboBox.getValue() != null) {
                ColorOption selectedFill = fillComboBox.getValue();
                circle2.getStyleClass().removeIf(style -> style.startsWith("fill-"));
                circle2.getStyleClass().add("fill-" + selectedFill.getCssName());
            }
        });
        
        // First row of 3 circles in HBox
        HBox circleRow = new HBox(40, circle1, circle3, circle4); // spacing between circles
        circleRow.setAlignment(Pos.CENTER);

        // Add the circles & drop-downs to the root pane, and that to the Scene
        root.getChildren().addAll(circleRow, circle2, strokeComboBox, fillComboBox);
        Scene scene = new Scene(root, 500, 400);

        // Load CSS
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Set up the stage
        stage.setTitle("Colorful Circles - Jess Monnier Module 7");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}