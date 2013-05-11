package ch.zhaw.jroute.view.panel;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

public class ControlPanel extends JPanel{
	
	private JButton startAlgorithmButton;
	private JButton getMapDataButton;
	
	public ControlPanel(){
		super(new GridLayout());
		
		this.createPanel();
	}
	
	public void createPanel(){
		
		//Create border around panel
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9), new TitledBorder("Control")));
		this.createButtons();
	}
	
	public void createButtons(){
		startAlgorithmButton = new JButton("Calculate Route");
		getMapDataButton = new JButton("Get Data");
		this.add(startAlgorithmButton);
		this.add(getMapDataButton);
	}

	public JButton getStartAlgorithmButton() {
		return startAlgorithmButton;
	}
	
	public JButton getMapDataButton(){
		return getMapDataButton;
	}
}
