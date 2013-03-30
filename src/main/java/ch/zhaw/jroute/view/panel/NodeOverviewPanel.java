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

import ch.zhaw.jroute.controller.NodeController;

public class NodeOverviewPanel extends JPanel{
	
	private JButton createNodeButton;
	
	public NodeOverviewPanel(){
		super(new GridLayout());
		
		//Create the actual panel
		this.createPanel();
	}
	
	public void createPanel(){
		//Set the border around the pabel
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9), new TitledBorder("Node")));
		
		//add the buttons the panel
		this.createButtons();
	}
	
	public void createButtons(){
		this.createNodeButton = new JButton("Create");
		this.add(createNodeButton);
		
	}

	public JButton getCreateNodeButton() {
		return createNodeButton;
	}
}
