package ch.zhaw.jroute.runner;

import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.avlist.AVKey;
import ch.zhaw.jroute.config.ConfigHandler;
import ch.zhaw.jroute.controller.GUIController;
import ch.zhaw.jroute.model.WayBuilder;
import ch.zhaw.jroute.model.WaypointBuilder;
import ch.zhaw.jroute.routedata.APIConnector;
import ch.zhaw.jroute.routedata.BoxHandler;
import ch.zhaw.jroute.view.JrouteView;
import ch.zhaw.jroute.view.template.ApplicationTemplate;

/**
 * Main class of jroute
 * @author yk
 *
 */
public class JrouteRunner {

	/**
	 * main method, sets the inital configuration of worldwind
	 * and creates view, the model and the main controller
	 * @param args
	 */
	public static void main(String[] args) {

		float lat = Float.parseFloat(ConfigHandler.getInstance()
				.getConfig("INITIAL_LATITUDE"));
		float lon = Float.parseFloat(ConfigHandler.getInstance()
				.getConfig("INITIAL_LONGITUDE"));
		float alt = Float.parseFloat(ConfigHandler.getInstance()
				.getConfig("INITIAL_ALTITUDE"));

		// Set inital point on the map
		Configuration.setValue(AVKey.INITIAL_LATITUDE, lat);
		Configuration.setValue(AVKey.INITIAL_LONGITUDE, lon);
		Configuration.setValue(AVKey.INITIAL_ALTITUDE, alt);

		// View
		JrouteView view = (JrouteView) ApplicationTemplate.start("Jroute",
				JrouteView.class);

		// Model
		WaypointBuilder waypointBuilder = new WaypointBuilder();
		WayBuilder wayBuilder = new WayBuilder();
		APIConnector apiConnector = new APIConnector();
		BoxHandler boxHandler = new BoxHandler(apiConnector);
		new GUIController(view, waypointBuilder,wayBuilder, boxHandler);
	}
}
