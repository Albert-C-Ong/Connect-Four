
/** MenuPanel.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Menu window for Connect Four. 
 * (renamed from ConnectFourMenuWindow)
 * 
 * @author Albert Ong
 * @since 09.05.2019
 */

package edu.sjsu.cs.cs151.connectfour.View;

import java.awt.*;
import java.net.URL;

import javax.swing.*;


public class MenuPanel extends JPanel {

  
  public MenuPanel(View parent) {
    
    setBackground(Color.WHITE);
    
    GridBagLayout layout = new GridBagLayout();
    setLayout(layout);
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.insets = new Insets(0, 0, 20, 0);
    
    URL url1 = View.class.getResource("/resources/ConnectFour_logo.png");
    JLabel logo = new JLabel(new ImageIcon(url1));
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
      String image_path = "/resources/menu_button_" + name.toLowerCase();
      String button_select_image = image_path + "_select.png";
      String button_deselect_image = image_path + "_deselect.png";
      
      // Assigns the default button icon as well as the icon
      // that appears when moused over. 
      URL url_select = View.class.getResource(button_select_image);
      URL url_deselect = View.class.getResource(button_deselect_image);
      button.setIcon(new ImageIcon(url_deselect));
      button.setRolloverIcon(new ImageIcon(url_select));
      
      add(button, gbc);
    }
    
    setVisible(true);
  }
}
