package ch.zhaw.jroute.view.panel;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

public class AlgorithmControlPanel extends JPanel {
	
	private JButton runAlgoButton;
	private JButton cleanAlgoButton;
	
	public AlgorithmControlPanel(){
		super(new GridLayout());
		
		this.createPanel();
	}
	
	private void createPanel(){
		
		//Create border around panel
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9), new TitledBorder("Algorithm")));
		this.createButtons();
	}

	private void createButtons(){
		runAlgoButton = new JButton("Get Path");
		cleanAlgoButton = new JButton("Clean");
		
		this.add(runAlgoButton);
		this.add(cleanAlgoButton);
	}

	public JButton getRunAlgoButton() {
		return runAlgoButton;
	}

	public JButton getCleanAlgoButton() {
		return cleanAlgoButton;
	}
}
