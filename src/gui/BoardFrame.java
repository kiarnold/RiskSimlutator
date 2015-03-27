package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import risk.Territory;

import java.awt.Color;
import java.awt.Canvas;
import java.util.List;

public class BoardFrame extends JFrame {

	private JPanel contentPane;

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
		        
		        int height = this.getHeight();
		        int width = this.getWidth();
		        
		        g2d.drawOval(width/2, height/2, 5, 5);
		        
		        
		        // get territory list
		        // interate through list
		        // territoryDraw for each at correct cords
		    }
		
		   @Override
		    public void paintComponent(Graphics g) {
		        
		        super.paintComponent(g);
		        doDrawing(g);
		    }
		    
		    /**
		     * Method to draw a territory represented as a graphical circle.
		     * 
		     **/
		    private void territoryDraw(Territory terra, Graphics2D g2d, Point cords) {
		    	// get color
		    	// get troop count
		    	// draw outer dark gray circle
		    	// draw inner colored circle
		    	// draw inner light gray circle
		    	// draw colored troop number in center
		    }
		
	}

}
