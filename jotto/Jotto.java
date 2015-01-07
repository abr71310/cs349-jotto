package jotto;

/**
 *  Two views with integrated controllers.  Uses java.util.Observ{er, able} instead
 *  of custom IView.
 */

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Jotto {

	public static boolean isAlpha(String s) {
	    char[] chars = s.toCharArray();

	    for (char c : chars) {
	        if(!Character.isLetter(c)) {
	            return false;
	        }
	    }

	    return true;
	}
	
    public static void main(String[] args){
        JFrame frame = new JFrame("Jotto Game 123 - Hello world, I like trains!");
        
        InputStream inStream = IWordList.class.getResourceAsStream("words.txt");
        Scanner in = new Scanner(new InputStreamReader(inStream));
        
        // create Model and initialize it
        JottoModel model;
        
        String input = JOptionPane.showInputDialog(frame,
                "Enter a 5-char target word, leave blank to generate, or input difficulty (0-2)",
                "Jotto: Target Word Selection / Generation",
                JOptionPane.QUESTION_MESSAGE);

        if (input.equals("") || input.equals(null)) {
        	model = new JottoModel(in);
        } else if (input.equals("0") || input.equals("1") || input.equals("2")) {
        	int num = Integer.parseInt(input);
        	model = new JottoModel(in, num);
        } else {
        	String str = input.toUpperCase();
    		//System.out.println("wtf is strlen? " + str.length());
        	//System.out.println("WTF is isAlpha? " + isAlpha(str));
        	while (str.length() != 5 || isAlpha(str) != true) {
        		//System.out.println("wtf is strlen? " + str.length());
            	//System.out.println("WTF is isAlpha? " + isAlpha(str));
        		str = JOptionPane.showInputDialog(frame,
                        "Enter a 5-LETTER target word, no more randomizing for you",
                        "Jotto: Target Word Selection",
                        JOptionPane.QUESTION_MESSAGE);
        	}
        	String diff = JOptionPane.showInputDialog(frame,
                    "Enter the difficulty (0-2) of the word you picked",
                    "Jotto: Target Word Difficulty",
                    JOptionPane.QUESTION_MESSAGE);
        	while (!diff.equals("0") && !diff.equals("1") && !diff.equals("2")) {
        		//System.out.println("WTF was entered here? "+diff);
            	diff = JOptionPane.showInputDialog(frame,
                        "Try again. enter the difficulty (0-2) of the word you picked",
                        "Jotto: Target Word Difficulty",
                        JOptionPane.QUESTION_MESSAGE);
        	}
        	if (diff.equals("0") || diff.equals("1") || diff.equals("2")) {
        		int di = Integer.parseInt(diff);
            	model = new JottoModel(in, str, di);	
        	} else { // randomize because the user is stupid
        		model = new JottoModel(in);
        	}
        	//model = new JottoModel(in, str, 0);
        }
        
        // create View, tell it about model (and controller)
        JottoViewer view = new JottoViewer(model);
        JottoResponseView response = new JottoResponseView(model);
        JottoGuessView guess = new JottoGuessView(model);

        // tell Model about View.
        model.addObserver(view);
        model.addObserver(response);
        model.addObserver(guess);

        // let all the views know that they're connected to the model
        model.notifyObservers();

        // create the window
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.NORTH;
        gc.gridwidth = 1;
        gc.gridheight = 2;
        gc.weightx = 0.1;
        gc.weighty = 0.3;
        frame.getContentPane().add(p);
        JPanel north = new JPanel(new BorderLayout());     
        JPanel south = new JPanel(new BorderLayout());
        JPanel east = new JPanel(new BorderLayout());

        north.setBorder(BorderFactory.createTitledBorder("Words Guessed"));
        north.setSize(100,100);
        south.setBorder(BorderFactory.createTitledBorder("Guess Here"));
        south.setSize(50,50);
        east.setBorder(BorderFactory.createTitledBorder("Hints?"));
        east.setSize(150,150);
        
        //p.setLayout(new BorderLayout());
        north.add(response, BorderLayout.NORTH);
        south.add(guess, BorderLayout.CENTER);
        east.add(view, BorderLayout.EAST);
        
        p.add(north, gc);
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.SOUTH;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.weightx = 0.25;
        gc.weighty = 0.25;
        p.add(south, gc);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.EAST;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.weightx = 0.5;
        gc.weighty = 0.5;
        p.add(east, gc);
        //frame.getContentPane().add(north);
        //frame.getContentPane().add(south);
        //frame.getContentPane().add(east);

        frame.setPreferredSize(new Dimension(750,300));
        frame.setMinimumSize(new Dimension(700,250));
        frame.setMaximumSize(new Dimension(1000,500));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
