package ch.zhaw.jroute.model.algorithm;

import java.util.List;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;

public interface IShortestPathAlgorithm {
	/**
	 * Gibt den k�rzesten Pfad zwischen start und end zur�ck
	 * @param start Start
	 * @param end Ziel
	 * @return List<Way> Wege die am k�rzesten von start nach end f�hren
	 */
	public List<Way> getShortestPath(Waypoint start,Waypoint end);
}