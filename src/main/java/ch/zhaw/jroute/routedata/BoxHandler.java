package ch.zhaw.jroute.routedata;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
	private static List<Waypoint> matchingWaypointList = new ArrayList<Waypoint>();
	private static List<Way> matchingWayList = new ArrayList<Way>();
	private static List<String> streetFilterList = new ArrayList<String>();

	@Override
	public List<Way> getAllWays(double left, double bottom, double right,
			double top) throws IOException {
		// start method time measuring
		long startMethodTime = System.nanoTime();

		// check for correct coordinates
		checkLatitudeCoordinates(bottom, top);
		checkLongitudeCoordinates(left, right);

		// create url
		URL boxURL = new URL(openStreetMapBoxURL + left + "," + bottom + ","
				+ right + "," + top);

		// start timer for connection
		long startApiCallTime = System.nanoTime();

		// get document via connection
		Document document = getDocumentOverNewConnection(boxURL);

		// stop timer for connection
		long endApiCallTime = System.nanoTime();
		long apiCallTime = endApiCallTime - startApiCallTime;

		// create xpath instance
		XPath xpath = XPathFactory.newInstance().newXPath();

		// set street filter
		addStreetFilter("motorway");
		addStreetFilter("tertiary");
		addStreetFilter("residential");
		// addStreetFilter("any");

		try {

			// get all ways, which match with the filter
			long setSelectedWaysStartTime = System.nanoTime();
			setSelectedWays(document, xpath, streetFilterList);
			long setSelectedWaysEndTime = System.nanoTime();

			// set all waypoints for the ways
			long setWaypointsForWaysStartTime = System.nanoTime();
			setWaypointsForWays(matchingWayList, document, xpath);
			long setWaypointsForWaysEndTime = System.nanoTime();

			// get and set all waypoint values
			long setValuesForWaypointsStartTime = System.nanoTime();
			setValuesForWaypoints(matchingWaypointList, document, xpath);
			long setValuesForWaypointsEndTime = System.nanoTime();

			// performance tests
			System.out.println("time setSelectedWaysTime : "
					+ (setSelectedWaysEndTime - setSelectedWaysStartTime)
					+ " ns");
			System.out
					.println("time setWaypointsForWaysTime : "
							+ (setWaypointsForWaysEndTime - setWaypointsForWaysStartTime)
							+ " ns");
			System.out
					.println("time setValuesForWaypointsTime : "
							+ (setValuesForWaypointsEndTime - setValuesForWaypointsStartTime)
							+ " ns");

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		// create wayList for return
		List<Way> wayList = new ArrayList<Way>();
		// insert all matched ways
		wayList.addAll(matchingWayList);

		// counting ways and waypoints for debugging
		int matchedWaySize = matchingWayList.size();
		int matchedWaypointSize = matchingWaypointList.size();
		int filterSize = streetFilterList.size();

		// cleanup <- useful??
		document = null;
		xpath = null;
		matchingWaypointList.clear();
		matchingWayList.clear();
		streetFilterList.clear();

		// stop timer for connection
		long endMethodTime = System.nanoTime();
		long methodTime = endMethodTime - startMethodTime;

		// sysout for debugging
		System.out.println("get all data from openstreetmap took: "
				+ apiCallTime + " ns");
		System.out.println("running whole method took: " + methodTime + " ns");
		System.out.println("time for document processing : "
				+ (methodTime - apiCallTime) + " ns");
		System.out.println("total ways matched: " + matchedWaySize);
		System.out.println("total waypoints matched: " + matchedWaypointSize);
		System.out.println("filter size: " + filterSize);

		return wayList;
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

			// set latitude and longitude for waypoint
			waypoint.setLat(lat);
			waypoint.setLon(lon);

			// set position (lon and lat in one value)
			Angle latAngle = Angle.fromDegreesLatitude(lat);
			Angle lonAngle = Angle.fromDegreesLatitude(lon);
			Position pos = new Position(latAngle, lonAngle, 0);
			waypoint.setCenter(pos);
		}
	}

	private static void setWaypointsForWays(List<Way> ways, Document document,
			XPath xpath) throws XPathExpressionException {

		// loop trough all ways
		for (Way way : ways) {
			long wayID = way.getWayID();

			// create new waypointlist for every way
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
						matchingWaypointList.add(waypoint);
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
			List<String> streetFilterList) throws XPathExpressionException {

		if (streetFilterList.isEmpty()) {
			throw new IllegalArgumentException("street filter is empty");
		}

		NodeList waysInXml = (NodeList) xpath.compile("/osm/way/tag").evaluate(
				document, XPathConstants.NODESET);

		// loop trough ways in xml
		for (int i = 0; i < waysInXml.getLength(); i++) {

			String vNodeValue = waysInXml.item(i).getAttributes()
					.getNamedItem("v").getNodeValue();

			if (streetFilterList.contains(vNodeValue)) {
				long id = Long.parseLong(waysInXml.item(i).getParentNode()
						.getAttributes().getNamedItem("id").getNodeValue());

				// create new way
				Way way = new Way(id);

				// add way to list
				matchingWayList.add(way);
			}

		}

		if (matchingWayList.isEmpty()) {
			throw new IllegalArgumentException(
					"street filter not matched with value in xml file");
		}

	}

	private void checkLongitudeCoordinates(double left, double right) {
		if (left > 90 || left < -90) {
			throw new IllegalArgumentException(
					"left longitude must be between -90 and 90");
		}
		if (right > 90 || right < -90) {
			throw new IllegalArgumentException(
					"left longitude must be between -90 and 90");
		}
		if (left > right) {
			throw new IllegalArgumentException(
					"first parameter can't be bigger than second!!");
		}

	}

	private void checkLatitudeCoordinates(double bottom, double top) {
		if (top > 180 || top < -180) {
			throw new IllegalArgumentException(
					"top latitude must be between -180 and 180");
		}
		if (bottom > 180 || bottom < -180) {
			throw new IllegalArgumentException(
					"bottom latitude must be between -180 and 180");
		}
		if (bottom > top) {
			throw new IllegalArgumentException(
					"first parameter can't be bigger than second!!");
		}

	}

	private Document getDocumentOverNewConnection(URL url) {
		Document document = null;
		try {
			// open connection
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			dbFactory.setNamespaceAware(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			document = dBuilder.parse(connection.getInputStream());
			document.getDocumentElement().normalize();

			// close connection
			connection.disconnect();

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
