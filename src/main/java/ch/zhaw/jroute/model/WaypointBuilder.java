package ch.zhaw.jroute.model;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class WaypointBuilder extends Observable implements IWaypointBuilder {

	private List<Waypoint> waypointList = new ArrayList<Waypoint>();

	public void createWaypointFromPosition(Position waypointPosition) {

		if (waypointPosition == null) {
			return;
		}

		Waypoint newWaypoint = new Waypoint(waypointPosition, 50);

		newWaypoint.setAttributes(getWaypointStyle());
		waypointList.add(newWaypoint);

		this.setChanged();
		this.notifyObservers(newWaypoint);

	}

	/**
	 * Creates the style attributes for a Waypoint
	 * 
	 * @return style attributes
	 */
	private ShapeAttributes getWaypointStyle() {
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
