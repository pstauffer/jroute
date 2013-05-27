package ch.zhaw.jroute.routedata;

import java.io.IOException;
import java.net.URL;

import org.w3c.dom.Document;

public interface IAPIConnector {
	public Document getDocumentOverNewConnection(URL url) throws IOException;
}
