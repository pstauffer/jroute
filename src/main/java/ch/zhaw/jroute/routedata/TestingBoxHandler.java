package ch.zhaw.jroute.routedata;

import java.io.IOException;

import org.apache.log4j.Logger;

public class TestingBoxHandler {
	private static Logger logger = Logger.getLogger("org.apache.log4j");

	public static void main(String[] args) {
		BoxHandler box = new BoxHandler();
		BoxHandlerOpenStreetMap box2 = new BoxHandlerOpenStreetMap();

		try {

			// List<Way> ways = box.getAllWays(-85.13076, 34.90578, -85.11613,
			// 34.91437);
			box.getAllWays(-85.13076, 34.90578, -85.11613, 34.91437);
			box2.getAllWays(-85.13076, 34.90578, -85.11613, 34.91437);

			// for (Way way : ways) {
			// logger.debug("--------");
			// logger.debug(" Way ID: " + way.getWayID());
			// logger.debug("--------");
			// for (Waypoint waypoints : way.getWaypointList()) {
			// logger.debug(waypoints.getWaypointID() + " - "
			// + waypoints.getLat() + " - " + waypoints.getLon());
			// }
			// }

		} catch (IOException e) {
			logger.fatal(e);
		}

	}
}
