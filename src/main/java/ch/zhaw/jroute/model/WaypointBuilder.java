package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class WaypointBuilder extends Observable implements IWaypointBuilder {

	private HashMap<Float,Waypoint> waypointList = new HashMap<Float,Waypoint>();
	private int currentLetter = 65; //A
	private int id = 0;
	private Waypoint startWaypoint;
	private Waypoint endWaypoint;

	public void createWaypointFromPosition(Position waypointPosition) {

		if (waypointPosition == null) {
			return;
		}

		Waypoint newWaypoint = new Waypoint(waypointPosition, 10);
		
        char c = (char)this.currentLetter;
        String text = String.valueOf(c);
        newWaypoint.setName(Integer.toString(id));
   
        newWaypoint.setWaypointID(id);
        
        newWaypoint.setStatus(WaypointStatusEnum.undefined);
        
		waypointList.put((float)id,newWaypoint);
		
		currentLetter++;
		id++;
		
		this.setChanged();
		this.notifyObservers(newWaypoint);

	}

	@Override
	public void registerObserver(Observer obs) {
		this.addObserver(obs);
	}


	@Override
	public void setStartWaypoint(WaypointStatusEnum status, Waypoint waypoint) {
		if(status == WaypointStatusEnum.undefined){
			startWaypoint = null;
		}else if(startWaypoint == null){
			startWaypoint = waypoint;
		}
		else{
			startWaypoint.setStatus(WaypointStatusEnum.undefined);
			waypointList.put(startWaypoint.getWaypointID(), startWaypoint);
			this.setChanged();
			this.notifyObservers(startWaypoint);
			startWaypoint = waypoint;
		}
		
		waypoint.setStatus(status);
		waypointList.put(waypoint.getWaypointID(), waypoint);
		
		this.setChanged();
		this.notifyObservers(waypoint);
	}
	
	public void setEndWaypoint(WaypointStatusEnum status, Waypoint waypoint) {
		if(status == WaypointStatusEnum.undefined){
			endWaypoint = null;
		}else if(endWaypoint == null){
			endWaypoint = waypoint;
		}
		else{
			endWaypoint.setStatus(WaypointStatusEnum.undefined);
			waypointList.put(endWaypoint.getWaypointID(), endWaypoint);
			this.setChanged();
			this.notifyObservers(endWaypoint);
			endWaypoint = waypoint;
		}
		
		waypoint.setStatus(status);
		waypointList.put(waypoint.getWaypointID(), waypoint);
		
		this.setChanged();
		this.notifyObservers(waypoint);
	}
	
	public HashMap<Float, Waypoint> getWaypointList() {
		return waypointList;
	}


	public Waypoint getStartWaypoint() {
		return startWaypoint;
	}


	public Waypoint getEndWaypoint() {
		return endWaypoint;
	}

}
