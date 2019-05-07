package test;

import java.awt.Dimension;
import java.util.concurrent.*;

import javax.swing.JFrame;

import edu.sjsu.cs.cs151.connectfour.Controller.*;
import edu.sjsu.cs.cs151.connectfour.Model.Model;
import edu.sjsu.cs.cs151.connectfour.View.*;

/** MvcTest.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 *  
 * A class for testing the MVC classes without the main menu.
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public class MvcTest {

	/**
	 * Main method
	 * Initializes a frame, then initializes the MVC objects, then
	 * starts the game
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
	    
		view = new View(null, queue);
		model = new Model();
		
		Controller game = new Controller(view, model, queue);
		
		frame.add(view);
	    frame.setTitle("MVC tester");
	    frame.setSize(new Dimension(1600, 900));
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    frame.setVisible(true);
	    
	    game.mainLoop();
	}
	
	
	private static BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
	private static View view;
	private static Model model;
}
