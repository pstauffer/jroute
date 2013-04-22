package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;

import java.util.Observer;

public interface IWayBuilder {
	public void registerObserver(Observer obs);
	
	public void createNewWay(Waypoint start);
	
	public void moveWayEndpoint(Position newPosition);
	
	public void finishWay(Waypoint end, double distance);
}


