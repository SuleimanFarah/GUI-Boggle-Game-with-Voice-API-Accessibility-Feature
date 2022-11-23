package boggle;

import java.util.*;

/**
 * The BoggleGame class for the first Assignment in CSC207, Fall 2022
 */
public class BoggleGame {

    /**
     * scanner used to interact with the user via console
     */ 
    public Scanner scanner; 
    /**
     * stores game statistics
     */ 
    private BoggleStats gameStats;

    /**
     * dice used to randomize letter assignments for a small grid
     */ 
    private final String[] dice_small_grid= //dice specifications, for small and large grids
            {"AAEEGN", "ABBJOO", "ACHOPS", "AFFKPS", "AOOTTW", "CIMOTU", "DEILRX", "DELRVY",
                    "DISTTY", "EEGHNW", "EEINSU", "EHRTVW", "EIOSST", "ELRTTY", "HIMNQU", "HLNNRZ"};
    /**
     * dice used to randomize letter assignments for a big grid
     */ 
    private final String[] dice_big_grid =
            {"AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM", "AEEGMU", "AEGMNN", "AFIRSY",
                    "BJKQXZ", "CCNSTW", "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DDHNOT", "DHHLOR",
                    "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU", "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"};

    /* 
     * BoggleGame constructor
     */
    public BoggleGame() {
        this.scanner = new Scanner(System.in);
        this.gameStats = new BoggleStats();
    }

    /* 
     * Provide instructions to the user, so they know how to play the game.
     */
    public void giveInstructions()
    {
        System.out.println("The Boggle board contains a grid of letters that are randomly placed.");
        System.out.println("We're both going to try to find words in this grid by joining the letters.");
        System.out.println("You can form a word by connecting adjoining letters on the grid.");
        System.out.println("Two letters adjoin if they are next to each other horizontally, ");
        System.out.println("vertically, or diagonally. The words you find must be at least 4 letters long, ");
        System.out.println("and you can't use a letter twice in any single word. Your points ");
        System.out.println("will be based on word length: a 4-letter word is worth 1 point, 5-letter");
        System.out.println("words earn 2 points, and so on. After you find as many words as you can,");
        System.out.println("I will find all the remaining words.");
        System.out.println("\nHit return when you're ready...");
    }


    /* 
     * Gets information from the user to initialize a new Boggle game.
     * It will loop until the user indicates they are done playing.
     */
    public void playGame(){
        int boardSize;
        while(true){
            System.out.println("Enter 1 to play on a big (5x5) grid; 2 to play on a small (4x4) one:");
            String choiceGrid = scanner.nextLine();

            //get grid size preference
            if(choiceGrid == "") break; //end game if user inputs nothing
            while(!choiceGrid.equals("1") && !choiceGrid.equals("2")){
                System.out.println("Please try again.");
                System.out.println("Enter 1 to play on a big (5x5) grid; 2 to play on a small (4x4) one:");
                choiceGrid = scanner.nextLine();
            }

            if(choiceGrid.equals("1")) boardSize = 5;
            else boardSize = 4;

            //get letter choice preference
            System.out.println("Enter 1 to randomly assign letters to the grid; 2 to provide your own.");
            String choiceLetters = scanner.nextLine();

            if(choiceLetters == "") break; //end game if user inputs nothing
            while(!choiceLetters.equals("1") && !choiceLetters.equals("2")){
                System.out.println("Please try again.");
                System.out.println("Enter 1 to randomly assign letters to the grid; 2 to provide your own.");
                choiceLetters = scanner.nextLine();
            }

            if(choiceLetters.equals("1")){
                playRound(boardSize,randomizeLetters(boardSize));
            } else {
                System.out.println("Input a list of " + boardSize*boardSize + " letters:");
                choiceLetters = scanner.nextLine();
                while(!(choiceLetters.length() == boardSize*boardSize)){
                    System.out.println("Sorry, bad input. Please try again.");
                    System.out.println("Input a list of " + boardSize*boardSize + " letters:");
                    choiceLetters = scanner.nextLine();
                }
                playRound(boardSize,choiceLetters.toUpperCase());
            }

            //round is over! So, store the statistics, and end the round.
            this.gameStats.summarizeRound();
            this.gameStats.endRound();

            //Shall we repeat?
            System.out.println("Play again? Type 'Y' or 'N'");
            String choiceRepeat = scanner.nextLine().toUpperCase();

            if(choiceRepeat == "") break; //end game if user inputs nothing
            while(!choiceRepeat.equals("Y") && !choiceRepeat.equals("N")){
                System.out.println("Please try again.");
                System.out.println("Play again? Type 'Y' or 'N'");
                choiceRepeat = scanner.nextLine().toUpperCase();
            }

            if(choiceRepeat == "" || choiceRepeat.equals("N")) break; //end game if user inputs nothing

        }

        //we are done with the game! So, summarize all the play that has transpired and exit.
        this.gameStats.summarizeGame();
        System.out.println("Thanks for playing!");
    }

