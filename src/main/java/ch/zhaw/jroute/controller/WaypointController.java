package ch.zhaw.jroute.controller;

import gov.nasa.worldwind.geom.Position;
import ch.zhaw.jroute.model.WaypointBuilder;
import ch.zhaw.jroute.view.JrouteView;
import ch.zhaw.jroute.view.layer.WaypointLayer;

public class WaypointController implements IWaypointController {
	
	private WaypointBuilder waypointBuilder;
	private JrouteView view;
	
	public WaypointController(WaypointLayer nodeLayer, JrouteView view){
		this.waypointBuilder = new WaypointBuilder();
		this.view = view;
		
		this.waypointBuilder.addObserver(nodeLayer);
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.jroute.controller.INodeController#createNewNode(gov.nasa.worldwind.geom.Position)
	 */
	@Override
	public void createNewNode(Position position){
		waypointBuilder.createWaypointFromPosition(view.getWwd().getCurrentPosition());
	}
	
	
	

}
