package ch.zhaw.jroute.view.panel;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

/**
 * Class which represent the panel where all connectors are shown
 * @author yk
 */
public class WayOverviewPanel extends JPanel{
	
	private JButton createWayButton;
	
	/**
	 * 
	 */
	public WayOverviewPanel(){
		super(new GridLayout());
		
		this.createPanel();
	}
	
	public void createPanel(){
		
		//Create border around panel
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9), new TitledBorder("Way")));
		this.createButtons();
	}
	
	public void createButtons(){
		this.createWayButton = new JButton("Create");
		this.add(createWayButton);
	}
	
	public JButton getCreateConnectorButton() {
		return createWayButton;
	}
}
