package ch.zhaw.jroute.performance;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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

import ch.zhaw.jroute.model.businessObjects.Way;
import ch.zhaw.jroute.routedata.IBoxHandler;

public class BoxHandlerXPathRegex implements IBoxHandler {
	final String openStreetMapBoxURL = "http://api.openstreetmap.org/api/0.6/map?bbox=";

	@Override
	public List<Way> getAllWays(double left, double bottom, double right,
			double top) throws IOException {
		List<Way> allWays = new ArrayList<Way>();

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

			NodeList waysInXML = (NodeList) xpath.compile(
					"/osm/way/tag[@k='highway'][@v='residential']").evaluate(
					document, XPathConstants.NODESET);
			for (int i = 0; i < waysInXML.getLength(); i++) {
				long wayID = Long.parseLong(waysInXML.item(i).getParentNode()
						.getAttributes().getNamedItem("id").getNodeValue());
				allWays.add(new Way(wayID));
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return allWays;
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
