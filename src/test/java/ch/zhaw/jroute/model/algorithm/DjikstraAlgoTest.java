package ch.zhaw.jroute.model.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.zhaw.jroute.model.businessObjects.Way;
import ch.zhaw.jroute.model.businessObjects.Waypoint;
import ch.zhaw.jroute.model.util.WayStatusEnum;

public class DjikstraAlgoTest {
	private static Logger logger = Logger.getLogger("org.apache.log4j");
	private List<Way> allWays;
	private Waypoint A;
	private Waypoint B;
	private Waypoint C;
	private Waypoint D;
	private Waypoint E;
	private Waypoint F;
	private Waypoint G;
	private Waypoint H;
	private Waypoint I;
	private Waypoint J;
	private Way way1;
	private Way way2;
	private Way way3;
	private Way way4;
	private Way way5;
	private Way way6;
	private Way way7;
	private Way way8;
	private Way way9;
	private Way way10;
	private DjikstraAlgo algo;

	@Before
	public void createTestSzenario() {
		logger.setLevel(org.apache.log4j.Level.INFO);

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

	@Before
	public void setUp() {
		createTestSzenario();
		algo = new DjikstraAlgo();
	}

	/**
	 * check a route from point A to point G
	 * 
	 * @author Pascal S.
	 */
	@Test
	public void shortestPathPart1() {

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = G;

		List<Way> shortestWay = null;
		try {
			shortestWay = algo.getShortestPath(startWaypoint, endWaypoint,
					allWays);
		} catch (Exception e) {
			e.printStackTrace();
		}

		float WayID0 = shortestWay.get(0).getWayID();
		float WayID1 = shortestWay.get(1).getWayID();

		float ExpectedWayID0 = 1;
		float ExpectedWayID1 = 8;

		assertTrue(WayID0 == ExpectedWayID0);
		assertTrue(WayID1 == ExpectedWayID1);

	}

	@Test
	public void checkWays() {

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = H;

		try {
			algo.getShortestPath(startWaypoint, endWaypoint, allWays);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		try {
			algo.getShortestPath(A, G, allWays);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		try {
			algo.getShortestPath(A, F, allWays);
			Assert.fail();
		} catch (Exception e) {

		}
	}

	@Test
	public void checkStatusResult() {

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = G;

		try {
			algo.getShortestPath(startWaypoint, endWaypoint, allWays);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WayStatusEnum WayStatus5 = allWays.get(0).getStatus();
		WayStatusEnum ExpectedWayStatus5 = WayStatusEnum.result;
		assertEquals(ExpectedWayStatus5, WayStatus5);
	}

	@Test
	public void checkStatusNoResult() {

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = G;

		try {
			algo.getShortestPath(startWaypoint, endWaypoint, allWays);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WayStatusEnum WayStatus5 = allWays.get(1).getStatus();
		WayStatusEnum ExpectedWayStatus5 = WayStatusEnum.noResult;
		assertEquals(ExpectedWayStatus5, WayStatus5);
	}

	@Test
	public void checkStatusUndefined() {

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = G;

		try {
			algo.getShortestPath(startWaypoint, endWaypoint, allWays);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = I;

		List<Way> shortestWay = null;
		try {
			shortestWay = algo.getShortestPath(startWaypoint, endWaypoint,
					allWays);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		float WayID0 = shortestWay.get(0).getWayID();
		float WayID8 = shortestWay.get(1).getWayID();

		float ExpectedWayID1 = 1;
		float ExpectedWayID9 = 9;

		assertTrue(WayID0 == ExpectedWayID1);
		assertTrue(WayID8 == ExpectedWayID9);

	}

	@Test
	public void shortestPathPart3() {

		Waypoint startWaypoint = A;
		Waypoint endWaypoint = J;

		List<Way> shortestWay = null;
		try {
			shortestWay = algo.getShortestPath(startWaypoint, endWaypoint,
					allWays);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		float WayID0 = shortestWay.get(0).getWayID();
		float WayID7 = shortestWay.get(1).getWayID();
		float WayID9 = shortestWay.get(2).getWayID();

		float ExpectedWayID1 = 1;
		float ExpectedWayID8 = 8;
		float ExpectedWayID10 = 10;

		assertTrue(WayID0 == ExpectedWayID1);
		assertTrue(WayID7 == ExpectedWayID8);
		assertTrue(WayID9 == ExpectedWayID10);

	}

}
