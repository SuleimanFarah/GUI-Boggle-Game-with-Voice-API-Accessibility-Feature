package boggle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class BoggleTimer{
    //set countdown time
    private final Integer startTime = 60;
    private Integer seconds = startTime;

    public void startTimer(BoggleModel model) {

        Timeline time = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            //update timer every second
            @Override
            public void handle(ActionEvent event) {
                seconds--;
                BoggleView.timerLabel.setText("Time: "+seconds.toString());
                if(seconds<=0){
                    terminateGame(model, time);
                }
            }
        });

        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        if(time!=null){
            time.stop();
        }
        time.play();
    }
    //add extra time whenever a correct word is guessed as a reward
    public void addTimer(){
        seconds += 5;
    }
    //terminates the game when time is up
    public void terminateGame(BoggleModel model, Timeline time){
        model.endGame();
        time.stop();
    }
}
