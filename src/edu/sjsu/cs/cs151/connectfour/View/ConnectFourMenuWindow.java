
/** ConnectFourMenuWindow.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Menu window for Connect Four. 
 * 
 * @author Albert Ong
 * @since 27.03.2019
 * 
 * TODO:
 *   Implement ConnectFourOnlineGameWindow 
 */

package edu.sjsu.cs.cs151.connectfour.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class ConnectFourMenuWindow extends JPanel implements ActionListener {

  // Retrieves the current working directory. 
  // This is used primarily for accessing image files. 
  private String cwd = System.getProperty("user.dir");
  
  public ConnectFourMenuWindow(ConnectFourMainWindow parent) {
    
    setBackground(Color.WHITE);
    
    GridBagLayout layout = new GridBagLayout();
    setLayout(layout);
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.insets = new Insets(0, 0, 20, 0);
    
    JLabel logo = new JLabel(new ImageIcon(cwd + "\\images\\ConnectFour_logo.png"));
    logo.setAlignmentX(Component.CENTER_ALIGNMENT);
    add(logo, gbc);

    
    String[] button_names = {"PLAY", "ONLINE_PLAY", "ABOUT", "EXIT"};
    
    for (String name : button_names) {
      
      // Creates a new button object for each button name. 
      JButton button = new JButton();
      
      // Assigns the button name, size, and action listener. 
      button.setName("MENU_" + name);
      button.setPreferredSize(new Dimension(400, 80)); 
      button.addActionListener(parent);
      
      // Aligns the button to the center of the window. 
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
      
      // Makes the border and content of the button opqaue. 
      button.setOpaque(false);
      button.setContentAreaFilled(false);
      button.setBorderPainted(false);
      
      // Finds the path to the image icons. 
      String image_path = cwd + "\\images\\menu_button_" + name.toLowerCase();
      String button_select_image = image_path + "_select.png";
      String button_deselect_image = image_path + "_deselect.png";
      
      // Assigns the default button icon as well as the icon
      // that appears when moused over. 
      button.setIcon(new ImageIcon(button_deselect_image));
      button.setRolloverIcon(new ImageIcon(button_select_image));
      
      add(button, gbc);
    }
    
    setVisible(true);
  }
  
  
  public void actionPerformed(ActionEvent event) {
    
    // Retrieves the button object that was pressed. 
    JButton button = (JButton)event.getSource();
    
    String button_name = button.getName();
    
    if (button_name == "PLAY") {
      System.out.println("Play button was pressed!");
    }
    else if (button_name == "ABOUT") {
      System.out.println("About button was pressed!");
    }
    else if (button_name == "ONLINE_PLAY") {
      System.out.println("Online play button was pressed!");
    }
    else if (button_name == "EXIT") {
    	System.exit(1);
    }
  }
}
