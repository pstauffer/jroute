package ch.zhaw.jroute.controller;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch.zhaw.jroute.model.IWayBuilder;
import ch.zhaw.jroute.model.IWaypointBuilder;
import ch.zhaw.jroute.view.JrouteView;

public class GUIController {

	private JrouteView view;
	private IWaypointBuilder waypointBuilder;
	private IWayBuilder wayBuilder;

	private WaypointController waypointController;
	private WayController wayController;
	private AlgorithmController algoController;

	boolean armedWay = false;
	boolean armedWaypoint = false;
	boolean armedStartWaypoint = false;
	boolean armedEndWaypoint = false;

	public GUIController(JrouteView view, IWaypointBuilder waypointBuilder,
			IWayBuilder wayBuilder) {

		this.view = view;
		this.wayBuilder = wayBuilder;
		this.waypointBuilder = waypointBuilder;

		// Controller
		this.waypointController = new WaypointController(view, waypointBuilder);
		this.wayController = new WayController(view, wayBuilder);
		this.algoController = new AlgorithmController(wayBuilder,waypointBuilder);

		view.addWaypointActionListener(new CreateWaypointListener());
		view.addWayActionListener(new CreateWayListener());
		view.addStartWaypointActionListener(new StartWaypointListener());
		view.addEndWaypointActionListener(new EndWaypointListener());
		view.addCalculateRouteActionListener(new CreateCalcRouteListener());
		
	}
	
	private class CreateCalcRouteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			algoController.StartAlgorithm();
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

}
