package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import risk.BoardUtils.Colors;
import risk.RiskBoard;
import risk.Territory;

import java.awt.Color;
import java.awt.Canvas;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BoardFrame extends JFrame {

	private JPanel contentPane;
	private int maxOvalSize = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoardFrame frame = new BoardFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BoardFrame() {
		setTitle("Simulation Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 75, 800, 600);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(105, 105, 105));
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		BoardPanel mainScreen = new BoardPanel();
		mainScreen.setForeground(new Color(105, 105, 105));
		mainScreen.setBackground(new Color(248, 248, 255));
		contentPane.add(mainScreen, BorderLayout.CENTER);
		
	}
	
	private class BoardPanel extends JPanel {
		   private void doDrawing(Graphics g) {

		        Graphics2D g2d = (Graphics2D) g;
		        
		        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        
		        int height = this.getHeight();
		        int width = this.getWidth();
		        
		        // define the outer circle to be a fraction of the screen.
		        maxOvalSize = (int) height/10;
		        		        
		        // get territory list
		        // TODO: Get user input to find risk load file
		        String fileName = "TestRisk.txt";
		        RiskBoard board = new RiskBoard(fileName);
		        List<Territory> terraList = board.getTerritories();
		        
		        // create cords list
		        Random rand = new Random();
		        Map<Territory, Point> terraLocs = new HashMap<Territory, Point>();
		        for (Territory terra : terraList) {
		        	Point p = randomCheckedPoint(terraLocs, rand);
		        	terraLocs.put(terra, p);
		        }
		        
		        // Iterate through list
		        for (Territory terra : terraLocs.keySet()) {
			        // territoryDraw for each at correct cords  
		        	territoryDraw(terra, g2d, terraLocs.get(terra));
		        }
		        // draw connection lines
		    }
		
		private Point randomCheckedPoint(Map<Territory, Point> terraLocs, Random rand) {
        	Point p = checkPoint(terraLocs, rand);    	
			return p;
		}

		private Point randomPoint(Random rand) {
			int x = rand.nextInt(getWidth() - maxOvalSize * 3) + maxOvalSize;
	        int y = rand.nextInt(getHeight() - maxOvalSize * 3) + maxOvalSize;
	        Point p = new Point(x, y);
	        	
			return p;
		}

		private Point checkPoint(Map<Territory, Point> terraLocs, Random rand) {
			Point p = randomPoint(rand);
			boolean checked = false;
			for (Territory terra : terraLocs.keySet()) {
        		while (checked) {
	        		if (terraLocs.get(terra) != null) {
	        			Point pt = terraLocs.get(terra);
	        			if ((p.x - pt.x > maxOvalSize*2 ||
	        					pt.x - p.x > maxOvalSize*2) &&
	        						(p.y - pt.y > maxOvalSize*2 ||
	        							pt.y -p.y > maxOvalSize*2)) {
	        				checked = true;
	        			} else {
	        				p = randomPoint(rand);
	        			}
	        		}
        		}
        	}
			
			return p;
		}

		@Override
		    public void paintComponent(Graphics g) {
		        
		        super.paintComponent(g);
		        doDrawing(g);
		    }
		    
		    /**
		     * Method to draw a territory represented as a graphical circle.
		     * 
		     * Only looks correct with: lineThickness = 10
		     * 
		     **/
		    private void territoryDraw(Territory terra, Graphics2D g2d, Point cords) {		    	
		    	int lineThickness = 10;
		    	
		    	// get color
		    	Color color = terra.getFaction().getColor();
		    	// get troop count
		    	String count = Integer.toString(terra. getTroops());
		    	
		    	// draw outer dark gray circle
		    	g2d.setColor(Color.DARK_GRAY);
		    	g2d.fillOval(cords.x, cords.y, maxOvalSize, maxOvalSize);
		    	
		    	// draw inner colored circle
		    	g2d.setColor(color);
		    	g2d.fillOval(cords.x+lineThickness/2, cords.y+lineThickness/2, maxOvalSize-lineThickness, maxOvalSize-lineThickness);
		    	
		    	// draw inner light gray circle
		    	g2d.setColor(Color.LIGHT_GRAY);
		    	g2d.fillOval(cords.x+lineThickness, cords.y+lineThickness, maxOvalSize-lineThickness*2, maxOvalSize-lineThickness*2);
		    	
		    	// draw colored troop number in center
		    	g2d.setColor(color);
		    	g2d.setFont(new Font("Monospaced", Font.BOLD, 25));
		    	
		    	int stringWidth = (int) g2d.getFontMetrics().getStringBounds(count, g2d).getWidth();
		    	int stringHight = (int) g2d.getFontMetrics().getStringBounds(count, g2d).getHeight();
        		int startX = (int) (stringWidth/2+cords.x+lineThickness*1.5);
        		int startY = (int) (stringHight/2+cords.y+lineThickness*2);
        		
        		g2d.drawString(count, startX, startY);
		    	
		    }
		
	}

}
