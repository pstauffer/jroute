package ch.zhaw.jroute.model;

import java.util.List;

public class Way {
	private int wayID;
	private List<Waypoint> waypointList;

	public Way() {

	}

	public Way(int wayID) {
		this.wayID = wayID;
	}

	public Way(int wayID, List<Waypoint> waypointList) {
		this.wayID = wayID;
		this.waypointList = waypointList;
	}

	public int getWayID() {
		return wayID;
	}

	public void setWayID(int wayID) {
		this.wayID = wayID;
	}

	public List<Waypoint> getWaypointList() {
		return waypointList;
	}

	public void setWaypointList(List<Waypoint> waypointList) {
		this.waypointList = waypointList;
	}

}
