package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class WayBuilder extends Observable implements IWayBuilder {
	
	private List<Way> allWays = new ArrayList<Way>();
	
	public WayBuilder(){
		
	}

	@Override
	public void registerObserver(Observer obs) {
		this.addObserver(obs);
	}

	@Override
	public void createNewWay(Waypoint start) {
		Way newWay = new Way();
		newWay.setStart(start);
		this.setChanged();
		this.notifyObservers(newWay);
	}

	@Override
	public void moveWayEndpoint(Position newPosition) {
		this.setChanged();
		this.notifyObservers(newPosition);
	}

}
