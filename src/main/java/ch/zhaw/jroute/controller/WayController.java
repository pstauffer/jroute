package ch.zhaw.jroute.controller;

import gov.nasa.worldwind.event.PositionEvent;
import gov.nasa.worldwind.event.PositionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ch.zhaw.jroute.controller.interfaces.IWayController;
import ch.zhaw.jroute.model.businessObjects.Waypoint;
import ch.zhaw.jroute.model.interfaces.IWayBuilder;
import ch.zhaw.jroute.view.JrouteView;

/**
 * Controller which connects the view with the way model
 * @author yk
 */
public class WayController implements IWayController{
	
	private JrouteView view;
	private IWayBuilder wayBuilder;
	private WayMouseAdapter mouseAdapter;
	private WayPositionListener positionListener;
	private boolean active;
	private boolean clicked = false;
	private Waypoint tempStart;
	
	public WayController(JrouteView view, IWayBuilder wayBuilder){
		this.view = view;
		this.wayBuilder = wayBuilder;
		
		this.mouseAdapter = new WayMouseAdapter();
		this.positionListener = new WayPositionListener();
		
		this.wayBuilder.registerObserver(view.getWayLayer());
	}
	
	public void addWayInputListener(){
		view.getWwd().getInputHandler().addMouseListener(mouseAdapter);
	}

	public void removeWayInputListener() {
		view.getWwd().getInputHandler().removeMouseListener(mouseAdapter);
		view.getWwd().removePositionListener(positionListener);	
	}
	
	private void createNewLine(){
		
		Waypoint currentWaypoint = view.getWaypointAtPosition();
		
		if(currentWaypoint == null){
			return;
		}
		view.getWwd().addPositionListener(positionListener);
		active = true;
		clicked = true;
		tempStart = currentWaypoint;
		wayBuilder.createNewWay(currentWaypoint);
	}
	
	private void finishLine(){
		Waypoint currentWaypoint = view.getWaypointAtPosition();
		
		if(currentWaypoint == null){
			return;
		}
		view.getWwd().removePositionListener(positionListener);
		active = false;
		clicked = false;
		
		double distance = view.calculateDistance(tempStart.getReferencePosition(), currentWaypoint.getReferencePosition());
		
		wayBuilder.finishWay(currentWaypoint,distance);
	}
	
	private class WayMouseAdapter extends MouseAdapter{
		
		public void mouseClicked(MouseEvent mouseEvent) {
			if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
				if(!clicked){
					createNewLine();
				}else{
					finishLine();
				}
				mouseEvent.consume();
			}
		}
	}
	
	private class WayPositionListener implements PositionListener{

		@Override
		public void moved(PositionEvent arg0) {
			if(active){
				wayBuilder.moveWayEndpoint(view.getWwd().getCurrentPosition());
			}
		}
		
	}

	@Override
	public void cleanAllWays() {
		wayBuilder.removeAllWays();
	}
}
