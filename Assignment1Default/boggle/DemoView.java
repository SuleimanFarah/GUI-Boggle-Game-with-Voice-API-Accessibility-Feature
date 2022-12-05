package boggle;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


//demo view
public class DemoView{
    BoggleTimer timer;
    Stage stage;
    static Label timerLabel;
    private Button startTimerButton;

    public DemoView(BoggleTimer timer, Stage stage){
        this.timer = timer;
        this.stage = stage;
        initUI();
    }

    private void initUI() {
        Group root= new Group();
        //create label for timer
        timerLabel = new Label();
        timerLabel.setText("Time: 60");
        timerLabel.setFont(new Font(16));
        timerLabel.setStyle("-fx-background-color: #0a0000; -fx-text-fill: white;");
        timerLabel.setVisible(false);
        //create strat button for a timed game
        startTimerButton = new Button();
        startTimerButton.setText("Start a timed Game");
        startTimerButton.setFont(new Font(16));
        startTimerButton.setStyle("-fx-background-color: #0a0000; -fx-text-fill: white;");
        startTimerButton.setOnAction(e ->{
            initializeTimer();
            Button source = (Button)e.getSource();
            source.setVisible(false);
            timerLabel.setVisible(true);
        });
        //temporary box
        HBox layout= new HBox(5);
        layout.getChildren().addAll(timerLabel, startTimerButton);
        root.getChildren().add(layout);
        Scene scene= new Scene(root, 300,70, Color.WHITE);
        this.stage.setScene(scene);
        this.stage.show();
}
    private void initializeTimer(){
        this.timer.startTimer(new BoggleModel());
    }
}