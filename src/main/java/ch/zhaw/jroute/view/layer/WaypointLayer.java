package ch.zhaw.jroute.view.layer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import ch.zhaw.jroute.model.Waypoint;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AnnotationAttributes;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.ShapeAttributes;

public class WaypointLayer extends RenderableLayer implements Observer{

	private ShapeAttributes shapeStyle;
	private AnnotationAttributes annotationStyle;
	private AnnotationLayer waypointAnnotationLayer;
	
	public WaypointLayer(AnnotationLayer waypointAnnotationLayer){
		super();
		this.setWaypointStyle();
		this.setAnnotationStyle();
		this.waypointAnnotationLayer = waypointAnnotationLayer;
	}
	
	public void update(Observable o, Object arg) {
		
		Waypoint waypoint = (Waypoint)arg;
		
		for(Renderable temp : this.getRenderables()){
			Waypoint existingWp = (Waypoint)temp;
			
			if(existingWp.getWaypointID() == waypoint.getWaypointID()){
				this.removeRenderable(existingWp);
				//Some crazy stuff happens
				//handleExistingWaypoint(waypoint);
			}
		}
		
		waypoint.setAttributes(shapeStyle);
		addAnnotation(waypoint);
		this.addRenderable(waypoint);
	}
	
	private void addAnnotation(Waypoint waypoint){
        GlobeAnnotation anno = new GlobeAnnotation(waypoint.getName(),waypoint.getReferencePosition(), this.annotationStyle);
        anno.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
        waypointAnnotationLayer.addAnnotation(anno);
	}
	
	//TODO: UGLY REDOOOO!!!
	private void handleExistingWaypoint(Waypoint waypoint){
		switch(waypoint.getStatus()){
		case undefined:
			shapeStyle.setInteriorMaterial(new Material(Color.GRAY));
			shapeStyle.setOutlineMaterial(new Material(Color.GRAY));
			waypoint.setAttributes(shapeStyle);
			break;
		case start:
			shapeStyle.setInteriorMaterial(new Material(Color.GREEN));
			shapeStyle.setOutlineMaterial(new Material(Color.GREEN));
			waypoint.setAttributes(shapeStyle);
			break;
		case end:
			shapeStyle.setInteriorMaterial(new Material(Color.RED));
			shapeStyle.setOutlineMaterial(new Material(Color.RED));
			waypoint.setAttributes(shapeStyle);
			break;
		default:
			break;
		}
		
		addAnnotation(waypoint);
		this.addRenderable(waypoint);
		
		//shapeStyle.setInteriorMaterial(new Material(Color.GRAY));
		//shapeStyle.setOutlineMaterial(new Material(Color.GRAY));
	}

	/**
	 * Creates the style attributes for a Waypoint
	 * 
	 * @return style attributes
	 */
	private void setWaypointStyle() {
		shapeStyle = new BasicShapeAttributes();
		shapeStyle.setDrawOutline(true);
		shapeStyle.setInteriorMaterial(new Material(Color.GRAY));
		shapeStyle.setInteriorOpacity(1.0);
		shapeStyle.setOutlineMaterial(new Material(Color.GRAY));
		shapeStyle.setOutlineOpacity(1.0);
		shapeStyle.setOutlineWidth(2.0);
		shapeStyle.setDrawInterior(true);
	}
	
	private void setAnnotationStyle(){
        annotationStyle = new AnnotationAttributes();
        annotationStyle.setAdjustWidthToText(AVKey.SIZE_FIT_TEXT);
        annotationStyle.setFrameShape(AVKey.SHAPE_RECTANGLE);
        annotationStyle.setDrawOffset(new Point(0, 10));
        annotationStyle.setLeaderGapWidth(5);
        annotationStyle.setTextColor(Color.BLACK);
        annotationStyle.setBackgroundColor(new Color(1f, 1f, 1f, 0.8f));
        annotationStyle.setCornerRadius(5);
        annotationStyle.setBorderColor(new Color(0xababab));
        annotationStyle.setFont(Font.decode("Arial-PLAIN-12"));
        annotationStyle.setTextAlign(AVKey.CENTER);
        annotationStyle.setInsets(new Insets(5, 5, 5, 5));
	}
}
