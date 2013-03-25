package ch.zhaw.jroute.view;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.LayerList;

import java.awt.BorderLayout;
import java.awt.Dimension;

import ch.zhaw.jroute.view.panel.SideNavigationPanel;
import ch.zhaw.jroute.view.template.ApplicationTemplate;

public class JrouteView{
	
    public static class AppFrame extends ApplicationTemplate.AppFrame
    {
        public AppFrame()
        {
            super(true, false, false);
            this.setSize(850, 800);
            
            //Set inital point on the map
            this.getWwd().setValue(AVKey.INITIAL_LATITUDE, 49.06);
            this.getWwd().setValue(AVKey.INITIAL_LONGITUDE, -122.77);
            this.getWwd().setValue(AVKey.INITIAL_ALTITUDE, 22000);
            
            this.getContentPane().add(new SideNavigationPanel(this.getWwd()), BorderLayout.WEST);
        }
    }
	
}