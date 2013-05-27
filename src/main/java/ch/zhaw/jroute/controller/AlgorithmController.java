package ch.zhaw.jroute.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.jroute.model.IWayBuilder;
import ch.zhaw.jroute.model.IWaypointBuilder;
import ch.zhaw.jroute.model.algorithm.DjikstraAlgo;
import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.view.JrouteView;

public class AlgorithmController implements IAlgorithmController {
	
	private IWayBuilder wayBuilder;
	private IWaypointBuilder waypointBuilder;
	private DjikstraAlgo algorithm;
	
	public AlgorithmController(JrouteView view, IWayBuilder wayBuilder, IWaypointBuilder waypointBuilder){
		this.wayBuilder = wayBuilder;
		this.waypointBuilder = waypointBuilder;
		
		this.algorithm = new DjikstraAlgo();
	}

	@Override
	public void StartAlgorithm() throws IOException {
		List<Way> wayList = new ArrayList<Way>(wayBuilder.getAllWays().values());
		
		if(waypointBuilder.getStartWaypoint()==null){
			throw new IOException("Please set start of the route");
		}
		if(waypointBuilder.getEndWaypoint()==null){
			throw new IOException("Please set end of the route");
		}
		
		List<Way> resultList = this.algorithm.getShortestPath(waypointBuilder.getStartWaypoint(), waypointBuilder.getEndWaypoint(),wayList );
		
		wayBuilder.showShortestPath(resultList);
	}

}
