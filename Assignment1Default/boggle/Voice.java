package boggle;

import com.sun.speech.freetts.VoiceManager;

public class Voice {
    private com.sun.speech.freetts.Voice voice;
    public Voice() {
        String name = "kevin16"; //this is the type of voice chosen
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice(name);
        this.voice.allocate();
    }

    public void sayWords(String something){
        this.voice.speak(something);
    }

    public void saySentences(String[] something) {
        for (String s : something) {
            this.sayWords(s);
        }
    }
}

