package ch.zhaw.jroute.view.layer;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AnnotationAttributes;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Renderable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import ch.zhaw.jroute.model.businessObjects.Waypoint;
import ch.zhaw.jroute.model.util.WaypointStatusEnum;

/**
 * Represents the layer which displays all waypoints on the worldwind globe
 * @author yk
 *
 */
public class WaypointLayer extends RenderableLayer implements Observer{

	private AnnotationAttributes annotationStyle;
	private AnnotationLayer waypointAnnotationLayer;
	
	public WaypointLayer(AnnotationLayer waypointAnnotationLayer){
		super();
		this.setAnnotationStyle();
		this.waypointAnnotationLayer = waypointAnnotationLayer;
	}
	
	/**
	 * method which is called if the model is updated with new data
	 * adds the actual waypoint to the layer
	 */
	public void update(Observable o, Object arg) {
		Waypoint waypoint = (Waypoint)arg;
		waypoint.setAttributes(getShapeStyle(Color.GRAY));
		
		if(waypoint.getStatus()==WaypointStatusEnum.start||waypoint.getStatus()==WaypointStatusEnum.end){
			//Check if the waypoint allready exists
			for(Renderable temp : this.getRenderables()){
				Waypoint existingWp = (Waypoint)temp;
				if(existingWp.getWaypointID() == waypoint.getWaypointID()){
					this.removeRenderable(existingWp);
					waypoint = handleExistingWaypoint(waypoint);
				}
			}
		}
		
		//addAnnotation(waypoint);
		this.addRenderable(waypoint);
	}
	
	/**
	 * removes all waypoints from the layer
	 */
	public void cleanLayer(){
		this.removeAllRenderables();
	}
	
	/**
	 * Adds an annotation to the waypoint
	 * unused at the moment because of missing config
	 * @param waypoint the waypoint to add an annotation
	 */
	@SuppressWarnings("unused")
	private void addAnnotation(Waypoint waypoint){
        GlobeAnnotation anno = new GlobeAnnotation(waypoint.getName(),waypoint.getReferencePosition(), this.annotationStyle);
        anno.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
        waypointAnnotationLayer.addAnnotation(anno);
	}
	
	/**
	 * Defines the color of a waypoint with the status enum
	 * @param waypoint to handle
	 * @return colored waypoint
	 */
	private Waypoint handleExistingWaypoint(Waypoint waypoint){
		switch(waypoint.getStatus()){
		case undefined:
			waypoint.setAttributes(getShapeStyle(Color.GRAY));
			break;
		case start:
			waypoint.setAttributes(getShapeStyle(Color.GREEN));
			break;
		case end:
			waypoint.setAttributes(getShapeStyle(Color.RED));
			break;
		default:
			break;
		}
		
		return waypoint;
	}
	
	/**
	 * Creates the style attributes for a Waypoint
	 * 
	 * @return style attributes
	 */
	private BasicShapeAttributes getShapeStyle(Color color){
		BasicShapeAttributes shapeStyle = new BasicShapeAttributes();
		shapeStyle.setDrawOutline(true);
		shapeStyle.setInteriorMaterial(new Material(color));
		shapeStyle.setInteriorOpacity(1.0);
		shapeStyle.setOutlineMaterial(new Material(color));
		shapeStyle.setOutlineOpacity(1.0);
		shapeStyle.setOutlineWidth(2.0);
		shapeStyle.setDrawInterior(true);
		return shapeStyle;
	}
	
	/**
	 * Creates the style ot the annotation
	 */
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
