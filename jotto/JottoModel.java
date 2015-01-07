package jotto;

import java.util.Observable;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class JottoModel extends Observable {
	public static int NUM_LETTERS = 5;
	public static final String[] LEVELS = {
      "Easy", "Medium", "Hard", "Any Difficulty"};
    public static final int MAX_GUESSES = 10;
    private static int CUR_GUESSES = 0;
    private static Word secretWord;
    private static WordList list;
    private static String lastGuess;
    private static int partial;
	private static int exact;
	private static ArrayList<String> guessed; 
	private static String lettersGuessed;
	private static ArrayList<Character> letters; 
	private static boolean won;
	private static boolean error;
	private static String errorMessage;
	
	private void initializeVars() {
    	CUR_GUESSES = 0;
		partial = exact = 0;
    	guessed = new ArrayList<String>();
    	letters = new ArrayList<Character>();
    	lettersGuessed = "";
    	won = false;
    	errorMessage = "";
	}
	
    public JottoModel(Scanner s) { // gives a random word of any difficulty because yoloswag360noscope
    	initializeVars();
    	list = new WordList(s);
    	secretWord = list.randomWord();
        setChanged();
        //System.out.println("The word is: " + secretWord.getWord() + " with difficulty: " + secretWord.getDifficulty());
    }
    
    public JottoModel(Scanner s, int d) { // gives a random word based on difficulty
    	initializeVars();
    	list = new WordList(s);
        secretWord = list.randomWord(d);
        setChanged();
    	//System.out.println("You want difficulty: " + d + " so your word is: " + secretWord.getWord());
    }
    
    public JottoModel(Scanner c, String s, int d) { // directly set the word
    	initializeVars();
    	list = new WordList(c);
    	String q = s.toUpperCase();
    	secretWord = new Word(q, d);
        setChanged();
        //System.out.println("The word is: " + secretWord.getWord() + " with difficulty: " + secretWord.getDifficulty());
    }

    public boolean didWeError() {
    	return error;
    }
    
    public String getError() {
    	return errorMessage;
    }
    
    public boolean didWeWin() {
    	return won;
    }
    
    public int getCurrentGuesses() {
        return CUR_GUESSES;
    }
    
    public int getLastPartial() {
    	return partial;
    }
    
    public int getLastExact() {
    	return exact;
    }
    
    public String getSecret() { // this is bad and i feel bad, but its for a good reason i swear
    	// jk no its not
    	return secretWord.getWord();
    }
    
    public int getDifficulty() { // get the difficulty of the word
    	return secretWord.getDifficulty();
    }
    
    public String getSimpleHint() {
    	Random rand = new Random();
    	String hint;
    	int index = rand.nextInt(NUM_LETTERS); 
    	//System.out.println("Hint index: " + index);
    	hint = "One Character is " + secretWord.getWord().charAt(index);
    	return hint;
    }
    
    public String getFullHint() {
    	Random rand = new Random();
    	String hint;
    	int index = rand.nextInt(NUM_LETTERS); 
    	//System.out.println("Hint index: " + index);
    	hint = "Character " + index + " is " + secretWord.getWord().charAt(index);
    	return hint;
    }

    
    public void endGame() {
    	CUR_GUESSES = 10;
    	System.out.println("You GAVE UP! The word was actually: " + secretWord.getWord());
        notifyObservers();
    	setChanged();
    	System.exit(0);
    }
    
    public String showLettersGuessed() {
    	return lettersGuessed;
    }
    
    public String showLastGuess() {
    	if (lastGuess != null) {
        	errorMessage = "Your Last Guess was: " + lastGuess;
    	} else {    		
    		errorMessage = "This is your first guess, please input something!";
    	}
    	return lastGuess;
    }
    
    public boolean makeGuess(String s) {
    	boolean gameOver = false;
    	errorMessage = "";
    	error = false;
    	String temp = s.toUpperCase();
    	//System.out.println("IS temp == secretWord.getWord()??" + temp.equals(secretWord.getWord()));
    	//System.out.println("Word is: " + secretWord.getWord());
    	//System.out.println("You guessed: " + temp);
        if (s.length() != NUM_LETTERS) {
        	error = true;
        	errorMessage = "Invalid # of chars in guess. Required: " + NUM_LETTERS;
        } else if (temp.equals(secretWord.getWord())) {
        	//System.out.println("You win! The word was indeed: " + secretWord.getWord());
        	//JOptionPane.showMessageDialog(this, "You WON!!!! The word was indeed: " + secretWord.getWord(), "You WON!!!!",
        	//		JOptionPane.INFORMATION_MESSAGE);
        	//System.exit(0);
        	error = false;
        	gameOver = true;
        	won = true;
        } else if (CUR_GUESSES+1 >= MAX_GUESSES) {
        	//System.out.println("You LOSE! The word was actually: " + secretWord.getWord());
        	//JOptionPane.showMessageDialog(this, "You LOSE! The word was actually: " + secretWord.getWord(), "You Lost :(",
        	//		JOptionPane.INFORMATION_MESSAGE);
        	//System.exit(0);
        	gameOver = true;
        	error = false;
        } else if (list.contains(temp) == false) {
        	error = true;
        	errorMessage = "Word isn't in dictionary, NOT COUNTED (free guess!)";
        } else if (guessed.contains(temp) == true) {
        	errorMessage = "You already guessed " + temp + "; guess something else!";
        	error = true;
        } else {
        	error = false;
        	lastGuess = temp;
        	CUR_GUESSES += 1;
        	System.out.println("Making guess #" + CUR_GUESSES + ": " + temp);
        	partial = 0;
        	exact = 0;
        	
        	// Figure out the matchings
        	char[] aletters = temp.toCharArray();
        	char[] bletters = secretWord.getWord().toCharArray();
        	for (int i=0; i<aletters.length; i+=1) {
        		for (int j=0; j<bletters.length; j+=1 ) {
        			if (aletters[i]==bletters[j]) {
        				if (i == j) {
        					exact += 1;
        				} else {
        					partial += 1;
        				}
        				bletters[j] = '*';
        				break;
        			}
        		}
        	}
        	for (int i=0; i<aletters.length; i+=1) {
        		if (!letters.contains(aletters[i])) {
        			letters.add(aletters[i]);
        			lettersGuessed += aletters[i];
        		}
        	}
        	
        	guessed.add(temp); // add the string to the list of already used words
            //System.out.println("Partial matches: " + partial + "; exact matches: " + exact);
            setChanged();
            notifyObservers();        	
        }
        return gameOver;
    }

}