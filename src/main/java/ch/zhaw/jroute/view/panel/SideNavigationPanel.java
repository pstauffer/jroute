package ch.zhaw.jroute.view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

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
	private MapDataControlPanel controlPanel;
	private AlgorithmControlPanel algoControlPanel;
	
	/**
	 * 
	 * @param worldWindWindow
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
		this.add(controlPanel);
		this.add(wayPanel);
		this.add(algoControlPanel);
	}
	
	public WaypointOverviewPanel getWaypointPanel() {
		return waypointPanel;
	}

	public WayOverviewPanel getWayPanel() {
		return wayPanel;
	}
	
	public MapDataControlPanel getControlPanel(){
		return controlPanel;
	}

	public AlgorithmControlPanel getAlgoControlPanel() {
		return algoControlPanel;
	}

}
