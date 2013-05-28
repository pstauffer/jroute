package ch.zhaw.jroute.routedata;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import ch.zhaw.jroute.model.Way;

public class BoxHandlerTestBig {
	private static Logger logger = Logger.getLogger("org.apache.log4j");
	BoxHandler testObject;
	List<Way> allWaysFromXML;

	@Before
	public void setUp() throws Exception {
		logger.setLevel(org.apache.log4j.Level.INFO);
		IAPIConnector apiConnector = new APIConnectorMockBig();
		testObject = new BoxHandler(apiConnector);
		allWaysFromXML = testObject.getAllWays(0, 0, 0, 0);

	}

	@Test
	public void countWays() {
		assertTrue(allWaysFromXML.size() == 460);
	}

	@Test
	public void countWaypionts() {
		int count = 0;

		for (Way way : allWaysFromXML) {
			count = count + way.getWaypointList().size();
		}

		assertTrue(count == 3044);
	}

}
