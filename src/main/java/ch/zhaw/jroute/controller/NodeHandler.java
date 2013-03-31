package ch.zhaw.jroute.controller;

import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeHandler {

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

					System.out.println("node id : "
							+ eElement.getAttribute("id"));

					System.out.println("lat : " + eElement.getAttribute("lat"));
					System.out.println("lat : " + eElement.getAttribute("lon"));

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}