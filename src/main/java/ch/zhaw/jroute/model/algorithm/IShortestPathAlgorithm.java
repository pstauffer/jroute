package ch.zhaw.jroute.model.algorithm;

import java.util.List;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public interface IShortestPathAlgorithm {
	/**
	 * Gibt den kürzesten Pfad zwischen start und end zurück
	 * 
	 * @param start
	 * @param end
	 * @param allWays
	 *            with distance between the waypoints
	 * @return List<Way> Wege die am kürzesten von start nach end führen
	 */
	public List<Way> getShortestPath(Waypoint start, Waypoint end,
			List<Way> allWays);
}
