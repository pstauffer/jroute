package ch.zhaw.jroute.model.interfaces;

import gov.nasa.worldwind.geom.Position;

import java.util.HashMap;
import java.util.Observer;

import ch.zhaw.jroute.model.businessObjects.Waypoint;
import ch.zhaw.jroute.model.util.WaypointStatusEnum;

/**
 * Represent the functionality which a WaypointBuilder must implement
 * @author yk
 */
public interface IWaypointBuilder {

	/**
	 * register an observer to get notified with new ways
	 * @param obs
	 */
	public void registerObserver(Observer obs);

	/**
	 * Creates a new Waypoint from a lat/lon position
	 * @param waypointPosition Lat/Long position
	 */
	public void createWaypointFromPosition(Position waypointPosition);
	
	/**
	 * Creates a new Waypoint from an exsisting one
	 * @param waypoint
	 */
	public void createWaypointFromExistingWaypoint(Waypoint waypoint);

	/**
	 * Marks an existing waypoint as start of a route
	 * @param status of the waypoint
	 * @param waypoint the waypoint to set as start
	 */
	public void setStartWaypoint(WaypointStatusEnum status, Waypoint waypoint);
	
	/**
	 * Marks an existing waypoint as end of a route
	 * @param status of the waypoint
	 * @param waypoint the waypoint to set as end
	 */
	public void setEndWaypoint(WaypointStatusEnum status, Waypoint waypoint);

	/**
	 * Returns all created Waypoints
	 * @return all waypoints
	 */
	public HashMap<Long, Waypoint> getWaypointList();

	/**
	 * returns the waypoint which is set as start
	 * @return start waypoint
	 */
	public Waypoint getStartWaypoint();

	/**
	 * returns the waypoint which is set as ends
	 * @return end waypoint
	 */
	public Waypoint getEndWaypoint();
	
	/**
	 * removes all waypoints from the builder
	 */
	public void removeAllWaypoints();
}
