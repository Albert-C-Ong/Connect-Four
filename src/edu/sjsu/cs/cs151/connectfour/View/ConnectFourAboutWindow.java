
/** ConnectFourAboutWindow.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * About window for Connect Four. 
 * 
 * @author Albert Ong
 * @since 24.04.2019
 */

package edu.sjsu.cs.cs151.connectfour.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class ConnectFourAboutWindow extends JPanel {
  
  // Retrieves the current working directory. 
  // This is used primarily for accessing image files. 
  private String cwd = System.getProperty("user.dir");
  
  public ConnectFourAboutWindow(ConnectFourMainWindow parent) {
    
    // Sets the background color to white. 
    setBackground(Color.WHITE);
    
    // Adds the grid layout and sets the constraints. 
    GridBagLayout layout = new GridBagLayout();
    setLayout(layout);
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.insets = new Insets(0, 0, 20, 0);
    
    // Adds the about page text
    JLabel text = new JLabel(new ImageIcon(cwd + "\\images\\ConnectFourAboutWindow_text.png"));
    add(text, gbc);
    
    // Creates the okay button. 
    JButton button = new JButton();
    button.setName("ABOUT_OKAY");
    button.setPreferredSize(new Dimension(400, 80));
    button.setIcon(new ImageIcon(cwd + "\\images\\menu_button_okay_deselect.png"));
    button.setRolloverIcon(new ImageIcon(cwd + "\\images\\menu_button_okay_select.png"));
    button.addActionListener(parent);
    
    // Makes the border and content of the button opaque. 
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setBorderPainted(false);
    
    // Adds the okay button. 
    add(button, gbc);
    
    setVisible(true);
  }
}
