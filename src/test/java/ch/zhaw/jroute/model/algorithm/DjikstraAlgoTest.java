package ch.zhaw.jroute.model.algorithm;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class DjikstraAlgoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList<Way> allWays = new ArrayList<Way>();

		Waypoint A = new Waypoint("A");
		Waypoint B = new Waypoint("B");
		Waypoint C = new Waypoint("C");
		Waypoint D = new Waypoint("D");
		Waypoint E = new Waypoint("E");
		Waypoint F = new Waypoint("F");
		Waypoint G = new Waypoint("G");
		Waypoint H = new Waypoint("H");
		Waypoint I = new Waypoint("I");

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = G;

		Way way1 = new Way(1, A, B, 2);
		Way way2 = new Way(2, A, C, 5);
		Way way3 = new Way(3, A, D, 7);
		Way way4 = new Way(4, B, E, 8);
		Way way5 = new Way(5, D, H, 6);
		Way way6 = new Way(6, C, F, 15);
		Way way7 = new Way(7, E, A, 9);
		Way way8 = new Way(8, B, G, 1);
		Way way9 = new Way(9, B, I, 6);

		allWays.add(way1);
		allWays.add(way2);
		allWays.add(way3);
		allWays.add(way4);
		allWays.add(way5);
		allWays.add(way6);
		allWays.add(way7);
		allWays.add(way8);
		allWays.add(way9);

		DjikstraAlgo algo = new DjikstraAlgo();

		List<Way> shortestWay = algo.getShortestPath(startWaypoint,
				endWaypoint, allWays);

		for (Way ways : shortestWay) {
			System.out.println(ways.getWayID());
		}

	}

}
