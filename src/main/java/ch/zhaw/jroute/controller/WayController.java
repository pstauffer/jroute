package ch.zhaw.jroute.controller;

import ch.zhaw.jroute.model.IWayBuilder;
import ch.zhaw.jroute.model.WayBuilder;
import ch.zhaw.jroute.view.JrouteView;

public class WayController implements IWayController{
	
	private JrouteView view;
	private IWayBuilder wayBuilder;
	
	public WayController(JrouteView view, WayBuilder wayBuilder){
		this.view = view;
		this.wayBuilder = wayBuilder;
	}
	
	

}
