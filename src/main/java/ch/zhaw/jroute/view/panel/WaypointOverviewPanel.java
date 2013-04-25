package ch.zhaw.jroute.view.panel;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
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
	
	private JButton createWaypointButton;
	private JButton startWaypointButton;
	private JButton endWaypointButton;
	
	public WaypointOverviewPanel(){
		super(new GridLayout(0,2));
		
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
		this.createWaypointButton = new JButton("Create");
		JButton placeholderButton = new JButton();
		placeholderButton.setVisible(false);
		
		//placeholderButton.setPreferredSize(new Dimension(0,0));
		
		this.startWaypointButton = new JButton("Start");
		this.endWaypointButton = new JButton("End");
		this.add(createWaypointButton);
		this.add(placeholderButton);
		this.add(startWaypointButton);
		this.add(endWaypointButton);
		
	}

	public JButton getCreateWaypointButton() {
		return createWaypointButton;
	}

	public JButton getStartWaypointButton() {
		return startWaypointButton;
	}

	public JButton getEndWaypointButton() {
		return endWaypointButton;
	}
}
