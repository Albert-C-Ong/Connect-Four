package edu.sjsu.cs.cs151.connectfour.View.animation;

import javax.swing.JLabel;
import javax.swing.Timer;

/** LoadingCircle.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A loading circle that is Runnable (for multithreading)
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public class LoadingCircle implements Runnable {

	
	public LoadingCircle() {
		shape = new LoadingShape();
		icon = new ShapeIcon(shape, 100, 100);
		label = new JLabel(icon);

	}
	
	
	public void run() {
		Timer t = new Timer(100, event ->
	    {
	      shape.move();
	      label.repaint();
	    });
	    t.start();
	}
	
	
	public JLabel getLabel() {
		return label;
	}
	
	
	public ShapeIcon getIcon() {
		return icon;
	}
	
	
	
	private MoveableShape shape;
	private ShapeIcon icon;
	private JLabel label;

}
