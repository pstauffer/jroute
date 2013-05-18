package ch.zhaw.jroute.routedata;

import java.io.IOException;
import java.util.List;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public class TestingBoxHandler {
	static long startTime;
	static long endTime;
	static long tookTime;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BoxHandlerDK box = new BoxHandlerDK();
		BoxHandlerWithElements boxElements = new BoxHandlerWithElements();
		BoxHandlerXpath boxXpath = new BoxHandlerXpath();
		BoxHandlerXPathRegex boxXpathRegex = new BoxHandlerXPathRegex();

		try {
			List<Way> ways = box.getAllWays(-85.13076, 34.90578, -85.11613,
					34.91437);

			// startTime = System.currentTimeMillis();
			// boxXpathRegex.getAllWays(-85.13076, 34.90578, -85.11613,
			// 34.91437);
			// endTime = System.currentTimeMillis();
			// tookTime = endTime - startTime;
			// System.out
			// .println("method boxXpathRegex took: " + tookTime + " ms");
			//
			// startTime = System.currentTimeMillis();
			// boxElements.getAllWays(-85.13076, 34.90578, -85.11613, 34.91437);
			// endTime = System.currentTimeMillis();
			// tookTime = endTime - startTime;
			// System.out.println("method boxElements took: " + tookTime +
			// " ms");
			//
			// startTime = System.currentTimeMillis();
			// boxXpath.getAllWays(-85.13076, 34.90578, -85.11613, 34.91437);
			// endTime = System.currentTimeMillis();
			// tookTime = endTime - startTime;
			// System.out.println("method boxXpath took: " + tookTime + " ms");

			for (Way way : ways) {
				System.out.println("--------");
				System.out.println(" Way ID: " + way.getWayID());
				System.out.println("--------");
				for (Waypoint waypoints : way.getWaypointList()) {
					System.out.println(waypoints.getWaypointID());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
