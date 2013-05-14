package ch.zhaw.jroute.routedata;

import java.io.IOException;
import java.util.List;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class TestingBox {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BoxHandler box = new BoxHandler();

		try {
			List<Way> ways = box.getAllWays(-85.13076, 34.90578, -85.11613,
					34.91437);
			System.out.println(ways.size());

			for (Way way : ways) {
				System.out.println("--------");
				System.out.println(" Way ID: " + way.getWayID());
				System.out.println("--------");
				for (Waypoint point : way.getWaypointList()) {
					System.out.println("Waypoint ID: " + point.getWaypointID()
							+ " Lon: " + point.getLon() + " Lat: "
							+ point.getLat() + "");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
