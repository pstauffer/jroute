package ch.zhaw.jroute.routedata;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BBoxHandlerTest {

	public static void main(String[] args) {

		File fXmlFile = new File(
				"src/test/java/ch/zhaw/jroute/routedata/APIMock.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList wayList = doc.getElementsByTagName("way");
			System.out.println("----------------------------");
			for (int temp = 0; temp < wayList.getLength(); temp++) {
				Node nNode = wayList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("way id : "
							+ eElement.getAttribute("id"));
					NodeList nd = eElement.getElementsByTagName("nd");

					for (int bla = 0; bla < nd.getLength(); bla++) {
						Node blaNode = nd.item(bla);
						System.out.println("\nCurrent Element :"
								+ blaNode.getNodeName());
						if (blaNode.getNodeType() == Node.ELEMENT_NODE) {
							Element blaElement = (Element) blaNode;
							System.out.println("node id : "
									+ blaElement.getAttribute("ref"));
						}

					}

				}
				System.out.println("----------------------------");

			}

			NodeList nodeList = doc.getElementsByTagName("node");
			System.out.println("----------------------------");
			for (int temp = 0; temp < nodeList.getLength(); temp++) {
				Node nNode = nodeList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("node id : "
							+ eElement.getAttribute("id"));
					System.out.println("lat : " + eElement.getAttribute("lat"));
					System.out.println("lon : " + eElement.getAttribute("lon"));

				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
