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

    //update timer every second
    public void startTimer() {

        Timeline time = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                seconds--;
                DemoView.timerLabel.setText("Countdown: "+seconds.toString());
                if(seconds<=0){
                    terminateGame();
                    time.stop();
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

    private void terminateGame(){
        //todo
    }
}