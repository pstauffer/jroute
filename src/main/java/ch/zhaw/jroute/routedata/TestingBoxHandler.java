package ch.zhaw.jroute.routedata;

import java.util.List;

import org.apache.log4j.Logger;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class TestingBoxHandler {
	private static Logger logger = Logger.getLogger("org.apache.log4j");

	public static void main(String[] args) {

		APIConnector connector = new APIConnector();
		BoxHandler box = new BoxHandler(connector);
		// BoxHandlerWaypoints box = new BoxHandlerWaypoints(connector);
		// BoxHandlerNew box = new BoxHandlerNew(connector);

		try {
			List<Way> ways = box.getAllWays(-85.43076, 34.90578, -85.00613,
					34.91437);

			for (Way way : ways) {
				logger.debug("--------");
				logger.debug(" Way ID: " + way.getWayID());
				logger.debug("--------");
				for (Waypoint waypoints : way.getWaypointList()) {
					logger.debug(waypoints.getWaypointID() + " - "
							+ waypoints.getLat() + " - " + waypoints.getLon());
				}
			}

		} catch (Exception e) {
			logger.fatal(e);
		}

	}
}
