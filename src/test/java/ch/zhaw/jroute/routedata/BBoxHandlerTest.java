package ch.zhaw.jroute.routedata;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ch.zhaw.jroute.model.Waypoint;

public class BBoxHandlerTest {
	private static Set<Waypoint> waypointHashSet = new HashSet<Waypoint>();

	public static void main(String[] args) {

		File xmlFile = new File(
				"src/test/java/ch/zhaw/jroute/routedata/APIMock.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();

			NodeList allWaypoints = document.getElementsByTagName("node");
			System.out.println("----------------------------");
			for (int temp = 0; temp < allWaypoints.getLength(); temp++) {
				Node waypointItem = allWaypoints.item(temp);
				System.out.println("\nCurrent Element :"
						+ waypointItem.getNodeName());
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

					waypointHashSet.add(tempWaypoint);

					System.out.println("node id : "
							+ tempWaypoint.getWaypointID());
					System.out.println("lat : " + tempWaypoint.getLat());
					System.out.println("lon : " + tempWaypoint.getLon());

				}
			}

			NodeList allWays = document.getElementsByTagName("way");
			System.out.println("----------------------------");
			for (int temp = 0; temp < allWays.getLength(); temp++) {
				Node wayItem = allWays.item(temp);
				System.out.println("\nCurrent Element :"
						+ wayItem.getNodeName());
				if (wayItem.getNodeType() == Node.ELEMENT_NODE) {
					Element wayElement = (Element) wayItem;
					System.out.println("way id : "
							+ wayElement.getAttribute("id"));

					NodeList allWaypointsOfTheWays = wayElement
							.getElementsByTagName("nd");

					// get all the nodes from the way
					for (int i = 0; i < allWaypointsOfTheWays.getLength(); i++) {
						Node WaypointOfTheWayItem = allWaypointsOfTheWays
								.item(i);
						System.out.println("\nCurrent Element :"
								+ WaypointOfTheWayItem.getNodeName());
						if (WaypointOfTheWayItem.getNodeType() == Node.ELEMENT_NODE) {
							Element waypointOfTheWayElement = (Element) WaypointOfTheWayItem;
							System.out.println("node id : "
									+ waypointOfTheWayElement
											.getAttribute("ref"));
						}

					}

				}
				System.out.println("----------------------------");

			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(waypointHashSet.size());

	}
}
