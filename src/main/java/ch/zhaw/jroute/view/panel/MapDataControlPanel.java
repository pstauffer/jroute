package ch.zhaw.jroute.view.panel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

public class MapDataControlPanel extends JPanel{
	
	private JButton getMapDataButton;
	private JButton selectDataAreaButton;
	private JButton cleanMapDataButton;
	private List<JCheckBox> allCheckBoxes;
	
	public MapDataControlPanel(){
		super(new GridLayout(5,2));
		
		this.createPanel();
	}
	
	private void createPanel(){
		
		//Create border around panel
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9), new TitledBorder("Data control")));
		this.createButtons();
	}
	
	private void createButtons(){
		
		allCheckBoxes = new ArrayList<JCheckBox>();
				
		getMapDataButton = new JButton("Get Data");
		selectDataAreaButton = new JButton("Select Area");
		cleanMapDataButton = new JButton("Clean Data");
		JButton placeholderButton = new JButton("");
		placeholderButton.setVisible(false);
		
		
		JCheckBox motorwayCheckBox = new JCheckBox("motorway");
		JCheckBox trunkCheckBox = new JCheckBox("trunk");
		
		allCheckBoxes.add(motorwayCheckBox);
		allCheckBoxes.add(trunkCheckBox);
		
		this.add(selectDataAreaButton);
		this.add(getMapDataButton);
		this.add(cleanMapDataButton);
		this.add(placeholderButton);
		this.add(motorwayCheckBox);
		this.add(trunkCheckBox);
	}
	
	public List<String> getSelectedFilter(){
		List<String> filterList = new ArrayList<String>();
		
		for(JCheckBox cb : allCheckBoxes) {
			if(cb.isSelected()){
				filterList.add(cb.getName());
			}
		}		
		
		return filterList;
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
