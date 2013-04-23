package ch.zhaw.jroute.model.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class DjikstraAlgo {
	static List<Way> allWays = new ArrayList<Way>();
	static List<Waypoint> allPoints = new ArrayList<Waypoint>();
	static List<Waypoint> redWaypoints = new ArrayList<Waypoint>();
	static HashSet<Waypoint> greenWaypoints = new HashSet<Waypoint>();

	public static void main(String[] args) {

		Waypoint A = new Waypoint("A");
		Waypoint B = new Waypoint("B");
		Waypoint C = new Waypoint("C");
		Waypoint D = new Waypoint("D");
		Waypoint E = new Waypoint("E");
		Waypoint F = new Waypoint("F");
		Waypoint G = new Waypoint("G");
		Waypoint H = new Waypoint("H");
		Waypoint I = new Waypoint("I");

		// set startpoint
		Waypoint startWaypoint = A;

		allPoints.add(A);
		allPoints.add(B);
		allPoints.add(C);
		allPoints.add(D);
		allPoints.add(E);
		allPoints.add(F);
		allPoints.add(G);
		allPoints.add(H);
		allPoints.add(I);

		Way way1 = new Way(1, A, B, 2);
		Way way2 = new Way(2, A, C, 5);
		Way way3 = new Way(3, A, D, 7);
		Way way4 = new Way(4, B, E, 8);
		Way way5 = new Way(5, D, H, 6);
		Way way6 = new Way(6, C, F, 15);
		Way way7 = new Way(7, E, A, 11);
		Way way8 = new Way(8, B, G, 1);
		Way way9 = new Way(9, B, H, 6);

		allWays.add(way1);
		allWays.add(way2);
		allWays.add(way3);
		allWays.add(way4);
		allWays.add(way5);
		allWays.add(way6);
		allWays.add(way7);
		allWays.add(way8);
		allWays.add(way9);

		// preparation for run
		greenWaypoints.clear();
		redWaypoints.clear();
		Waypoint nextWaypoint;

		// preparation for all points
		for (Waypoint all : allPoints) {
			all.setDistanceToStart(Integer.MAX_VALUE);
			all.setWaypointBefore(null);
		}

		// preparation for startpoint
		greenWaypoints.add(startWaypoint);
		startWaypoint.setDistanceToStart(0);
		startWaypoint.setWaypointBefore(startWaypoint);

		// worst-case => dijkstra (points * points)
		int runs = allPoints.size() * allPoints.size();

		for (int i = 0; i < runs; i++) {

			if (i == 0) {
				nextWaypoint = startWaypoint;
			} else {
				nextWaypoint = getShortestDistanceRedWaypoint();
			}

			for (Way nextWay : getConnectedForwardWays(nextWaypoint)) {

				Waypoint connectedForwardWaypoint = nextWay.getEnd();
				if (!greenWaypoints.contains(connectedForwardWaypoint)) {
					redWaypoints.add(connectedForwardWaypoint);
				}

				double connectedForwardDistance = nextWay.getDistance();
				double connectedForwardWaypointDistanceToStart = connectedForwardWaypoint
						.getDistanceToStart();
				double additionDistance = (nextWaypoint.getDistanceToStart() + connectedForwardDistance);
				if (additionDistance < connectedForwardWaypointDistanceToStart) {
					connectedForwardWaypoint.setWaypointBefore(nextWaypoint);
					connectedForwardWaypoint
							.setDistanceToStart(additionDistance);
				}
				redWaypoints.remove(nextWaypoint);
				greenWaypoints.add(nextWaypoint);
			}

			for (Way nextWay : getConnectedBackwardWays(nextWaypoint)) {

				Waypoint connectedBackwardWaypoint = nextWay.getStart();
				if (!greenWaypoints.contains(connectedBackwardWaypoint)) {
					redWaypoints.add(connectedBackwardWaypoint);
				}

				double connectedBackwardDistance = nextWay.getDistance();
				double connectedBackwardWaypointDistanceToStart = connectedBackwardWaypoint
						.getDistanceToStart();
				double additionDistance = (nextWaypoint.getDistanceToStart() + connectedBackwardDistance);
				if (additionDistance < connectedBackwardWaypointDistanceToStart) {
					connectedBackwardWaypoint.setWaypointBefore(nextWaypoint);
					connectedBackwardWaypoint
							.setDistanceToStart(additionDistance);
				}
				redWaypoints.remove(nextWaypoint);
				greenWaypoints.add(nextWaypoint);
			}

		}

		for (Waypoint points : greenWaypoints) {
			System.out.println("point: " + points.getName() + " vorgaenger: "
					+ points.getWaypointBefore() + " distanz: "
					+ points.getDistanceToStart());
		}

		ArrayList<Waypoint> shortestPath = new ArrayList<Waypoint>();
		Waypoint finish = G;
		shortestPath.add(finish);

		System.out.println("get shortest path from " + startWaypoint.getName()
				+ " to " + finish.getName() + " with distance: "
				+ finish.getDistanceToStart() + "");

		for (int b = 0; b < greenWaypoints.size(); b++) {

			// stop the loop, if the beforePoint is the startPoint
			if (finish.getWaypointBefore().equals(startWaypoint)) {
				shortestPath.add(startWaypoint);
				break;
			}

			// add the beforePoint to the list
			shortestPath.add(finish.getWaypointBefore());

			// set the beforePoint as activePoint for the next looping
			Waypoint next = finish.getWaypointBefore();
			finish = next;

		}

		Collections.reverse(shortestPath);

		for (Waypoint path : shortestPath) {
			System.out.println(path.getName());
		}

	}

	static Comparator<Waypoint> sortByDistance = new Comparator<Waypoint>() {
		public int compare(Waypoint point1, Waypoint point2) {
			return (int) (point1.getDistanceToStart() - point2
					.getDistanceToStart());
		}
	};

	static Waypoint getShortestDistanceRedWaypoint() {
		Collections.sort(redWaypoints, sortByDistance);
		if (redWaypoints.isEmpty()) {
			return null;
		} else {
			return redWaypoints.get(0);
		}
	}

	static List<Way> getConnectedBackwardWays(Waypoint waypoint) {
		List<Way> connectedBackwardWays = new ArrayList<Way>();
		for (Way ways : allWays) {
			if (ways.getEnd().equals(waypoint)) {
				connectedBackwardWays.add(ways);
			}
		}
		return connectedBackwardWays;
	}

	static List<Way> getConnectedForwardWays(Waypoint waypoint) {
		List<Way> connectedForwardWays = new ArrayList<Way>();
		for (Way ways : allWays) {
			if (ways.getStart().equals(waypoint)) {
				connectedForwardWays.add(ways);
			}
		}
		return connectedForwardWays;
	}

}
