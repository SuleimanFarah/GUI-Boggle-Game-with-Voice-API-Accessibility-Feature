package boggle;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Stack;


/**
 * Boggle View
 *
 * Manages the GUI Component of the group project and is the view in the mock Model-View-Controller model.
 */
public class BoggleView {

    BoggleModel model; //reference to model
    BoggleTimer timer;
    Stage stage;

    Button newGame, endGame, muteMusic, startTimerButton; //buttons for functions
    Label scoreLabel = new Label("");
    Label gridTypelabel = new Label("");
    static Label timerLabel;

    RadioButton gridType; //4x4 boggle grid radio button
    RadioButton gridType2; //5x5 boggle grid radio button

    ArrayList<Button> buttonList; // list of grid buttons (interacted with to engage with boggle)
    BorderPane borderPane;
    Canvas canvas;
    GraphicsContext gc; //the graphics context will be linked to the canvas

    HBox controls;

    VBox scoreBox;

    private Stack<Object> undoStack; //undo stack, to facilitate drawing/erasing of graphics objects

    private double width; //height and width of canvas
    private double height;

    /**
     * Constructor
     *
     * @param model reference to tetris model
     * @param stage application stage
     */

    public BoggleView(BoggleModel model, Stage stage, BoggleTimer timer) {
        this.model = model;
        this.stage = stage;
        this.timer = timer;
        this.buttonList = new ArrayList<>();
        initUI();
    }

    /**
     * Initialize interface
     */
    private void initUI() {
        this.stage.setTitle("TSDC Boggle");
        this.width = 600;
        this.height = 500;

        borderPane = new BorderPane();

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        //labels
        gridTypelabel.setId("GameModeLabel");
        scoreLabel.setId("ScoreLabel");

        gridTypelabel.setText("GridType: 4x4");
        gridTypelabel.setMinWidth(50);
        gridTypelabel.setFont(new Font(20));

        //create label for timer
        timerLabel = new Label();
        timerLabel.setText("Time: 60");
        timerLabel.setFont(new Font(20));
        timerLabel.setMinWidth(50);
        timerLabel.setVisible(false);

        final ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton gridType = new RadioButton("4x4");
        gridType.setToggleGroup(toggleGroup);
        gridType.setSelected(true);
        gridType.setUserData(Color.SALMON);
        gridType.setFont(new Font(16));

        RadioButton gridType2 = new RadioButton("5x5");
        gridType2.setToggleGroup(toggleGroup);
        gridType2.setUserData(Color.SALMON);
        gridType2.setFont(new Font(16));

        scoreLabel.setText("Score is: 0");
        scoreLabel.setFont(new Font(20));

        //add buttons
        newGame = new Button("New Game");
        newGame.setId("newGame");
        newGame.setPrefSize(150, 50);
        newGame.setFont(new Font(12));

        endGame = new Button("End Game");
        endGame.setId("endGame");
        endGame.setPrefSize(150, 50);
        endGame.setFont(new Font(12));

        muteMusic = new Button("Mute Music");
        muteMusic.setId("Save");
        muteMusic.setPrefSize(150, 50);
        muteMusic.setFont(new Font(12));

        startTimerButton = new Button("Start a timed Game");
        startTimerButton.setId("startTimer");
        startTimerButton.setPrefSize(150, 50);
        startTimerButton.setFont(new Font(12));

        HBox controls = new HBox(20, newGame, endGame, muteMusic);
        controls.setPadding(new Insets(20, 20, 20, 20));
        controls.setAlignment(Pos.CENTER);

        VBox scoreBox = new VBox(20, scoreLabel, gridTypelabel, gridType, gridType2);
        scoreBox.setPadding(new Insets(20, 20, 20, 20));
        scoreBox.setAlignment(Pos.TOP_CENTER);

        HBox timerBox = new HBox(5);
        timerBox.setPadding(new Insets(20, 20, 20, 20));
        timerBox.getChildren().addAll(timerLabel, startTimerButton);
        timerBox.setAlignment(Pos.BASELINE_CENTER);

        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> swapGridType(newVal));


