package boggle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Stack;


/**
 * Tetris View
 *
 * Based on the Tetris assignment in the Nifty Assignments Database, authored by Nick Parlante
 */
public class BoggleView {

    BoggleModel model; //reference to model
    Stage stage;

    Button newGame, endGame, muteMusic; //buttons for functions
    Label scoreLabel = new Label("");
    Label gridTypelabel = new Label("");

    BorderPane borderPane;
    Canvas canvas;
    GraphicsContext gc; //the graphics context will be linked to the canvas

    private Stack<Object> undoStack; //undo stack, to facilitate drawing/erasing of graphics objects

    private double width; //height and width of canvas
    private double height;

    /**
     * Constructor
     *
     * @param model reference to tetris model
     * @param stage application stage
     */

    public BoggleView(BoggleModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
        initUI();
    }

    /**
     * Initialize interface
     */
    private void initUI() {
        this.stage.setTitle("TSDC Boggle");
        this.width = 500;
        this.height = 650;

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        //labels
        gridTypelabel.setId("GameModeLabel");
        scoreLabel.setId("ScoreLabel");

        gridTypelabel.setText("GridType: 4x4");
        gridTypelabel.setMinWidth(250);
        gridTypelabel.setFont(new Font(20));
        gridTypelabel.setStyle("-fx-text-fill: #e8e6e3");

        final ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton pilotButtonHuman = new RadioButton("4x4 (Default)");
        pilotButtonHuman.setToggleGroup(toggleGroup);
        pilotButtonHuman.setSelected(true);
        pilotButtonHuman.setUserData(Color.SALMON);
        pilotButtonHuman.setFont(new Font(16));
        pilotButtonHuman.setStyle("-fx-text-fill: #e8e6e3");

        RadioButton pilotButtonComputer = new RadioButton("Computer (Default)");
        pilotButtonComputer.setToggleGroup(toggleGroup);
        pilotButtonComputer.setUserData(Color.SALMON);
        pilotButtonComputer.setFont(new Font(16));
        pilotButtonComputer.setStyle("-fx-text-fill: #e8e6e3");

        scoreLabel.setText("Score is: 0");
        scoreLabel.setFont(new Font(20));
        scoreLabel.setStyle("-fx-text-fill: #e8e6e3");

        //add buttons
        startButton = new Button("Start");
        startButton.setId("Start");
        startButton.setPrefSize(150, 50);
        startButton.setFont(new Font(12));
        startButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        stopButton = new Button("Stop");
        stopButton.setId("Start");
        stopButton.setPrefSize(150, 50);
        stopButton.setFont(new Font(12));
        stopButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        saveButton = new Button("Save");
        saveButton.setId("Save");
        saveButton.setPrefSize(150, 50);
        saveButton.setFont(new Font(12));
        saveButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        loadButton = new Button("Load");
        loadButton.setId("Load");
        loadButton.setPrefSize(150, 50);
        loadButton.setFont(new Font(12));
        loadButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        newButton = new Button("New Game");
        newButton.setId("New");
        newButton.setPrefSize(150, 50);
        newButton.setFont(new Font(12));
        newButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        HBox controls = new HBox(20, saveButton, loadButton, newButton, startButton, stopButton);
        controls.setPadding(new Insets(20, 20, 20, 20));
        controls.setAlignment(Pos.CENTER);

        Slider slider = new Slider(0, 100, 50);
        slider.setShowTickLabels(true);
        slider.setStyle("-fx-control-inner-background: palegreen;");

        VBox vBox = new VBox(20, slider);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.TOP_CENTER);

