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
	
	boolean armedWay = false;
	boolean armedWaypoint = false;
	
	public GUIController(JrouteView view, IWaypointBuilder waypointBuilder, IWayBuilder wayBuilder){
		
		this.view = view;
		this.wayBuilder = wayBuilder;
		this.waypointBuilder = waypointBuilder;
		
		//Controller
		this.waypointController = new WaypointController(view,waypointBuilder);
		this.wayController = new WayController(view,wayBuilder);
		
		view.addWaypointActionListener(new CreateWaypointListener());
		view.addWayActionListener(new CreateWayListener());
	}
	
	private class CreateWaypointListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			
			((Component) view.getWwd()).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			if(armedWay){
				wayController.removeWayInputListener();
				armedWay =false;
			}
			if(!armedWaypoint){
				waypointController.addWaypointInputListener();
				armedWaypoint = true;
			}
		}
	}
	
	private class CreateWayListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			((Component) view.getWwd()).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			if(armedWaypoint){
				waypointController.removeWaypointInputListener();
				armedWaypoint = false;
			}
			if(!armedWay){
				wayController.addWayInputListener();
				armedWay =true;
			}
		}
	}

}
