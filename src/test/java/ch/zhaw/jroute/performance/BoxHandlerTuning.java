package ch.zhaw.jroute.performance;

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

import ch.zhaw.jroute.model.businessObjects.Way;
import ch.zhaw.jroute.model.businessObjects.Waypoint;
import ch.zhaw.jroute.routedata.IBoxHandler;

/**
 * BoxHandler Class for API Calls to openstreetmap.org needed for getting all
 * ways and waypoints with all values
 * 
 * defined through Interface IBoxHandler
 * 
 * @author pascal
 */
public class BoxHandlerTuning implements IBoxHandler {
	private final String openStreetMapBoxURL = "http://api.openstreetmap.org/api/0.6/map?bbox=";
	private static List<Waypoint> matchingWaypointList = new ArrayList<Waypoint>();
	private static List<Way> matchingWayList = new ArrayList<Way>();
	private static List<String> streetFilterList = new ArrayList<String>();

	/**
	 * defined through Interface IBoxHandler
	 */
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
			setSelectedWays(streetFilterList, document, xpath);
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
		matchingWaypointList.clear(); // useful? objects already in use
		matchingWayList.clear(); // useful? objects already in use
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

	/**
	 * adding an additional filter-value for the streets
	 * 
	 * @param filter
	 */
	private static void addStreetFilter(String filter) {
		streetFilterList.add(filter);
	}

	/**
	 * get and set the coordinate values for the matched waypoints also set the
	 * position for a waypoint
	 * 
	 * @param waypointlist
	 * @param document
	 * @param xpath
	 * @throws XPathExpressionException
	 */
	private static void setValuesForWaypoints(List<Waypoint> waypointList,
			Document document, XPath xpath) throws XPathExpressionException {

		// loop trough all matching waypoints
		for (Waypoint waypoint : waypointList) {

			long waypointID = waypoint.getWaypointID();
			String latWaypointInXml = (String) xpath.compile(
					"/osm/node[@id='" + waypointID + "']/@lat").evaluate(
					document, XPathConstants.STRING);
			String lonWaypointInXml = (String) xpath.compile(
					"/osm/node[@id='" + waypointID + "']/@lon").evaluate(
					document, XPathConstants.STRING);

			double lat = Double.parseDouble(latWaypointInXml);
			double lon = Double.parseDouble(lonWaypointInXml);

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

	/**
	 * get all waypoints for matched ways and set a waypointlist for every way
	 * set the start and end waypoint for a way
	 * 
	 * @param waylist
	 * @param document
	 * @param xpath
	 * @throws XPathExpressionException
	 */
	private static void setWaypointsForWays(List<Way> wayList,
			Document document, XPath xpath) throws XPathExpressionException {

		// loop trough all ways
		for (Way way : wayList) {
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

	/**
	 * get all ways, which contains the filter in the filterlist
	 * 
	 * @param streetFilterList
	 * @param document
	 * @param xpath
	 * @throws XPathExpressionException
	 * @exception IllegalArgumentException
	 */
	private static void setSelectedWays(List<String> streetFilterList,
			Document document, XPath xpath) throws XPathExpressionException {

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

	/**
	 * check, if the longitude coordinates are correct
	 * 
	 * @param left
	 * @param right
	 * @exception IllegalArgumentException
	 */
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

	/**
	 * check, if the latitude coordinates are correct
	 * 
	 * @param bottom
	 * @param top
	 * @exception IllegalArgumentException
	 */
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

	/**
	 * open connection to openstreetmap url, create new document and after that,
	 * close the connection
	 * 
	 * @param url
	 * @return document
	 * @exception IOException
	 * @exception ParserConfigurationException
	 * @exception SAXException
	 */
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
