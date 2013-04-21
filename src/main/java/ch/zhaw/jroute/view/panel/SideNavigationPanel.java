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
	
	private WaypointOverviewPanel waypointPanel;
	private WayOverviewPanel wayPanel;
	
	/**
	 * 
	 * @param worldWindWindow
	 */
	public SideNavigationPanel(WorldWindow worldWindWindow){
		
		//Add a new borderlayout to the the Frame
		super(new BorderLayout());
		this.setPreferredSize(new Dimension(200,400));
		
		this.waypointPanel = new WaypointOverviewPanel();
		this.wayPanel = new WayOverviewPanel();
		
		this.add(waypointPanel,BorderLayout.NORTH);
		this.add(wayPanel,BorderLayout.SOUTH);
	}
	
	public WaypointOverviewPanel getWaypointPanel() {
		return waypointPanel;
	}

	public WayOverviewPanel getConnectorPanel() {
		return wayPanel;
	}
	
	

}
