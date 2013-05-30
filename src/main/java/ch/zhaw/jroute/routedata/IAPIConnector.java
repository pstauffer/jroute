package ch.zhaw.jroute.routedata;

import java.io.IOException;
import java.net.URL;

import org.w3c.dom.Document;

/**
 * 
 * @author yk
 *
 */
public interface IAPIConnector {
	
	/**
	 * open connection to a openstreetmap url, create new document and after that,
	 * close the connection
	 * 
	 * @param url to the openstreetmap API 
	 * @return document the xml response of the API
	 * @throws IOException if call fails or call is invalid
	 */
	public Document getDocumentOverNewConnection(URL url) throws IOException;
}
