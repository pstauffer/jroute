package ch.zhaw.jroute.routedata;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

class WayHandler {
	private static final int TestWayID1 = 46116390;
	private static final int TestWayID2 = 46116391;
	private static final double TestLat1 = 47.43714;
	private static final double TestLat2 = 47.42298;
	private static final double TestLon1 = 9.10741;
	private static final double TestLon2 = 9.13668;

	private static List<Way> allWays = new ArrayList<Way>();

	public static void main(String argv[]) {

		getWayFromID(TestWayID1);
		getWayFromID(TestWayID2);

		getDistance(TestLat1, TestLon1, TestLat2, TestLon2);

		// check distance 30m
		getDistance((float) 40, (float) 10, (float) 40, (float) 10.00035);

		for (Way ways : allWays) {
			System.out.println("ways " + ways.getWayID());
			for (Waypoint waypoints : ways.getWaypointList()) {
				System.out.println("nodes " + waypoints.getWaypointID());
			}
		}

	}

	/**
	 * calculate the distance between to nodes
	 * 
	 * @return ...
	 */
	public static void getDistance(double lat1, double lon1, double lat2,
			double lon2) {
		float latForDistance = (float) ((lat2 - lat1) * 111.11);
		float lonForDistance = (float) ((lon2 - lon1)
				* Math.cos(lat2 / 360 * 3.14 * 2) * 111.11);
		float distance = (float) Math.sqrt(latForDistance * latForDistance
				+ lonForDistance * lonForDistance);
		System.out.println("distanz: " + distance + " km");
		System.out.println("distanz: " + distance * 1000 + " m");

	}

	/**
	 * get all nodes from a wayid
	 * 
	 * @return ...
	 */
	public static void getWayFromID(int id) {

		try {

			// URL url = new URL(
			// "http://api.openstreetmap.org/api/0.6/map?bbox=-85.13076,34.90578,-85.11613,34.91437");

			URL url = new URL("http://www.openstreetmap.org/api/0.6/way/" + id
					+ "/full");

			URLConnection connection = url.openConnection();

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = dBuilder.parse(connection.getInputStream());

			doc.getDocumentElement().normalize();

			NodeList nList2 = doc.getElementsByTagName("node");
			List<Waypoint> list1 = new ArrayList<Waypoint>();

			for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {

				Node nNode2 = nList2.item(temp2);

				if (nNode2.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode2;

					int nodeID = Integer.parseInt(eElement.getAttribute("id"));
					float lat = Float.parseFloat(eElement.getAttribute("lat"));
					float lon = Float.parseFloat(eElement.getAttribute("lon"));

					list1.add(new Waypoint(nodeID, lat, lon));

				}

			}

			NodeList nList = doc.getElementsByTagName("way");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					int wayID = Integer.parseInt(eElement.getAttribute("id"));

					allWays.add(new Way(wayID, list1));

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}