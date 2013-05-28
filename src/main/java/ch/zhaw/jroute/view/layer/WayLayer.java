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
import ch.zhaw.jroute.model.WayStatusEnum;
import ch.zhaw.jroute.model.Waypoint;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AnnotationAttributes;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.util.measure.LengthMeasurer;

public class WayLayer extends RenderableLayer implements Observer {

	private Way currentWay;
	private AnnotationLayer wayAnnotationLayer;
	private AnnotationAttributes annotationStyle;
	private WorldWindow worldWindow;
	private LengthMeasurer lenghtMeasurer = new LengthMeasurer();
	private List<Way> shortestPathList = new ArrayList<Way>();
	private boolean showWays = false;

	public WayLayer(WorldWindow worldWindow, AnnotationLayer wayAnnotationLayer) {
		super();
		this.setAnnotationAttributes();

		this.worldWindow = worldWindow;
		this.wayAnnotationLayer = wayAnnotationLayer;
		
	}

	private ShapeAttributes getShapeStyle(Color color) {
		ShapeAttributes shapeStyle = new BasicShapeAttributes();
		shapeStyle.setOutlineMaterial(new Material(color));
		shapeStyle.setOutlineWidth(2d);
		return shapeStyle;
	}

	private void setAnnotationAttributes() {
		AnnotationAttributes defaultAttributes = new AnnotationAttributes();
		defaultAttributes.setCornerRadius(10);
		defaultAttributes.setInsets(new Insets(8, 8, 8, 8));
		defaultAttributes.setBackgroundColor(new Color(0f, 0f, 0f, .5f));
		defaultAttributes.setTextColor(Color.WHITE);
		// defaultAttributes.setDrawOffset(new Point(25, 25));
		defaultAttributes.setDistanceMinScale(.5);
		defaultAttributes.setDistanceMaxScale(2);
		defaultAttributes.setDistanceMinOpacity(.5);
		// defaultAttributes.setLeaderGapWidth(14);
		defaultAttributes.setDrawOffset(new Point(20, 40));

		annotationStyle = new AnnotationAttributes();
		annotationStyle.setDefaults(defaultAttributes);
		annotationStyle.setFrameShape(AVKey.SHAPE_NONE); // No frame
		annotationStyle.setFont(Font.decode("Arial-PLAIN-14"));
		annotationStyle.setTextColor(Color.BLACK);
		annotationStyle.setTextAlign(AVKey.CENTER);
		annotationStyle.setDrawOffset(new Point(0, 5)); // centered just above
		// geoAttr.setEffect(AVKey.TEXT_EFFECT_OUTLINE); // Black outline
		annotationStyle.setBackgroundColor(Color.BLACK);
	}

