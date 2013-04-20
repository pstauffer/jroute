package ch.zhaw.jroute.model;

import java.util.List;

public class Way {
	private Waypoint waypoint1;
	private Waypoint waypoint2;
	private int wayID;
	private List<Waypoint> waypointList;
	double distance;

	public Way() {

	}

	public Way(int wayID, Waypoint waypoint1, Waypoint waypoint2,
			double distance) {
		this.wayID = wayID;
		this.waypoint1 = waypoint1;
		this.waypoint2 = waypoint2;
		this.distance = distance;
	}

	public Way(int wayID) {
		this.wayID = wayID;
	}

	public Way(int wayID, List<Waypoint> waypointList) {
		this.wayID = wayID;
		this.waypointList = waypointList;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Waypoint getWaypoint1() {
		return waypoint1;
	}

	public Waypoint getWaypoint2() {
		return waypoint2;
	}

	public void setWaypoint1(Waypoint waypoint1) {
		this.waypoint1 = waypoint1;
	}

	public void setWaypoint2(Waypoint waypoint2) {
		this.waypoint2 = waypoint2;
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