        VBox scoreBox = new VBox(20, scoreLabel, gameModeLabel, pilotButtonHuman, pilotButtonComputer);
        scoreBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.TOP_CENTER);

        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> swapPilot(newVal));

        //timeline structures the animation, and speed between application "ticks"
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> updateBoard()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //configure this such that you start a new game when the user hits the newButton
        //Make sure to return the focus to the borderPane once you're done!
        newButton.setOnAction(e -> {
            this.model.newGame();
            borderPane.requestFocus();
        });

        //configure this such that you restart the game when the user hits the startButton
        //Make sure to return the focus to the borderPane once you're done!
        startButton.setOnAction(e -> {
            this.paused = false;
            borderPane.requestFocus();
        });

        //configure this such that you pause the game when the user hits the stopButton
        //Make sure to return the focus to the borderPane once you're done!
        stopButton.setOnAction(e -> {
            this.paused = true;
            borderPane.requestFocus();
        });

        //configure this such that the save view pops up when the saveButton is pressed.
        //Make sure to return the focus to the borderPane once you're done!
        saveButton.setOnAction(e -> {
            createSaveView();
            borderPane.requestFocus();
        });

        //configure this such that the load view pops up when the loadButton is pressed.
        //Make sure to return the focus to the borderPane once you're done!
        loadButton.setOnAction(e -> {
            createLoadView();
            borderPane.requestFocus();
        });

        //configure this such that you adjust the speed of the timeline to a value that
        //ranges between 0 and 3 times the default rate per model tick.  Make sure to return the
        //focus to the borderPane once you're done!
        slider.setOnMouseReleased(e -> {
            if(slider.getValue() == 0){
                timeline.setRate(1);
            } else {
                timeline.setRate(3 * slider.getValue()/100);
            }
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            borderPane.requestFocus();
        });

        //configure this such that you can use controls to rotate and place pieces as you like!!
        //You'll want to respond to tie key presses to these moves:
        // TetrisModel.MoveType.DROP, TetrisModel.MoveType.ROTATE, TetrisModel.MoveType.LEFT
        //and TetrisModel.MoveType.RIGHT
        //make sure that you don't let the human control the board
        //if the autopilot is on, however.
        borderPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if (!model.getAutoPilotMode()) {
                    if(k.getCode() == KeyCode.UP){
                        model.modelTick(TetrisModel.MoveType.ROTATE);
                    } else if (k.getCode() == KeyCode.DOWN) {
                        model.modelTick(TetrisModel.MoveType.DROP);
                    }else if(k.getCode() == KeyCode.LEFT){
                        model.modelTick(TetrisModel.MoveType.LEFT);
                    }else if(k.getCode() == KeyCode.RIGHT){
                        model.modelTick(TetrisModel.MoveType.RIGHT);
                    }
                }
            }
        });

        borderPane.setTop(controls);
        borderPane.setRight(scoreBox);
        borderPane.setCenter(canvas);
        borderPane.setBottom(vBox);

        var scene = new Scene(borderPane, 800, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Based on user selection, switch from the current grid type to
     * the type selected (4x4, 5x5).
     * @param val
     */
    private void swapGridType(Toggle val){
        RadioButton state = (RadioButton)val.getToggleGroup().getSelectedToggle();
        String stateText = state.getText();
        if(stateText.equals("4x4 (Default)")){
            gridTypelabel.setText("GridType: 4x4 (Default)");
            //change grid type from the model
        }else if(stateText.equals("5x5")){
            gridTypelabel.setText("GridType: 5x5");
            //change grid type from the model (also end the game before doing so)
        }
    }

    /**
     * Create a line between the most recent button pressed and the last.
     */
    private void buttonLineSelection(int x, int y) {
        if (this.paused != true) {
            paintBoard();
            this.model.modelTick(TetrisModel.MoveType.DOWN);
            updateScore();
        }
    }

    /**
     * Update score on UI
     */
    private void updateScore() {
        if (this.paused != true) {
            scoreLabel.setText("Score is: " + model.getScore() + "\nPieces placed:" + model.getCount());
        }
    }

    /**
     * Methods to calibrate sizes of pixels relative to board size
     */
    private final int yPixel(int y) {
        return (int) Math.round(this.height -1 - (y+1)*dY());
    }
    //    private final int xPixel(int x) {
//        return (int) Math.round(this.width -1 - (x+1)*dX());
//    }
    private final int xPixel(int x) {return (int) Math.round((x)*dX());}
    private final float dX() {
        return( ((float)(this.width-2)) / this.model.getBoard().getWidth() );
    }
    private final float dY() {
        return( ((float)(this.height-2)) / this.model.getBoard().getHeight() );
    }

    /**
     * Draw the board
     */
    public void paintBoard() {

        // Draw a rectangle around the whole screen
        gc.setStroke(Color.GREEN);
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, this.width-1, this.height-1);

        // Draw the line separating the top area on the screen
        gc.setStroke(Color.BLACK);
        int spacerY = yPixel(this.model.getBoard().getHeight() - this.model.BUFFERZONE - 1);
        gc.strokeLine(0, spacerY, this.width-1, spacerY);

        // Factor a few things out to help the optimizer
        final int dx = Math.round(dX()-2);
        final int dy = Math.round(dY()-2);
        final int bWidth = this.model.getBoard().getWidth();

        int x, y;
        // Loop through and draw all the blocks; sizes of blocks are calibrated relative to screen size
        for (x=0; x<bWidth; x++) {
            int left = xPixel(x);	// the left pixel
            // draw from 0 up to the col height
            final int yHeight = this.model.getBoard().getColumnHeight(x);
            for (y=0; y<yHeight; y++) {
                if (this.model.getBoard().getGrid(x, y)) {
                    gc.setFill(Color.RED);
                    gc.fillRect(left+1, yPixel(y)+1, dx, dy);
                    gc.setFill(Color.GREEN);
                }
            }
        }

    }
}