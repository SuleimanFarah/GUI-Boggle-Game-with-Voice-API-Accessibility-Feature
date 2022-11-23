package boggle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
<<<<<<< HEAD
=======
import java.util.Objects;
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
import java.util.TreeSet;

/**
 * The Dictionary class for the first Assignment in CSC207, Fall 2022
 * The Dictionary will contain lists of words that are acceptable for Boggle 
 */
public class Dictionary {

    /**
     * set of legal words for Boggle
     */
    private TreeSet<String> legalWords;

    /**
     * Class constructor 
     * 
     * @param filename the file containing a list of legal words.
     */
    public Dictionary(String filename) {
        String line = "";
        int wordcount = 0;
        this.legalWords = new TreeSet<String>();
        try
        {
<<<<<<< HEAD
            BufferedReader br = new BufferedReader(new FileReader(filename));
=======
            BufferedReader br = new BufferedReader(new FileReader("wordlist.txt"));
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
            while ((line = br.readLine()) != null)
            {
                if (line.strip().length() > 0) {
                    legalWords.add(line.strip());
                    wordcount++;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Initialized " + wordcount + " words in the Dictionary.");;
    }

    /* 
     * Checks to see if a provided word is in the dictionary.
     *
     * @param word  The word to check
     * @return  A boolean indicating if the word has been found
     */
    public boolean containsWord(String word) {
        return this.legalWords.contains(word.toLowerCase());
    }

    /* 
     * Checks to see if a provided string is a prefix of any word in the dictionary.
     *
     * @param str  The string to check
     * @return  A boolean indicating if the string has been found as a prefix
     */
<<<<<<< HEAD
    public boolean isPrefix(String str) {
        return this.legalWords.subSet(str.toLowerCase(), str.toLowerCase() + Character.MAX_VALUE).size() != 0;
=======
    public boolean isPrefix(String str){
        String lower = str.toLowerCase();
        if (this.legalWords.ceiling(lower)==null){
            return false;
        }
        else {
            return this.legalWords.ceiling(lower).contains(lower);
        }

//        for (String w: this.legalWords){
//            if (w.startsWith(lower)){
//                return true;
//            }
//        }
//        return false;
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
    }

}
