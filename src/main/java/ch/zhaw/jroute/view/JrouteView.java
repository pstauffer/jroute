package ch.zhaw.jroute.view;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.MarkerLayer;
import gov.nasa.worldwind.pick.PickedObject;
import gov.nasa.worldwind.pick.PickedObjectList;
import gov.nasa.worldwind.util.measure.LengthMeasurer;
import gov.nasa.worldwind.util.measure.MeasureTool;
import gov.nasa.worldwind.util.measure.MeasureToolController;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ch.zhaw.jroute.controller.IWaypointController;
import ch.zhaw.jroute.model.Waypoint;
import ch.zhaw.jroute.view.layer.OSMMapnikLayer;
import ch.zhaw.jroute.view.layer.WayLayer;
import ch.zhaw.jroute.view.layer.WaypointLayer;
import ch.zhaw.jroute.view.panel.SideNavigationPanel;
import ch.zhaw.jroute.view.template.ApplicationTemplate;

public class JrouteView extends ApplicationTemplate.AppFrame {

	// Layers
	private AnnotationLayer wayAnnotationLayer = new AnnotationLayer();
	private AnnotationLayer waypointAnnotationLayer = new AnnotationLayer();
	private WayLayer wayLayer;
	private WaypointLayer waypointLayer;
	private LengthMeasurer lenghtMeasurer = new LengthMeasurer();
	private MeasureTool measureTool = new MeasureTool(this.getWwd());
	private MeasureToolController measureController = new MeasureToolController();

	// Controllers
	private IWaypointController waypointController;

	// Gui components
	private SideNavigationPanel navigationPanel;

	public JrouteView() {
		super(true, false, false);
		this.setSize(850, 800);

		wayLayer = new WayLayer(this.getWwd(), wayAnnotationLayer);
		waypointLayer = new WaypointLayer(waypointAnnotationLayer);

		// hide not usefull layers and add open streetmap layer
		this.addExtraLayer();


		// create side panel
		this.navigationPanel = new SideNavigationPanel(this.getWwd());

		// add side panel to the window
		this.getContentPane().add(this.navigationPanel, BorderLayout.WEST);

	}

	public void addMeasureTool() {
		measureTool.setMeasureShapeType(MeasureTool.SHAPE_QUAD);
		measureTool.setController(new MeasureToolController());

		measureTool.clear();
		measureTool.setArmed(true);
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
		layers.add(wayAnnotationLayer);
		layers.add(waypointAnnotationLayer);

		wayLayer.setName("Connector layer");
		waypointLayer.setName("Waypoint layer");
		wayAnnotationLayer.setName("Way Annotation layer");
		waypointAnnotationLayer.setName("Waypoint Annotation layer");

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

	public void addWaypointActionListener(ActionListener listener) {
		this.navigationPanel.getWaypointPanel().getCreateWaypointButton()
				.addActionListener(listener);
	}

	public void addWayActionListener(ActionListener listener) {
		this.navigationPanel.getWayPanel().getCreateConnectorButton()
				.addActionListener(listener);
	}

	public void addStartWaypointActionListener(ActionListener listener) {
		this.navigationPanel.getWaypointPanel().getStartWaypointButton()
				.addActionListener(listener);
	}

	public void addEndWaypointActionListener(ActionListener listener) {
		this.navigationPanel.getWaypointPanel().getEndWaypointButton()
				.addActionListener(listener);
	}

	public void addCalculateRouteActionListener(ActionListener listener) {
		this.navigationPanel.getAlgoControlPanel().getRunAlgoButton()
				.addActionListener(listener);
	}

	public void addGetDataActionListener(ActionListener listener) {
		this.navigationPanel.getControlPanel().getMapDataButton()
				.addActionListener(listener);
	}

	public void addStopCreatingWaypointListener(ActionListener listener) {
		this.navigationPanel.getWaypointPanel().getStopCreatingWaypointButton()
				.addActionListener(listener);
	}

	public void addStopCreatingWayListener(ActionListener listener) {
		this.navigationPanel.getWayPanel().getStopCreatingWayButton()
				.addActionListener(listener);
	}

	public void addStartSelectAreaListener(ActionListener listener) {
		this.navigationPanel.getControlPanel().getSelectAreaButton()
				.addActionListener(listener);
	}

	public void addClearRouteDataListener(ActionListener listener) {
		this.navigationPanel.getAlgoControlPanel().getCleanAlgoButton()
				.addActionListener(listener);
	}
	
	public void addClearDataListener(ActionListener listener) {
		this.navigationPanel.getControlPanel().getCleanMapDataButton()
				.addActionListener(listener);
	}

	public Waypoint getWaypointAtPosition() {
		PickedObjectList list = this.getWwd().getObjectsAtCurrentPosition();
		Waypoint currentWaypoint = null;

		for (PickedObject obj : list) {
			if (obj.getObject() instanceof Waypoint) {
				currentWaypoint = (Waypoint) obj.getObject();
			}
		}

		return currentWaypoint;
	}

	// Maybe this is wrong position for this
	public double calculateDistance(Position start, Position end) {
		ArrayList<Position> tempPos = new ArrayList<Position>();

		tempPos.add(start);
		tempPos.add(end);

		lenghtMeasurer.setPositions(tempPos);
		return lenghtMeasurer.getLength(this.getWwd().getModel().getGlobe());
	}
	
	public WaypointLayer getWaypointLayer() {
		return waypointLayer;
	}

	public WayLayer getWayLayer() {
		return wayLayer;
	}

	public MeasureTool getDataArea() {
		return measureTool;
	}
	
	public void showException(String message){
		JOptionPane.showMessageDialog(this,message,"Error", JOptionPane.ERROR_MESSAGE);
	}

}
