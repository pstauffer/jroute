package ch.zhaw.jroute.routedata;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class BoxHandler implements IBoxHandler {
	private final String openStreetMapBoxURL = "http://api.openstreetmap.org/api/0.6/map?bbox=";
	private static List<Waypoint> MatchingWaypointList = new ArrayList<Waypoint>();
	private static List<Way> MatchingWayList = new ArrayList<Way>();
	private static List<String> streetFilterList = new ArrayList<String>();

	@Override
	public List<Way> getAllWays(double left, double bottom, double right,
			double top) throws IOException {

		// check for correct coordinates
		checkCoordinates(left, bottom, right, top);

		// create url
		URL boxURL = new URL(openStreetMapBoxURL + left + "," + bottom + ","
				+ right + "," + top);

		// start timer for connection
		long startTime = System.nanoTime();

		// get document via connection
		Document document = makeConnection(boxURL);

		// stop timer for connection
		long endTime = System.nanoTime();
		long tookTime = endTime - startTime;
		System.out.println("get all data took: " + tookTime + " ns");

		// create xpath instance
		XPath xpath = XPathFactory.newInstance().newXPath();

		// set street filter
		addStreetFilter("motorway");
		// addStreetFilter("tertiary");
		// addStreetFilter("residential");

		try {

			// get all ways, which match with the filter
			setSelectedWays(document, xpath, streetFilterList);

			// set all waypoints for the ways
			setWaypointsForWays(MatchingWayList, document, xpath);

			// get and set all waypoint values
			setValuesForWaypoints(MatchingWaypointList, document, xpath);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return MatchingWayList;
	}

	private static void addStreetFilter(String filter) {
		streetFilterList.add(filter);
	}

	private static void setValuesForWaypoints(List<Waypoint> waypointList,
			Document document, XPath xpath) throws XPathExpressionException {

		// loop trough all matching waypoints
		for (Waypoint waypoint : waypointList) {
			long waypointID = waypoint.getWaypointID();
			NodeList waypointsInXml = (NodeList) xpath.compile(
					"/osm/node[@id='" + waypointID + "']").evaluate(document,
					XPathConstants.NODESET);

			double lat = Double.parseDouble(waypointsInXml.item(0)
					.getAttributes().getNamedItem("lat").getNodeValue());
			double lon = Double.parseDouble(waypointsInXml.item(0)
					.getAttributes().getNamedItem("lon").getNodeValue());

			// set position (lon and lat in one value)
			Angle latAngle = Angle.fromDegreesLatitude(waypoint.getLat());
			Angle lonAngle = Angle.fromDegreesLatitude(waypoint.getLon());
			Position pos = new Position(latAngle, lonAngle, 0);
			waypoint.setCenter(pos);

			// set latitude and longitude for waypoint
			waypoint.setLat(lat);
			waypoint.setLon(lon);

		}
	}

	private static void setWaypointsForWays(List<Way> ways, Document document,
			XPath xpath) throws XPathExpressionException {

		// loop trough all ways
		for (Way way : ways) {
			long wayID = way.getWayID();

			List<Waypoint> waypointsFromWay = new ArrayList<Waypoint>();
			NodeList wayInXml = (NodeList) xpath.compile(
					"/osm/way[@id='" + wayID + "']").evaluate(document,
					XPathConstants.NODESET);

			// loop trough all childnodes
			for (int i = 0; i < wayInXml.getLength(); i++) {
				NodeList wayChildNodeList = wayInXml.item(i).getChildNodes();
				for (int j = 0; j < wayChildNodeList.getLength(); j++) {
					String wayChildNodeName = wayChildNodeList.item(j)
							.getNodeName();
					String nodeName = "nd";
					if (wayChildNodeName.equals(nodeName)) {
						long waypointID = Long.parseLong(wayChildNodeList
								.item(j).getAttributes().getNamedItem("ref")
								.getNodeValue());

						// create new waypoint with id
						Waypoint waypoint = new Waypoint(waypointID);

						// add waypoint to the waypointlist of the way
						waypointsFromWay.add(waypoint);

						// add waypoint to the matching waypointlist
						MatchingWaypointList.add(waypoint);
					}
				}
			}

			// get start and end waypoint
			Waypoint tempStartWaypoint = waypointsFromWay.get(0);
			int lastTempWaypoint = waypointsFromWay.size() - 1;
			Waypoint tempEndWaypoint = waypointsFromWay.get(lastTempWaypoint);

			// set start and end for the way
			way.setStart(tempStartWaypoint);
			way.setEnd(tempEndWaypoint);

			// set waypointlist for the way
			way.setWaypointList(waypointsFromWay);
		}
	}

	private static void setSelectedWays(Document document, XPath xpath,
			List<String> selectWayList) throws XPathExpressionException {
		NodeList waysInXml = (NodeList) xpath.compile("/osm/way/tag").evaluate(
				document, XPathConstants.NODESET);

		// loop trough ways in xml
		for (int i = 0; i < waysInXml.getLength(); i++) {
			String vNodeValue = waysInXml.item(i).getAttributes()
					.getNamedItem("v").getNodeValue();
			if (selectWayList.contains(vNodeValue)) {
				long id = Long.parseLong(waysInXml.item(i).getParentNode()
						.getAttributes().getNamedItem("id").getNodeValue());

				// create new way
				Way way = new Way(id);

				// add way to
				MatchingWayList.add(way);
			} else {
				throw new IllegalArgumentException(
						"filter for street not matched with xml!");
			}
		}
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

	private Document makeConnection(URL url) {
		Document document = null;
		try {
			URLConnection connection = url.openConnection();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			dbFactory.setNamespaceAware(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			document = dBuilder.parse(connection.getInputStream());
			document.getDocumentElement().normalize();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return document;
	}

}
