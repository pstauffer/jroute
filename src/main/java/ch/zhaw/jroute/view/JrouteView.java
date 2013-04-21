package ch.zhaw.jroute.view;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.MarkerLayer;
import gov.nasa.worldwind.pick.PickedObject;
import gov.nasa.worldwind.pick.PickedObjectList;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import ch.zhaw.jroute.controller.IWaypointController;
import ch.zhaw.jroute.model.Waypoint;
import ch.zhaw.jroute.view.layer.OSMMapnikLayer;
import ch.zhaw.jroute.view.layer.WayLayer;
import ch.zhaw.jroute.view.layer.WaypointLayer;
import ch.zhaw.jroute.view.panel.SideNavigationPanel;
import ch.zhaw.jroute.view.template.ApplicationTemplate;

public class JrouteView extends ApplicationTemplate.AppFrame {

	// Layers
	private WayLayer wayLayer = new WayLayer();
	private WaypointLayer waypointLayer = new WaypointLayer();
	private MarkerLayer markerLayer = new MarkerLayer();

	// Controllers
	private IWaypointController waypointController;

	// Gui components
	private SideNavigationPanel navigationPanel;

	public JrouteView() {
		super(true, false, false);
		this.setSize(850, 800);

		// hide not usefull layers and add open streetmap layer
		this.addExtraLayer();

		// Set inital point on the map
		this.getWwd().setValue(AVKey.INITIAL_LATITUDE, 49.06);
		this.getWwd().setValue(AVKey.INITIAL_LONGITUDE, -122.77);
		this.getWwd().setValue(AVKey.INITIAL_ALTITUDE, 22000);
		
		//create side panel
		this.navigationPanel = new SideNavigationPanel(this.getWwd());

		//add side panel to the window
		this.getContentPane().add(this.navigationPanel, BorderLayout.WEST);
	}

	/**
	 * adds the additional openstreetmap layer to the map and hides some other
	 * not used layers
	 */
	private void addExtraLayer() {
		// Layer layer = (Layer) new OpenStreetMapWMSLayer();
		LayerList layers = this.getWwd().getModel().getLayers();
		layers.add(new OSMMapnikLayer());
		layers.add(wayLayer);
		layers.add(waypointLayer);
		layers.add(markerLayer);

		wayLayer.setName("Connector layer");
		waypointLayer.setName("Waypoint layer");
		markerLayer.setName("Marker layer");

		for (Layer layer : layers) {
			// layer.setEnabled(false);
			if (layer.getName().contains("Place")) {
				layer.setEnabled(false);
			}
			if (layer.getName().contains("Landsat")) {
				layer.setEnabled(false);
			}
			if (layer.getName().contains("Blue")) {
				layer.setEnabled(false);
			}
			if (layer.getName().contains("Open")) {
				layer.setEnabled(true);
			}
		}

		this.getWwd().redraw();
	}
	
	public void addWaypointActionListener(ActionListener listener){
		this.navigationPanel.getWaypointPanel().getCreateWaypointButton()
		.addActionListener(listener);
	}
	
	public void addWayActionListener(ActionListener listener){
		this.navigationPanel.getConnectorPanel().getCreateConnectorButton()
		.addActionListener(listener);
	}
	
	public Waypoint getWaypointAtPosition(){
		PickedObjectList list = this.getWwd().getObjectsAtCurrentPosition();
		Waypoint currentWaypoint = null;
		
		for(PickedObject obj : list){
			if (obj.getObject() instanceof Waypoint) {
				currentWaypoint = (Waypoint) obj.getObject();
			}
		}
		
		return currentWaypoint;
	}
	
	
	public WaypointLayer getWaypointLayer() {
		return waypointLayer;
	}
	
	public WayLayer getWayLayer(){
		return wayLayer;
	}
	
}
