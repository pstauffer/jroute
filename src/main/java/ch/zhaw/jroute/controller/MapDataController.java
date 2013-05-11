package ch.zhaw.jroute.controller;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;

import java.util.List;
import java.util.Set;

import ch.zhaw.jroute.model.IWayBuilder;
import ch.zhaw.jroute.model.IWaypointBuilder;
import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.Waypoint;
import ch.zhaw.jroute.routedata.IBoxHandler;

public class MapDataController {
	
	private IBoxHandler boxHandler;
	private IWaypointBuilder waypointBuilder;
	private IWayBuilder wayBuilder;
	
	public MapDataController(IBoxHandler boxHandler,IWaypointBuilder waypointBuilder, IWayBuilder wayBuilder){
		
		this.boxHandler = boxHandler;
		this.waypointBuilder = waypointBuilder;
		this.wayBuilder = wayBuilder;
	}	
	
	public void getDataForMapSection(){
		
		double left = waypointBuilder.getStartWaypoint().getCenter().getLongitude().degrees;
		double right = waypointBuilder.getEndWaypoint().getCenter().getLongitude().degrees;
		double bottom = waypointBuilder.getStartWaypoint().getCenter().getLatitude().degrees + 0.0000005;
		double top = waypointBuilder.getEndWaypoint().getCenter().getLatitude().degrees - 0.0000005;
		
		List<Way> wayData = this.boxHandler.getAllWays(left, bottom, right, top);
		
		for(Way way : wayData){
			for(Waypoint waypoint : way.getWaypointList()){
				Angle lat = Angle.fromDegreesLatitude(waypoint.getLat());
				Angle lon = Angle.fromDegreesLatitude(waypoint.getLon());
				
				Position pos = new Position(lat,lon,0);
				waypoint.setCenter(pos);
				waypointBuilder.createWaypointFromPosition(pos);
			}
			
			wayBuilder.createNewWay(way.getWaypointList().get(0));
			wayBuilder.finishWay(way.getWaypointList().get(way.getWaypointList().size()-1), 0);
		}
		
	}

}
