package boggle;

public class inCorrectWords {
    int numWordsNotFound;
    private static inCorrectWords firstInstance = null;

    private inCorrectWords(){
        firstInstance.numWordsNotFound = 0;
    }

    public static inCorrectWords getFirstInstance(){
        if (firstInstance == null){
            firstInstance = new inCorrectWords();
        }
        return  firstInstance;
    }
    //for when they find a correct word
    public void incrementNumWordsNotFound(){
        firstInstance.numWordsNotFound++;
    }

    // for new round
    public void resetNumWordsNotFounds(){
        firstInstance.numWordsNotFound=0;
    }
}
