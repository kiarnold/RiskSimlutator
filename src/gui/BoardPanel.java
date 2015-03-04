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
        
        // set locations based on size of list
			// algorithm to assign positions for minimal overlap?
        List<Point> positions = getListPositions(territories.size(), height, width);
        
        // Go through each territory
        for (int i = 0; i <= territories.size(); i++) {
        	
        	Point p = positions.get(i);
        	
        	g2d.drawOval(p.x, p.y, 5, 5);
        	
        	// draw connection lines underneath
        
        	// draw territory circles on top
        }
        
    }
    
    /**
     * Method to compute evenly spaced positions for Territories given a mostly square board.
     * 
     * @param size
     * @param height
     * @param width
     * @return
     */
    private List<Point> getListPositions(int size, int height, int width) {
    	List<Point> positions = new ArrayList<>();
    	
        double sqrDivisor = Math.sqrt(Math.ceil(size));
        
        int divHeight = (int) Math.round(height/sqrDivisor);
        int divWidth = (int) Math.round(height/sqrDivisor);
        
        for (int i = 1; i*divHeight < height; i ++) {
        	for (int j = 1; j*divWidth < width; j++) {
        		if (j*i > size) {
        			break;
        		}
        		
        		positions.add(new Point(i*divHeight, j*divWidth));
        	}
        }
        
		return positions;
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