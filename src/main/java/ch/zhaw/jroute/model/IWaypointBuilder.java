package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;

import java.util.Observer;



public interface IWaypointBuilder {
	
	public void registerObserver(Observer obs);
	public void createWaypointFromPosition(Position waypointPosition);
	


}
