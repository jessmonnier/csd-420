/*
 * Jess Monnier
 * 29 May 2025
 * Program playing with JavaFX to produce something 
 * resembling a hand giving a peace sign gesture.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Peace extends Application {
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Peace, dudes!");
        
        Color skinTone = Color.PEACHPUFF;
        
        // Palm
        Rectangle palm = new Rectangle(215, 120, 140, 110);
        palm.setFill(skinTone);
        palm.setStroke(skinTone.darker());
        palm.setArcWidth(90);
        palm.setArcHeight(90);

        // Index finger - slightly right and rotated
        Rectangle indexFinger = new Rectangle(315, 36, 30, 100);
        indexFinger.setFill(skinTone);
        indexFinger.setStroke(skinTone.darker());
        indexFinger.setArcWidth(30);
        indexFinger.setArcHeight(30);
        indexFinger.setRotate(15);

        // Middle finger - slightly left and rotated
        Rectangle middleFinger = new Rectangle(270, 30, 30, 105);
        middleFinger.setFill(skinTone);
        middleFinger.setStroke(skinTone.darker());
        middleFinger.setArcWidth(30);
        middleFinger.setArcHeight(30);
        middleFinger.setRotate(-10);

        // Pinky
        Rectangle pinky = new Rectangle(218, 109, 27, 48);
        pinky.setFill(skinTone);
        pinky.setStroke(skinTone.darker());
        pinky.setArcWidth(30);
        pinky.setArcHeight(30);
        pinky.setRotate(-30);

        // Ring Finger
        Rectangle ringFinger = new Rectangle(250, 105, 30, 54);
        ringFinger.setFill(skinTone);
        ringFinger.setStroke(skinTone.darker());
        ringFinger.setArcWidth(30);
        ringFinger.setArcHeight(30);
        ringFinger.setRotate(-30);

        // Thumb
        Rectangle thumb = new Rectangle(316, 130, 35, 70);
        thumb.setFill(skinTone);
        thumb.setStroke(skinTone.darker());
        thumb.setArcWidth(30);
        thumb.setArcHeight(30);
        thumb.setRotate(110);

        // Group everything
        Group group = new Group(middleFinger, indexFinger, palm, thumb, ringFinger, pinky);

        Scene scene = new Scene(group, 500, 300);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
