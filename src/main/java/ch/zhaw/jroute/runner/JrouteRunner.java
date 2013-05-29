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

public class JrouteRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// load config
		ConfigHandler configHandler = new ConfigHandler();

		float lat = Float.parseFloat(configHandler
				.getConfig("INITIAL_LATITUDE"));
		float lon = Float.parseFloat(configHandler
				.getConfig("INITIAL_LONGITUDE"));
		float alt = Float.parseFloat(configHandler
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
		GUIController guiController = new GUIController(view, waypointBuilder,
				wayBuilder, boxHandler);
	}
}
