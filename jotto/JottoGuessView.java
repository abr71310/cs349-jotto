package jotto;

import javax.swing.event.*;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Container;
import java.awt.event.*;
import java.awt.Toolkit;
import java.util.*;

class JottoGuessView extends JPanel implements Observer {

    // the view's main user interface
    private JButton button;
    private JTextField text;
    private JLabel label;
    private int guess;
    private int remaining;
    private JFrame parent = (JFrame)this.getParent();
    
    // the model that this view is showing
    private JottoModel model;

    public JottoGuessView(JottoModel model_) {
    	// set the model
        model = model_;
        
        // a BorderLayout with default constraints creates boundaries
        // for areas in the window
        this.setLayout(new BorderLayout());
    	// useful for adding panes (separate views) into
    	// create the view UI
        guess = model.getCurrentGuesses()+1;
        int curGuess = guess-1;
        button = new JButton("Make Guess #" + guess);
        button.setSize(100,40);
        text = new JTextField("Type Guess Input Here");
        remaining = 10-guess;
        //label = new JLabel("You have " + remaining + " guesses remaining.");
        this.add(button, BorderLayout.SOUTH);
        this.add(text, BorderLayout.CENTER);
        //this.add(label, BorderLayout.SOUTH);
        
        // setup the event to go to the "controller"
        // (this anonymous class is essentially the controller)
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean test = model.makeGuess(text.getText());
                if (test == true) {
                	if (model.didWeWin() == true) {
                		JOptionPane.showMessageDialog(parent, "You WIN! The word was indeed: " + model.getSecret(), "WINNER!!", JOptionPane.INFORMATION_MESSAGE);
                	} else {
                		JOptionPane.showMessageDialog(parent, "You LOST! The word was actually: " + model.getSecret(), "Lost!!!", JOptionPane.INFORMATION_MESSAGE);
                	}
                	System.exit(0);
                } else {
                	// test for errors
                	if (model.didWeError() == true) {
                		JOptionPane.showMessageDialog(parent, model.getError(), "Oops!!", JOptionPane.INFORMATION_MESSAGE);
                	}
                }
            }
        });
        text.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
                boolean test = model.makeGuess(text.getText());
                if (test == true) {
                	if (model.didWeWin() == true) {
                		JOptionPane.showMessageDialog(parent, "You WIN! The word was indeed: " + model.getSecret(), "WINNER!!", JOptionPane.INFORMATION_MESSAGE);
                	} else {
                		JOptionPane.showMessageDialog(parent, "You LOST! The word was actually: " + model.getSecret(), "Lost!!!", JOptionPane.INFORMATION_MESSAGE);
                	}
                	System.exit(0);
                } else {
                	// test for errors
                	if (model.didWeError() == true) {
                		JOptionPane.showMessageDialog(parent, model.getError(), "Oops!!", JOptionPane.INFORMATION_MESSAGE);
                	}
                }
            }
        });
    }

    // Observer interface
    @Override
    public void update(Observable arg0, Object arg1) {
        System.out.println("JottoGuessView: update");
        guess = model.getCurrentGuesses()+1;
        remaining = 10-guess;
        button.setText("Make Guess #" + guess);
        text.setText("Type Guess Input Here");
        //label.setText("You have " + remaining + " guesses remaining.");
    }
}

