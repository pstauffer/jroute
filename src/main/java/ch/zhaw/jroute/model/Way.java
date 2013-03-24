package ch.zhaw.jroute.model;

import java.util.List;

public class Way {
	private int wayID;
	private List<Node> nodeList;

	public Way(int wayID, List<Node> nodeList) {
		this.wayID = wayID;
		this.nodeList = nodeList;
	}

	public int getWayID() {
		return wayID;
	}

	public void setWayID(int wayID) {
		this.wayID = wayID;
	}

	public List<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}

}
