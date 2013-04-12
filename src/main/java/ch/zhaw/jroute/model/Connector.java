package ch.zhaw.jroute.model;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwindx.examples.util.DirectedPath;

public class Connector extends DirectedPath {
	private double distance;
	private Node from;
	private Node to;
	private DirectedPath path;

	Connector(Node from, Node to, double distance) {
		this.from = from;
		this.to = to;
		this.distance = distance;
	}

	public Connector() {
		super();
		// this.color = Color.BLACK;
		// this.lineWidth = 3.0;

		ShapeAttributes attrs = new BasicShapeAttributes();
		attrs.setOutlineMaterial(Material.BLACK);
		attrs.setOutlineWidth(2d);
		this.setAttributes(attrs);
		this.setVisible(true);
		this.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
		this.setPathType(AVKey.GREAT_CIRCLE);
		this.setFollowTerrain(true);
	}

	public Connector(Node from) {
		super();
		this.setFrom(from);
		// this.setFollowTerrain(true);
		ShapeAttributes attrs = new BasicShapeAttributes();
		attrs.setOutlineMaterial(Material.RED);
		attrs.setOutlineWidth(2d);
		attrs.setOutlineWidth(2.0);

	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Node getFrom() {
		return from;
	}

	public void setFrom(Node from) {
		this.from = from;
	}

	public Node getTo() {
		return to;
	}

	public void setTo(Node to) {
		this.to = to;
	}

	public DirectedPath getPath() {
		return path;
	}

	public void setPath(DirectedPath path) {
		this.path = path;
	}

}
