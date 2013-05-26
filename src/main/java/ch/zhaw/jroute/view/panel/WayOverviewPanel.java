package ch.zhaw.jroute.view.panel;

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
	private JButton stopCreatingWayButton;
	
	/**
	 * 
	 */
	public WayOverviewPanel(){
		super(new GridLayout());
		
		createPanel();
	}
	
	public void createPanel(){
		
		//Create border around panel
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9), new TitledBorder("Way")));
		createButtons();
	}
	
	public void createButtons(){
		createWayButton = new JButton("Create");
		stopCreatingWayButton = new JButton("Stop");
		
		add(createWayButton);
		add(stopCreatingWayButton);
	}
	
	public JButton getCreateConnectorButton() {
		return createWayButton;
	}

	public JButton getStopCreatingWayButton() {
		return stopCreatingWayButton;
	}
}
