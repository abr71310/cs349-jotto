package jotto;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import java.io.*;

class JottoResponseView extends JPanel implements Observer {

    // the view's main user interface
    private JButton button;
    //private TableModel myData = new MyTableModel();
    //private JTable table = new JTable(myData);
    private JTable table = new JTable(11,3);
    private JLabel label = new JLabel();
    private JFrame parent = (JFrame)this.getParent();
    
    // the model that this view is showing
    private JottoModel model;

    private int guess;
    private int partial;
    private int exact;
    private String lastGuess;
    
    public JottoResponseView(JottoModel model_) {
    	// set the model
        model = model_;
    	
    	JMenu menu = new JMenu("File");
    	menu.add(new AbstractAction("Quit") {
    		public void actionPerformed(ActionEvent e) {
    			System.out.println("ActionEvent found: "+ e);
    			System.exit(0);
    		}
    	});
    	
    	// useful for adding panes (separate views) into
        // create the view UI
        button = new JButton("?");
        button.setSize(50,40);
        button.setText("END (give up)");
        
        // JTable bullshit
        table.setEnabled(false); // disable editing
        
        this.setLayout(new BorderLayout());

        // a GridBagLayout with default constraints centres
        // the widget in the window
    	this.add(label, BorderLayout.SOUTH);

        this.add(button, BorderLayout.CENTER);
        //this.add(menu);
        this.add(table, BorderLayout.NORTH);
        
        table.setValueAt("Word Guessed", 0, 0);
    	table.setValueAt("Partial", 0, 1);
    	table.setValueAt("Exact", 0, 2);
        
        // setup the event to go to the "controller"
        // (this anonymous class is essentially the controller)
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JOptionPane.showMessageDialog(parent, "You SURRENDERED! The word was actually: " + model.getSecret(), "GGNORE", JOptionPane.INFORMATION_MESSAGE);
            	//JOptionPane.showMessageDialog(parent, "You Lost!");
            	model.endGame();
            }
        });
    }

    // Observer interface
    @Override
    public void update(Observable arg0, Object arg1) {
    	guess = model.getCurrentGuesses();
    	if (guess > 0) { // we guessed at least one thing
    		partial = model.getLastPartial();
    		exact = model.getLastExact();
    		lastGuess = model.showLastGuess();
    		table.setValueAt(lastGuess, guess, 0);
    		table.setValueAt(partial, guess, 1);
    		table.setValueAt(exact, guess, 2);
    	}
    	label.setText(lastGuess);
    	//System.out.println("Your Last Guess was: " + lastGuess + " which had " + partial + " partial matches and " + exact + " exact matches.");
    	
        System.out.println("JottoResponseView: update");
    }
}

