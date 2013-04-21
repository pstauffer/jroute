package ch.zhaw.jroute.controller;

import gov.nasa.worldwind.event.PositionEvent;
import gov.nasa.worldwind.event.PositionListener;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ch.zhaw.jroute.model.IWayBuilder;
import ch.zhaw.jroute.model.WayBuilder;
import ch.zhaw.jroute.model.Waypoint;
import ch.zhaw.jroute.view.JrouteView;

public class WayController implements IWayController{
	
	private JrouteView view;
	private IWayBuilder wayBuilder;
	private WayMouseAdapter mouseAdapter;
	
	public WayController(JrouteView view, IWayBuilder wayBuilder){
		this.view = view;
		this.wayBuilder = wayBuilder;
		
		this.mouseAdapter = new WayMouseAdapter();
		
		this.wayBuilder.registerObserver(view.getWayLayer());
	}
	
	public void addWayInputListener(){
		view.getWwd().getInputHandler().addMouseListener(mouseAdapter);
	}

	public void removeWayInputListener() {
		view.getWwd().getInputHandler().removeMouseListener(mouseAdapter);
	}
	
	private void createNewLine(){
		
		Waypoint currentWaypoint = view.getWaypointAtPosition();
	}
	
	private class WayMouseAdapter extends MouseAdapter{
		
		public void mouseClicked(MouseEvent mouseEvent) {
			if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
				/*
				 * addPosition(); layer.addRenderable(node);
				 * 
				 * if (mouseEvent.isControlDown()) removePosition();
				 */
				createNewLine();
				mouseEvent.consume();
			}
		}
	}
	
	private class WayPositionListener implements PositionListener{

		@Override
		public void moved(PositionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
