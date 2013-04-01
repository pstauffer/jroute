package ch.zhaw.jroute.model;

public class NodePascal {
	int nodeID;
	String lat;
	String lon;

	public NodePascal() {

	}

	public NodePascal(int nodeID, String lat, String lon) {
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

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

}
