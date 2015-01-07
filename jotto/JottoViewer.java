package jotto;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

class JottoViewer extends JPanel implements Observer {

    // the view's main user interface
	private JButton hint1;
	private JButton hint2;
    private JButton button;
    private JButton diff;
    private JLabel label;
    private JLabel remain;

    // the model that this view is showing
    private JottoModel model;

    private String guess;
    private String help;
    private int num;
    
    public JottoViewer(JottoModel model_) {
        model = model_;
    	
        this.setLayout(new BorderLayout());

    	// useful for adding panes (separate views) into
    	// create the view UI
        hint1 = new JButton("Easy Hint");
        hint2 = new JButton("Giveaway Hint");
        diff = new JButton("Difficulty");
        Box b = Box.createVerticalBox();
        button = new JButton("Show letters guessed!");
        button.setSize(50,30);
        label = new JLabel();
        remain = new JLabel();
        //button.setMaximumSize(new Dimension(100, 30));
        //button.setPreferredSize(new Dimension(100, 30));
        
        // a GridBagLayout with default constraints centres
        // the widget in the window
        this.add(button, BorderLayout.NORTH);
        b.add(hint1);
        b.add(hint2);
        b.add(diff);
        this.add(b, BorderLayout.EAST);
        this.add(remain, BorderLayout.CENTER);
        this.add(label, BorderLayout.SOUTH);

        // setup the event to go to the "controller"
        // (this anonymous class is essentially the controller)
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guess = model.showLettersGuessed();
                label.setText(guess);
            }
        });
        
        hint1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		help = model.getSimpleHint();
        		label.setText(help);
        	}
        });
        
        hint2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		help = model.getFullHint();
        		label.setText(help);
        	}
        });
        
        diff.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		help = "Difficulty is: " + model.getDifficulty();
        		label.setText(help);
        	}
        });
    }

    // Observer interface
    @Override
    public void update(Observable arg0, Object arg1) {
        System.out.println("JottoViewer: update");
        //button.setText("");
        num = model.MAX_GUESSES-model.getCurrentGuesses();
        remain.setText(num + " guesses left.");
    }
}

