package ch.zhaw.jroute.view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import gov.nasa.worldwind.WorldWindow;

import javax.swing.JPanel;

/**
 * 
 * @author yk
 *
 */
public class SideNavigationPanel extends JPanel{
	
	/**
	 * 
	 * @param worldWindWindow
	 */
	public SideNavigationPanel(WorldWindow worldWindWindow){
		
		//Add a new borderlayout to the the Frame
		super(new BorderLayout());
		
		this.setPreferredSize(new Dimension(200,400));
		
		this.add(new ConnectorOverviewPanel());
	}

}
