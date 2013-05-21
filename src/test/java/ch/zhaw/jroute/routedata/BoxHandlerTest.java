package ch.zhaw.jroute.routedata;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoxHandlerTest {
	
	BoxHandler testObject;

	@Before
	public void setUp() throws Exception {
		
		IAPIConnector apiConnector = new MockAPIConnector();
		
		testObject = new BoxHandler(apiConnector);
		
		
	}

	@Test
	public void testGetAllWays() {
		fail("Not yet implemented");
	}

}
