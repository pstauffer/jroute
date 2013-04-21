package ch.zhaw.jroute.model.algorithm;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class DjikstraAlgo {
	static List<Way> allWays = new ArrayList<Way>();
	static List<Waypoint> allPoints = new ArrayList<Waypoint>();
	List<Waypoint> greenWaypoints = new ArrayList<Waypoint>();
	List<Waypoint> redWaypoints = new ArrayList<Waypoint>();

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

		// allPoints.add(A);
		// allPoints.add(B);
		// allPoints.add(C);
		// allPoints.add(D);
		// allPoints.add(E);
		// allPoints.add(F);
		// allPoints.add(G);
		// allPoints.add(H);
		// allPoints.add(I);

		Way way1 = new Way(1, A, B, 10);
		Way way2 = new Way(2, A, C, 10);
		Way way3 = new Way(3, A, D, 10);
		Way way4 = new Way(4, B, A, 10);
		Way way5 = new Way(5, B, G, 10);
		Way way6 = new Way(6, C, F, 10);
		Way way7 = new Way(7, D, I, 10);
		Way way8 = new Way(8, D, H, 10);

		allWays.add(way1);
		allWays.add(way2);
		allWays.add(way3);
		allWays.add(way4);
		allWays.add(way5);
		allWays.add(way6);
		allWays.add(way7);
		allWays.add(way8);

		for (Waypoint blubb : getConnectedWaypoints(A)) {
			System.out.println(blubb.getName());
		}

	}

	static List<Waypoint> getConnectedWaypoints(Waypoint waypoint) {
		List<Waypoint> connectedWaypoints = new ArrayList<Waypoint>();
		for (Way ways : allWays) {
			if (ways.getStart().equals(waypoint)) {
				connectedWaypoints.add(ways.getEnd());
			}
			if (ways.getEnd().equals(waypoint)) {
				connectedWaypoints.add(ways.getStart());
			}
		}
		return connectedWaypoints;

	}
}
