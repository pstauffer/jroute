package ch.zhaw.jroute.routedata;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class APIConnector implements IAPIConnector {
	
	/**
	 * open connection to openstreetmap url, create new document and after that,
	 * close the connection
	 * 
	 * @param url
	 * @return document
	 * @exception IOException
	 * @exception ParserConfigurationException
	 * @exception SAXException
	 */
	public Document getDocumentOverNewConnection(URL url) {
		Document document = null;
		try {
			// open connection
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			dbFactory.setNamespaceAware(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			document = dBuilder.parse(connection.getInputStream());
			document.getDocumentElement().normalize();

			// close connection
			connection.disconnect();

		} catch (IOException e) {
			//logger.fatal(e);
		} catch (ParserConfigurationException e) {
			//logger.fatal(e);
		} catch (SAXException e) {
			//logger.fatal(e);
		}
		return document;
	}

}
