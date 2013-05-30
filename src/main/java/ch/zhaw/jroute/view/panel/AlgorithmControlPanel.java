package ch.zhaw.jroute.view.panel;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

/**
 * Panel whitch represent all gui items to controll the shortest path algorithm
 * @author yk
 */
public class AlgorithmControlPanel extends JPanel {
	
	private JButton runAlgoButton;
	private JButton cleanAlgoButton;
	
	/**
	 * constructor call parent constructor with gridLayout
	 */
	public AlgorithmControlPanel(){
		super(new GridLayout());
		
		this.createPanel();
	}
	
	/**
	 * creates the border in the panel and calls method creation method
	 */
	private void createPanel(){
		
		//Create border around panel
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9), new TitledBorder("Algorithm")));
		this.createButtons();
	}

	/**
	 * creates the actual buttons in the panel
	 */
	private void createButtons(){
		runAlgoButton = new JButton("Get Path");
		cleanAlgoButton = new JButton("Clean");
		
		this.add(runAlgoButton);
		this.add(cleanAlgoButton);
	}

	/**
	 * returns the button to start the algorithm
	 * @return button to start the algorithm
	 */
	public JButton getRunAlgoButton() {
		return runAlgoButton;
	}
	
	/**
	 * returns the button to clean the result of the algorithm
	 * @return button to clean the results
	 */
	public JButton getCleanAlgoButton() {
		return cleanAlgoButton;
	}
}
