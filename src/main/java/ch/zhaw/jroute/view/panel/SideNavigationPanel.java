package ch.zhaw.jroute.view.panel;

import gov.nasa.worldwind.WorldWindow;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * This panel represent a container for all sub panels
 * @author yk
 */
public class SideNavigationPanel extends JPanel{
	
	private WaypointOverviewPanel waypointPanel;
	private WayOverviewPanel wayPanel;
	private MapDataControlPanel controlPanel;
	private AlgorithmControlPanel algoControlPanel;
	
	/**
	 * Constructor to initialize all sub panels 
	 * - WaypointOverviewPanel: control the static waypoints
	 * - WayOverviewPanel: control the static ways
	 * - MapDataControlPanel: get data from the api and control it
	 * - AlgorithmControlPanel: run the shortestpath algorithm
	 * @param worldWindWindow the parent main window
	 */
	public SideNavigationPanel(WorldWindow worldWindWindow){
		
		//Add a new borderlayout to the the Frame
		super(new GridLayout(4,1));
		this.setPreferredSize(new Dimension(200,400));
		
		this.waypointPanel = new WaypointOverviewPanel();
		this.wayPanel = new WayOverviewPanel();
		this.controlPanel = new MapDataControlPanel();
		this.algoControlPanel = new AlgorithmControlPanel();
		
		this.add(waypointPanel);
		this.add(wayPanel);
		this.add(controlPanel);
		this.add(algoControlPanel);
	}
	
	/**
	 * @return the waypoint overview panel
	 */
	public WaypointOverviewPanel getWaypointPanel() {
		return waypointPanel;
	}

	/**
	 * @return the way overview panel
	 */
	public WayOverviewPanel getWayPanel() {
		return wayPanel;
	}
	
	/**
	 * @return the map control panel
	 */
	public MapDataControlPanel getControlPanel(){
		return controlPanel;
	}

	/**
	 * @return the algorithm control panel
	 */
	public AlgorithmControlPanel getAlgoControlPanel() {
		return algoControlPanel;
	}

}
