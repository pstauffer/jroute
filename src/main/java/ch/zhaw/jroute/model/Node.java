package ch.zhaw.jroute.model;

public class Node {
	private int nodeID;
	private int Lat;
	private int Lon;

	public Node(int nodeID, int Lat, int Lon) {
		this.nodeID = nodeID;
		this.Lat = Lat;
		this.Lon = Lon;
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public int getLat() {
		return Lat;
	}

	public void setLat(int lat) {
		Lat = lat;
	}

	public int getLon() {
		return Lon;
	}

	public void setLon(int lon) {
		Lon = lon;
	}

}
