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
	Way way27776903;
	Way way77618443;
	Way way77618444;
	Way way77618445;
	Way way77618446;
	Way way77618447;
	Way way77618448;
	Way way77618456;
	Way way77618457;

	@Before
	public void setUp() throws Exception {
		IAPIConnector apiConnector = new APIConnectorMock();
		testObject = new BoxHandler(apiConnector);
		allWaysFromXML = testObject.getAllWays(0, 0, 0, 0);

		way27776903 = allWaysFromXML.get(0);
		way77618443 = allWaysFromXML.get(1);
		way77618444 = allWaysFromXML.get(2);
		way77618445 = allWaysFromXML.get(3);
		way77618456 = allWaysFromXML.get(4);
		way77618457 = allWaysFromXML.get(5);
		way77618446 = allWaysFromXML.get(6);
		way77618447 = allWaysFromXML.get(7);
		way77618448 = allWaysFromXML.get(8);

	}

	@Test
	public void testWays() throws IOException {

		assertTrue(way27776903.getWayID() == 27776903);
		assertTrue(way77618443.getWayID() == 77618443);
		assertTrue(way77618444.getWayID() == 77618444);
		assertTrue(way77618445.getWayID() == 77618445);
		assertTrue(way77618446.getWayID() == 77618446);
		assertTrue(way77618447.getWayID() == 77618447);
		assertTrue(way77618448.getWayID() == 77618448);
		assertTrue(way77618456.getWayID() == 77618456);
		assertTrue(way77618457.getWayID() == 77618457);

	}

	@Test
	public void testWaysAllFalse() throws IOException {

		long expectedWayID1 = 777777;
		long expectedWayID2 = 666666;
		long expectedWayID3 = 555555;

		assertFalse(way27776903.getWayID() == expectedWayID1);
		assertFalse(way77618446.getWayID() == expectedWayID2);
		assertFalse(way77618457.getWayID() == expectedWayID3);

	}

	@Test
	public void testWaysManyFalse() throws IOException {

		long expectedWayID1 = 77618445;
		long expectedWayID2 = 666666;
		long expectedWayID3 = 555555;

		assertTrue(way77618445.getWayID() == expectedWayID1);
		assertFalse(way77618444.getWayID() == expectedWayID2);
		assertFalse(way77618456.getWayID() == expectedWayID3);

	}

	@Test
	public void testWaypointsFromWay1() {

		List<Waypoint> way1List = way27776903.getWaypointList();

		assertTrue(way1List.get(0).getWaypointID() == 304994979);
		assertTrue(way1List.get(1).getWaypointID() == 319408587);
		assertTrue(way1List.get(2).getWaypointID() == 319408586);

	}

	@Test
	public void testWaypointsFromWay1AllFalse() {
		List<Waypoint> way1List = way27776903.getWaypointList();

		long expectedID1 = 777777;
		long expectedID2 = 666666;
		long expectedID3 = 555555;

		assertFalse(way1List.get(0).getWaypointID() == expectedID1);
		assertFalse(way1List.get(1).getWaypointID() == expectedID2);
		assertFalse(way1List.get(2).getWaypointID() == expectedID3);
	}

	@Test
	public void testWaypointsFromWay1ManyFalse() {
		List<Waypoint> way1List = way27776903.getWaypointList();

		long expectedID2 = 666666;
		long expectedID3 = 555555;

		assertTrue(way1List.get(0).getWaypointID() == 304994979);
		assertFalse(way1List.get(1).getWaypointID() == expectedID2);
		assertFalse(way1List.get(2).getWaypointID() == expectedID3);
	}

	@Test
	public void testValuesForWaypointFromWay1() {

		List<Waypoint> way1List = way27776903.getWaypointList();
		Waypoint point1 = way1List.get(0);
		Waypoint point2 = way1List.get(1);
		Waypoint point3 = way1List.get(2);

		assertTrue(point1.getLat() == 51.5074015);
		assertTrue(point1.getLon() == -0.1084121);
		assertTrue(point2.getLat() == 51.5074343);
		assertTrue(point2.getLon() == -0.1081264);
		assertTrue(point3.getLat() == 51.5074089);
		assertTrue(point3.getLon() == -0.1080108);
	}

	@Test
	public void testValuesForWaypointFromWay1ManyFalse() {

		List<Waypoint> way1List = way27776903.getWaypointList();
		Waypoint point1 = way1List.get(0);
		Waypoint point2 = way1List.get(1);
		Waypoint point3 = way1List.get(2);

		assertFalse(point1.getLat() == 52.5074015);
		assertTrue(point1.getLon() == -0.1084121);
		assertFalse(point2.getLat() == 58.5074343);
		assertFalse(point2.getLon() == -0.1481264);
		assertTrue(point3.getLat() == 51.5074089);
		assertTrue(point3.getLon() == -0.1080108);
	}

	@Test
	public void testStartWaypointFromWays() {
		Waypoint start1 = way27776903.getStart();

		assertTrue(start1.equals(way27776903.getWaypointList().get(0)));
	}

	@Test
	public void testStartWaypointFromWaysFalse() {
		Waypoint start1 = way27776903.getStart();

		assertFalse(start1.equals(way27776903.getWaypointList().get(1)));
	}

	@Test
	public void testEndWaypointFromWays() {
		Waypoint start1 = way27776903.getEnd();

		assertTrue(start1.equals(way27776903.getWaypointList().get(2)));
	}

	@Test
	public void testEndWaypointFromWaysFalse() {
		Waypoint start1 = way27776903.getEnd();

		assertFalse(start1.equals(way27776903.getWaypointList().get(1)));
	}

}
