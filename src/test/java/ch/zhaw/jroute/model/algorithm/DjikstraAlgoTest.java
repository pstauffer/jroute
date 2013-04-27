package ch.zhaw.jroute.model.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.WayStatusEnum;
import ch.zhaw.jroute.model.Waypoint;

public class DjikstraAlgoTest {
	List<Way> allWays;
	Waypoint A;
	Waypoint B;
	Waypoint C;
	Waypoint D;
	Waypoint E;
	Waypoint F;
	Waypoint G;
	Waypoint H;
	Waypoint I;
	Waypoint J;
	Way way1;
	Way way2;
	Way way3;
	Way way4;
	Way way5;
	Way way6;
	Way way7;
	Way way8;
	Way way9;
	Way way10;

	public void createTestSzenario() {

		allWays = new ArrayList<Way>();

		A = new Waypoint("A");
		B = new Waypoint("B");
		C = new Waypoint("C");
		D = new Waypoint("D");
		E = new Waypoint("E");
		F = new Waypoint("F");
		G = new Waypoint("G");
		H = new Waypoint("H");
		I = new Waypoint("I");
		J = new Waypoint("J");

		way1 = new Way(1, A, B, 2);
		way2 = new Way(2, A, C, 5);
		way3 = new Way(3, A, D, 7);
		way4 = new Way(4, C, E, 8);
		way5 = new Way(5, D, H, 6);
		way6 = new Way(6, F, F, 15);
		way7 = new Way(7, E, A, 9);
		way8 = new Way(8, B, G, 1);
		way9 = new Way(9, B, I, 6);
		way10 = new Way(10, G, J, 6);

		allWays.add(way1);
		allWays.add(way2);
		allWays.add(way3);
		allWays.add(way4);
		allWays.add(way5);
		allWays.add(way6);
		allWays.add(way7);
		allWays.add(way8);
		allWays.add(way9);
		allWays.add(way10);

	}

	/**
	 * check a route from point A to point G
	 * 
	 * @author Pascal S.
	 */
	@Test
	public void shortestPathPart1() {

		createTestSzenario();

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = G;

		DjikstraAlgo algo = new DjikstraAlgo();

		List<Way> shortestWay = algo.getShortestPath(startWaypoint,
				endWaypoint, allWays);

		int WayID0 = shortestWay.get(0).getWayID();
		int WayID1 = shortestWay.get(1).getWayID();

		int ExpectedWayID0 = 1;
		int ExpectedWayID1 = 8;

		// variant 1
		assertEquals(ExpectedWayID0, WayID0);
		assertEquals(ExpectedWayID1, WayID1);

		// variant 2
		assertTrue(WayID0 == ExpectedWayID0);
		assertTrue(WayID1 == ExpectedWayID1);

	}

	@Test
	public void checkWays() {
		createTestSzenario();

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = H;

		DjikstraAlgo algo = new DjikstraAlgo();

		algo.getShortestPath(startWaypoint, endWaypoint, allWays);

		assertTrue(way1.getStatus() == WayStatusEnum.noResult);
		assertTrue(way2.getStatus() == WayStatusEnum.noResult);
		assertTrue(way3.getStatus() == WayStatusEnum.result);
		assertTrue(way4.getStatus() == WayStatusEnum.noResult);
		assertTrue(way5.getStatus() == WayStatusEnum.result);
		assertTrue(way6.getStatus() == WayStatusEnum.undefined);
		assertTrue(way7.getStatus() == WayStatusEnum.noResult);
		assertTrue(way8.getStatus() == WayStatusEnum.noResult);
		assertTrue(way9.getStatus() == WayStatusEnum.noResult);
		assertTrue(way10.getStatus() == WayStatusEnum.noResult);

	}

	@Test
	public void checkCompleteGraph() {
		createTestSzenario();
		Waypoint startWaypoint = A;

		DjikstraAlgo algo = new DjikstraAlgo();

		algo.preparation(startWaypoint, allWays);
		algo.calculateGraph(startWaypoint, allWays);

		assertTrue(A.getDistanceToStart() == 0);
		assertTrue(A.getWaypointBefore().equals(A));

		assertTrue(B.getDistanceToStart() == 2);
		assertTrue(B.getWaypointBefore().equals(A));

		assertTrue(C.getDistanceToStart() == 5);
		assertTrue(C.getWaypointBefore().equals(A));

		assertTrue(D.getDistanceToStart() == 7);
		assertTrue(D.getWaypointBefore().equals(A));

		assertTrue(E.getDistanceToStart() == 9);
		assertTrue(E.getWaypointBefore().equals(A));

		assertTrue(F.getDistanceToStart() == Integer.MAX_VALUE);

		assertTrue(G.getDistanceToStart() == 3);
		assertTrue(G.getWaypointBefore().equals(B));

		assertTrue(H.getDistanceToStart() == 13);
		assertTrue(H.getWaypointBefore().equals(D));

		assertTrue(I.getDistanceToStart() == 8);
		assertTrue(I.getWaypointBefore().equals(B));

		assertTrue(J.getDistanceToStart() == 9);
		assertTrue(J.getWaypointBefore().equals(G));

	}

