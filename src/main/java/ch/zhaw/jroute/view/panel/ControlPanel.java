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
	private JButton selectDataAreaButton;
	
	public ControlPanel(){
		super(new GridLayout(2,2));
		
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
		selectDataAreaButton = new JButton("Select Area");
		this.add(startAlgorithmButton);
		this.add(getMapDataButton);
		this.add(selectDataAreaButton);
	}

	public JButton getStartAlgorithmButton() {
		return startAlgorithmButton;
	}
	
	public JButton getMapDataButton(){
		return getMapDataButton;
	}
	
	public JButton getSelectAreaButton(){
		return selectDataAreaButton;
	}
}
