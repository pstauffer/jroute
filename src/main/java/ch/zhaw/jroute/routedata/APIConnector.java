package ch.zhaw.jroute.routedata;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class APIConnector implements IAPIConnector {
	
	private static Logger logger = Logger.getLogger("org.apache.log4j");
	
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
	public Document getDocumentOverNewConnection(URL url) throws IOException {
		Document document = null;
		HttpURLConnection connection = null;

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			dbFactory.setNamespaceAware(true);
			DocumentBuilder dBuilder = null;
			
			try {
				dBuilder = dbFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				logger.fatal("Error during creation of xml parser", e);
				throw new RuntimeException(e);
			}
			
			// open connection
			try {
				connection = (HttpURLConnection) url
						.openConnection();
			} catch (IOException e) {
				logger.fatal("Error during data import from API", e);
				throw e;
			}
			
			
			try {
				document = dBuilder.parse(connection.getInputStream());
			} catch (SAXException e) {
				logger.fatal("Error during the xml parse process", e);
				throw new RuntimeException(e);
			} catch (IOException e) {
				logger.fatal("Error during parsing the imported Data", e);
				throw e;
			}finally{
				connection.disconnect();
			}
			
			document.getDocumentElement().normalize();

		
		return document;
	}

}
