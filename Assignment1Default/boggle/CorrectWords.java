package boggle;

public class CorrectWords {
    int numWordsFound;
    private static CorrectWords firstInstance = null;

    private CorrectWords(){
        firstInstance.numWordsFound = 0;
    }

    public static CorrectWords getFirstInstance(){
        if (firstInstance == null){
            firstInstance = new CorrectWords();
        }
        return  firstInstance;
    }
    //for when they find a corect word
    public void incrementNumWordsFound(){
        firstInstance.numWordsFound++;
    }

    // for new round
    public void resetNumWordsFounds(){
        firstInstance.numWordsFound=0;
    }
}
