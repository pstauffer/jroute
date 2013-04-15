package ch.zhaw.jroute.controller;

import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ch.zhaw.jroute.model.Waypoint;

class NodeHandler {

	private static Waypoint node1 = new Waypoint();

	public static void main(String argv[]) {

		try {

			URL url = new URL(
					"http://www.openstreetmap.org/api/0.6/node/1701898656");
			URLConnection connection = url.openConnection();

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = dBuilder.parse(connection.getInputStream());

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("node");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("Current Element " + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					int nodeID = Integer.parseInt(eElement.getAttribute("id"));
					float lat = Float.parseFloat(eElement.getAttribute("lat"));
					float lon = Float.parseFloat(eElement.getAttribute("lon"));

					node1.setNodeID(nodeID);
					node1.setLat(lat);
					node1.setLon(lon);

				}

			}

			System.out.println("nodeID " + node1.getNodeID());
			System.out.println("lat " + node1.getLat());
			System.out.println("lon " + node1.getLon());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}