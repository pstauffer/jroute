package ch.zhaw.jroute.model.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import ch.zhaw.jroute.model.businessObjects.Way;
import ch.zhaw.jroute.model.businessObjects.Waypoint;
import ch.zhaw.jroute.model.util.WayStatusEnum;

/**
 * DjikstraAlgo Class for run the djikstra algorithm and calculate the shortest
 * way
 * 
 * defined through Interface IShortestPathAlgorithm
 * 
 * @author pascal
 */
public class DjikstraAlgo implements IShortestPathAlgorithm {
	private Set<Waypoint> allPointsList;
	private List<Waypoint> redWaypointList;
	private Set<Waypoint> greenWaypointList;
	private List<Waypoint> shortestWaypointList;
	private List<Way> shortestWayList;
	private static Logger logger = Logger.getLogger("org.apache.log4j");

	/**
	 * constructor for djikstra algorithm class
	 */
	public DjikstraAlgo() {
		allPointsList = new HashSet<Waypoint>();
		redWaypointList = new ArrayList<Waypoint>();
		greenWaypointList = new HashSet<Waypoint>();
		shortestWaypointList = new ArrayList<Waypoint>();
		shortestWayList = new ArrayList<Way>();
	}

	public void cleanUp() {
		allPointsList.clear();
		redWaypointList.clear();
		greenWaypointList.clear();
		shortestWaypointList.clear();
		shortestWayList.clear();
	}

	/**
	 * prepare all waypoints and ways for the djikstra algorithm
	 * 
	 * @param startWaypoint
	 * @param allWaysList
	 */
	private void preparation(Waypoint startWaypoint, List<Way> allWaysList) {

		for (Way way : allWaysList) {
			Waypoint from = way.getStart();
			Waypoint to = way.getEnd();
			allPointsList.add(from);
			allPointsList.add(to);
			from.setDistanceToStart(Integer.MAX_VALUE);
			to.setDistanceToStart(Integer.MAX_VALUE);
			from.setWaypointBefore(null);
			to.setWaypointBefore(null);
		}

		// preparation for run
		greenWaypointList.clear();
		redWaypointList.clear();

		// preparation for startpoint
		redWaypointList.add(startWaypoint);
		startWaypoint.setDistanceToStart(0);
		startWaypoint.setWaypointBefore(startWaypoint);

	}

	/**
	 * calculate the whole graph for djikstra algorithm
	 * 
	 * @param startWaypoint
	 * @param allWaysList
	 */
	private void calculateGraph(Waypoint startWaypoint, List<Way> allWaysList) {
		Waypoint nextWaypointForCalculating = startWaypoint;

		// worst-case => dijkstra (points * points)
		int runs = allPointsList.size() * allPointsList.size();

		for (int i = 0; i < runs; i++) {

			if (redWaypointList.isEmpty()) {
				break;
			}

			nextWaypointForCalculating = getShortestDistanceRedWaypoint(redWaypointList);

			List<Way> nextForwardWaysForCalculating = getConnectedForwardWays(
					nextWaypointForCalculating, allWaysList);

			for (Way nextWay : nextForwardWaysForCalculating) {

				Waypoint connectedForwardWaypoint = nextWay.getEnd();
				if (!greenWaypointList.contains(connectedForwardWaypoint)) {
					redWaypointList.add(connectedForwardWaypoint);
				}

				double connectedForwardDistance = nextWay.getDistance();
				double connectedForwardWaypointDistanceToStart = connectedForwardWaypoint
						.getDistanceToStart();
				double additionDistance = (nextWaypointForCalculating
						.getDistanceToStart() + connectedForwardDistance);
				if (additionDistance < connectedForwardWaypointDistanceToStart) {
					connectedForwardWaypoint
							.setWaypointBefore(nextWaypointForCalculating);
					connectedForwardWaypoint
							.setDistanceToStart(additionDistance);
				}
				redWaypointList.remove(nextWaypointForCalculating);
				greenWaypointList.add(nextWaypointForCalculating);
			}

			List<Way> nextBackwardWaysForCalculating = getConnectedBackwardWays(
					nextWaypointForCalculating, allWaysList);

			for (Way nextWay : nextBackwardWaysForCalculating) {

				Waypoint connectedBackwardWaypoint = nextWay.getStart();
				if (!greenWaypointList.contains(connectedBackwardWaypoint)) {
					redWaypointList.add(connectedBackwardWaypoint);
				}

				double connectedBackwardDistance = nextWay.getDistance();
				double connectedBackwardWaypointDistanceToStart = connectedBackwardWaypoint
						.getDistanceToStart();
				double additionDistance = (nextWaypointForCalculating
						.getDistanceToStart() + connectedBackwardDistance);
				if (additionDistance < connectedBackwardWaypointDistanceToStart) {
					connectedBackwardWaypoint
							.setWaypointBefore(nextWaypointForCalculating);
					connectedBackwardWaypoint
							.setDistanceToStart(additionDistance);
				}
				redWaypointList.remove(nextWaypointForCalculating);
				greenWaypointList.add(nextWaypointForCalculating);
			}

		}
	}

	/**
	 * check, if the waypoint is reachable
	 * 
	 * @param waypoint
	 * @return true or false
	 */
	private boolean checkUnreachableWaypoint(Waypoint waypoint) {

		if (waypoint.getDistanceToStart() == Integer.MAX_VALUE) {
			return true;
		}
		return false;

	}

