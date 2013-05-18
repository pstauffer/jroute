package ch.zhaw.jroute.model.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.WayStatusEnum;
import ch.zhaw.jroute.model.Waypoint;

public class DjikstraAlgo implements IShortestPathAlgorithm {
	private Set<Waypoint> allPointsList;
	private List<Waypoint> redWaypointList;
	private Set<Waypoint> greenWaypointList;
	private List<Waypoint> shortestWaypointList;
	private List<Way> shortestWayList;

	public DjikstraAlgo() {
		allPointsList = new HashSet<Waypoint>();
		redWaypointList = new ArrayList<Waypoint>();
		greenWaypointList = new HashSet<Waypoint>();
		shortestWaypointList = new ArrayList<Waypoint>();
		shortestWayList = new ArrayList<Way>();
	}

	void preparation(Waypoint startWaypoint, List<Way> allWaysList) {

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

	void calculateGraph(Waypoint startWaypoint, List<Way> allWaysList) {
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

	boolean checkUnreachableWaypoint(Waypoint waypoint) {

		if (waypoint.getDistanceToStart() == Integer.MAX_VALUE) {
			return true;
		}
		return false;

	}

	@Override
	public List<Way> getShortestPath(Waypoint startWaypoint,
			Waypoint endWaypoint, List<Way> allWays) {

		createCopysFromWays(allWays);
		preparation(startWaypoint, allWays);
		calculateGraph(startWaypoint, allWays);
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

		return shortestWayList;
	}

	public void createCopysFromWays(List<Way> allWays) {
		List<Way> copyOfAllWays = new ArrayList<Way>();
		copyOfAllWays.addAll(allWays);

		// System.out.println(allWays.get(0).getEnd().getDistanceToStart());
		// System.out.println(copyOfAllWays.get(0).getEnd().getDistanceToStart());

		allWays.get(0).getEnd().setDistanceToStart(3.0);

		// System.out.println(allWays.get(0).getEnd().getDistanceToStart());
		// System.out.println(copyOfAllWays.get(0).getEnd().getDistanceToStart());

		// List<Waypoint> copyOfAllWaypoints = new ArrayList<Waypoint>();
		// copyOfAllWays.get(0).getWaypointList().addAll(copyOfAllWaypoints);
	}

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

	private Comparator<Waypoint> sortByDistance = new Comparator<Waypoint>() {
		public int compare(Waypoint point1, Waypoint point2) {
			return (int) (point1.getDistanceToStart() - point2
					.getDistanceToStart());
		}
	};

	private Waypoint getShortestDistanceRedWaypoint(List<Waypoint> redWaypoints) {
		Collections.sort(redWaypoints, sortByDistance);
		return redWaypoints.get(0);
	}

	private List<Way> getConnectedBackwardWays(Waypoint waypoint, List<Way> ways) {
		List<Way> connectedBackwardWays = new ArrayList<Way>();
		for (Way way : ways) {
			if (way.getEnd().equals(waypoint)) {
				connectedBackwardWays.add(way);
			}
		}
		return connectedBackwardWays;
	}

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
