package ch.zhaw.jroute.routedata;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class BoxHandlerTest {
	private static Logger logger = Logger.getLogger("org.apache.log4j");
	BoxHandler testObject;
	List<Way> allWaysFromXML;
	Way way27776903;
	Way way77618443;
	Way way77618444;
	Way way77618445;
	Way way77618456;
	Way way77618457;

	@Before
	public void setUp() throws Exception {
		logger.setLevel(org.apache.log4j.Level.INFO);
		IAPIConnector apiConnector = new APIConnectorMockSmall();
		testObject = new BoxHandler(apiConnector);
		allWaysFromXML = testObject.getAllWays(0, 0, 0, 0);

		for (Way way : allWaysFromXML) {
			if (way.getWayID() == 27776903) {
				way27776903 = way;
			}
			if (way.getWayID() == 77618443) {
				way77618443 = way;
			}
			if (way.getWayID() == 77618444) {
				way77618444 = way;
			}
			if (way.getWayID() == 77618445) {
				way77618445 = way;
			}
			if (way.getWayID() == 77618456) {
				way77618456 = way;
			}
			if (way.getWayID() == 77618457) {
				way77618457 = way;
			}
		}

	}

	@Test
	public void countWays() {
		assertTrue(allWaysFromXML.size() == 6);
	}

	@Test
	public void countWaypoints() {
		int count = 0;

		for (Way way : allWaysFromXML) {
			count = count + way.getWaypointList().size();
		}

		System.out.println(count);
		assertTrue(count == 15);
	}

	@Test
	public void testWays() throws IOException {

		assertTrue(way27776903.getWayID() == 27776903);
		assertTrue(way77618443.getWayID() == 77618443);
		assertTrue(way77618444.getWayID() == 77618444);
		assertTrue(way77618445.getWayID() == 77618445);
		assertTrue(way77618456.getWayID() == 77618456);
		assertTrue(way77618457.getWayID() == 77618457);

	}

	@Test
	public void testWaysAllFalse() throws IOException {

		assertFalse(way27776903.getWayID() == 777777);
		assertFalse(way77618445.getWayID() == 666666);
		assertFalse(way77618457.getWayID() == 555555);

	}

	@Test
	public void testWaysManyFalse() throws IOException {

		assertTrue(way77618445.getWayID() == 77618445);
		assertFalse(way77618444.getWayID() == 666666);
		assertFalse(way77618456.getWayID() == 555555);

	}

	@Test
	public void testWaypointsFromWay27776903() {

		List<Waypoint> wayList = way27776903.getWaypointList();

		assertTrue(wayList.get(0).getWaypointID() == 304994979);
		assertTrue(wayList.get(1).getWaypointID() == 319408587);
		assertTrue(wayList.get(2).getWaypointID() == 319408586);

	}

	@Test
	public void testWaypointsFromWay77618443() {

		List<Waypoint> wayList = way77618443.getWaypointList();

		assertTrue(wayList.get(0).getWaypointID() == 913315328);
		assertTrue(wayList.get(1).getWaypointID() == 913315265);

	}

	@Test
	public void testWaypointsFromWay77618444() {

		List<Waypoint> wayList = way77618444.getWaypointList();

		assertTrue(wayList.get(0).getWaypointID() == 913315265);
		assertTrue(wayList.get(1).getWaypointID() == 913315198);

	}

	@Test
	public void testWaypointsFromWay77618445() {

		List<Waypoint> wayList = way77618445.getWaypointList();

		assertTrue(wayList.get(0).getWaypointID() == 913315198);
		assertTrue(wayList.get(1).getWaypointID() == 913315351);

	}

	@Test
	public void testWaypointsFromWay77618456() {

		List<Waypoint> wayList = way77618456.getWaypointList();

		System.out.println(wayList.size());
		assertTrue(wayList.get(0).getWaypointID() == 913315315);
		assertTrue(wayList.get(1).getWaypointID() == 913315305);
		assertTrue(wayList.get(2).getWaypointID() == 913315348);
		assertTrue(wayList.get(3).getWaypointID() == 913315198);

	}

	@Test
	public void testWaypointsFromWay77618457() {

		List<Waypoint> wayList = way77618457.getWaypointList();

		assertTrue(wayList.get(0).getWaypointID() == 913315198);
		assertTrue(wayList.get(1).getWaypointID() == 913315265);

	}

	@Test
	public void testWaypointsFromWay77618457AllFalse() {

		List<Waypoint> wayList = way77618457.getWaypointList();

		assertFalse(wayList.get(0).getWaypointID() == 666666);
		assertFalse(wayList.get(1).getWaypointID() == 555555);

	}

	@Test
	public void testWaypointsFromWay77618456ManyFalse() {

		List<Waypoint> wayList = way77618456.getWaypointList();

		assertFalse(wayList.get(0).getWaypointID() == 777777);
		assertFalse(wayList.get(1).getWaypointID() == 666666);
		assertFalse(wayList.get(2).getWaypointID() == 555555);
		assertTrue(wayList.get(3).getWaypointID() == 913315198);

	}

	@Test
	public void testValuesForWaypointFromWay27776903() {

		List<Waypoint> wayList = way27776903.getWaypointList();
		Waypoint point1 = wayList.get(0);
		Waypoint point2 = wayList.get(1);
		Waypoint point3 = wayList.get(2);

		assertTrue(point1.getLat() == 51.5074015);
		assertTrue(point1.getLon() == -0.1084121);
		assertTrue(point2.getLat() == 51.5074343);
		assertTrue(point2.getLon() == -0.1081264);
		assertTrue(point3.getLat() == 51.5074089);
		assertTrue(point3.getLon() == -0.1080108);
	}

	@Test
	public void testValuesForWaypointFromWay77618443() {

		List<Waypoint> wayList = way77618443.getWaypointList();
		Waypoint point1 = wayList.get(0);
		Waypoint point2 = wayList.get(1);

		assertTrue(point1.getLat() == 51.5073721);
		assertTrue(point1.getLon() == -0.1082076);
		assertTrue(point2.getLat() == 51.5074046);
		assertTrue(point2.getLon() == -0.1077597);
	}

	@Test
	public void testValuesForWaypointFromWay77618444() {

		List<Waypoint> wayList = way77618444.getWaypointList();
		Waypoint point1 = wayList.get(0);
		Waypoint point2 = wayList.get(1);

		assertTrue(point1.getLat() == 51.5074046);
		assertTrue(point1.getLon() == -0.1077597);
		assertTrue(point2.getLat() == 51.5073117);
		assertTrue(point2.getLon() == -0.1077294);
	}

	@Test
	public void testValuesForWaypointFromWay77618445() {

		List<Waypoint> wayList = way77618445.getWaypointList();
		Waypoint point1 = wayList.get(0);
		Waypoint point2 = wayList.get(1);

		assertTrue(point1.getLat() == 51.5073117);
		assertTrue(point1.getLon() == -0.1077294);
		assertTrue(point2.getLat() == 51.5071418);
		assertTrue(point2.getLon() == -0.1080137);
	}

	@Test
	public void testValuesForWaypointFromWay77618456() {

		List<Waypoint> wayList = way77618456.getWaypointList();
		Waypoint point1 = wayList.get(0);
		Waypoint point2 = wayList.get(1);
		Waypoint point3 = wayList.get(2);
		Waypoint point4 = wayList.get(3);

		assertTrue(point1.getLat() == 51.5071419);
		assertTrue(point1.getLon() == -0.1074396);
		assertTrue(point2.getLat() == 51.5071117);
		assertTrue(point2.getLon() == -0.1076883);
		assertTrue(point3.getLat() == 51.5073147);
		assertTrue(point3.getLon() == -0.1076811);
		assertTrue(point4.getLat() == 51.5073117);
		assertTrue(point4.getLon() == -0.1077294);
	}

	@Test
	public void testValuesForWaypointFromWay77618457() {

		List<Waypoint> wayList = way77618457.getWaypointList();
		Waypoint point1 = wayList.get(0);
		Waypoint point2 = wayList.get(1);

		assertTrue(point1.getLat() == 51.5073117);
		assertTrue(point1.getLon() == -0.1077294);
		assertTrue(point2.getLat() == 51.5074046);
		assertTrue(point2.getLon() == -0.1077597);
	}

	@Test
	public void testValuesForWaypointFromWay77618457AllFalse() {

		List<Waypoint> wayList = way77618457.getWaypointList();
		Waypoint point1 = wayList.get(0);
		Waypoint point2 = wayList.get(1);

		assertFalse(point1.getLat() == 99.5073117);
		assertFalse(point1.getLon() == -5.1077294);
		assertFalse(point2.getLat() == 99.5074046);
		assertFalse(point2.getLon() == -5.1077597);
	}

	@Test
	public void testValuesForWaypointFromWay77618457ManyFalse() {

		List<Waypoint> wayList = way77618457.getWaypointList();
		Waypoint point1 = wayList.get(0);
		Waypoint point2 = wayList.get(1);

		assertTrue(point1.getLat() == 51.5073117);
		assertTrue(point1.getLon() == -0.1077294);
		assertFalse(point2.getLat() == 99.5074046);
		assertFalse(point2.getLon() == -4.1077597);
	}

	@Test
	public void testStartAndEndWaypointFromWay27776903() {
		Way way = way27776903;
		Waypoint start = way.getStart();
		Waypoint end = way.getEnd();

		assertTrue(start.equals(way.getWaypointList().get(0)));
		assertTrue(end.equals(way.getWaypointList().get(2)));
		assertTrue(start.getWaypointID() == 304994979);
		assertTrue(end.getWaypointID() == 319408586);
	}

	@Test
	public void testStartAndEndWaypointFromWay77618443() {
		Way way = way77618443;
		Waypoint start = way.getStart();
		Waypoint end = way.getEnd();

		assertTrue(start.equals(way.getWaypointList().get(0)));
		assertTrue(end.equals(way.getWaypointList().get(1)));
		assertTrue(start.getWaypointID() == 913315328);
		assertTrue(end.getWaypointID() == 913315265);
	}

	@Test
	public void testStartAndEndWaypointFromWay77618444() {
		Way way = way77618444;
		Waypoint start = way.getStart();
		Waypoint end = way.getEnd();

		assertTrue(start.equals(way.getWaypointList().get(0)));
		assertTrue(end.equals(way.getWaypointList().get(1)));
		assertTrue(start.getWaypointID() == 913315265);
		assertTrue(end.getWaypointID() == 913315198);
	}

	@Test
	public void testStartAndEndWaypointFromWay77618445() {
		Way way = way77618445;
		Waypoint start = way.getStart();
		Waypoint end = way.getEnd();

		assertTrue(start.equals(way.getWaypointList().get(0)));
		assertTrue(end.equals(way.getWaypointList().get(1)));
		assertTrue(start.getWaypointID() == 913315198);
		assertTrue(end.getWaypointID() == 913315351);
	}

	@Test
	public void testStartAndEndWaypointFromWay77618456() {
		Way way = way77618456;
		Waypoint start = way.getStart();
		Waypoint end = way.getEnd();

		assertTrue(start.equals(way.getWaypointList().get(0)));
		assertTrue(end.equals(way.getWaypointList().get(3)));
		assertTrue(start.getWaypointID() == 913315315);
		assertTrue(end.getWaypointID() == 913315198);
	}

	@Test
	public void testStartAndEndWaypointFromWay77618457() {
		Way way = way77618457;
		Waypoint start = way.getStart();
		Waypoint end = way.getEnd();

		assertTrue(start.equals(way.getWaypointList().get(0)));
		assertTrue(end.equals(way.getWaypointList().get(1)));
		assertTrue(start.getWaypointID() == 913315198);
		assertTrue(end.getWaypointID() == 913315265);
	}

	@Test
	public void testStartAndEndWaypointFromWay77618443False() {
		Way way = way77618443;
		Waypoint start = way.getStart();
		Waypoint end = way.getEnd();

		assertFalse(start.equals(way.getWaypointList().get(1)));
		assertFalse(end.equals(way.getWaypointList().get(0)));
		assertFalse(start.getWaypointID() == 555555);
		assertFalse(end.getWaypointID() == 4444444);
	}

}
