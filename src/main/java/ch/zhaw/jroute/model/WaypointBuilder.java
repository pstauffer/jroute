package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import ch.zhaw.jroute.config.ConfigHandler;
import ch.zhaw.jroute.model.businessObjects.Waypoint;
import ch.zhaw.jroute.model.interfaces.IWaypointBuilder;
import ch.zhaw.jroute.model.util.WaypointStatusEnum;

/**
 * This class handles all the waypoints produced in the GUI or the data
 * import, updates the registered layers
 * @author yk
 */
public class WaypointBuilder extends Observable implements IWaypointBuilder {

	private HashMap<Long, Waypoint> waypointList = new HashMap<Long, Waypoint>();
	private int id = 0;
	private Waypoint startWaypoint;
	private Waypoint endWaypoint;

	@Override
	public void createWaypointFromPosition(Position waypointPosition) {
		
		int radius = Integer.parseInt(ConfigHandler.getInstance().getConfig("WAYPOINTRADIUS"));

		if (waypointPosition == null) {
			return;
		}

		Waypoint newWaypoint = new Waypoint(waypointPosition, radius);

		newWaypoint.setName(Integer.toString(id));

		newWaypoint.setWaypointID(id);

		newWaypoint.setStatus(WaypointStatusEnum.undefined);

		waypointList.put((long) id, newWaypoint);

		id++;

		this.setChanged();
		this.notifyObservers(newWaypoint);

	}
	
	@Override
	public void createWaypointFromExistingWaypoint(Waypoint waypoint) 
	{
		int radius = Integer.parseInt(ConfigHandler.getInstance().getConfig("WAYPOINTRADIUS"));
		waypoint.setRadius(radius);
		waypoint.setName(String.valueOf(waypoint.getWaypointID()));

		waypoint.setStatus(WaypointStatusEnum.undefined);

		waypointList.put((long) waypoint.getWaypointID(), waypoint);

		this.setChanged();
		this.notifyObservers(waypoint);
		
	}

	@Override
	public void registerObserver(Observer obs) {
		this.addObserver(obs);
	}

	@Override
	public void setStartWaypoint(WaypointStatusEnum status, Waypoint waypoint) {
		if (status == WaypointStatusEnum.undefined) {
			startWaypoint = null;
		} else if (startWaypoint == null) {
			startWaypoint = waypoint;
		} else {
			startWaypoint.setStatus(WaypointStatusEnum.undefined);
			waypointList.put(startWaypoint.getWaypointID(), startWaypoint);
			this.setChanged();
			this.notifyObservers(startWaypoint);
			startWaypoint = waypoint;
		}

		waypoint.setStatus(status);
		waypointList.put(waypoint.getWaypointID(), waypoint);

		this.setChanged();
		this.notifyObservers(waypoint);
	}

	@Override
	public void setEndWaypoint(WaypointStatusEnum status, Waypoint waypoint) {
		if (status == WaypointStatusEnum.undefined) {
			endWaypoint = null;
		} else if (endWaypoint == null) {
			endWaypoint = waypoint;
		} else {
			endWaypoint.setStatus(WaypointStatusEnum.undefined);
			waypointList.put(endWaypoint.getWaypointID(), endWaypoint);
			this.setChanged();
			this.notifyObservers(endWaypoint);
			endWaypoint = waypoint;
		}

		waypoint.setStatus(status);
		waypointList.put(waypoint.getWaypointID(), waypoint);

		this.setChanged();
		this.notifyObservers(waypoint);
	}

	@Override
	public HashMap<Long, Waypoint> getWaypointList() {
		return waypointList;
	}

	@Override
	public Waypoint getStartWaypoint() {
		return startWaypoint;
	}

	@Override
	public Waypoint getEndWaypoint() {
		return endWaypoint;
	}

	@Override
	public void removeAllWaypoints() {
		waypointList.clear();
		id = 0;
		startWaypoint = null;
		endWaypoint = null;
	}

}
