package ch.zhaw.jroute.view.panel;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

import ch.zhaw.jroute.controller.WaypointController;

public class WaypointOverviewPanel extends JPanel{
	
	private JButton createWaypointBut;
	
	public WaypointOverviewPanel(){
		super(new GridLayout());
		
		//Create the actual panel
		this.createPanel();
	}
	
	public void createPanel(){
		//Set the border around the pabel
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9), new TitledBorder("Waypoint")));
		
		//add the buttons the panel
		this.createButtons();
	}
	
	public void createButtons(){
		this.createWaypointBut = new JButton("Create");
		this.add(createWaypointBut);
		
	}

	public JButton getCreateWaypointButton() {
		return createWaypointBut;
	}
}
