package gui;

import java.awt.*;

import javax.swing.JPanel;

import risk.RiskBoard;
import risk.Territory;

import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {
	private RiskBoard board = new RiskBoard();
	
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        List<Territory> territories = board.getTerritories();
        
        int height = this.getHeight();
        int width = this.getWidth();
        
        List<Point> positions = new ArrayList<Point>();
        
        // set locations based on size of list
        
        
        
        // Go through each territory
        for (Territory territory : territories) {

       			// algorithm to assign positions for minimal overlap?
        
        	// draw connection lines underneath
        
        	// draw territory circles on top
        }
        
    }

    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        doDrawing(g);
    }
    
    public void loadBoard(RiskBoard board) {
    	this.board = board; 
    }
    
}