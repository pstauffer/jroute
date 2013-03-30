package ch.zhaw.jroute.view;

import gov.nasa.worldwind.avlist.AVKey;

import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.MarkerLayer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import ch.zhaw.jroute.view.panel.SideNavigationPanel;
import ch.zhaw.jroute.view.template.ApplicationTemplate;
import ch.zhaw.jroute.view.layer.*;

public class JrouteView{
	
    public static class AppFrame extends ApplicationTemplate.AppFrame
    {
    	private boolean armed = false;
    	private ConnectorLayer connectorLayer = new ConnectorLayer();
    	private NodeLayer nodeLayer = new NodeLayer();
    	private MarkerLayer markerLayer = new MarkerLayer();
    	
    	
        public AppFrame()
        {
            super(true, false, false);
            this.setSize(850, 800);
            
            //hide not usefull layers and add open streetmap layer
            this.addExtraLayer();
            
            //Set inital point on the map
            this.getWwd().setValue(AVKey.INITIAL_LATITUDE, 49.06);
            this.getWwd().setValue(AVKey.INITIAL_LONGITUDE, -122.77);
            this.getWwd().setValue(AVKey.INITIAL_ALTITUDE, 22000);
            
            this.getContentPane().add(new SideNavigationPanel(this.getWwd()), BorderLayout.WEST);
        }
        
        /**
         * 
         */
        private void addExtraLayer(){
        	//Layer layer = (Layer) new OpenStreetMapWMSLayer();
            LayerList layers = this.getWwd().getModel().getLayers();
            layers.add(new OSMMapnikLayer());
            layers.add(connectorLayer);
            layers.add(nodeLayer);
            layers.add(markerLayer);
            
            connectorLayer.setName("Connector layer");
            nodeLayer.setName("Node layer");
            markerLayer.setName("Marker layer");
            
            for(Layer layer:layers){
            	//layer.setEnabled(false);
            	if(layer.getName().contains("Place")){
            		layer.setEnabled(false);
            	}
            	if(layer.getName().contains("Landsat")){
            		layer.setEnabled(false);
            	}
            	if(layer.getName().contains("Open")){
            		layer.setEnabled(true);
            	}
            }
            
            this.getWwd().redraw();
        }
    }
	
}