package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwindx.examples.util.DirectedPath;

import java.util.ArrayList;
import java.util.List;

public class Way extends DirectedPath {
	private Waypoint start;
	private Waypoint end;
	private int wayID;
	private List<Waypoint> waypointList;
	private double distance;
	private String name;
	private List<Position> pointList = new ArrayList<Position>();

	public Way() {
		super();
	}

	public Way(int wayID, Waypoint start, Waypoint end,
			double distance) {
		super();
		this.wayID = wayID;
		this.start = start;
		this.end = end;
		this.distance = distance;
	}

	public Way(int wayID) {
		super();
		this.wayID = wayID;
	}

	public Way(int wayID, List<Waypoint> waypointList) {
		super();
		this.wayID = wayID;
		this.waypointList = waypointList;
	}


	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Waypoint getStart() {
		return start;
	}

	public Waypoint getEnd() {
		return end;
	}

	public void setStart(Waypoint start) {
		if(pointList.isEmpty()){
			pointList.add(start.getReferencePosition());
			pointList.add(start.getReferencePosition());
		}
		this.pointList.set(0, (Position) start.getCenter());
		this.setPositions(pointList);
		
		this.start = start;
	}

	public void setEnd(Waypoint end) {
		this.pointList.set(1, (Position) end.getCenter());
		this.setPositions(pointList);
		
		this.end = end;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
