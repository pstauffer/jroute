package ch.zhaw.jroute.routedata;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Class to connect the OpenStreetMap API
 * @author ps
 */
public class APIConnector implements IAPIConnector {

	private static Logger logger = Logger.getLogger("org.apache.log4j");

	public Document getDocumentOverNewConnection(URL url) throws IOException {
		Document document = null;
		HttpURLConnection connection = null;
		InputStream connectionStream = null;
		DocumentBuilder dBuilder = null;

		// open http connection
		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			logger.fatal("Error during http connection start", e);
			throw e;
		}

		// download file over http connection
		try {
			connectionStream = connection.getInputStream();
		} catch (IOException e) {
			logger.fatal("Error during download over api connection", e);
			connection.disconnect();
			throw e;
		}

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(true);

		// create document builder
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.fatal("Error during creation of xml parser", e);
			throw new RuntimeException(e);
		}

		// parse downloaded xml file
		try {
			document = dBuilder.parse(connectionStream);
		} catch (SAXException e) {
			logger.fatal("Error during the xml parse process", e);
			throw new RuntimeException(e);
		}

		document.getDocumentElement().normalize();

		// close connection
		connection.disconnect();

		return document;
	}

}
