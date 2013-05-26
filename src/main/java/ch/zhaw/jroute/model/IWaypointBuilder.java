package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;

import java.util.HashMap;
import java.util.Observer;

public interface IWaypointBuilder {

	public void registerObserver(Observer obs);

	public void createWaypointFromPosition(Position waypointPosition);
	
	public void createWaypointFromExistingWaypoint(Waypoint waypoint);

	public void setStartWaypoint(WaypointStatusEnum status, Waypoint waypoint);

	public void setEndWaypoint(WaypointStatusEnum status, Waypoint waypoint);

	public HashMap<Long, Waypoint> getWaypointList();

	public Waypoint getStartWaypoint();

	public Waypoint getEndWaypoint();
}
