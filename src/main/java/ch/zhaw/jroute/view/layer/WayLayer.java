package ch.zhaw.jroute.view.layer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ch.zhaw.jroute.model.Way;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AnnotationAttributes;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.ShapeAttributes;

public class WayLayer extends RenderableLayer implements Observer{

	private Way currentWay;
	private ShapeAttributes attrs;
	
	public WayLayer(){
		super();
		this.setShapeAttributes();
	}
	
	private void setShapeAttributes(){
        attrs = new BasicShapeAttributes();
        attrs.setOutlineMaterial(Material.BLACK);
        attrs.setOutlineWidth(2d);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Way){
			currentWay = (Way)arg1;
			currentWay.setAttributes(attrs);
			
			currentWay.setFollowTerrain(true);
			currentWay.setVisible(true);
			currentWay.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
			currentWay.setPathType(AVKey.LINEAR);
	        this.addRenderable(currentWay);
	        //this.wwd.redraw();
			
		}else if(arg1 instanceof Position){
			List<Position> newPositions = new ArrayList();
			newPositions.addAll((Collection<? extends Position>) currentWay.getPositions());
			newPositions.set(1, (Position) arg1);
			currentWay.setPositions(newPositions);
		}else if(arg1 == null){
			
		}
		
	}
}
