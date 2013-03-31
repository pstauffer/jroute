package ch.zhaw.jroute.view;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;

import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.MarkerLayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import ch.zhaw.jroute.controller.INodeController;
import ch.zhaw.jroute.controller.NodeController;
import ch.zhaw.jroute.view.panel.SideNavigationPanel;
import ch.zhaw.jroute.view.template.ApplicationTemplate;
import ch.zhaw.jroute.view.layer.*;

public class JrouteView extends ApplicationTemplate.AppFrame {

	private boolean nodeArmed = false;

	// Layers
	private ConnectorLayer connectorLayer = new ConnectorLayer();
	private NodeLayer nodeLayer = new NodeLayer();
	private MarkerLayer markerLayer = new MarkerLayer();

	// Controllers
	private INodeController nodeController;

	// Gui components
	private SideNavigationPanel navigationPanel;

	public JrouteView() {
		super(true, false, false);
		this.setSize(850, 800);

		// hide not usefull layers and add open streetmap layer
		this.addExtraLayer();
		
		this.nodeController = new NodeController(this.nodeLayer, this);

		// Set inital point on the map
		this.getWwd().setValue(AVKey.INITIAL_LATITUDE, 49.06);
		this.getWwd().setValue(AVKey.INITIAL_LONGITUDE, -122.77);
		this.getWwd().setValue(AVKey.INITIAL_ALTITUDE, 22000);
		
		//create side panel
		this.navigationPanel = new SideNavigationPanel(this.getWwd());

		//add side panel to the window
		this.getContentPane().add(this.navigationPanel, BorderLayout.WEST);
		
		this.addActionListener();
	}

	/**
	 * adds the additional openstreetmap layer to the map and hides some other
	 * not used layers
	 */
	private void addExtraLayer() {
		// Layer layer = (Layer) new OpenStreetMapWMSLayer();
		LayerList layers = this.getWwd().getModel().getLayers();
		layers.add(new OSMMapnikLayer());
		layers.add(connectorLayer);
		layers.add(nodeLayer);
		layers.add(markerLayer);

		connectorLayer.setName("Connector layer");
		nodeLayer.setName("Node layer");
		markerLayer.setName("Marker layer");

		for (Layer layer : layers) {
			// layer.setEnabled(false);
			if (layer.getName().contains("Place")) {
				layer.setEnabled(false);
			}
			if (layer.getName().contains("Landsat")) {
				layer.setEnabled(false);
			}
			if (layer.getName().contains("Open")) {
				layer.setEnabled(true);
			}
		}

		this.getWwd().redraw();
	}

	public void addNodeInputListener(){
		if(!nodeArmed){
			this.nodeArmed = true;
			this.getWwd().getInputHandler().addMouseListener(new MouseAdapter()
	        {
	            public void mouseClicked(MouseEvent mouseEvent)
	            {
	                if (mouseEvent.getButton() == MouseEvent.BUTTON1)
	                {
	                	nodeController.createNewNode(getWwd().getCurrentPosition());
	                	/*addPosition();
	                	layer.addRenderable(node);
	
	                    if (mouseEvent.isControlDown())
	                        removePosition();*/
	                	
	                    mouseEvent.consume();
	                }
	            }
	        });
		}
	}
	
	private void addActionListener() {
		this.navigationPanel.getNodePanel().getCreateNodeButton()
				.addActionListener(new CreateNodeListener());
	}

	private class CreateNodeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			((Component) getWwd()).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			addNodeInputListener();
		}
	}
	

	public NodeLayer getNodeLayer() {
		return nodeLayer;
	}
}
