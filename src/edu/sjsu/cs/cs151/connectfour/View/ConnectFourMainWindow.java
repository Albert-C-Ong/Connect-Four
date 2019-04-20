/** ConnectFourMainWindow.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Main window for Connect Four. 
 * 
 * @author Albert Ong
 * @since 29.03.2019
 */

package edu.sjsu.cs.cs151.connectfour.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import edu.sjsu.cs.cs151.connectfour.network.*;


public class ConnectFourMainWindow extends JFrame implements ActionListener {

  // Retrieves the current working directory. 
  // This is used primarily for accessing image files. 
  private String cwd = System.getProperty("user.dir");
  
  // Creates the menu, about, and game window objects. 
  private ConnectFourMenuWindow menu_window = new ConnectFourMenuWindow(this);
  private ConnectFourAboutWindow about_window = new ConnectFourAboutWindow(this);
  private ConnectFourGameWindow game_window = new ConnectFourGameWindow(this);
  
  /* Constructor for the ConnectFourMainWindow object */
  public ConnectFourMainWindow() {
    
    // Assigns the title, size, and background color of the window. 
    setTitle("Connect Four");
    setSize(new Dimension(1600, 900));
    setResizable(false);
    
    // Retrieves and sets the window icon. 
    ImageIcon icon = new ImageIcon(cwd + "\\images\\window_icon.png");
    setIconImage(icon.getImage());
    
    // Displays the menu window by default. 
    add(menu_window);
    
    // Makes the background for all JOptionPane objects white. 
    UIManager UI=new UIManager();
    UI.put("OptionPane.background", Color.WHITE);
    UI.put("Panel.background", Color.WHITE);
    
    // Makes the window visible. 
    setVisible(true);
  }
  
  
  /* Makes only the menu window visible. */
  public void viewMenuWindow() {
    about_window.setVisible(false);
    game_window.setVisible(false);
    
    menu_window.setVisible(true);
    add(menu_window);;
  }
  
  
  /* The method that activates whenever a button is pressed. */
  public void actionPerformed(ActionEvent event) {
    
    // Retrieves the button object that was pressed. 
    JButton button = (JButton)event.getSource();
    String button_name = button.getName();
    
    // If the menu play button was pressed...
    if (button_name.equals("MENU_PLAY")) {
      menu_window.setVisible(false);
      game_window.setVisible(true);
      add(game_window);
    }
    
    // If the menu online play button was pressed. 
    else if (button_name.equals("MENU_ONLINE_PLAY")) {
      
      // Styles the searching message. 
      JLabel searching_message = new JLabel("Searching for a match...");
      searching_message.setForeground(new Color(255, 42, 42));
      searching_message.setFont(new Font("Arial", Font.BOLD, 30));
      
      // Loads and scales the loading icon. 
      Image loading_image = new ImageIcon(cwd + "\\images\\ConnectFourMenuWindow_loading_icon.png").getImage();
      Image scaled_loading_image = loading_image.getScaledInstance(75, 75,  Image.SCALE_SMOOTH );  
      ImageIcon scaled_loading_icon = new ImageIcon(scaled_loading_image);
      
      SwingWorker<Object, Object> server = null;
      final Network player;
      
      Client c = new Client(this);
      String address = c.findServer();
      
      if(!address.startsWith("No")) {
  		  
    	  String hostName = "";
    	  try {
    		  hostName = InetAddress.getByName(address).getHostName();
    	  } catch (UnknownHostException e) {
			e.printStackTrace();
    	  }
    	  
  		  int join = JOptionPane.showOptionDialog(this,
  				  "Connect to " + hostName+"?", 
                  "Match found", 
                  JOptionPane.YES_NO_OPTION, 
                  JOptionPane.QUESTION_MESSAGE, 
                  scaled_loading_icon, 
                  new String[] {"Join", "Cancel"}, 
                  null);
    		 
    		 if(join == 0) player = c;
    		 else return;
       }
     
       else {
    	   int host = JOptionPane.showOptionDialog(this,
           "No matches found. Host game?", 
           "Error", 
           JOptionPane.YES_NO_OPTION, 
           JOptionPane.QUESTION_MESSAGE, 
           scaled_loading_icon, 
           new String[] {"Host", "Cancel"}, 
           null);
    		  
    	   if(host == 0) {
    		   Server s = new Server(this);
    		   player = s;
    	   }
    	   else return;
       }
      
      menu_window.setVisible(false);
      add(player);
      
      server = new SwingWorker<Object, Object>(){
	        @Override
	        protected Object doInBackground() throws Exception {
	            player.startRunning();
	            return null;
	        }
	  };
      
      server.execute();
    }
    
    // If the menu about button was pressed...
    else if (button_name.equals("MENU_ABOUT")) {
      menu_window.setVisible(false);
      about_window.setVisible(true);
      add(about_window);
    }
    
    // If the menu exit button was pressed...
    else if (button_name.equals("MENU_EXIT")) {
      dispose(); // closes the window
    }
    
    // If the about okay button was pressed...
    else if (button_name.equals("ABOUT_OKAY")) {
      viewMenuWindow(); // Makes only the menu window visible. 
    }
  }
}