package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class WayBuilder extends Observable implements IWayBuilder {
	
	Map<Integer, Way> allWays = new HashMap<Integer, Way>();
	private Way activeWay = new Way();
	private int id = 0;
	
	public WayBuilder(){
		
	}

	@Override
	public void registerObserver(Observer obs) {
		this.addObserver(obs);
	}

	@Override
	public void createNewWay(Waypoint start) {
		activeWay = new Way();
		activeWay.setStart(start);
		this.setChanged();
		this.notifyObservers(activeWay);
	}
	
	public void finishWay(Waypoint end,double distance){
		activeWay.setEnd(end);
		activeWay.setName(activeWay.getStart().getName() + "-" + end.getName());
		activeWay.setWayID(id);
		activeWay.setDistance(distance);
		allWays.put(id,activeWay);
		id++;
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void moveWayEndpoint(Position newPosition) {
		this.setChanged();
		this.notifyObservers(newPosition);
	}

	public Map<Integer, Way> getAllWays() {
		return allWays;
	}

	@Override
	public void showShortestPath(List<Way> resultList) {
		for(Way resultWay : resultList){
			
		}
		
	}
}
