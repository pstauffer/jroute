package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.SurfaceCircle;

import java.util.ArrayList;
import java.util.List;

public class Waypoint extends SurfaceCircle {
	private int waypointID;
	private float lat;
	private float lon;
	private String name;
	private List<Waypoint> nextWaypoints = new ArrayList<Waypoint>();
	private Waypoint before;
	private double distanceToStart;
	private WaypointStatusEnum status;

	public Waypoint() {

	}

	public Waypoint(Position curPos, int i) {
		super(curPos, i);
	}

	public Waypoint(int nodeID, float lat, float lon) {
		this.waypointID = nodeID;
		this.lat = lat;
		this.lon = lon;
	}

	public int getWaypointID() {
		return waypointID;
	}

	public void setWaypointID(int nodeID) {
		this.waypointID = nodeID;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	public Waypoint(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setNext(Waypoint next) {
		this.nextWaypoints.add(next);
	}

	List<Waypoint> getNext() {
		return nextWaypoints;
	}

	public void setWaypointBefore(Waypoint before) {
		this.before = before;
	}

	public Waypoint getWaypointBefore() {
		return before;
	}

	public void setDistanceToStart(double distance) {
		this.distanceToStart = distance;
	}

	public double getDistanceToStart() {
		return distanceToStart;
	}

	public String toString() {
		return this.name;
	}

	public WaypointStatusEnum getStatus() {
		return status;
	}

	public void setStatus(WaypointStatusEnum status) {
		this.status = status;
	}

}
