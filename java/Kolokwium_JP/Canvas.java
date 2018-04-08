package scondProjectKolokium;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseMotionListener, MouseListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	float mouseX,mouseY;
	boolean isClicked, mouseIsInside;

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseIsInside = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseIsInside = false;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		isClicked= true;
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		isClicked= false;
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if(mouseIsInside)
		{
			if(!isClicked)
			{
				g.clearRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(Color.black);
				
				g.drawOval((int)mouseX-50, (int)mouseY-50, 100, 100);
			}
			else
			{
				g.clearRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(Color.black);

				g.fillOval((int)mouseX-50, (int)mouseY-50, 100, 100);
			}
			
		}
		else
		{
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
		}
		
	}
	
	Canvas()
	{
		addMouseListener(this);
		addMouseMotionListener(this);
		isClicked= false;
		mouseIsInside = true;
	
		repaint();
	}
}
