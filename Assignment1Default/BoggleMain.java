package boggle;

import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;


/**
 * The Main class for the first Assignment in CSC207, Fall 2022
 */
public class BoggleMain extends Application {
    /**
     * Main method.
     *
     * @param args command line arguments.
     **/
    private final int BUFFER_SIZE = 128000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        /**
         * @param filename the name of the file that is going to be played
         */

        //Instantiating Media class
        BoggleModel model = new BoggleModel();
        BoggleTimer timer = new BoggleTimer();
        BoggleMusic music = new BoggleMusic();
        BoggleView view = new BoggleView(model, primaryStage, timer, music);
        model.runGame();
    }
}
