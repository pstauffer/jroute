import ch.zhaw.jroute.controller.GUIController;
import ch.zhaw.jroute.model.WayBuilder;
import ch.zhaw.jroute.model.WaypointBuilder;
import ch.zhaw.jroute.routedata.BoxHandlerOpenStreetMap;
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
		BoxHandlerOpenStreetMap boxHandler = new BoxHandlerOpenStreetMap();
		GUIController guiController = new GUIController(view, waypointBuilder,
				wayBuilder, boxHandler);
	}

}
