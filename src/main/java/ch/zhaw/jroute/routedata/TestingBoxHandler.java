package ch.zhaw.jroute.routedata;

import java.io.IOException;
import java.util.List;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class TestingBoxHandler {

	public static void main(String[] args) {
		BoxHandler box = new BoxHandler();

		try {

			long startTime = System.nanoTime();
			List<Way> ways = box.getAllWays(-85.13076, 34.90578, -85.11613,
					34.91437);
			long endTime = System.nanoTime();
			long tookTime = endTime - startTime;

			for (Way way : ways) {
				System.out.println("--------");
				System.out.println(" Way ID: " + way.getWayID());
				System.out.println("--------");
				for (Waypoint waypoints : way.getWaypointList()) {
					System.out.println(waypoints.getWaypointID() + " - "
							+ waypoints.getLat() + " - " + waypoints.getLon());
				}
			}

			System.out.println("get all data took: " + tookTime + " ns");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
