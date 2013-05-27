package ch.zhaw.jroute.view.panel;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

public class MapDataControlPanel extends JPanel{
	
	private JButton getMapDataButton;
	private JButton selectDataAreaButton;
	private JButton cleanMapDataButton;
	
	public MapDataControlPanel(){
		super(new GridLayout(2,2));
		
		this.createPanel();
	}
	
	private void createPanel(){
		
		//Create border around panel
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9), new TitledBorder("Data control")));
		this.createButtons();
	}
	
	private void createButtons(){
		getMapDataButton = new JButton("Get Data");
		selectDataAreaButton = new JButton("Select Area");
		cleanMapDataButton = new JButton("Clean Data");
		
		this.add(getMapDataButton);
		this.add(selectDataAreaButton);
		this.add(cleanMapDataButton);
	}
	
	public JButton getMapDataButton(){
		return getMapDataButton;
	}
	
	public JButton getSelectAreaButton(){
		return selectDataAreaButton;
	}

	public JButton getCleanMapDataButton() {
		return cleanMapDataButton;
	}
}
