package ch.zhaw.jroute.model.interfaces;

import gov.nasa.worldwind.geom.Position;

import java.util.List;
import java.util.Map;
import java.util.Observer;

import ch.zhaw.jroute.model.businessObjects.Way;
import ch.zhaw.jroute.model.businessObjects.Waypoint;

/**
 * Represent the functionality which a WayBuilder must implement
 * @author yk
 */
public interface IWayBuilder {
	/**
	 * register an observer to get notified with new ways
	 * @param obs
	 */
	public void registerObserver(Observer obs);
	
	/**
	 * Create a new Way from scratch 
	 * @param start waypoint of the way
	 */
	public void createNewWay(Waypoint start);
	
	/**
	 * Adds an already existing way to the view and the model
	 * @param existingWay
	 */
	public void addExistingWay(Way existingWay);
	
	/**
	 * moves the endpoint of a way, used to draw a way by hand
	 * @param newPosition
	 */
	public void moveWayEndpoint(Position newPosition);
	
	/**
	 * finishes a way created by hand
	 * @param end waypoint of the way
	 * @param distance from start to end
	 */
	public void finishWay(Waypoint end, double distance);

	/**
	 * All ways are stored in the model, this method returns them all
	 * @return all the buildet ways
	 */
	public Map<Integer, Way> getAllWays();
	
	/**
	 * this updates the view with the result of the shortest path algorithm
	 * @param resultList the ways which represent to shortest path between start and end
	 */
	public void showShortestPath(List<Way> resultList);
	
	/**
	 * removes all ways from the model
	 */
	public void removeAllWays();
}


