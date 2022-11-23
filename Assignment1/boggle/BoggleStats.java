package boggle;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The BoggleStats class for the first Assignment in CSC207, Fall 2022
 * The BoggleStats will contain statsitics related to game play Boggle 
 */
public class BoggleStats {

    /**
     * set of words the player finds in a given round 
     */  
    private Set<String> playerWords = new HashSet<String>();  
    /**
     * set of words the computer finds in a given round 
     */  
    private Set<String> computerWords = new HashSet<String>();  
    /**
     * the player's score for the current round
     */  
    private int pScore; 
    /**
     * the computer's score for the current round
     */  
    private int cScore; 
    /**
     * the player's total score across every round
     */  
    private int pScoreTotal; 
    /**
     * the computer's total score across every round
     */  
    private int cScoreTotal; 
    /**
     * the average number of words, per round, found by the player
     */  
    private double pAverageWords; 
    /**
     * the average number of words, per round, found by the computer
     */  
    private double cAverageWords; 
    /**
     * the current round being played
     */  
    private int round; 

    /**
     * enumarable types of players (human or computer)
     */  
    public enum Player {
        Human("Human"),
        Computer("Computer");
        private final String player;
        Player(final String player) {
            this.player = player;
        }
    }

    /* BoggleStats constructor
     * ----------------------
     * Sets round, totals and averages to 0.
     * Initializes word lists (which are sets) for computer and human players.
     */
    public BoggleStats() {
<<<<<<< HEAD
        this.cScoreTotal = 0;
        this.cScore = 0;
        this.round = 0;
        this.pScore = 0;
        this.pScoreTotal = 0;
        this.pAverageWords = 0.0;
        this.cAverageWords = 0.0;
        this.playerWords =  new HashSet<>();
        this.computerWords = new HashSet<>();

=======
        this.round = 0;
        this.pScore = 0;
        this.cScore = 0;
        this.pScoreTotal = 0;
        this.cScoreTotal = 0;
        this.pAverageWords = 0;
        this.cAverageWords = 0;

        this.playerWords = new HashSet<String>();
        this.computerWords = new HashSet<String>();
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
    }

    /* 
     * Add a word to a given player's word list for the current round.
     * You will also want to increment the player's score, as words are added.
     *
     * @param word     The word to be added to the list
     * @param player  The player to whom the word was awarded
     */
    public void addWord(String word, Player player) {
<<<<<<< HEAD

        if (Objects.equals(player.player, "Human")){
            this.playerWords.add(word);
            this.pScore = (this.pScore + 1) + (word.length() - 4);
        } else if (Objects.equals(player.player, "Computer")) {
            this.computerWords.add(word);
            this.cScore = (this.cScore + 1) + (word.length() - 4);
=======
        if (Objects.equals(player.player, "Human")){
            this.playerWords.add(word);
            this.pScore += 1 + (word.length() - 4);
        }
        else{
            this.computerWords.add(word);
            this.cScore += 1 + (word.length() - 4);
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
        }
    }

    /* 
     * End a given round.
     * This will clear out the human and computer word lists, so we can begin again.
     * The function will also update each player's total scores, average scores, and
     * reset the current scores for each player to zero.
     * Finally, increment the current round number by 1.
     */
    public void endRound() {
<<<<<<< HEAD
        this.pScoreTotal += pScore;
        this.cScoreTotal += cScore;
        this.pAverageWords = (int) ((this.playerWords.size() + (this.round * this.pAverageWords)) /  (this.round + 1));
        this.cAverageWords = (int) ((this.computerWords.size() + (this.round * this.cAverageWords)) /  (this.round + 1));
        this.computerWords.clear();
        this.playerWords.clear();
        this.pScore = 0;
        this.cScore = 0;
        this.round++;
=======
        this.pAverageWords = (int) ((this.round * this.pAverageWords + this.playerWords.size()) / (this.round + 1));
        this.cAverageWords = (int) ((this.round * this.cAverageWords + this.computerWords.size()) / (this.round + 1));

        this.playerWords.clear();
        this.computerWords.clear();

        this.pScoreTotal += this.pScore;
        this.cScoreTotal += this.cScore;

        this.pScore = 0;
        this.cScore = 0;

        this.round += 1;
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
    }

    /* 
     * Summarize one round of boggle.  Print out:
     * The words each player found this round.
     * Each number of words each player found this round.
     * Each player's score this round.
     */
    public void summarizeRound() {
<<<<<<< HEAD
        System.out.println("\nThe player found the following words: " + this.playerWords.toString());
        System.out.println("\nThe computer found the following words: " + this.computerWords.toString());
        System.out.println("\nThe player found " + this.playerWords.size() + " words and the computer found " + this.computerWords.size() +" words!");
        System.out.println("\nThe player had a score of " + this.pScore + " and the computer had a score of " + this.cScore);
=======
        System.out.println("P words:" + this.playerWords);
        System.out.println("C words:" + this.computerWords);
        System.out.println("P amount of words: " + this.playerWords.size());
        System.out.println("C amount of words: " + this.computerWords.size());
        System.out.println("P score: " + this.pScore);
        System.out.println("C score: " + this.cScore);
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
    }

    /* 
     * Summarize the entire boggle game.  Print out:
     * The total number of rounds played.
     * The total score for either player.
     * The average number of words found by each player per round.
     */
    public void summarizeGame() {
<<<<<<< HEAD
        System.out.println("\nThere was a total of " + this.round + " rounds played.");
        System.out.println("\nThe player had a total score of " + this.pScoreTotal + " and the computer had a total score of " + this.cScoreTotal +".");
        System.out.println("\nThe average number of words the player guessed was " + this.pAverageWords + " and the computer had an average word score of " +this.cAverageWords);
=======
        System.out.println("Total rounds played: " + this.round);
        System.out.println("P total score: " + this.pScoreTotal);
        System.out.println("C total score: " + this.cScoreTotal);
        System.out.println("P average words found per round " + this.pAverageWords);
        System.out.println("C average words found per round " + this.cAverageWords);
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
    }

    /* 
     * @return Set<String> The player's word list
     */
    public Set<String> getPlayerWords() {
        return this.playerWords;
    }

    /*
     * @return int The number of rounds played
     */
    public int getRound() { return this.round; }

    /*
    * @return int The current player score
    */
    public int getScore() {
        return this.pScore;
    }

}
