package test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;

import edu.sjsu.cs.cs151.connectfour.View.animation.*;

/** LoadingTest.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 *  
 * A class for testing the loading circle animation
 * 
 * @author Holly Lind
 * @since 04.05.2019
 */
public class LoadingTest {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		LoadingCircle circle = new LoadingCircle();
		frame.add(circle.getLabel());
		circle.run();
		
		frame.setLayout(new FlowLayout());
	    frame.setTitle("Loading circle tester");
	    frame.setSize(new Dimension(1600, 900));
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    frame.setVisible(true);
	}
}
