package ch.zhaw.jroute.controller;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gov.nasa.worldwind.geom.Position;
import ch.zhaw.jroute.model.IWaypointBuilder;
import ch.zhaw.jroute.model.WaypointBuilder;
import ch.zhaw.jroute.view.JrouteView;
import ch.zhaw.jroute.view.layer.WaypointLayer;

public class WaypointController implements IWaypointController {
	
	private IWaypointBuilder waypointBuilder;
	private JrouteView view;
	
	public WaypointController(JrouteView view, IWaypointBuilder builder){
		
		this.view = view;
		this.waypointBuilder = builder;
		this.waypointBuilder.registerObserver(view.getWaypointLayer());
		
		view.addWaypointActionListener(new CreateWaypointListener());
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.jroute.controller.INodeController#createNewNode(gov.nasa.worldwind.geom.Position)
	 */
	@Override
	public void createNewNode(Position position){
		waypointBuilder.createWaypointFromPosition(view.getWwd().getCurrentPosition());
	}
	
	public void addWaypointInputListener(){
			view.getWwd().getInputHandler().addMouseListener(new MouseAdapter()
	        {
	            public void mouseClicked(MouseEvent mouseEvent)
	            {
	                if (mouseEvent.getButton() == MouseEvent.BUTTON1)
	                {
	                	createNewNode(view.getWwd().getCurrentPosition());
	                	/*addPosition();
	                	layer.addRenderable(node);
	
	                    if (mouseEvent.isControlDown())
	                        removePosition();*/
	                	
	                    mouseEvent.consume();
	                }
	            }
	        });
		
	}
	
	private class CreateWaypointListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			((Component) view.getWwd()).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			addWaypointInputListener();
		}
	}
}
