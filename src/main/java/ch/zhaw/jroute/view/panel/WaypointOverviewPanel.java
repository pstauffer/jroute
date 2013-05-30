package ch.zhaw.jroute.view.panel;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

/**
 * panel which represents the gui elements to create static waypoints
 * and set the start and end point of a path
 * @author yk
 *
 */
public class WaypointOverviewPanel extends JPanel{
	
	private JButton createWaypointButton;
	private JButton startWaypointButton;
	private JButton endWaypointButton;
	private JButton stopCreatingWaypointButton;

	/**
	 * Constructor to create the GridLayout
	 */
	public WaypointOverviewPanel(){
		super(new GridLayout(2,2));
		
		//Create the actual panel
		createPanel();
	}
	
	/**
	 * creates the actual panel with the border
	 */
	public void createPanel(){
		//Set the border around the pabel
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9), new TitledBorder("Waypoint")));
		
		//add the buttons the panel
		createButtons();
	}
	
	/**
	 * creates the buttons in the panel
	 */
	public void createButtons(){
		createWaypointButton = new JButton("Create");
		stopCreatingWaypointButton = new JButton("Stop");
		startWaypointButton = new JButton("Start");
		endWaypointButton = new JButton("End");
		
		this.add(createWaypointButton);
		this.add(stopCreatingWaypointButton);
		this.add(startWaypointButton);
		this.add(endWaypointButton);
		
	}

	/**
	 * @return the button to create a new waypoint
	 */
	public JButton getCreateWaypointButton() {
		return createWaypointButton;
	}

	/**
	 * @return the button set the start for a path
	 */
	public JButton getStartWaypointButton() {
		return startWaypointButton;
	}

	/**
	 * @return the button to set the end for a path
	 */
	public JButton getEndWaypointButton() {
		return endWaypointButton;
	}
	
	/**
	 * @return the button stop creating waypoints
	 */
	public JButton getStopCreatingWaypointButton() {
		return stopCreatingWaypointButton;
	}
}
