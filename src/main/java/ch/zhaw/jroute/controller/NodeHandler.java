package ch.zhaw.jroute.controller;

import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ch.zhaw.jroute.model.NodePascal;

public class NodeHandler {

	static NodePascal node1 = new NodePascal();

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

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					int nodeID = Integer.parseInt(eElement.getAttribute("id"));
					String lat = eElement.getAttribute("lat");
					String lon = eElement.getAttribute("lon");

					node1.setNodeID(nodeID);
					node1.setLat(lat);
					node1.setLon(lon);

				}

			}

			System.out.println(node1.getNodeID());
			System.out.println(node1.getLat());
			System.out.println(node1.getLon());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}