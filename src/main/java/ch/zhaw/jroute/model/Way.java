package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.SurfacePolyline;

import java.util.ArrayList;
import java.util.List;

public class Way extends Path {
	private Waypoint start;
	private Waypoint end;
	private long wayID;
	private List<Waypoint> waypointList = new ArrayList<Waypoint>();
	private List<Waypoint> splitPointList = new ArrayList<Waypoint>();
	private double distance;
	private String name;
	private WayStatusEnum status;

	public Way() {
		super();
	}

	public Way(long wayID, Waypoint start, Waypoint end, double distance) {
		super();
		this.wayID = wayID;
		this.start = start;
		this.end = end;
		this.distance = distance;
	}

	public Way(long wayID) {
		super();
		this.wayID = wayID;
	}

	public Way(long wayID, List<Waypoint> waypointList) {
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
		this.start = start;
	}

	public void setEnd(Waypoint end) {
		this.end = end;
	}

	public long getWayID() {
		return wayID;
	}

	public void setWayID(long wayID) {
		this.wayID = wayID;
	}

	public List<Waypoint> getWaypointList() {
		return waypointList;
	}

	public void setWaypointList(List<Waypoint> waypointList) {
		this.waypointList = waypointList;
	}

	public void addWaypoint(Waypoint point) {
		waypointList.add(point);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WayStatusEnum getStatus() {
		return status;
	}

	public void setStatus(WayStatusEnum status) {
		this.status = status;
	}

	public List<Waypoint> getSplitPointList() {
		return splitPointList;
	}

	public void setSplitPointList(List<Waypoint> splitPointList) {
		this.splitPointList = splitPointList;
	}
	
	public void addSplitPoint(Waypoint waypoint){
		this.splitPointList.add(waypoint);
	}
}
