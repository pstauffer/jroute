package ch.zhaw.jroute.controller;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gov.nasa.worldwind.geom.Position;
import ch.zhaw.jroute.model.IWaypointBuilder;
import ch.zhaw.jroute.model.Waypoint;
import ch.zhaw.jroute.model.WaypointBuilder;
import ch.zhaw.jroute.model.WaypointStatusEnum;
import ch.zhaw.jroute.view.JrouteView;
import ch.zhaw.jroute.view.layer.WaypointLayer;

public class WaypointController implements IWaypointController {
	
	private IWaypointBuilder waypointBuilder;
	private JrouteView view;
	private WaypointMouseAdapter mouseAdapter;
	private StartWaypointMouseAdapter startMouseAdapter;
	private EndWaypointMouseAdapter endMouseAdapter;
	private IWayController wayController;
	
	public WaypointController(JrouteView view, IWaypointBuilder builder){
		
		this.view = view;
		this.waypointBuilder = builder;
		this.waypointBuilder.registerObserver(view.getWaypointLayer());
		
		this.mouseAdapter = new WaypointMouseAdapter();
		this.startMouseAdapter = new StartWaypointMouseAdapter();
		this.endMouseAdapter = new EndWaypointMouseAdapter();
		
		this.wayController = wayController;
	}
	
	public void createNewNode(Position position){
		waypointBuilder.createWaypointFromPosition(position);
	}
	
	public void addWaypointInputListener(){
		view.getWwd().getInputHandler().addMouseListener(mouseAdapter);
	}
	
	public void removeWaypointInputListener() {
		view.getWwd().getInputHandler().removeMouseListener(mouseAdapter);
	}
	
	public void addStartWaypointInputListener(){
		view.getWwd().getInputHandler().addMouseListener(startMouseAdapter);
	}
	
	public void removeStartWaypointInputListener() {
		view.getWwd().getInputHandler().removeMouseListener(startMouseAdapter);
	}
	
	public void addEndWaypointInputListener(){
		view.getWwd().getInputHandler().addMouseListener(endMouseAdapter);
	}
	
	public void removeEndWaypointInputListener() {
		view.getWwd().getInputHandler().removeMouseListener(endMouseAdapter);
	}
	
	private void setStartWaypoint(Waypoint existingWp){
		switch(existingWp.getStatus()){
		case undefined:
			waypointBuilder.setStartWaypoint(WaypointStatusEnum.start, existingWp);
			break;
		case start:
			waypointBuilder.setStartWaypoint(WaypointStatusEnum.undefined, existingWp);
			break;
		default:
			break;
		}
	}
	
	private void setEndWaypoint(Waypoint existingWp){
		switch(existingWp.getStatus()){
		case undefined:
			waypointBuilder.setEndWaypoint(WaypointStatusEnum.end, existingWp);
			break;
		case end:
			waypointBuilder.setEndWaypoint(WaypointStatusEnum.undefined, existingWp);
			break;
		default:
			break;
		}
	}
	
	private class WaypointMouseAdapter extends MouseAdapter{
		
		public void mouseClicked(MouseEvent mouseEvent) {
			if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
				Waypoint existingWp = view.getWaypointAtPosition();
				if(existingWp!=null){
					return;
				}else{
					createNewNode(view.getWwd().getCurrentPosition());
				}

				mouseEvent.consume();
			}
		}
	}
	
	private class StartWaypointMouseAdapter extends MouseAdapter{
		
		public void mouseClicked(MouseEvent mouseEvent) {
			if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
				Waypoint existingWp = view.getWaypointAtPosition();
				if(existingWp!=null){
					view.getWayLayer().cleanUpAlgoPath();
					setStartWaypoint(existingWp);
				}else{
					return;
				}

				mouseEvent.consume();
			}
		}
	}
	
	private class EndWaypointMouseAdapter extends MouseAdapter{
		
		public void mouseClicked(MouseEvent mouseEvent) {
			if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
				Waypoint existingWp = view.getWaypointAtPosition();
				if(existingWp!=null){
					view.getWayLayer().cleanUpAlgoPath();
					setEndWaypoint(existingWp);
				}else{
					return;
				}

				mouseEvent.consume();
			}
		}
	}

	@Override
	public void cleanWaypoints() {
		waypointBuilder.removeAllWaypoints();
	}
	
	
}
