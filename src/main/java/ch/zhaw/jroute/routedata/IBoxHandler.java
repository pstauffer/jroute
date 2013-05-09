package ch.zhaw.jroute.routedata;

import java.util.Set;

import ch.zhaw.jroute.model.Way;

public interface IBoxHandler {

	/**
	 * Gibt alle Wege einer Box zurueck
	 * 
	 * @param left
	 * @param bottom
	 * @param right
	 * @param top
	 * 
	 * @return List<Way> alle Wege und zugehoerigen Waypoints inkl. Lat und Lon
	 */
	Set<Way> getAllWays(double left, double bottom, double right, double top);
}
