package ch.zhaw.jroute.controller;

import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WayHandler {

	public static void main(String argv[]) {

		try {

			URL url = new URL(
					"http://www.openstreetmap.org/api/0.6/way/46116390");
			URLConnection connection = url.openConnection();

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = dBuilder.parse(connection.getInputStream());

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("way");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println("way id : "
							+ eElement.getAttribute("id"));

				}

				NodeList nList2 = doc.getElementsByTagName("nd");

				for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {

					Node haaa = nList2.item(temp2);

					if (haaa.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) haaa;

						System.out.println("nd ref : "
								+ eElement.getAttribute("ref"));

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}