        //Although this is the same as endGame, all the appropriate scores are reset but are do not need to be dispayed to the user.
        newGame.setOnAction(e -> {
            System.out.println("new game!");
           //Accessibility feature: read out instructions when game starts
            Voice voice = new Voice();
            String[] instructions = new String[]{
                    "The Boggle board contains a grid of letters that are randomly placed.",
                    "We're both going to try to find words in this grid by joining the letters.",
                    "You can form a word by connecting adjoining letters on the grid.",
                    "Two letters adjoin if they are next to each other horizontally, ",
                    "vertically, ",
                    "or diagonally.",
                    "The words you find must be at least 4 letters long, ",
                    "and you can't use a letter twice in any single word.",
                    "Your points will be based on word length:",
                    "a 4-letter word is worth 1 point,",
                    "5-letter words earn 2 points,",
                    "and so on.",
                    "After you find as many words as you can,",
                    "I will find all the remaining words.",
                    "Hit return when you're ready..."
            };
            voice.saySentences(instructions);
            borderPane.requestFocus();
        });

        //Once this button is clicked the model should be called to end game. The scores are announced on the screen while the game summary
        //is printed in the terminal
        endGame.setOnAction(e -> {
            wordToVoice(endGame);
            System.out.println("end game!");
            borderPane.requestFocus();
        });

        //Configures this such that it mutes the music playing in the game during launch.
        muteMusic.setOnAction(e -> {
            //mute music code
            wordToVoice(muteMusic);
            System.out.println("mute music!");
            borderPane.requestFocus();
        });

        //starts a timed game. Timer will be visible once button is clicked and starts to countdown
        startTimerButton.setOnAction(e -> {
            wordToVoice(startTimerButton);
            initializeTimer();
            Button source = (Button) e.getSource();
            source.setVisible(false);
            timerLabel.setVisible(true);
            borderPane.requestFocus();
        });

        buttonArrayList();
        addButtonsToCanvas();

        //Accessibility feature: read out the word of the button clicked
        for(Button button: buttonList){
            button.setOnAction(e ->{
                wordToVoice(button);
            });
        }

        GridPane gridPane = addButtonsToCanvas();

        borderPane.setCenter(gridPane);
        borderPane.setTop(controls);
        borderPane.setRight(scoreBox);
        borderPane.setBottom(timerBox);

//        gc.setStroke(Color.BLANCHEDALMOND);
//        gc.setFill(Color.CORAL);
//        gc.fillRect(150, 20, this.width, this.height);


        var scene = new Scene(borderPane, 800, 600);
        this.stage.setScene(scene);
        this.stage.show();


    }

    /**
     * Based on user selection, switch from the current grid type to
     * the type selected (4x4, 5x5).
     *
     * @param val
     */
    private void swapGridType(Toggle val) {
        RadioButton state = (RadioButton) val.getToggleGroup().getSelectedToggle();
        String stateText = state.getText();
        if (stateText.equals("4x4")) {
            gridTypelabel.setText("GridType: 4x4");
            buttonList.clear();
            this.model.changeGridSize(4);
            //change grid type from the model
        } else if (stateText.equals("5x5")) {
            gridTypelabel.setText("GridType: 5x5");
            buttonList.clear();
            this.model.changeGridSize(5);
            //change grid type from the model (also end the game before doing so)
        }
        buttonArrayList();
        GridPane g = addButtonsToCanvas();
        borderPane.setCenter(g);
    }


    /**
     * This function edits the existing button arraylist with a new set of gridSize x gridSize buttons to be displayed.
     */
    private void buttonArrayList() {
        int size = this.model.getGrid().numCols();
        this.buttonList.clear();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.buttonList.add(new Button(Character.toString(this.model.getGrid().getCharAt(i, j))));
            }
        }
    }

    /**
     * Create a line between the most recent button pressed and the last.
     *
     * @param x, y
     **/

    private void buttonLineSelection(int x, int y) {
        //to be created in later sprint
    }

    /**
     * Update score on UI
     */
    private void updateScore() {
        scoreLabel.setText("Score is: " + model.getScore());
    }

    /**
     * Using the class attribute for button arraylist, add all buttons into the canvas in a manner
     * appropriate to its grid size. The function should return a gridpane which is to be displayed on the screen.
     *
     * @return GridPane gPane
     */
    private GridPane addButtonsToCanvas() {
        int count = 0;
        GridPane gPane = new GridPane();
        if (buttonList.size() == 16) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    gPane.add(buttonList.get(count), i, j);
                    count++;
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    gPane.add(buttonList.get(count), i, j);
                    count++;
                }
            }
        }
        return gPane;
    }

    //initialize Timer
    private void initializeTimer() {
        this.timer.startTimer(this.model);
    }
    //Accessibility feature: read out the word of the button clicked
    private void wordToVoice(Button buttonClicked){
        Voice voice = new Voice();
        String character = buttonClicked.getText();
        voice.sayWords(character);
    }
}