	/**
	 * defined through the interface
	 */
	@Override
	public List<Way> getShortestPath(Waypoint startWaypoint,
			Waypoint endWaypoint, List<Way> allWays) {

		long startShortestPathMethodTime = System.nanoTime();

		cleanUp();

		long startPreparationTime = System.nanoTime();
		preparation(startWaypoint, allWays);
		long endPreparationTime = System.nanoTime();

		long startCalculateTime = System.nanoTime();
		calculateGraph(startWaypoint, allWays);
		long endCalculateTime = System.nanoTime();

		setWayStatus(allWays);

		if (checkUnreachableWaypoint(endWaypoint)) {
			throw new IllegalArgumentException("EndWaypoint is not reachable: "
					+ endWaypoint);
		}

		shortestWaypointList.add(endWaypoint);
		Waypoint tempPoint = endWaypoint;

		for (int b = 0; b < greenWaypointList.size(); b++) {

			// stop the loop, if the beforePoint is the startPoint
			if (tempPoint.getWaypointBefore().equals(startWaypoint)) {
				shortestWaypointList.add(startWaypoint);
				Way newWay = getWay(startWaypoint.getWaypointBefore(),
						tempPoint, allWays);
				shortestWayList.add(newWay);
				newWay.setStatus(WayStatusEnum.result);
				break;
			}

			// add the beforePoint to the list
			shortestWaypointList.add(tempPoint.getWaypointBefore());
			Way newWay = getWay(tempPoint, tempPoint.getWaypointBefore(),
					allWays);

			shortestWayList.add(newWay);
			newWay.setStatus(WayStatusEnum.result);

			// set the beforePoint as activePoint for the next looping
			tempPoint = tempPoint.getWaypointBefore();

		}

		Collections.reverse(shortestWaypointList);
		Collections.reverse(shortestWayList);

		long endShortestPathMethodTime = System.nanoTime();

		// sysout for debugging
		logger.debug("whole shortestPath Method Time: "
				+ (endShortestPathMethodTime - startShortestPathMethodTime)
				+ " ns");
		logger.debug("time for preperation: "
				+ (endPreparationTime - startPreparationTime));
		logger.debug("time for calculate: "
				+ (endCalculateTime - startCalculateTime));

		return shortestWayList;
	}

	/**
	 * set the correct status for all ways in the list
	 * 
	 * @param allWays
	 */
	private void setWayStatus(List<Way> allWays) {

		for (Way way : allWays) {
			Waypoint point1 = way.getStart();
			Waypoint point2 = way.getEnd();

			if (checkUnreachableWaypoint(point1)
					|| checkUnreachableWaypoint(point2)) {
				way.setStatus(WayStatusEnum.undefined);
			} else {
				way.setStatus(WayStatusEnum.noResult);
			}
		}

	}

	/**
	 * search the way, which matches with the input waypoints
	 * 
	 * @param point1
	 * @param point2
	 * @param ways
	 * @return
	 */
	private Way getWay(Waypoint point1, Waypoint point2, List<Way> ways) {
		for (Way way : ways) {
			if ((way.getStart().equals(point1) && way.getEnd().equals(point2))
					|| (way.getStart().equals(point2) && way.getEnd().equals(
							point1))) {
				return way;
			}
		}
		return null;
	}

	// /**
	// * defines the comparator for the waypoint sort by distance
	// */
	// private Comparator<Waypoint> sortByDistance = new Comparator<Waypoint>()
	// {
	// public int compare(Waypoint point1, Waypoint point2) {
	// return (int) (point1.getDistanceToStart() - point2
	// .getDistanceToStart());
	// }
	// };

	/** * defines the comparator for the waypoint sort by distance */
	private Comparator<Waypoint> sortByDistance = new Comparator<Waypoint>() {
		public int compare(Waypoint point1, Waypoint point2) {
			if (point1.getDistanceToStart() < point2.getDistanceToStart())
				return -1;
			if (point1.getDistanceToStart() > point2.getDistanceToStart())
				return 1;
			return 0;
		}
	};

	/**
	 * sort the red waypoints by distance and give the waypoint back with the
	 * shortest distance
	 * 
	 * @param redWaypoints
	 * @return
	 */
	private Waypoint getShortestDistanceRedWaypoint(List<Waypoint> redWaypoints) {
		Collections.sort(redWaypoints, sortByDistance);
		return redWaypoints.get(0);
	}

	/**
	 * get all backward ways from a waypoint
	 * 
	 * @param waypoint
	 * @param ways
	 * @return List<Way
	 */
	private List<Way> getConnectedBackwardWays(Waypoint waypoint, List<Way> ways) {
		List<Way> connectedBackwardWays = new ArrayList<Way>();
		for (Way way : ways) {
			if (way.getEnd().equals(waypoint)) {
				connectedBackwardWays.add(way);
			}
		}
		return connectedBackwardWays;
	}

	/**
	 * get all forwarding ways from a waypoint
	 * 
	 * @param waypoint
	 * @param ways
	 * @return List<Way>
	 */
	private List<Way> getConnectedForwardWays(Waypoint waypoint, List<Way> ways) {
		List<Way> connectedForwardWays = new ArrayList<Way>();
		for (Way way : ways) {
			if (way.getStart().equals(waypoint)) {
				connectedForwardWays.add(way);
			}
		}
		return connectedForwardWays;
	}

}
