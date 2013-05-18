package ch.zhaw.jroute.model.algorithm;

import java.util.List;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public interface IShortestPathAlgorithm {
	/**
	 * Gibt den kuerzesten Pfad zwischen start und end zurueck
	 * 
	 * @param start
	 * @param end
	 * @param allWays
	 *            with distance between the waypoints
	 * @return List<Way> Wege die am kuerzesten von start nach end fuehren
	 */
	List<Way> getShortestPath(Waypoint start, Waypoint end, List<Way> allWays);
}
