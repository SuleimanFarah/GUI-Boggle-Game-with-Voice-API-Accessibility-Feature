import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

import boggle.*;
import boggle.Dictionary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoggleTests {

    //BoggleGame  Test
    @Test
<<<<<<< HEAD
    static void findAllWords_small() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
=======
    void findAllWords_small() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
        BoggleGame game = new BoggleGame();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

<<<<<<< HEAD
        Dictionary boggleDict = new Dictionary("wordlist.txt"); //change back to wordlist.txt
=======
        Dictionary boggleDict = new Dictionary("wordlist.txt");
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        Object r = method.invoke(game, allWords, boggleDict, grid);
<<<<<<< HEAD
        Set<String> expected = new HashSet<>(Arrays.asList("GHOST", "HOST", "THIN"));
        assertEquals(expected, allWords.keySet());
    }
    @Test
    void findAllWords_large() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
=======

        Set<String> expected = new HashSet<>(Arrays.asList("GHOST", "HOST", "THIN"));
        assertEquals(expected, allWords.keySet());
        assertEquals(allWords.get("GHOST").size(), 5);
        assertEquals(allWords.get("GHOST").get(0).getCol(100), 3);
        assertEquals(allWords.get("HOST").size(), 4);
    }
    @Test
    void findAllWords_small2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
        BoggleGame game = new BoggleGame();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

<<<<<<< HEAD
        Dictionary boggleDict = new Dictionary("wordlist.txt"); //change back to wordlist.txt
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(5);
        grid.initalizeBoard("DYALCALEATTEISRKTROOALSCM");
        Object r = method.invoke(game, allWords, boggleDict, grid);
        assertEquals(250, allWords.size());
    }

    @Test
    void findAllWords_piazza()throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleGame game = new BoggleGame();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("wordlist.txt"); //change back to wordlist.txt
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(5);
        grid.initalizeBoard("PIALTPFAETORSSXOMHHVESIAA");
        Object r = method.invoke(game, allWords, boggleDict, grid);
        Set<String> expected = new HashSet<>(Arrays.asList("ROOM", "SEARS", "ALAS", "PROOFS", "SASH", "ALARM", "LEAST", "TEXTS", "ALES", "POOR", "AFAR", "ARMS", "TEXT", "FROM", "MOORS",
                                                                                                            "ROES", "SALTS", "PROSE", "HAHA", "MORASS", "FARM", "STEAL",
                                                                                                            "SHAH", "LETS", "MOOSE", "MORSE", "MOROSE", "MOOR", "SEAS", "MOOS", "LASE",
                                                                                                            "FARMS", "SHAHS", "SEAR", "PROMISE", "LASH", "PROOF", "AROSE", "MORSEL",
                                                                                                            "ROME", "LAST", "TEST", "LASS", "LEST", "MORALES", "LESS", "AFRO",
                                                                                                            "PROS", "AFROS", "TEAS", "TEAR", "SEMI", "MESH", "TEAROOM", "AIMS",
                                                                                                            "SOME", "ROOFS", "SEAL", "SALES", "EAST", "SALE", "RASH", "FORM",
                                                                                                            "ROSE", "ASSET", "ORAL", "TEAROOMS", "MORALE", "FORMS", "ROOMS", "SALT","TEARS","LEASH", "ROOF", "ISMS", "SETTS", "ALTS", "STET", "SETS", "MORAL", "ALARMS", "TEAL", "FAST", "LEAF", "EARS"));
        assertEquals(expected, allWords.keySet());
=======
        Dictionary boggleDict = new Dictionary("wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(5);
        grid.initalizeBoard("mereatgiyfenteheanoxodtnu");
        Object r = method.invoke(game, allWords, boggleDict, grid);

        assertEquals(96, allWords.keySet().size());
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
    }


    //Dictionary Test
    @Test
    void containsWord() {
<<<<<<< HEAD
        Dictionary dict = new Dictionary("wordlist.txt"); //personally setting directory to wordlist
        assertTrue(dict.containsWord("ENZYME"));
        assertTrue(dict.containsWord("enzYME"));
        assertTrue(dict.isPrefix("pench"));
        assertTrue(dict.isPrefix(""));
        assertFalse(dict.isPrefix("kmzxaty"));
=======
        Dictionary dict = new Dictionary("C:\\Users\\gunjo\\CLASSES\\CSC207\\ghumang4\\Assignment1\\wordlist.txt");
        assertTrue(dict.containsWord("ENZYME"));
        assertTrue(dict.isPrefix("pench"));
        assertFalse(dict.isPrefix("alsdjfaosdfbalsjddf"));
        assertTrue(dict.isPrefix("wrench"));
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
    }

    //BoggleGrid Test
    @Test
    void setupBoard() {
        BoggleGrid grid = new BoggleGrid(10);
        String letters = "";
        for (int i = 0; i < 10; i++) {
            letters = letters + "0123456789";
        }

        grid.initalizeBoard(letters);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(letters.charAt(i*10+j), grid.getCharAt(i, j));
            }
        }
    }
<<<<<<< HEAD
    @Test
    void secondBoard_setuptest(){
        BoggleGrid grid = new BoggleGrid(5);
        String letters = "abcdefghijklmnopqrstuvwxy";

        grid.initalizeBoard(letters);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(letters.charAt(i*5+j), grid.getCharAt(i, j));
            }
        }
    }
=======
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba

    //BoggleStats Test
    @Test
    void endRoundTest() {
        BoggleStats stats = new BoggleStats();
        stats.endRound();
        stats.endRound();
        stats.endRound();
        assertEquals(3, stats.getRound());
    }

}
<<<<<<< HEAD

=======
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
