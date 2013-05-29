package ch.zhaw.jroute.controller.interfaces;

import gov.nasa.worldwind.geom.Position;

public interface IWaypointController {

	public void createNewNode(Position position);
	public void cleanWaypoints();
	public void addWaypointInputListener();
	public void removeWaypointInputListener();
	public void addStartWaypointInputListener();
	public void addEndWaypointInputListener();
	public void removeStartWaypointInputListener();
	public void removeEndWaypointInputListener();
}