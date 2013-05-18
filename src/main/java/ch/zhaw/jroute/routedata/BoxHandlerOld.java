package ch.zhaw.jroute.routedata;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class BoxHandlerOld implements IBoxHandler {

	@Override
	public List<Way> getAllWays(double left, double bottom, double right,
			double top) throws IOException {

		final String openStreetMapBoxURL = "http://api.openstreetmap.org/api/0.6/map?bbox=";
		List<Waypoint> waypointsInBox = new ArrayList<Waypoint>();
		List<Way> waysInBox = new ArrayList<Way>();

		checkCoordinates(left, bottom, right, top);

		try {

			URL url = new URL(openStreetMapBoxURL + left + "," + bottom + ","
					+ right + "," + top);
			URLConnection connection = url.openConnection();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(connection.getInputStream());
			document.getDocumentElement().normalize();

			// get all the waypoints inclusive the coordinates
			NodeList allWaypoints = document.getElementsByTagName("node");
			for (int temp = 0; temp < allWaypoints.getLength(); temp++) {
				Node waypointItem = allWaypoints.item(temp);
				if (waypointItem.getNodeType() == Node.ELEMENT_NODE) {
					Element waypointElement = (Element) waypointItem;
					long nodeID = Long.parseLong(waypointElement
							.getAttribute("id"));
					double nodeLat = Double.parseDouble(waypointElement
							.getAttribute("lat"));
					double nodeLon = Double.parseDouble(waypointElement
							.getAttribute("lon"));

					Waypoint tempWaypoint = new Waypoint(nodeID, nodeLat,
							nodeLon);

					// set position (lon and lat in one value)
					Angle lat = Angle
							.fromDegreesLatitude(tempWaypoint.getLat());
					Angle lon = Angle
							.fromDegreesLatitude(tempWaypoint.getLon());

					Position pos = new Position(lat, lon, 0);
					tempWaypoint.setCenter(pos);

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

					long wayID = Long.parseLong(wayElement.getAttribute("id"));

					Way tempWay = new Way(wayID);
					waysInBox.add(tempWay);

					// get all the nodes from the way
					NodeList allWaypointsOfTheWays = wayElement
							.getElementsByTagName("nd");

					for (int i = 0; i < allWaypointsOfTheWays.getLength(); i++) {

						Node waypointOfTheWayItem = allWaypointsOfTheWays
								.item(i);
						if (waypointOfTheWayItem.getNodeType() == Node.ELEMENT_NODE) {

							Element waypointOfTheWayElement = (Element) waypointOfTheWayItem;

							long nodeID = Long
									.parseLong(waypointOfTheWayElement
											.getAttribute("ref"));

							for (Waypoint wp : waypointsInBox) {
								if (wp.getWaypointID() == nodeID) {
									tempWaypointList.add(wp);
								} else {
									// throw new IllegalArgumentException(
									// "waypoint not found in xml");
								}
							}

							// set start and end of the way
							Waypoint tempStartWaypoint = tempWaypointList
									.get(0);
							int lastTempWaypoint = tempWaypointList.size() - 1;
							Waypoint tempEndWaypoint = tempWaypointList
									.get(lastTempWaypoint);

							tempWay.setStart(tempStartWaypoint);
							tempWay.setEnd(tempEndWaypoint);

							// set waypointlist for the way
							tempWay.setWaypointList(tempWaypointList);

						}
					}

				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return (ArrayList<Way>) waysInBox;
	}

	private void checkCoordinates(double left, double bottom, double right,
			double top) {
		if (left > 90 || left < -90) {
			throw new IllegalArgumentException(
					"left longitude must be between -90 and 90");
		}
		if (right > 90 || right < -90) {
			throw new IllegalArgumentException(
					"left longitude must be between -90 and 90");
		}
		if (top > 180 || top < -180) {
			throw new IllegalArgumentException(
					"top latitude must be between -180 and 180");
		}
		if (bottom > 180 || bottom < -180) {
			throw new IllegalArgumentException(
					"bottom latitude must be between -180 and 180");
		}
	}
}