    /* 
     * Play a round of Boggle.
     * This initializes the main objects: the board, the dictionary, the map of all
     * words on the board, and the set of words found by the user. These objects are
     * passed by reference from here to many other functions.
     */
    public void playRound(int size, String letters){
        //step 1. initialize the grid
        BoggleGrid grid = new BoggleGrid(size);
        grid.initalizeBoard(letters);
        //step 2. initialize the dictionary of legal words
        Dictionary boggleDict = new Dictionary("/wordlist.txt"); //you may have to change the path to the wordlist, depending on where you place it.
        //step 3. find all legal words on the board, given the dictionary and grid arrangement.
        Map<String, ArrayList<Position>> allWords = new HashMap<String, ArrayList<Position>>();
        findAllWords(allWords, boggleDict, grid);
        //step 4. allow the user to try to find some words on the grid
        humanMove(grid, allWords);
        //step 5. allow the computer to identify remaining words
        computerMove(allWords);
    }

    /*
     * This method should return a String of letters (length 16 or 25 depending on the size of the grid).
     * There will be one letter per grid position, and they will be organized left to right,
     * top to bottom. A strategy to make this string of letters is as follows:
     * -- Assign a one of the dice to each grid position (i.e. dice_big_grid or dice_small_grid)
     * -- "Shuffle" the positions of the dice to randomize the grid positions they are assigned to
     * -- Randomly select one of the letters on the given die at each grid position to determine
     *    the letter at the given position
     *
     * @return String a String of random letters (length 16 or 25 depending on the size of the grid)
     */
    private String randomizeLetters(int size){
        String acc = "";
        if (size*size == 16){
            List<String> small_dice = new ArrayList<>();
            small_dice.addAll(Arrays.stream(this.dice_small_grid).toList());

            for (int i = 0; i <= size - 1; i++){
                for (int k = 0; k <= size - 1; k++){
                    int allPossibleArrangements = new Random().nextInt(small_dice.size());
                    String shuffled_dieSTR = small_dice.get(allPossibleArrangements);
                    small_dice.remove(allPossibleArrangements);
                    int temp_coord1 = new Random().nextInt(shuffled_dieSTR.length());
                    acc = acc + shuffled_dieSTR.charAt(temp_coord1);
                }
            }
        } else if (size*size == 25) {
            List<String> big_dice = new ArrayList<>();
            big_dice.addAll(Arrays.stream(this.dice_big_grid).toList());

            for (int m = 0; m <= size - 1; m++){
                for (int j = 0; j <= size - 1; j++){
                    int allPossibleArrangements1 = new Random().nextInt(big_dice.size());
                    String shuffled_dieSTR1 = big_dice.get(allPossibleArrangements1);
                    big_dice.remove(allPossibleArrangements1);
                    int temp_coord = new Random().nextInt(shuffled_dieSTR1.length());
                    acc = acc + shuffled_dieSTR1.charAt(temp_coord);
                }
            }
        }
        return acc;
    }


