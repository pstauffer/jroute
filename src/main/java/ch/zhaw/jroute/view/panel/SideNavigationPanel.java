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
	
	private NodeOverviewPanel nodePanel;
	private ConnectorOverviewPanel connectorPanel;
	
	/**
	 * 
	 * @param worldWindWindow
	 */
	public SideNavigationPanel(WorldWindow worldWindWindow){
		
		//Add a new borderlayout to the the Frame
		super(new BorderLayout());
		this.setPreferredSize(new Dimension(200,400));
		
		this.nodePanel = new NodeOverviewPanel();
		this.connectorPanel = new ConnectorOverviewPanel();
		
		this.add(nodePanel,BorderLayout.NORTH);
		this.add(connectorPanel,BorderLayout.SOUTH);
	}
	
	public NodeOverviewPanel getNodePanel() {
		return nodePanel;
	}

	public ConnectorOverviewPanel getConnectorPanel() {
		return connectorPanel;
	}
	
	

}
