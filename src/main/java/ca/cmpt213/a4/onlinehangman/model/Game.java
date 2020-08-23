package ca.cmpt213.a4.onlinehangman.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *  This class is for the Game model. This is where the game logic model is implemented
 *  @author: Mark Angelo Monroy (Student ID: 301326143, SFU ID: mamonroy@sfu.ca)
 */

public class Game {
    private String currentWord;
    private List<String> commonWords;
    private int guesses;
    private int incorrectGuesses;
    private int correctGuesses;
    private String completeWord;
    private String status;
    private String inputLetter;
    private long gameID;
    private List<String> attemptedLetters;

    // Constructor
    public Game() {
        // Imports the words from a file called commonwords.txt
        try {
            commonWords = new ArrayList<>();
            Scanner sc = new Scanner(new File("src/commonWords.txt"));
            while (sc.hasNext()) {
                commonWords.add(sc.next());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found!\n");
        }
        status = "Active";
        attemptedLetters = new ArrayList<>();
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord.toUpperCase();
    }

    public void setCommonWords(List<String> commonWords) {
        this.commonWords = commonWords;
    }

    public void setIncorrectGuesses(int incorrectGuesses) {
        this.incorrectGuesses = incorrectGuesses;
    }

    public void setCompleteWord(String completeWord) {
        this.completeWord = completeWord.toUpperCase();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInputLetter(String inputLetter) {
        this.inputLetter = inputLetter.toUpperCase();
    }

    public void setGameID(long gameID) {
        this.gameID = gameID;
    }

    public List<String> getAttemptedLetters() {
        return attemptedLetters;
    }

    public void setAttemptedLetters(List<String> attemptedLetter) {
        this.attemptedLetters = attemptedLetter;
    }

    public int getGuesses() {
        return guesses;
    }

    public void setGuesses(int guesses) {
        this.guesses = guesses;
    }

    public long getGameID() {
        return gameID;
    }

    public void setCorrectGuesses(int correctGuesses) {
        this.correctGuesses = correctGuesses;
    }

    public int getCorrectGuesses() {
        return correctGuesses;
    }

    public String getInputLetter() {
        return inputLetter;
    }

    public String getStatus() {
        return status;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public List<String> getCommonWords() {
        return commonWords;
    }

    public int getIncorrectGuesses() {
        return incorrectGuesses;
    }

    public String getCompleteWord() {
        return completeWord;
    }
    // Initializes the attributes for starting the game
    public void startGame() {
        // Randomly selects a word from a List of Strings
        Random rand = new Random();
        completeWord = commonWords.get(rand.nextInt(commonWords.size())).toUpperCase();
        incorrectGuesses = 0;
        currentWord = new String(new char[completeWord.length()]).replace("\0", "_ ");
        status = "Active";
    }

    // Sets the letter in the current word progress of the user
    public void setLetter(String letter) {
        boolean shouldIncrement = false;
        for (int i = 0; i < completeWord.length(); i++) {
            if (completeWord.charAt(i) == letter.charAt(0)) {
                char[] myNameChars = currentWord.toCharArray();
                myNameChars[i*2] = letter.charAt(0);
                currentWord = String.valueOf(myNameChars);
                shouldIncrement = true;
            }
        }
        if(shouldIncrement) {
            correctGuesses++;
        }

    }

    // Clear the input letter from previous user input
    public void clearLetter() {
        inputLetter = null;
    }

    // Get how many tries left for the user
    public int getTriesLeft() {
        return 7 - incorrectGuesses;
    }

    // Checks if the letter specified is within the word
    public void guessLetter(String letter) {
        if(completeWord.contains(letter.toUpperCase())) {
            setLetter(letter.toUpperCase());
        } else {
            incorrectGuesses++;
        }
        if(!(attemptedLetters.contains(letter))) {
            attemptedLetters.add(letter);
        }
        guesses++;
    }

    // Checks if the user has enough tries left to continue the game
    public boolean isLost() {
        if(getTriesLeft() < 0) {
            return true;
        }
        return false;
    }

    // Checks if the game is won by the user
    public boolean isWon() {
        if(completeWord.equals(currentWord.replaceAll("\\s",""))) {
            return true;
        }
        return false;
    }
}
