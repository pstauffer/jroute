package ch.zhaw.jroute.routedata;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
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

public class BoxHandlerSave implements IBoxHandler {
	final String openStreetMapBoxURL = "http://api.openstreetmap.org/api/0.6/map?bbox=";

	@Override
	public List<Way> getAllWays(double left, double bottom, double right,
			double top) throws IOException {
		List<Way> allWays = new ArrayList<Way>();
		HashMap<Long, Waypoint> waypointMap = new HashMap<Long, Waypoint>();
		HashMap<Long, Waypoint> wayMap = new HashMap<Long, Waypoint>();

		checkCoordinates(left, bottom, right, top);

		try {

			URL url = new URL(openStreetMapBoxURL + left + "," + bottom + ","
					+ right + "," + top);
			URLConnection connection = url.openConnection();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			dbFactory.setNamespaceAware(true);

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(connection.getInputStream());
			document.getDocumentElement().normalize();
			XPath xpath = XPathFactory.newInstance().newXPath();

			NodeList waysInXml = (NodeList) xpath.compile("/osm/way").evaluate(
					document, XPathConstants.NODESET);
			for (int i = 0; i < waysInXml.getLength(); i++) {
				NodeList wayChilds = waysInXml.item(i).getChildNodes();

				for (int j = 0; j < wayChilds.getLength(); j++) {
					if (wayChilds.item(j).getNodeName() == "tag") {
						String street = wayChilds.item(j).getAttributes()
								.getNamedItem("v").getNodeValue();
						String streetCompare = "residential";
						if (street.equals(streetCompare)) {
							long wayID = Long.parseLong(waysInXml.item(i)
									.getAttributes().getNamedItem("id")
									.getNodeValue());
							allWays.add(new Way(wayID));
						}
					}
				}
			}

			// NodeList waypointsFromWay = (NodeList)
			// xpath.compile("/osm/way/nd")
			// .evaluate(document, XPathConstants.NODESET);
			// for (int i = 0; i < waypointsFromWay.getLength(); i++) {
			// long wayID = Long.parseLong(waypointsFromWay.item(i)
			// .getParentNode().getAttributes().getNamedItem("id")
			// .getNodeValue());
			//
			// for (Way ways : allWays) {
			// if (ways.getWayID() == wayID) {
			// long waypointID = Long.parseLong(waypointsFromWay
			// .item(i).getAttributes().getNamedItem("ref")
			// .getNodeValue());
			// Waypoint tempWaypoint = new Waypoint(waypointID);
			// waypointMap.put(waypointID, tempWaypoint);
			// wayMap.put(wayID, tempWaypoint);
			// }
			// }
			// }
			//
			// NodeList waypointInXML = (NodeList) xpath.compile("/osm/node")
			// .evaluate(document, XPathConstants.NODESET);
			// for (int i = 0; i < waypointInXML.getLength(); i++) {
			//
			// long waypointID = Long.parseLong(waypointInXML.item(i)
			// .getAttributes().getNamedItem("id").getNodeValue());
			//
			// if (waypointMap.containsValue(waypointID)) {
			// double lon = Double
			// .parseDouble(waypointInXML.item(i).getAttributes()
			// .getNamedItem("lon").getNodeValue());
			// double lat = Double
			// .parseDouble(waypointInXML.item(i).getAttributes()
			// .getNamedItem("lat").getNodeValue());
			// waypointMap.get(waypointID).setLat(lat);
			// waypointMap.get(waypointID).setLon(lon);
			// }
			// }

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return null;
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
