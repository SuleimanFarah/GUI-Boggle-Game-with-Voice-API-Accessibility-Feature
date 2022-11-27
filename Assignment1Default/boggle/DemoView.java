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

    public DemoView(BoggleTimer timer, Stage stage){
        this.timer = timer;
        this.stage = stage;
        initUI();
    }

    private void initUI() {
        Group root= new Group();
        timerLabel = new Label();
        timerLabel.setText("Countdown: 60");
        timerLabel.setFont(Font.font(30));
        timerLabel.setTextFill(Color.BEIGE);

        this.timer.startTimer();

        HBox layout= new HBox(5);
        layout.getChildren().add(timerLabel);
        root.getChildren().add(layout);
        Scene scene= new Scene(root, 300,70, Color.BLACK);
        this.stage.setScene(scene);
        this.stage.show();
}}