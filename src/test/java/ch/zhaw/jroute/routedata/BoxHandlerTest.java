package ch.zhaw.jroute.routedata;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class BoxHandlerTest {
	BoxHandler testObject;
	List<Way> allWaysFromXML;
	Way way1;
	Way way2;
	Way way3;
	Waypoint way1point1;
	Waypoint way1point2;
	Waypoint way1point3;
	Waypoint way1point4;
	Waypoint way2point1;
	Waypoint way2point2;
	Waypoint way2point3;
	Waypoint way2point4;
	Waypoint way2point5;
	Waypoint way3point1;
	Waypoint way3point2;
	Waypoint way3point3;

	@Before
	public void setUp() throws Exception {
		IAPIConnector apiConnector = new APIConnectorMock();
		testObject = new BoxHandler(apiConnector);
		allWaysFromXML = testObject.getAllWays(0, 0, 0, 0);

		way1 = allWaysFromXML.get(0);
		way2 = allWaysFromXML.get(1);
		way3 = allWaysFromXML.get(2);

		way1point1 = way1.getWaypointList().get(0);
		way1point2 = way1.getWaypointList().get(1);
		way1point3 = way1.getWaypointList().get(2);
		way1point4 = way1.getWaypointList().get(3);
		way2point1 = way2.getWaypointList().get(0);
		way2point2 = way2.getWaypointList().get(1);
		way2point3 = way2.getWaypointList().get(2);
		way2point4 = way2.getWaypointList().get(3);
		way2point5 = way2.getWaypointList().get(4);
		way3point1 = way3.getWaypointList().get(0);
		way3point2 = way3.getWaypointList().get(1);
		way3point3 = way3.getWaypointList().get(2);

	}

	@Test
	public void testWays() throws IOException {
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
	public void testWaysAllFalse() throws IOException {
		long wayID1 = way1.getWayID();
		long wayID2 = way2.getWayID();
		long wayID3 = way3.getWayID();

		long expectedWayID1 = 777777;
		long expectedWayID2 = 666666;
		long expectedWayID3 = 555555;

		assertFalse(wayID1 == expectedWayID1);
		assertFalse(wayID2 == expectedWayID2);
		assertFalse(wayID3 == expectedWayID3);

	}

	@Test
	public void testWaysManyFalse() throws IOException {
		long wayID1 = way1.getWayID();
		long wayID2 = way2.getWayID();
		long wayID3 = way3.getWayID();

		long expectedWayID1 = 77618442;
		long expectedWayID2 = 666666;
		long expectedWayID3 = 555555;

		assertTrue(wayID1 == expectedWayID1);
		assertFalse(wayID2 == expectedWayID2);
		assertFalse(wayID3 == expectedWayID3);

	}

	@Test
	public void testWaypointsFromWay1() {
		long waypointID1 = way1point1.getWaypointID();
		long waypointID2 = way1point2.getWaypointID();
		long waypointID3 = way1point3.getWaypointID();
		long waypointID4 = way1point4.getWaypointID();

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
	public void testWaypointsFromWay1AllFalse() {
		long waypointID1 = way1point1.getWaypointID();
		long waypointID2 = way1point2.getWaypointID();
		long waypointID3 = way1point3.getWaypointID();
		long waypointID4 = way1point4.getWaypointID();

		long expectedID1 = 777777;
		long expectedID2 = 666666;
		long expectedID3 = 555555;
		long expectedID4 = 444444;

		assertFalse(waypointID1 == expectedID1);
		assertFalse(waypointID2 == expectedID2);
		assertFalse(waypointID3 == expectedID3);
		assertFalse(waypointID4 == expectedID4);
	}

	@Test
	public void testWaypointsFromWay1ManyFalse() {
		long waypointID1 = way1point1.getWaypointID();
		long waypointID2 = way1point2.getWaypointID();
		long waypointID3 = way1point3.getWaypointID();
		long waypointID4 = way1point4.getWaypointID();

		long expectedID1 = 913315328;
		long expectedID2 = 777777;
		long expectedID3 = 913315198;
		long expectedID4 = 444444;

		assertTrue(waypointID1 == expectedID1);
		assertFalse(waypointID2 == expectedID2);
		assertTrue(waypointID3 == expectedID3);
		assertFalse(waypointID4 == expectedID4);
	}

	@Test
	public void testWaypointsFromWay2() {
		long waypointID1 = way2point1.getWaypointID();
		long waypointID2 = way2point2.getWaypointID();
		long waypointID3 = way2point3.getWaypointID();
		long waypointID4 = way2point4.getWaypointID();
		long waypointID5 = way2point5.getWaypointID();

		long expectedID1 = 913315315;
		long expectedID2 = 913315305;
		long expectedID3 = 913315348;
		long expectedID4 = 913315198;
		long expectedID5 = 913315265;

		assertTrue(waypointID1 == expectedID1);
		assertTrue(waypointID2 == expectedID2);
		assertTrue(waypointID3 == expectedID3);
		assertTrue(waypointID4 == expectedID4);
		assertTrue(waypointID5 == expectedID5);

	}

	@Test
	public void testWaypointsFromWay3() {
		long waypointID1 = way3point1.getWaypointID();
		long waypointID2 = way3point2.getWaypointID();
		long waypointID3 = way3point3.getWaypointID();

		long expectedID1 = 304994979;
		long expectedID2 = 319408587;
		long expectedID3 = 319408586;

		assertTrue(waypointID1 == expectedID1);
		assertTrue(waypointID2 == expectedID2);
		assertTrue(waypointID3 == expectedID3);

	}

	@Test
	public void testValuesForWaypointFromWay1() {
		double waypointLat1 = way1point1.getLat();
		double waypointLon1 = way1point1.getLon();
		double waypointLat2 = way1point2.getLat();
		double waypointLon2 = way1point2.getLon();
		double waypointLat3 = way1point3.getLat();
		double waypointLon3 = way1point3.getLon();
		double waypointLat4 = way1point4.getLat();
		double waypointLon4 = way1point4.getLon();

		double expectedLat1 = 51.5073721;
		double expectedLon1 = -0.1082076;
		double expectedLat2 = 51.5074046;
		double expectedLon2 = -0.1077597;
		double expectedLat3 = 51.5073117;
		double expectedLon3 = -0.1077294;
		double expectedLat4 = 51.5071418;
		double expectedLon4 = -0.1080137;

		assertTrue(waypointLat1 == expectedLat1);
		assertTrue(waypointLon1 == expectedLon1);
		assertTrue(waypointLat2 == expectedLat2);
		assertTrue(waypointLon2 == expectedLon2);
		assertTrue(waypointLat3 == expectedLat3);
		assertTrue(waypointLon3 == expectedLon3);
		assertTrue(waypointLat4 == expectedLat4);
		assertTrue(waypointLon4 == expectedLon4);
	}

	@Test
	public void testValuesForWaypointFromWay2() {
		double waypointLat1 = way2point1.getLat();
		double waypointLon1 = way2point1.getLon();
		double waypointLat2 = way2point2.getLat();
		double waypointLon2 = way2point2.getLon();
		double waypointLat3 = way2point3.getLat();
		double waypointLon3 = way2point3.getLon();
		double waypointLat4 = way2point4.getLat();
		double waypointLon4 = way2point4.getLon();
		double waypointLat5 = way2point5.getLat();
		double waypointLon5 = way2point5.getLon();

		double expectedLat1 = 51.5071419;
		double expectedLon1 = -0.1074396;
		double expectedLat2 = 51.5071117;
		double expectedLon2 = -0.1076883;
		double expectedLat3 = 51.5073147;
		double expectedLon3 = -0.1076811;
		double expectedLat4 = 51.5073117;
		double expectedLon4 = -0.1077294;
		double expectedLat5 = 51.5074046;
		double expectedLon5 = -0.1077597;

		assertTrue(waypointLat1 == expectedLat1);
		assertTrue(waypointLon1 == expectedLon1);
		assertTrue(waypointLat2 == expectedLat2);
		assertTrue(waypointLon2 == expectedLon2);
		assertTrue(waypointLat3 == expectedLat3);
		assertTrue(waypointLon3 == expectedLon3);
		assertTrue(waypointLat4 == expectedLat4);
		assertTrue(waypointLon4 == expectedLon4);
		assertTrue(waypointLat5 == expectedLat5);
		assertTrue(waypointLon5 == expectedLon5);
	}

	@Test
	public void testValuesForWaypointFromWay3() {
		double waypointLat1 = way3point1.getLat();
		double waypointLon1 = way3point1.getLon();
		double waypointLat2 = way3point2.getLat();
		double waypointLon2 = way3point2.getLon();
		double waypointLat3 = way3point3.getLat();
		double waypointLon3 = way3point3.getLon();

		double expectedLat1 = 51.5074015;
		double expectedLon1 = -0.1084121;
		double expectedLat2 = 51.5074343;
		double expectedLon2 = -0.1081264;
		double expectedLat3 = 51.5074089;
		double expectedLon3 = -0.1080108;

		assertTrue(waypointLat1 == expectedLat1);
		assertTrue(waypointLon1 == expectedLon1);
		assertTrue(waypointLat2 == expectedLat2);
		assertTrue(waypointLon2 == expectedLon2);
		assertTrue(waypointLat3 == expectedLat3);
		assertTrue(waypointLon3 == expectedLon3);
	}

	@Test
	public void testValuesForWaypointFromWay3False() {
		double waypointLat1 = way3point1.getLat();
		double waypointLon1 = way3point1.getLon();
		double waypointLat2 = way3point2.getLat();
		double waypointLon2 = way3point2.getLon();
		double waypointLat3 = way3point3.getLat();
		double waypointLon3 = way3point3.getLon();

		double expectedLat1 = 51.4444;
		double expectedLon1 = -0.14444;
		double expectedLat2 = 51.533333;
		double expectedLon2 = -0.102222;
		double expectedLat3 = 51.50111289;
		double expectedLon3 = -3.104448;

		assertFalse(waypointLat1 == expectedLat1);
		assertFalse(waypointLon1 == expectedLon1);
		assertFalse(waypointLat2 == expectedLat2);
		assertFalse(waypointLon2 == expectedLon2);
		assertFalse(waypointLat3 == expectedLat3);
		assertFalse(waypointLon3 == expectedLon3);
	}

	@Test
	public void testStartWaypointFromWays() {
		Waypoint waypointStart1 = way1.getStart();
		Waypoint waypointStart2 = way2.getStart();
		Waypoint waypointStart3 = way3.getStart();

		Waypoint expectedStartWaypointWay1 = way1point1;
		Waypoint expectedStartWaypointWay2 = way2point1;
		Waypoint expectedStartWaypointWay3 = way3point1;

		assertTrue(waypointStart1.equals(expectedStartWaypointWay1));
		assertTrue(waypointStart2.equals(expectedStartWaypointWay2));
		assertTrue(waypointStart3.equals(expectedStartWaypointWay3));
	}

	@Test
	public void testStartWaypointFromWaysFalse() {
		Waypoint waypointStart1 = way1.getStart();
		Waypoint waypointStart2 = way2.getStart();

		Waypoint expectedStartWaypointWay1 = way1point2;
		Waypoint expectedStartWaypointWay2 = way3point3;

		assertFalse(waypointStart1.equals(expectedStartWaypointWay1));
		assertFalse(waypointStart2.equals(expectedStartWaypointWay2));
	}

	@Test
	public void testEndWaypointFromWays() {
		Waypoint waypointEnd1 = way1.getEnd();
		Waypoint waypointEnd2 = way2.getEnd();
		Waypoint waypointEnd3 = way3.getEnd();

		Waypoint expectedEndWaypointWay1 = way1point4;
		Waypoint expectedEndWaypointWay2 = way2point5;
		Waypoint expectedEndWaypointWay3 = way3point3;

		assertTrue(waypointEnd1.equals(expectedEndWaypointWay1));
		assertTrue(waypointEnd2.equals(expectedEndWaypointWay2));
		assertTrue(waypointEnd3.equals(expectedEndWaypointWay3));
	}

	@Test
	public void testEndWaypointFromWaysFalse() {
		Waypoint waypointEnd1 = way1.getEnd();
		Waypoint waypointEnd3 = way3.getEnd();

		Waypoint expectedEndWaypointWay1 = way2point3;
		Waypoint expectedEndWaypointWay3 = way3point2;

		assertFalse(waypointEnd1.equals(expectedEndWaypointWay1));
		assertFalse(waypointEnd3.equals(expectedEndWaypointWay3));
	}

}
