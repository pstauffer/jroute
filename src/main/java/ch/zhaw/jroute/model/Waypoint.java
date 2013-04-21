package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.SurfaceCircle;

import java.util.ArrayList;
import java.util.List;

public class Waypoint extends SurfaceCircle {
	int waypointID;
	float lat;
	float lon;
	String name;
	List<Waypoint> nextWaypoints = new ArrayList<Waypoint>();
	Waypoint before;
	double distanceToStart;
	boolean status;

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

	public int getNodeID() {
		return waypointID;
	}

	public void setNodeID(int nodeID) {
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

	public boolean getStatus() {
		return status;
	}

	public void setGreenStatus() {
		status = true;
	}

	public void setRedStatus() {
		status = false;
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

	void setBefore(Waypoint before) {
		this.before = before;
	}

	Waypoint getBefore() {
		return before;
	}

	void setDistance(double distance) {
		this.distanceToStart = distance;
	}

	public double getDistanceToStart() {
		return distanceToStart;
	}

	public String toString() {
		return this.name;
	}

}
