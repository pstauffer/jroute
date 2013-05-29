package ch.zhaw.jroute.model.interfaces;

import gov.nasa.worldwind.geom.Position;

import java.util.HashMap;
import java.util.Observer;

import ch.zhaw.jroute.model.businessObjects.Waypoint;
import ch.zhaw.jroute.model.util.WaypointStatusEnum;

public interface IWaypointBuilder {

	public void registerObserver(Observer obs);

	public void createWaypointFromPosition(Position waypointPosition);
	
	public void createWaypointFromExistingWaypoint(Waypoint waypoint);

	public void setStartWaypoint(WaypointStatusEnum status, Waypoint waypoint);

	public void setEndWaypoint(WaypointStatusEnum status, Waypoint waypoint);

	public HashMap<Long, Waypoint> getWaypointList();

	public Waypoint getStartWaypoint();

	public Waypoint getEndWaypoint();
	
	public void removeAllWaypoints();
}
