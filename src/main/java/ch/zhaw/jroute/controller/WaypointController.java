package ch.zhaw.jroute.controller;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gov.nasa.worldwind.geom.Position;
import ch.zhaw.jroute.model.IWaypointBuilder;
import ch.zhaw.jroute.model.WaypointBuilder;
import ch.zhaw.jroute.view.JrouteView;
import ch.zhaw.jroute.view.layer.WaypointLayer;

public class WaypointController implements IWaypointController {
	
	private IWaypointBuilder waypointBuilder;
	private JrouteView view;
	private WaypointMouseAdapter mouseAdapter;
	private IWayController wayController;
	
	public WaypointController(JrouteView view, IWaypointBuilder builder){
		
		this.view = view;
		this.waypointBuilder = builder;
		this.waypointBuilder.registerObserver(view.getWaypointLayer());
		
		this.mouseAdapter = new WaypointMouseAdapter();
		
		this.wayController = wayController;
	}
	
	public void createNewNode(Position position){
		waypointBuilder.createWaypointFromPosition(view.getWwd().getCurrentPosition());
	}
	
	public void addWaypointInputListener(){
		view.getWwd().getInputHandler().addMouseListener(mouseAdapter);
	}
	
	public void removeWaypointInputListener() {
		view.getWwd().getInputHandler().removeMouseListener(mouseAdapter);
	}
	
	private class WaypointMouseAdapter extends MouseAdapter{
		
		public void mouseClicked(MouseEvent mouseEvent) {
			if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
				createNewNode(view.getWwd().getCurrentPosition());
				/*
				 * addPosition(); layer.addRenderable(node);
				 * 
				 * if (mouseEvent.isControlDown()) removePosition();
				 */

				mouseEvent.consume();
			}
		}
	}
}
