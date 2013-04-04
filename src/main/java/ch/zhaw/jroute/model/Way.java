package ch.zhaw.jroute.model;

import java.util.List;

public class Way {
	private int wayID;
	private List<NodePascal> nodeList;

	public Way() {

	}

	public Way(int wayID) {
		this.wayID = wayID;
	}

	public Way(int wayID, List<NodePascal> nodeList) {
		this.wayID = wayID;
		this.nodeList = nodeList;
	}

	public int getWayID() {
		return wayID;
	}

	public void setWayID(int wayID) {
		this.wayID = wayID;
	}

	public List<NodePascal> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<NodePascal> nodeList) {
		this.nodeList = nodeList;
	}

}
