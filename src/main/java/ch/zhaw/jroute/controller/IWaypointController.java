package ch.zhaw.jroute.controller;

import gov.nasa.worldwind.geom.Position;

public interface IWaypointController {

	public void createNewNode(Position position);
	public void addWaypointInputListener();
	public void removeWaypointInputListener();
}