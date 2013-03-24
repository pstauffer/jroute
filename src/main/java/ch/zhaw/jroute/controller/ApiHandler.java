package ch.zhaw.jroute.controller;

import java.io.IOException;

public class ApiHandler {

	public static void main(String[] args) throws IOException {
		// http://www.openstreetmap.org/api/0.6/way/46116390
		// nd ref = node
		// way id="46116390"
		// node id="1701898656"
		// lat="40.7238783" lon="-74.007475"

		HttpHandler.getNode(1701898656);
		HttpHandler.getWay(46116390);
		HttpHandler.getWay(46116390);

	}
}
