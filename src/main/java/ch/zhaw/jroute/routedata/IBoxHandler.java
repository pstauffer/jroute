package ch.zhaw.jroute.routedata;

import java.io.IOException;
import java.util.List;

import ch.zhaw.jroute.model.businessObjects.Way;

/**
 * BoxHandler Interface for API Calls to openstreetmap.org needed for getting
 * all ways and waypoints with all values
 * 
 * @author pascal
 */
public interface IBoxHandler {

	/**
	 * give me all ways back from openstreetmap
	 * 
	 * @param left
	 *            (is the longitude of the left side of the box)
	 * @param bottom
	 *            (is the latitude of the bottom side of the box)
	 * @param right
	 *            (is the longitude of the right side of the box)
	 * @param top
	 *            (is the latitude of the top side of the box)
	 * @return List<Way> all Way included the Waypoints with longitude and
	 *         latitude
	 * @throws IOException 
	 */
	List<Way> getAllWays(double left, double bottom, double right, double top,List<String> filterList)
			throws IOException;
}
