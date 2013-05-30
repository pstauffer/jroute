package ch.zhaw.jroute.view.panel;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

/**
 * this panel represent als gui elements to control the static ways
 * @author yk
 */
public class WayOverviewPanel extends JPanel{
	
	private JButton createWayButton;
	private JButton stopCreatingWayButton;
	
	/**
	 * Constructor to create the GridLayout
	 */
	public WayOverviewPanel(){
		super(new GridLayout());
		
		createPanel();
	}
	
	/**
	 * creates the actual panel with the border
	 */
	public void createPanel(){
		
		//Create border around panel
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9), new TitledBorder("Way")));
		createButtons();
	}
	
	/**
	 * creates the buttons in the panel
	 */
	public void createButtons(){
		createWayButton = new JButton("Create");
		stopCreatingWayButton = new JButton("Stop");
		
		add(createWayButton);
		add(stopCreatingWayButton);
	}
	
	/**
	 * @return the button to create a new Way
	 */
	public JButton getCreateWayButton() {
		return createWayButton;
	}

	/**
	 * @return the button to stop way creation
	 */
	public JButton getStopCreatingWayButton() {
		return stopCreatingWayButton;
	}
}
