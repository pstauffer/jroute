package ch.zhaw.jroute.routedata;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class BoxHandlerTest {

	@Test
	public void amountOfWays() {
		HashSet<Way> ways = getAllWaysInBox();

		int size = ways.size();
		int expectedSize = 3;

		assertTrue(size == expectedSize);

	}

	@Test
	public void amountOfWaypointsInWays() {
		Set<Way> ways = getAllWaysInBox();

		int size = 0;
		for (Way way : ways) {
			size = (size + way.getWaypointList().size());
		}

		int expectedSize = 12;

		assertTrue(size == expectedSize);

	}

	@Test
	public void CheckWays() {
		HashSet<Way> ways = getAllWaysInBox();

		int temp = 0;
		for (Way way : ways) {
			if (way.getWayID() == 77618455) {
				temp++;
			}
			if (way.getWayID() == 27776903) {
				temp++;
			}
			if (way.getWayID() == 77618442) {
				temp++;
			}
		}

		int expectedSize = 3;
		assertTrue(temp == expectedSize);
	}

	@Test
	public void CheckWaypointsOfWay1() {
		HashSet<Way> ways = getAllWaysInBox();

		int temp = 0;
		for (Way way : ways) {
			if (way.getWayID() == 77618455) {
				for (Waypoint waypoint : way.getWaypointList()) {
					if (waypoint.getWaypointID() == 913315315) {
						temp++;
					}
					if (waypoint.getWaypointID() == 913315305) {
						temp++;
					}
					if (waypoint.getWaypointID() == 913315348) {
						temp++;
					}
					if (waypoint.getWaypointID() == 913315198) {
						temp++;
					}
					if (waypoint.getWaypointID() == 913315265) {
						temp++;
					}
				}
			}
		}

		int expectedSize = 5;
		assertTrue(temp == expectedSize);
	}

	@Test
	public void CheckWaypointsOfWay2() {
		HashSet<Way> ways = getAllWaysInBox();

		int temp = 0;
		for (Way way : ways) {
			if (way.getWayID() == 77618442) {
				for (Waypoint waypoint : way.getWaypointList()) {
					if (waypoint.getWaypointID() == 913315328) {
						temp++;
					}
					if (waypoint.getWaypointID() == 913315265) {
						temp++;
					}
					if (waypoint.getWaypointID() == 913315198) {
						temp++;
					}
					if (waypoint.getWaypointID() == 913315351) {
						temp++;
					}
				}
			}
		}

		int expectedSize = 4;
		assertTrue(temp == expectedSize);
	}

	@Test
	public void CheckWaypointsOfWay3() {
		HashSet<Way> ways = getAllWaysInBox();

		int temp = 0;
		for (Way way : ways) {
			if (way.getWayID() == 27776903) {
				for (Waypoint waypoint : way.getWaypointList()) {
					if (waypoint.getWaypointID() == 304994979) {
						temp++;
					}
					if (waypoint.getWaypointID() == 319408587) {
						temp++;
					}
					if (waypoint.getWaypointID() == 319408586) {
						temp++;
					}
				}
			}
		}

		int expectedSize = 3;
		assertTrue(temp == expectedSize);
	}

	@Test
	public void CheckSomeWaypoints() {
		HashSet<Way> ways = getAllWaysInBox();

		double lat1 = 0;
		float lat2 = 0;
		float lat3 = 0;
		double lon1 = 0;
		float lon2 = 0;
		float lon3 = 0;

		for (Way way : ways) {
			if (way.getWayID() == 27776903) {
				for (Waypoint waypoint : way.getWaypointList()) {
					if (waypoint.getWaypointID() == 304994979) {
						lat1 = waypoint.getLat();
						lon1 = waypoint.getLon();
					}
					if (waypoint.getWaypointID() == 319408587) {
						lat2 = waypoint.getLat();
						lon2 = waypoint.getLon();
					}
					if (waypoint.getWaypointID() == 319408586) {
						lat3 = waypoint.getLat();
						lon3 = waypoint.getLon();
					}
				}
			}
		}

		double expectedLat1 = 51.5074015;
		double expectedLon1 = -0.1084121;

		// assertTrue(lat1 == expectedLat1);
		// assertTrue(lat2 == expectedLat2);
		// assertTrue(lat3 == expectedLat3);
		// assertTrue(lon1 == expectedLon1);
		// assertTrue(lon2 == expectedLon2);
		// assertTrue(lon3 == expectedLon3);

	}

	private HashSet<Way> getAllWaysInBox() {
		Set<Waypoint> waypointsInBox = new HashSet<Waypoint>();
		Set<Way> waysInBox = new HashSet<Way>();

		File xmlFile = new File(
				"src/test/java/ch/zhaw/jroute/routedata/SmallBoxAPIMock.xml");

		// File xmlFile = new File(
		// "src/test/java/ch/zhaw/jroute/routedata/BigBoxAPIMock.xml");

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();

			// get all the waypoints
			NodeList allWaypoints = document.getElementsByTagName("node");
			for (int temp = 0; temp < allWaypoints.getLength(); temp++) {
				Node waypointItem = allWaypoints.item(temp);
				if (waypointItem.getNodeType() == Node.ELEMENT_NODE) {
					Element waypointElement = (Element) waypointItem;
					Integer nodeID = Integer.parseInt(waypointElement
							.getAttribute("id"));
					float nodeLat = Float.parseFloat(waypointElement
							.getAttribute("lat"));
					float nodeLon = Float.parseFloat(waypointElement
							.getAttribute("lon"));

					Waypoint tempWaypoint = new Waypoint(nodeID, nodeLat,
							nodeLon);

					waypointsInBox.add(tempWaypoint);

				}
			}

			// get all the ways with reference to the waypoints
			NodeList allWays = document.getElementsByTagName("way");
			for (int temp = 0; temp < allWays.getLength(); temp++) {

				List<Waypoint> tempWaypointList = new ArrayList<Waypoint>();

				Node wayItem = allWays.item(temp);
				if (wayItem.getNodeType() == Node.ELEMENT_NODE) {
					Element wayElement = (Element) wayItem;

					int WayID = Integer.parseInt(wayElement.getAttribute("id"));

					Way tempWay = new Way(WayID);
					waysInBox.add(tempWay);

					// get all the nodes from the way
					NodeList allWaypointsOfTheWays = wayElement
							.getElementsByTagName("nd");

					for (int i = 0; i < allWaypointsOfTheWays.getLength(); i++) {

						Node WaypointOfTheWayItem = allWaypointsOfTheWays
								.item(i);
						if (WaypointOfTheWayItem.getNodeType() == Node.ELEMENT_NODE) {

							Element waypointOfTheWayElement = (Element) WaypointOfTheWayItem;

							int nodeID = Integer
									.parseInt(waypointOfTheWayElement
											.getAttribute("ref"));

							for (Waypoint wp : waypointsInBox) {
								if (wp.getWaypointID() == nodeID) {
									tempWaypointList.add(wp);
								}
							}

							tempWay.setWaypointList(tempWaypointList);
						}
					}

				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (HashSet<Way>) waysInBox;
	}

}
