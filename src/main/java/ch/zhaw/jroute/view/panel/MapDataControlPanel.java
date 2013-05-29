package ch.zhaw.jroute.view.panel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

public class MapDataControlPanel extends JPanel{
	
	private JButton getMapDataButton;
	private JButton selectDataAreaButton;
	private JButton cleanMapDataButton;
	private JButton showMapDataFilterButton;
	private List<JCheckBox> allCheckBoxes;
	
	public MapDataControlPanel(){
		super(new GridLayout(0,2));
		
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
		showMapDataFilterButton = new JButton("Set Filter");
		
		
		JButton placeholderButton = new JButton("");
		placeholderButton.setVisible(false);
		
		
		JCheckBox motorwayCheckBox = new JCheckBox("motorway");
		JCheckBox trunkCheckBox = new JCheckBox("trunk");
		JCheckBox linkCheckBox = new JCheckBox("motorway_link");
		JCheckBox junctionCheckBox = new JCheckBox("motorway_junction");
		JCheckBox trunkLinksCheckBox = new JCheckBox("trunk_link");
		JCheckBox primaryCheckBox = new JCheckBox("primary");
		JCheckBox primarylinkCheckBox = new JCheckBox("primary_link");
		JCheckBox secondaryCheckBox = new JCheckBox("secondary");
		JCheckBox secondarylinkCheckBox = new JCheckBox("secondary_link");
		JCheckBox tertiaryCheckBox = new JCheckBox("tertiary");
		JCheckBox tertiarylinkCheckBox = new JCheckBox("tertiary_link");
		JCheckBox livingstreetCheckBox = new JCheckBox("living_street");
		JCheckBox residentialCheckBox = new JCheckBox("residential");
		JCheckBox racewayCheckBox = new JCheckBox("raceway");
		JCheckBox roadCheckBox = new JCheckBox("road");
		JCheckBox pathCheckBox = new JCheckBox("path");
		JCheckBox footwayCheckBox = new JCheckBox("footway");
		
		
		allCheckBoxes.add(motorwayCheckBox);
		allCheckBoxes.add(trunkCheckBox);
		allCheckBoxes.add(linkCheckBox);
		allCheckBoxes.add(junctionCheckBox);
		allCheckBoxes.add(primaryCheckBox);
		allCheckBoxes.add(primarylinkCheckBox);
		allCheckBoxes.add(secondaryCheckBox);
		allCheckBoxes.add(secondarylinkCheckBox);
		allCheckBoxes.add(tertiaryCheckBox);
		allCheckBoxes.add(tertiarylinkCheckBox);
		allCheckBoxes.add(livingstreetCheckBox);
		allCheckBoxes.add(residentialCheckBox);
		allCheckBoxes.add(racewayCheckBox);
		allCheckBoxes.add(roadCheckBox);
		allCheckBoxes.add(pathCheckBox);
		allCheckBoxes.add(footwayCheckBox);
		
		final JComponent[] inputs = new JComponent[] {
				new JLabel("--- Drive ---"),
				motorwayCheckBox,
				trunkCheckBox,
				linkCheckBox,
				junctionCheckBox,
				trunkLinksCheckBox,
				primaryCheckBox,
				primarylinkCheckBox,
				secondaryCheckBox,
				secondarylinkCheckBox,
				tertiaryCheckBox,
				tertiarylinkCheckBox,
				livingstreetCheckBox,
				residentialCheckBox,
				racewayCheckBox,
				roadCheckBox,
				new JLabel("--- Walk ---"),
				pathCheckBox,
				footwayCheckBox
		};
		
		showMapDataFilterButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
				}
				
		});
		
		this.add(selectDataAreaButton);
		this.add(getMapDataButton);
		this.add(cleanMapDataButton);
		this.add(showMapDataFilterButton);
		
	}
	
	
	public List<String> getSelectedFilter(){
		List<String> filterList = new ArrayList<String>();
		
		for(JCheckBox cb : allCheckBoxes) {
			if(cb.isSelected()){
				filterList.add(cb.getText());
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
