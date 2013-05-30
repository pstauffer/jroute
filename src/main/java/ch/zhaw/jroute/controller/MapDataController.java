package ch.zhaw.jroute.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import ch.zhaw.jroute.controller.interfaces.IMapDataController;
import ch.zhaw.jroute.model.businessObjects.Way;
import ch.zhaw.jroute.model.interfaces.IWayBuilder;
import ch.zhaw.jroute.model.interfaces.IWaypointBuilder;
import ch.zhaw.jroute.routedata.IBoxHandler;

public class MapDataController implements IMapDataController {
	
	private IBoxHandler boxHandler;
	private IWaypointBuilder waypointBuilder;
	private IWayBuilder wayBuilder;
	private static Logger logger = Logger.getLogger("org.apache.log4j");
	
	public MapDataController(IBoxHandler boxHandler,IWaypointBuilder waypointBuilder, IWayBuilder wayBuilder){
		
		this.boxHandler = boxHandler;
		this.waypointBuilder = waypointBuilder;
		this.wayBuilder = wayBuilder;
	}	
	
	public void getDataForMapSection(double lon1, double lat1, double lon2, double lat2,List<String> filterList) throws IOException{
			
			double left = lon1;
			double bottom = lat1;
			double right = lon2;
			double top =  lat2;
		
		List<Way> wayData = this.boxHandler.getAllWays(left, bottom, right, top,filterList);
		
		for(Way way : wayData){
			
			if(way.getStart()==null){
				logger.debug("no start set");
			}
			if(way.getEnd()==null){
				logger.debug("no end set");
			}
			
			waypointBuilder.createWaypointFromExistingWaypoint(way.getStart());
			waypointBuilder.createWaypointFromExistingWaypoint(way.getEnd());
			
			wayBuilder.addExistingWay(way);
		}
		
	}

}