    /* 
     * This should be a recursive function that finds all valid words on the boggle board.
     * Every word should be valid (i.e. in the boggleDict) and of length 4 or more.
     * Words that are found should be entered into the allWords HashMap.  This HashMap
     * will be consulted as we play the game.
     *
     * Note that this function will be a recursive function.  You may want to write
     * a wrapper for your recursion. Note that every legal word on the Boggle grid will correspond to
     * a list of grid positions on the board, and that the Position class can be used to represent these
     * positions. The strategy you will likely want to use when you write your recursion is as follows:
     * -- At every Position on the grid:
     * ---- add the Position of that point to a list of stored positions
     * ---- if your list of stored positions is >= 4, add the corresponding word to the allWords Map
     * ---- recursively search for valid, adjacent grid Positions to add to your list of stored positions.
     * ---- Note that a valid Position to add to your list will be one that is either horizontal, diagonal, or
     *      vertically touching the current Position
     * ---- Note also that a valid Position to add to your list will be one that, in conjunction with those
     *      Positions that precede it, form a legal PREFIX to a word in the Dictionary (this is important!)
     * ---- Use the "isPrefix" method in the Dictionary class to help you out here!!
     * ---- Positions that already exist in your list of stored positions will also be invalid.
     * ---- You'll be finished when you have checked EVERY possible list of Positions on the board, to see
     *      if they can be used to form a valid word in the dictionary.
     * ---- Food for thought: If there are N Positions on the grid, how many possible lists of positions
     *      might we need to evaluate?
     *
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     * @param boggleDict A dictionary of legal words
     * @param boggleGrid A boggle grid, with a letter at each position on the grid
     */
    private void findAllWords(Map<String,ArrayList<Position>> allWords, Dictionary boggleDict, BoggleGrid boggleGrid) {

        List<Integer> switchValue = new ArrayList<>();
        String current_word = "";
        for (int i = 0; i < boggleGrid.numRows(); i++){
            for (int j = 0; j < boggleGrid.numCols(); j++){
                List<List<Integer>> val_list = new ArrayList<>();
                switchValue.add(i);
                switchValue.add(j);
                val_list.add(switchValue);
                helper_findAllWords(allWords, boggleDict, boggleGrid, current_word, val_list, i, j);
                val_list.clear();
                switchValue.clear();
            }

        }
        for (String val: allWords.keySet()){
            if (!boggleDict.isPrefix(val)){
                allWords.remove(val);
            }
        }

    }
    /*
    The purpose of the recursive helper function (helper_findAllWords) is to help findAllWords find all the words starting
    from the current letter in the grid to any 8 adjacent positions and return a map of possible words and their respective Position objects.
    The class makes sure to consider if the current_word is an existing prefix of words in boggleDict or boggleDict contains current_word
    for which we can add it to allWords with the appropriate Position values that make up the word. Moreover, the class removes
    unneccessary position rows and columns from progress along with removing letters that we will no longer be recursing with
    from current_word.

     @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     @param boggleDict A dictionary of legal words
     @param boggleGrid A boggle grid, with a letter at each position on the grid
     @param current_word Aa string accumulator that holds all adjacent characters of a potential word
     @param progress An arraylist version of Position which is used to avoid comparing the identity of two Position
     objects while we cant edit the Position class's hashcode and equal functions.
     @param current_row An integer value representing the row of the current character being looped or recursed through
     @param current_col An integer value representing the column of the current character being looped or recursed through
     */
    private void helper_findAllWords(Map<String,ArrayList<Position>> allWords, Dictionary boggleDict, BoggleGrid boggleGrid, String current_word, List<List<Integer>> progress, int current_row, int current_col){
        current_word = current_word + Character.toString(boggleGrid.getCharAt(current_row, current_col));


        if (boggleDict.containsWord(current_word.toLowerCase()) && current_word.length() >= 4){
            ArrayList<Position> word_pos = new ArrayList<>();
            for (List<Integer> val: progress){
                word_pos.add(new Position(val.get(0), val.get(1)));
            }
            allWords.put(current_word, word_pos);
        } else if (!boggleDict.isPrefix(current_word)) {
            List<Integer> temp = new ArrayList<>();
            temp.add(current_row);
            temp.add(current_col);
            progress.remove(temp);
            current_word = "" + current_word.charAt(current_word.length() - 1);
            return;
        }

        for (int j = current_row - 1;  j <= (current_row + 1) && j < boggleGrid.numRows(); j++){
            for (int k = current_col - 1; k <= (current_col + 1) && k < boggleGrid.numCols(); k++){
                List <Integer> temp = new ArrayList<>();
                temp.add(j);
                temp.add(k);
                if(!progress.contains(temp) && j >= 0 && k >= 0){
                    progress.add(temp);
                    helper_findAllWords(allWords, boggleDict, boggleGrid, current_word, progress, j, k);
                }
                temp.clear();
            }
        }
        List<Integer> temp = new ArrayList<>();
        temp.add(current_row);
        temp.add(current_col);
        progress.remove(temp);
        current_word = "" + current_word.charAt(current_word.length() - 1);

    }

    /* 
     * Gets words from the user.  As words are input, check to see that they are valid.
     * If yes, add the word to the player's word list (in boggleStats) and increment
     * the player's score (in boggleStats).
     * End the turn once the user hits return (with no word).
     *
     * @param board The boggle board
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     */
    private void humanMove(BoggleGrid board, Map<String,ArrayList<Position>> allWords){
        System.out.println("It's your turn to find some words!");
        while(true) {
            System.out.println(board.toString());
            System.out.println("Please enter a word (min length 4, max length 8): ");
            String choice = scanner.nextLine().toUpperCase();
            if (choice == ""){break;}

            if (choice.length() < 4){
                while(!(choice.length() >= 4)){
                    System.out.println("Error. Please enter a valid boggle word (has minimum length 4, maximum length 8): ");
                    choice = scanner.nextLine().toUpperCase();
                }
            }

            if (allWords.containsKey(choice) && !gameStats.getPlayerWords().contains(choice)){
                gameStats.addWord(choice, BoggleStats.Player.Human);
            }
        }
    }


    /* 
     * Gets words from the computer.  The computer should find words that are
     * both valid and not in the player's word list.  For each word that the computer
     * finds, update the computer's word list and increment the
     * computer's score (stored in boggleStats).
     *
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     */
    private void computerMove(Map<String,ArrayList<Position>> all_words){
        for(String val: gameStats.getPlayerWords()){
            all_words.remove(val);
        }
        for(String val: all_words.keySet()){
            gameStats.addWord(val, BoggleStats.Player.Computer);
        }
    }

}
