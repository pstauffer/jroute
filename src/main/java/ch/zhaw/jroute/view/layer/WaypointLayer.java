package ch.zhaw.jroute.view.layer;

import java.util.Observable;
import java.util.Observer;

import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Renderable;

public class WaypointLayer extends RenderableLayer implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		this.addRenderable((Renderable) arg);
	}

	
	
}
