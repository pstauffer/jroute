import ch.zhaw.jroute.controller.WaypointController;
import ch.zhaw.jroute.model.WaypointBuilder;
import ch.zhaw.jroute.view.JrouteView;
import ch.zhaw.jroute.view.template.ApplicationTemplate;


public class JrouteRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JrouteView view = (JrouteView) ApplicationTemplate.start("Jroute", JrouteView.class);
		WaypointBuilder waypointBuilder = new WaypointBuilder();
		WaypointController waypointController = new WaypointController(view,waypointBuilder);
	}

}