	public void update(Observable arg0, Object arg1) {
		
		//This handles if the layer is updated with the results of the dijkstra
		if (arg1 instanceof List<?>) {
			if(showWays){
				List<Way> resultList = (List<Way>) arg1;
				Iterable<Renderable> allRenderables = this.getRenderables();
				for (Way resultWay : resultList) {
					if (resultWay.getStatus() == WayStatusEnum.result) {
						for (Renderable temp : allRenderables) {
							Way existingWay = (Way) temp;
	
							if (resultWay.getWayID() == existingWay.getWayID()) {
								this.removeRenderable(existingWay);
	
								resultWay = handleExistingWay(resultWay);
								resultWay.setFollowTerrain(true);
								resultWay.setVisible(true);
								resultWay.setValue("SURFACE_PATH_DEPTH_OFFSET",0.50);
								resultWay.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
								resultWay.setPathType(AVKey.LINEAR);
								shortestPathList.add(resultWay);
								this.addRenderable(resultWay);
							}
						}
					}
				}
			}else{
				List<Way> resultList = (List<Way>) arg1;
				Iterable<Renderable> allRenderables = this.getRenderables();
				for (Way resultWay : resultList) {
					resultWay.setAttributes(getShapeStyle(Color.GREEN));
					resultWay.setFollowTerrain(true);
					resultWay.setVisible(true);
					resultWay.setValue("SURFACE_PATH_DEPTH_OFFSET",0.50);
					resultWay.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
					resultWay.setPathType(AVKey.LINEAR);
					shortestPathList.add(resultWay);
					this.addRenderable(resultWay);
				}
			}
		}
		//Adds a new Way to the Map
		else if (arg1 instanceof Way) {
			
			if(showWays){
				currentWay = (Way) arg1;
				
				if(currentWay.getStart()==null || currentWay.getEnd()==null){
					
				}else{
					currentWay.setDistance(calculateDistance(currentWay.getStart().getReferencePosition(),currentWay.getEnd().getReferencePosition()));
				}
				currentWay.setAttributes(getShapeStyle(Color.BLACK));
				currentWay.setFollowTerrain(true);
				currentWay.setVisible(true);
				currentWay.setValue("SURFACE_PATH_DEPTH_OFFSET",0.50);
				currentWay.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
				currentWay.setPathType(AVKey.LINEAR);
				this.addRenderable(currentWay);
				//addAnnotation(currentWay);
				// this.wwd.redraw();
			}
		//Updates the current way with a new position
		} else if (arg1 instanceof Position) {
			List<Position> newPositions = new ArrayList<Position>();
			newPositions.addAll((Collection<? extends Position>) currentWay
					.getPositions());
			newPositions.set(1, (Position) arg1);
			currentWay.setPositions(newPositions);
		} else if (arg1 == null) {
			addAnnotation(currentWay);
			return;
		}
		this.worldWindow.redraw();
	}

	private Way handleExistingWay(Way way) {
		switch (way.getStatus()) {
		case undefined:
			way.setAttributes(getShapeStyle(Color.BLACK));
			break;
		case result:
			way.setAttributes(getShapeStyle(Color.GREEN));
			break;
		case noResult:
			way.setAttributes(getShapeStyle(Color.RED));
			break;
		default:
			break;
		}

		return way;
	}
	
	public void cleanUpAlgoPath(){
		if(!shortestPathList.isEmpty()){
			for(Way way: shortestPathList){
				this.removeRenderable(way);
				way.setStatus(WayStatusEnum.undefined);
				way = handleExistingWay(way);
				way.setFollowTerrain(true);
				way.setVisible(true);
				way.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
				way.setValue("SURFACE_PATH_DEPTH_OFFSET",0.5);
				way.setPathType(AVKey.LINEAR);
				this.addRenderable(way);
			}
			worldWindow.redraw();
			shortestPathList.clear();
		}
	}
	
	public void cleanLayer(){
		this.removeAllRenderables();
		shortestPathList.clear();
		currentWay = null;
	}

	private void addAnnotation(Way newWay) {
		double meters = (double) Math
				.round((newWay.getDistance() / 1000) * 100) / 100;
		Position middle = calcMiddle(newWay.getStart().getReferencePosition(),
				newWay.getEnd().getReferencePosition());
		//GlobeAnnotation anno = new GlobeAnnotation(Double.toString(meters),
		//		middle, this.annotationStyle);
		GlobeAnnotation anno = new GlobeAnnotation(String.valueOf(newWay.getDistance()),
				middle, this.annotationStyle);
		anno.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
		wayAnnotationLayer.addAnnotation(anno);
	}

	private Position calcMiddle(Position pos1, Position pos2) {

		Angle newLatitude = Angle
				.fromDegrees((pos1.latitude.degrees + pos2.latitude.degrees) / 2.0);
		Angle newLongitude = Angle
				.fromDegrees((pos1.longitude.degrees + pos2.longitude.degrees) / 2.0);
		double height = worldWindow.getModel().getGlobe()
				.getElevation(newLatitude, newLongitude);

		return new Position(newLatitude, newLongitude, height);
	}
	
	public double calculateDistance(Position start, Position end) {
		ArrayList<Position> tempPos = new ArrayList<Position>();

		tempPos.add(start);
		tempPos.add(end);

		lenghtMeasurer.setPositions(tempPos);
		return lenghtMeasurer.getLength(worldWindow.getModel().getGlobe());
	}
}