	@Test
	public void checkUnreachableEndWayointTrue() {
		createTestSzenario();
		Waypoint startWaypoint = A;
		Waypoint endWaypoint = F;

		DjikstraAlgo algo = new DjikstraAlgo();

		algo.preparation(startWaypoint, allWays);
		algo.calculateGraph(startWaypoint, allWays);
		boolean CheckWaypoint = algo.checkUnreachableWaypoint(endWaypoint);

		assertTrue(CheckWaypoint);

	}

	@Test
	public void checkUnreachableEndWayointFalse() {
		createTestSzenario();
		Waypoint startWaypoint = A;
		Waypoint endWaypoint = B;

		DjikstraAlgo algo = new DjikstraAlgo();

		algo.preparation(startWaypoint, allWays);
		algo.calculateGraph(startWaypoint, allWays);
		boolean CheckWaypoint = algo.checkUnreachableWaypoint(endWaypoint);

		assertTrue(!CheckWaypoint);

	}

	@Test
	public void checkStatusResult() {

		createTestSzenario();

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = G;

		DjikstraAlgo algo = new DjikstraAlgo();

		algo.getShortestPath(startWaypoint, endWaypoint, allWays);

		WayStatusEnum WayStatus5 = allWays.get(0).getStatus();
		WayStatusEnum ExpectedWayStatus5 = WayStatusEnum.result;
		assertEquals(ExpectedWayStatus5, WayStatus5);
	}

	@Test
	public void checkStatusNoResult() {

		createTestSzenario();

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = G;

		DjikstraAlgo algo = new DjikstraAlgo();

		algo.getShortestPath(startWaypoint, endWaypoint, allWays);

		WayStatusEnum WayStatus5 = allWays.get(1).getStatus();
		WayStatusEnum ExpectedWayStatus5 = WayStatusEnum.noResult;
		assertEquals(ExpectedWayStatus5, WayStatus5);
	}

	@Test
	public void checkStatusUndefined() {

		createTestSzenario();

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = G;

		DjikstraAlgo algo = new DjikstraAlgo();

		algo.getShortestPath(startWaypoint, endWaypoint, allWays);

		WayStatusEnum WayStatus5 = allWays.get(5).getStatus();
		WayStatusEnum ExpectedWayStatus5 = WayStatusEnum.undefined;
		assertEquals(ExpectedWayStatus5, WayStatus5);
	}

	/**
	 * check a route from point A to point I
	 * 
	 * @author Pascal S.
	 */
	@Test
	public void shortestPathPart2() {

		createTestSzenario();

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = I;

		DjikstraAlgo algo = new DjikstraAlgo();

		List<Way> shortestWay = algo.getShortestPath(startWaypoint,
				endWaypoint, allWays);

		int WayID0 = shortestWay.get(0).getWayID();
		int WayID8 = shortestWay.get(1).getWayID();

		int ExpectedWayID1 = 1;
		int ExpectedWayID9 = 9;

		assertEquals(ExpectedWayID1, WayID0);
		assertEquals(ExpectedWayID9, WayID8);

		assertTrue(WayID0 == ExpectedWayID1);
		assertTrue(WayID8 == ExpectedWayID9);

	}

	@Test
	public void shortestPathPart3() {

		createTestSzenario();

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = J;

		DjikstraAlgo algo = new DjikstraAlgo();

		List<Way> shortestWay = algo.getShortestPath(startWaypoint,
				endWaypoint, allWays);

		int WayID0 = shortestWay.get(0).getWayID();
		int WayID7 = shortestWay.get(1).getWayID();
		int WayID9 = shortestWay.get(2).getWayID();

		int ExpectedWayID1 = 1;
		int ExpectedWayID8 = 8;
		int ExpectedWayID10 = 10;

		assertEquals(ExpectedWayID1, WayID0);
		assertEquals(ExpectedWayID8, WayID7);
		assertEquals(ExpectedWayID10, WayID9);
		assertTrue(WayID0 == ExpectedWayID1);
		assertTrue(WayID7 == ExpectedWayID8);
		assertTrue(WayID9 == ExpectedWayID10);

	}

}
