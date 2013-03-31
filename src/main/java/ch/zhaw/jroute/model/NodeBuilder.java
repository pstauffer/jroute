package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class NodeBuilder extends Observable implements INodeBuilder{
	
	private List<Node> nodeList = new ArrayList<Node>();
	
	public void createNodeFromPosition(Position nodePosition){
        
		if (nodePosition == null)
            return;
		
		Node newNode = new Node(nodePosition,1000);
		
		newNode.setAttributes(getNodeStyle());
		nodeList.add(newNode);
		
		this.setChanged();
		this.notifyObservers(newNode);
		
	}
	
	/**
	 * Creates the style attributes for a node
	 * @return style attributes
	 */
	private ShapeAttributes getNodeStyle(){
        Color color = new Color(0f, 0f, 1f);
        ShapeAttributes attr = new BasicShapeAttributes();
        attr.setDrawOutline(true);
        attr.setInteriorMaterial(new Material(color));
        attr.setInteriorOpacity(1.0);
        attr.setOutlineMaterial(new Material(color));
        attr.setOutlineOpacity(1.0);
        attr.setOutlineWidth(2.0);
        attr.setDrawInterior(true);
        
        return attr;
	}


	
}
