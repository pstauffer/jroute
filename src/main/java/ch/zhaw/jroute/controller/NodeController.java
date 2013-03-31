package ch.zhaw.jroute.controller;

import gov.nasa.worldwind.geom.Position;
import ch.zhaw.jroute.model.NodeBuilder;
import ch.zhaw.jroute.view.JrouteView;
import ch.zhaw.jroute.view.layer.NodeLayer;

public class NodeController implements INodeController {
	
	private NodeBuilder nodeBuilder;
	private JrouteView view;
	
	public NodeController(NodeLayer nodeLayer, JrouteView view){
		this.nodeBuilder = new NodeBuilder();
		this.view = view;
		
		this.nodeBuilder.addObserver(nodeLayer);
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.jroute.controller.INodeController#createNewNode(gov.nasa.worldwind.geom.Position)
	 */
	@Override
	public void createNewNode(Position position){
		nodeBuilder.createNodeFromPosition(view.getWwd().getCurrentPosition());
	}

}
