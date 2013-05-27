package ch.zhaw.jroute.controller;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.util.measure.MeasureTool;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import ch.zhaw.jroute.model.IWayBuilder;
import ch.zhaw.jroute.model.IWaypointBuilder;
import ch.zhaw.jroute.routedata.IBoxHandler;
import ch.zhaw.jroute.view.JrouteView;

public class GUIController {

	private JrouteView view;
	private IWaypointBuilder waypointBuilder;
	private IWayBuilder wayBuilder;

	private WaypointController waypointController;
	private WayController wayController;
	private AlgorithmController algoController;
	private MapDataController mapDataController;

	boolean armedWay = false;
	boolean armedWaypoint = false;
	boolean armedStartWaypoint = false;
	boolean armedEndWaypoint = false;

	public GUIController(JrouteView view, IWaypointBuilder waypointBuilder,
			IWayBuilder wayBuilder, IBoxHandler boxHandler) {

		this.view = view;
		this.wayBuilder = wayBuilder;
		this.waypointBuilder = waypointBuilder;

		// Controller
		this.waypointController = new WaypointController(view, waypointBuilder);
		this.wayController = new WayController(view, wayBuilder);
		this.algoController = new AlgorithmController(wayBuilder,waypointBuilder);
		this.mapDataController = new MapDataController(boxHandler,waypointBuilder, wayBuilder);

		view.addWaypointActionListener(new CreateWaypointListener());
		view.addWayActionListener(new CreateWayListener());
		view.addStartWaypointActionListener(new StartWaypointListener());
		view.addEndWaypointActionListener(new EndWaypointListener());
		view.addCalculateRouteActionListener(new CreateCalcRouteListener());
		view.addGetDataActionListener(new CreateGetDataListener());
		view.addStartSelectAreaListener(new StartSelectDataAreaListener());
		view.addStopCreatingWayListener(new StopCreatingWayListener());
		view.addStopCreatingWaypointListener(new StopCreatingWaypointListener());
		view.addClearRouteDataListener(new ClearAlgorithmDataListener());
	}
	
	private class ClearAlgorithmDataListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			view.getWayLayer().cleanUpAlgoPath();
		}
		
	}
	
	private class CreateCalcRouteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			algoController.StartAlgorithm();
		}
	}
	
	private class CreateGetDataListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		
			MeasureTool tool = view.getDataArea();
			List<Position> positions = (List<Position>) tool.getPositions();
			
			Position pos1 = positions.get(0);
			Position pos2 = positions.get(0);
			
			
			for(Position pos : positions){
				//waypointController.createNewNode(pos);
				
				double lat = pos.getLatitude().degrees;
				double lon = pos.getLongitude().degrees;
				
				if(lat< pos1.getLatitude().degrees && lon< pos1.getLongitude().degrees){
					pos1 = pos;
				}
				
				if(lat> pos2.getLatitude().degrees && lon> pos2.getLongitude().degrees){
					pos2 = pos;
				}
			}
			
			mapDataController.getDataForMapSection(pos1.getLongitude().degrees,pos1.getLatitude().degrees,pos2.getLongitude().degrees,pos2.getLatitude().degrees);
			tool.dispose();
		}
		
	}
	
	private class StartSelectDataAreaListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//mapDataController.getDataForMapSection();
			view.addMeasureTool();
		}
		
	}

	private class CreateWaypointListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {

			((Component) view.getWwd()).setCursor(Cursor
					.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			if (armedWay) {
				wayController.removeWayInputListener();
				armedWay = false;
			}
			if (armedStartWaypoint) {
				waypointController.removeStartWaypointInputListener();
				armedStartWaypoint = false;
			}
			if (armedEndWaypoint) {
				waypointController.removeEndWaypointInputListener();
				armedEndWaypoint = false;
			}
			if (!armedWaypoint) {
				waypointController.addWaypointInputListener();
				armedWaypoint = true;
			}
		}
	}
	
	private class StopCreatingWaypointListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			((Component) view.getWwd()).setCursor(Cursor
					.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if (armedStartWaypoint) {
				waypointController.removeStartWaypointInputListener();
				armedStartWaypoint = false;
			}
			if (armedEndWaypoint) {
				waypointController.removeEndWaypointInputListener();
				armedEndWaypoint = false;
			}
			if (armedWaypoint) {
				waypointController.removeWaypointInputListener();
				armedWaypoint = false;
			}
		}
	}

	private class StartWaypointListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			((Component) view.getWwd()).setCursor(Cursor
					.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			if (armedWay) {
				wayController.removeWayInputListener();
				armedWay = false;
			}
			if (armedWaypoint) {
				waypointController.removeWaypointInputListener();
				armedWaypoint = false;
			}
			if (armedEndWaypoint) {
				waypointController.removeEndWaypointInputListener();
				armedEndWaypoint = false;
			}
			if (!armedStartWaypoint) {
				waypointController.addStartWaypointInputListener();
				armedStartWaypoint = true;
			}
		}
	}

	private class EndWaypointListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			((Component) view.getWwd()).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			if(armedWay){
				wayController.removeWayInputListener();
				armedWay =false;
			}
			if(armedWaypoint){
				waypointController.removeWaypointInputListener();
				armedWaypoint = false;
			}
			if(armedStartWaypoint){
				waypointController.removeStartWaypointInputListener();
				armedStartWaypoint = false;
			}
			if(!armedEndWaypoint){
				waypointController.addEndWaypointInputListener();
				armedEndWaypoint = true;
			}
		}
	}

	private class CreateWayListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			((Component) view.getWwd()).setCursor(Cursor
					.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			if (armedWaypoint) {
				waypointController.removeWaypointInputListener();
				armedWaypoint = false;
			}
			if (armedStartWaypoint) {
				waypointController.removeStartWaypointInputListener();
				armedStartWaypoint = false;
			}
			if (armedEndWaypoint) {
				waypointController.removeEndWaypointInputListener();
				armedEndWaypoint = false;
			}
			if (!armedWay) {
				wayController.addWayInputListener();
				armedWay = true;
			}
		}
	}
	
	private class StopCreatingWayListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			((Component) view.getWwd()).setCursor(Cursor
					.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if (armedWay) {
				wayController.removeWayInputListener();
				armedWay = false;
			}
		}
	}

}
