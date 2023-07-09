import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * A JavaFX application for the Duck Hunt game.
 */
public class DuckHunt extends Application {
    double scale = 800;
    double picScale = 800;
    private List<String> imagePaths;
    private int currentImageIndex;

    /**
     * the entry point of the application. The introduction page, introduction music
     * and background choice screen is played here.
     *
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("HUBBM Duck Hunt");

        //initialize the background pictures to switch between
        imagePaths = new ArrayList<>();
        imagePaths.add("assets/foreground/1.png");
        imagePaths.add("assets/foreground/2.png");
        imagePaths.add("assets/foreground/3.png");
        imagePaths.add("assets/foreground/4.png");
        imagePaths.add("assets/foreground/5.png");
        imagePaths.add("assets/foreground/6.png");

        //create starting image
        Image image = new Image("assets/favicon/1.png");
        ImageView startingImage = new ImageView(image);
        startingImage.setFitWidth(picScale);
        startingImage.setFitHeight(picScale);
        startingImage.setPreserveRatio(true);

        //create starting label
        Label text = new Label("PRESS ENTER TO START\n   PRESS ESC TO EXIT");
        text.setFont(Font.font("Helvetica", FontWeight.BOLD, 34));
        text.setTextFill(Color.LIGHTBLUE);
        text.setTranslateX(0);
        text.setTranslateY(150);

        //create the root pane and scene for the starting screen
        StackPane root = new StackPane(startingImage, text);
        Scene scene = new Scene(root, picScale, picScale - 50);

        primaryStage.setWidth(scale);
        primaryStage.setHeight(scale);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();

        //create and play the starting music
        MediaPlayer startingMusic = new MediaPlayer(new Media(new File("C:/Users/maria/Desktop/CS/spring/bbm104/assignments/as4/src/assets/effects/Title.mp3").toURI().toString()));
        startingMusic.setCycleCount(MediaPlayer.INDEFINITE);
        startingMusic.play();

        primaryStage.show();

        //add event handler for key presses
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                //transition to the next screen when enter key is pressed

                //create the background images
                Image image2 = new Image("assets/foreground/1.png");
                ImageView backgroundImage = new ImageView(image2);
                backgroundImage.setFitWidth(picScale);
                backgroundImage.setFitHeight(picScale);
                backgroundImage.setPreserveRatio(true);

                //create other background screen with arrow keys
                Text arrowKeys = new Text("USE ARROW KEYS TO NAVIGATE");
                arrowKeys.setFont(Font.font("Helvetica", 28));
                arrowKeys.setTranslateX(0);
                arrowKeys.setTranslateY(300);

                StackPane root2 = new StackPane(backgroundImage, arrowKeys);
                Scene scene2 = new Scene(root2, picScale, picScale - 50);

                primaryStage.setScene(scene2);

                //stop the starting music and play the intro music
                startingMusic.stop();
                MediaPlayer introMusic = new MediaPlayer(new Media(new File("C:/Users/maria/Desktop/CS/spring/bbm104/assignments/as4/src/assets/effects/Intro.mp3").toURI().toString()));
                introMusic.setCycleCount(MediaPlayer.INDEFINITE);
                introMusic.play();

                //press arrow keys to switch between background images
                scene2.addEventHandler(KeyEvent.KEY_PRESSED, (key2) -> {
                    if (key2.getCode() == KeyCode.RIGHT) {

                        Image image3 = new Image("assets/foreground/2.png");
                        ImageView backgroundImage3 = new ImageView(image3);
                        StackPane root3 = new StackPane(backgroundImage3, arrowKeys);
                        Scene scene3 = new Scene(root3, picScale, picScale - 50);

                        primaryStage.setScene(scene3);
                    } else if (key2.getCode() == KeyCode.LEFT) {
                    }
                });
            } else if (key.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });
    }
}
