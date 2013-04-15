package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.SurfaceCircle;

import java.util.ArrayList;
import java.util.List;

public class Waypoint extends SurfaceCircle {
	int nodeID;
	float lat;
	float lon;
	String name;
	List<Waypoint> next = new ArrayList<Waypoint>();
	List<Connector> edges = new ArrayList<Connector>();
	Waypoint before;
	double distanceToStart;
	boolean status;

	public Waypoint() {

	}

	public Waypoint(Position curPos, int i) {
		super(curPos, i);
	}

	public Waypoint(int nodeID, float lat, float lon) {
		this.nodeID = nodeID;
		this.lat = lat;
		this.lon = lon;
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
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

	public void setEdge(Connector edge) {
		this.edges.add(edge);
	}

	public List<Connector> getEdge() {
		return edges;
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
		this.next.add(next);
	}

	List<Waypoint> getNext() {
		return next;
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

	public double getDistance() {
		return distanceToStart;
	}

	public String toString() {
		return this.name;
	}

}
