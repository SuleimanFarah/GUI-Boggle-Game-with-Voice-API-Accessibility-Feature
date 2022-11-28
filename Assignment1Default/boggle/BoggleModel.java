package boggle;

public class BoggleModel {
    private BoggleStats stats;
    private BoggleGrid baseGrid;
    private Dictionary bogDict;
    private BoggleGame boggleGame;
    private int size; //boggle grid size, is equivalent to the num rows.
    public int nextSize = 0; //subsequent value of

    private String humanGuess;
    public BoggleModel(){
        this.stats = new BoggleStats();
        this.baseGrid = new BoggleGrid(4); //default grid size is 4x4
        this.size = 4;
        this.boggleGame = new BoggleGame();
        this.bogDict = new Dictionary("/Users/sfarah/TSDC/Assignment1Default/wordlist.txt"); //change path references where appropriate
        this.humanGuess = "";
    }

    /**
     * A new game begins. All game related variables are set to the respective default values.
     */
    public void runGame(){

    }

    public void playRound(){
        // increment player scores if they get a guess. A guess is defined by consecutive adjacent selections of buttons.
    }

    /**
     * Concludes a game, all appropriate scores are tallied. All appropriate game variables are reset.
     */
    public void endGame(){

    }

    /**
     * Creates a new boggle board with a new random selection of letters according to the changed size.
     * @param value
     */
    public void changeGridSize(int value){
        this.baseGrid = new BoggleGrid(value);
        this.size = value;
        this.baseGrid.initalizeBoard(this.boggleGame.randomizeLetters(value));
        System.out.println(this.baseGrid);
    }


    public int getScore() {
        return getStats().getScore();
    }

    public BoggleStats getStats(){
        return this.stats;
    }

    public BoggleGrid getGrid(){
        return this.baseGrid;
    }
}
