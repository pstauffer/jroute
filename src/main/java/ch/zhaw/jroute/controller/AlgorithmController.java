package ch.zhaw.jroute.controller;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.jroute.model.IWayBuilder;
import ch.zhaw.jroute.model.IWaypointBuilder;
import ch.zhaw.jroute.model.algorithm.DjikstraAlgo;
import ch.zhaw.jroute.model.Way;

public class AlgorithmController implements IAlgorithmController {
	
	private IWayBuilder wayBuilder;
	private IWaypointBuilder waypointBuilder;
	private DjikstraAlgo algorithm;
	
	public AlgorithmController(IWayBuilder wayBuilder, IWaypointBuilder waypointBuilder){
		this.wayBuilder = wayBuilder;
		this.waypointBuilder = waypointBuilder;
		
		this.algorithm = new DjikstraAlgo();
	}

	@Override
	public void StartAlgorithm() {
		List<Way> wayList = new ArrayList<Way>(wayBuilder.getAllWays().values());
		List<Way> resultList = this.algorithm.getShortestPath(waypointBuilder.getStartWaypoint(), waypointBuilder.getEndWaypoint(),wayList );
		wayBuilder.showShortestPath(resultList);
	}

}
