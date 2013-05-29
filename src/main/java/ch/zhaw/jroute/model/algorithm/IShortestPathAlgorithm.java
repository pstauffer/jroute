package ch.zhaw.jroute.model.algorithm;

import java.util.List;

import ch.zhaw.jroute.model.businessObjects.Way;
import ch.zhaw.jroute.model.businessObjects.Waypoint;

/**
 * DjikstraAlgo Interface for run the djikstra algorithm and calculate the
 * shortest way
 * 
 * @author pascal
 */
public interface IShortestPathAlgorithm {

	/**
	 * get the shortest path for route from start to end waypoint
	 * 
	 * @param start
	 * @param end
	 * @param allWays
	 *            with distance between the waypoints
	 * @return List<Way>
	 */
	List<Way> getShortestPath(Waypoint start, Waypoint end, List<Way> allWays);
}
