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

public class WayHandler {
	static List<Way> allWays = new ArrayList<Way>();

	public static void main(String argv[]) {

		getWayFromID(46116390);
		getWayFromID(46116391);

		for (Way ways : allWays) {
			System.out.println("ways " + ways.getWayID());
			for (NodePascal nodes : ways.getNodeList()) {
				System.out.println("nodes " + nodes.getNodeID());
			}
		}

	}

	public static void getWayFromID(int id) {

		try {

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