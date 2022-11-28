package boggle;

public class BoggleModel {
    private BoggleStats stats;
    private BoggleGrid baseGrid;
    private Dictionary bogDict;
    private int size;

    private String humanGuess;
    public BoggleModel(){
        this.stats = new BoggleStats();
        this.baseGrid = new BoggleGrid(4); //default grid size is 4x4
        this.size =
        this.bogDict = new Dictionary("/Users/sfarah/TSDC/Assignment1Default/wordlist.txt"); //change path references where appropriate
        this.humanGuess = "";
    }

    /**
     * A new game begins. All game related variables are set to the respective default values.
     */
    public void runGame(){

    }

    public void playRound(){

    }

    /**
     * Concludes a game, all appropriate scores are tallied. All appropriate game variables are reset.
     */
    public void endGame(){

    }

    /**
     *
     * @param value
     */
    public void changeGridSize(int value){

    }



}
