package boggle;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class BoggleMusic {
    private Clip sound;
    //function to open up the file and start the music.
    public void start(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        sound = clip;
        clip.open(AudioSystem.getAudioInputStream(new File(filename)));
        clip.start();
    }
    //function to play the music after a pause. Music will loop continuosly until the app is closed by the user.
    public void play(){
        sound.start();
        sound.loop(Clip.LOOP_CONTINUOUSLY);
    }
    //function to pause the music.
    public void pause(){
        sound.stop();
    }

}
