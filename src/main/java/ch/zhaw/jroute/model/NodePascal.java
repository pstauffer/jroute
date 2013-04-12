package ch.zhaw.jroute.model;

public class NodePascal {
	private int nodeID;
	private float lat;
	private float lon;

	public NodePascal() {

	}

	/**
	 * constructor for a new node object
	 */
	public NodePascal(int nodeID, float lat, float lon) {
		this.nodeID = nodeID;
		this.lat = lat;
		this.lon = lon;
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

}
