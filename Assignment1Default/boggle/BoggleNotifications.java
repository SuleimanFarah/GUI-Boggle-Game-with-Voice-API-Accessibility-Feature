package boggle;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class BoggleNotifications {
    //Class for providing notifications for the inputted words.
    private static Clip sound;
    //function for starting the sound notifications in the given state.
    public static void start(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        sound = clip;
        clip.open(AudioSystem.getAudioInputStream(new File(filename)));
        clip.start();
    }
    //Providing state for 2 different cases: Correct Inputs and incorrect inputs.
    public void notif(String state) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(state == "correct")
            BoggleNotifications.start("correct.wav");
        else if(state == "incorrect")
            BoggleNotifications.start("incorrect.wav");
    }
}
