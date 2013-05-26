package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.SurfaceCircle;

import java.util.ArrayList;
import java.util.List;

public class Waypoint extends SurfaceCircle {
	private long waypointID;
	private double lat;
	private double lon;
	private String name;
	private List<Waypoint> nextWaypoints = new ArrayList<Waypoint>();
	private Waypoint before;
	private double distanceToStart;
	private WaypointStatusEnum status;
	private List<Way> addedWays = new ArrayList<Way>();

	public Waypoint() {

	}

	public Waypoint(long waypointID) {
		this.waypointID = waypointID;
	}

	public Waypoint(Position curPos, int i) {
		super(curPos, i);
	}

	public Waypoint(long nodeID, double lat, double lon) {
		this.waypointID = nodeID;
		this.lat = lat;
		this.lon = lon;
	}

	public long getWaypointID() {
		return waypointID;
	}

	public void setWaypointID(long nodeID) {
		this.waypointID = nodeID;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
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

	public List<Way> getAddedWays() {
		return addedWays;
	}

	public void setAddedWays(List<Way> addedWays) {
		this.addedWays = addedWays;
	}
	
	public void addWay(Way way){
		this.addedWays.add(way);
	}
}
