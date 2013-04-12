package ch.zhaw.jroute.controller;

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

import ch.zhaw.jroute.model.NodePascal;
import ch.zhaw.jroute.model.Way;

class WayHandler {
	private static List<Way> allWays = new ArrayList<Way>();

	public static void main(String argv[]) {

		getWayFromID(46116390);
		getWayFromID(46116391);

		getDistance((float) 47.43714, (float) 9.10741, (float) 47.42298,
				(float) 9.13668);

		// check distance 30m
		getDistance((float) 40, (float) 10, (float) 40, (float) 10.00035);

		for (Way ways : allWays) {
			System.out.println("ways " + ways.getWayID());
			for (NodePascal nodes : ways.getNodeList()) {
				System.out.println("nodes " + nodes.getNodeID());
			}
		}

	}

	/**
	 * calculate the distance between to nodes
	 * 
	 * @return ...
	 */
	public static void getDistance(float lat1, float lon1, float lat2,
			float lon2) {
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
			List<NodePascal> list1 = new ArrayList<NodePascal>();

			for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {

				Node nNode2 = nList2.item(temp2);

				if (nNode2.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode2;

					int nodeID = Integer.parseInt(eElement.getAttribute("id"));
					float lat = Float.parseFloat(eElement.getAttribute("lat"));
					float lon = Float.parseFloat(eElement.getAttribute("lon"));

					list1.add(new NodePascal(nodeID, lat, lon));

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