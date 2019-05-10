
/** View.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Main window for Connect Four. 
 * (renamed from ConnectFourMainWindow)
 * 
 * @author Albert Ong, Holly Lind
 * @since 09.05.2019
 */

package edu.sjsu.cs.cs151.connectfour.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.*;
import javax.swing.*;

import edu.sjsu.cs.cs151.connectfour.Controller.*;
import edu.sjsu.cs.cs151.connectfour.network.*;

public class View extends JFrame implements ActionListener {

	
	private MenuPanel menu_window;
	private AboutPanel about_window;
	private GamePanel game_window;
	private LoadingPanel loading_window;
	private BlockingQueue<Message> queue;
	private Client c;
	private Server s;
	
	
	/* Constructor for the ConnectFourMainWindow object */
	public View(BlockingQueue<Message> queue) {
		this.queue = queue;
		
		//initialize panels
		menu_window = new MenuPanel(this);
		about_window = new AboutPanel(this);
		game_window = new GamePanel(queue, true);
		loading_window = new LoadingPanel(this);
		
		// Assigns the title, size, and background color of the window.
		setTitle("Connect Four");
		setSize(new Dimension(1600, 900));
		setResizable(false);

		// Retrieves and sets the window icon.
		URL url = View.class.getResource("/resources/window_icon.png");
		ImageIcon icon = new ImageIcon(url);
		setIconImage(icon.getImage());

		// Displays the menu window by default.
		add(menu_window);

		// Makes the background for all JOptionPane objects white.
		UIManager.put("OptionPane.background", Color.WHITE);
		UIManager.put("Panel.background", Color.WHITE);
		
		// Makes the window visible.
		setVisible(true);
	}

	
	public void replacePanel(JPanel oldPanel, JPanel newPanel) {
		oldPanel.setVisible(false);
		
		add(newPanel);
		newPanel.setVisible(true);
	}
	
	
	/* The method that activates whenever a button is pressed. */
	public void actionPerformed(ActionEvent event) {

		// Retrieves the button object that was pressed.
		JButton button = (JButton) event.getSource();
		String button_name = button.getName();

		// If the menu play button was pressed...
		if (button_name.equals("MENU_PLAY")) {
	    	try {
	    		queue.put(new StartLocalGameMessage());
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
		}

		// If the menu online play button was pressed.
		else if (button_name.equals("MENU_ONLINE_PLAY")) {
	    	try {
	    		queue.put(new StartOnlineGameMessage());
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
		}

		// If the menu about button was pressed...
		else if (button_name.equals("MENU_ABOUT")) {
	    	try {
	    		queue.put(new OpenAboutMessage());
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
		}

		// If the menu exit button was pressed...
		else if (button_name.equals("MENU_EXIT")) {
	    	try {
	    		queue.put(new CloseFrameMessage());
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
		}

		// If the about okay button was pressed...
		else if (button_name.equals("ABOUT_OKAY")) {
	    	try {
	    		queue.put(new CloseAboutMessage());
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
		}
		
		// If the loading exit button was pressed...
		else if (button_name.equals("LOADING_EXIT")) {
	    	try {
	    		queue.put(new ExitLoadingMessage());
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
		}
	}
	
	
	public MenuPanel getMenuPanel() {
		return menu_window;
	}
	
	
	public GamePanel getGamePanel() {
		return game_window;
	}
	
	
	public AboutPanel getAboutPanel() {
		return about_window;
	}
	
	
	public LoadingPanel getLoadingPanel() {
		return loading_window;
	}
	
	
	public Client getClient() {
		return c;
	}
	
	
	public void setClient(Client c) {
		this.c = c;
	}
	
	
	public Server getServer() {
		return s;
	}
	
	
	public void setServer(Server s) {
		this.s = s;
	}
	
	
	public void joinOnlineDialogBox(String hostName) {
		int join = JOptionPane.showOptionDialog(this, "Connect to " + hostName + "?", "Match found",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(),
				new String[] { "Join", "Cancel" }, null);
		
		if (join == 0) {
	    	try {
	    		queue.put(new JoinAsClientMessage());
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
		}
	}
	
	
	public void hostOnlineDialogBox() {
		int host = JOptionPane.showOptionDialog(this, "No matches found. Host game?", "Error",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(),
				new String[] { "Host", "Cancel" }, null);
		
		if (host == 0) {
	    	try {
	    		queue.put(new JoinAsServerMessage());
	    	}
	    	catch (InterruptedException exception) {
	    		exception.printStackTrace();
	    	}
		} 
	}
}