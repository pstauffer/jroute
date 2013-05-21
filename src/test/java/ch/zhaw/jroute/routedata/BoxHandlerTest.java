package ch.zhaw.jroute.routedata;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class BoxHandlerTest {
	BoxHandler testObject;
	List<Way> allWays;
	Way way1;
	Way way2;
	Way way3;
	Waypoint waypoint1;
	Waypoint waypoint2;
	Waypoint waypoint3;
	Waypoint waypoint4;

	@Before
	public void setUp() throws Exception {
		IAPIConnector apiConnector = new MockAPIConnector();
		testObject = new BoxHandler(apiConnector);
		allWays = testObject.getAllWays(0, 0, 0, 0);
		way1 = allWays.get(0);
		way2 = allWays.get(1);
		way3 = allWays.get(2);
		waypoint1 = way1.getWaypointList().get(0);
		waypoint2 = way1.getWaypointList().get(1);
		waypoint3 = way1.getWaypointList().get(2);
		waypoint4 = way1.getWaypointList().get(3);

	}

	@Test
	public void testWay1() throws IOException {
		long wayID1 = way1.getWayID();
		long wayID2 = way2.getWayID();
		long wayID3 = way3.getWayID();

		long expectedWayID1 = 77618442;
		long expectedWayID2 = 77618455;
		long expectedWayID3 = 27776903;

		assertTrue(wayID1 == expectedWayID1);
		assertTrue(wayID2 == expectedWayID2);
		assertTrue(wayID3 == expectedWayID3);

	}

	@Test
	public void testWaypointsFromWay1() {
		long waypointID1 = waypoint1.getWaypointID();
		long waypointID2 = waypoint2.getWaypointID();
		long waypointID3 = waypoint3.getWaypointID();
		long waypointID4 = waypoint4.getWaypointID();

		long expectedID1 = 913315328;
		long expectedID2 = 913315265;
		long expectedID3 = 913315198;
		long expectedID4 = 913315351;

		assertTrue(waypointID1 == expectedID1);
		assertTrue(waypointID2 == expectedID2);
		assertTrue(waypointID3 == expectedID3);
		assertTrue(waypointID4 == expectedID4);
	}

	@Test
	public void testValuesForWaypointFromWay1() {
		double waypointLat1 = waypoint1.getLat();
		double waypointLon1 = waypoint1.getLon();
		System.out.println(waypointLon1);

		double expectedLat1 = 51.5073721;
		double expectedLon1 = -0.1082076;

		assertTrue(waypointLat1 == expectedLat1);
		assertTrue(waypointLon1 == expectedLon1);
	}

}
