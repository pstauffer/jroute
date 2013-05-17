package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;

import java.util.List;
import java.util.Map;
import java.util.Observer;

public interface IWayBuilder {
	public void registerObserver(Observer obs);
	
	public void createNewWay(Waypoint start);
	
	public void addExistingWay(Way existingWay);
	
	public void moveWayEndpoint(Position newPosition);
	
	public void finishWay(Waypoint end, double distance);

	public Map<Integer, Way> getAllWays();
	
	public void showShortestPath(List<Way> resultList);
}


