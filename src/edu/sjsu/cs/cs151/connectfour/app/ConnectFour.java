
/** ConnectFour.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * The main program to the Connect Four game. 
 * 
 * @author Krish Ghiya, Holly Lind, and Albert Ong
 * @since 24.04.2019
 */

package edu.sjsu.cs.cs151.connectfour.app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.sjsu.cs.cs151.connectfour.Controller.Controller;
import edu.sjsu.cs.cs151.connectfour.Controller.Message;
import edu.sjsu.cs.cs151.connectfour.Model.Model;
import edu.sjsu.cs.cs151.connectfour.View.View;

public class ConnectFour {

	public static BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
	public static View view;
	public static Model model;
	

	public static void main(String[] args) {
		view = new View(queue);
		model = new Model();
		Controller game = new Controller(view, model, queue);
		
		game.mainLoop();
		view.dispose();
		queue.clear();
	}
}
