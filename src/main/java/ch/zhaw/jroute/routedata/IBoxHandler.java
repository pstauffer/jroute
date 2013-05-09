package ch.zhaw.jroute.routedata;

import java.util.Set;

import ch.zhaw.jroute.model.Way;

public interface IBoxHandler {

	/**
	 * Gibt alle Wege einer Box zurueck
	 * 
	 * @param left
	 *            (is the longitude of the left side of the box)
	 * @param bottom
	 *            (is the latitude of the bottom side of the box)
	 * @param right
	 *            (is the longitude of the right side of the box)
	 * @param top
	 *            (is the latitude of the top side of the box)
	 * 
	 * @return List<Way> alle Wege und zugehoerigen Waypoints inkl. Lat und Lon
	 */
	Set<Way> getAllWays(double left, double bottom, double right, double top);
}
