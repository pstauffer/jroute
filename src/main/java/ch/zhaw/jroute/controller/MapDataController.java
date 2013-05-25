package ch.zhaw.jroute.controller;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import ch.zhaw.jroute.model.IWayBuilder;
import ch.zhaw.jroute.model.IWaypointBuilder;
import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;
import ch.zhaw.jroute.routedata.IBoxHandler;

public class MapDataController {
	
	private IBoxHandler boxHandler;
	private IWaypointBuilder waypointBuilder;
	private IWayBuilder wayBuilder;
	private static Logger logger = Logger.getLogger("org.apache.log4j");
	
	public MapDataController(IBoxHandler boxHandler,IWaypointBuilder waypointBuilder, IWayBuilder wayBuilder){
		
		this.boxHandler = boxHandler;
		this.waypointBuilder = waypointBuilder;
		this.wayBuilder = wayBuilder;
	}	
	
	public void getDataForMapSection(double lon1, double lat1, double lon2, double lat2){
			
			double left = lon1;
			double bottom = lat1;
			double right = lon2;
			double top =  lat2;
		
		List<Way> wayData = null;
		try {
			wayData = this.boxHandler.getAllWays(left, bottom, right, top);
		} catch (IOException e) {
			//TODO: Handle Exception
		}
		
		for(Way way : wayData){
			
			/*for(Waypoint waypoint : way.getWaypointList()){
				Angle lat = Angle.fromDegreesLatitude(waypoint.getLat());
				Angle lon = Angle.fromDegreesLatitude(waypoint.getLon());
				
				Position pos = new Position(lat,lon,0);
				waypointBuilder.createWaypointFromPosition(pos);
			}*/
			
			//wayBuilder.createNewWay(way.getWaypointList().get(0));
			//wayBuilder.finishWay(way.getWaypointList().get(way.getWaypointList().size()-1), 0);
			
			if(way.getStart()==null){
				logger.debug("no start set");
			}
			if(way.getEnd()==null){
				logger.debug("no end set");
			}
			
			waypointBuilder.createWaypointFromPosition((Position) way.getStart().getCenter());
			waypointBuilder.createWaypointFromPosition((Position) way.getEnd().getCenter());
			
			wayBuilder.addExistingWay(way);
		}
		
	}

}
