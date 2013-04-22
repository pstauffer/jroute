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

	private HashMap<Integer,Waypoint> waypointList = new HashMap<Integer,Waypoint>();
	private int currentLetter = 65; //A
	private int id = 0;

	public void createWaypointFromPosition(Position waypointPosition) {

		if (waypointPosition == null) {
			return;
		}

		Waypoint newWaypoint = new Waypoint(waypointPosition, 50);
		
        char c = (char)this.currentLetter;
        String text = String.valueOf(c);
        newWaypoint.setName(text);
   
        newWaypoint.setWaypointID(id);
        
        newWaypoint.setStatus(WaypointStatusEnum.undefined);
        
		waypointList.put(id,newWaypoint);
		
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
	public void setStartEndpoint(WaypointStatusEnum status, Waypoint waypoint) {
		//TODO: Add a check if start and end are allready set
		waypoint.setStatus(status);
		waypointList.put(waypoint.getWaypointID(), waypoint);
		
		this.setChanged();
		this.notifyObservers(waypoint);
	}

}
