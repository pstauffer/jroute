import ch.zhaw.jroute.controller.GUIController;
import ch.zhaw.jroute.model.WayBuilder;
import ch.zhaw.jroute.model.WaypointBuilder;
import ch.zhaw.jroute.routedata.APIConnector;
import ch.zhaw.jroute.routedata.BoxHandler;
import ch.zhaw.jroute.routedata.IAPIConnector;
import ch.zhaw.jroute.view.JrouteView;
import ch.zhaw.jroute.view.template.ApplicationTemplate;

public class JrouteRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
