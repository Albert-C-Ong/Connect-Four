/** ConnectFourMenuWindow.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Menu window for Connect Four. 
 * 
 * @author Krish Ghiya, Holly Lind, and Albert Ong
 * @since 20.03.2019
 * 
 * TODO:
 *   Finish implementation and functionality. 
 */

package edu.sjsu.cs.cs151.connectfour.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class ConnectFourMenuWindow extends JFrame implements ActionListener {

  //Retrieves the current working directory. 
  // This is used primarily for accessing image files. 
  private String cwd = System.getProperty("user.dir");
  
  public ConnectFourMenuWindow() {
    
    // Assigns the title, size, and background color of the window. 
    setTitle("Connect Four");
    setSize(new Dimension(1600, 900));
    getContentPane().setBackground(Color.WHITE);
    
    // Retrieves and sets the window icon. 
    ImageIcon icon = new ImageIcon(cwd + "\\images\\window_icon.png");
    setIconImage(icon.getImage());
    
    GridBagLayout layout = new GridBagLayout();
    setLayout(layout);
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.insets = new Insets(0, 0, 35, 0);
    
    JLabel logo = new JLabel(new ImageIcon(cwd + "\\images\\ConnectFour_logo.png"));
    logo.setAlignmentX(Component.CENTER_ALIGNMENT);
    add(logo, gbc);

    
    String[] button_names = {"Play", "About", "Exit"};
    
    for (String name : button_names) {
      
      JButton button = new JButton();
      button.setName(name);
      button.setAlignmentX(Component.CENTER_ALIGNMENT);
      button.setPreferredSize(new Dimension(400, 80)); 
      button.addActionListener(this);
      
      button.setOpaque(false);
      button.setContentAreaFilled(false);
      button.setBorderPainted(false);
      
      String icon_path = cwd + "\\images\\menu_button_" + name.toLowerCase() + ".png";
      button.setIcon(new ImageIcon(icon_path));
      
      add(button, gbc);
    }
    
    setResizable(false);
    setVisible(true);
  }
  
  
  public void actionPerformed(ActionEvent event) {
    
    // Retrieves the button object that was pressed. 
    JButton button = (JButton)event.getSource();
    
    String button_name = button.getName();
    
    if (button_name == "Play") {
      System.out.println("Play button was pressed!");
      // changePanel(new ConnectFourGameWindow());
    }
    else if (button_name == "About") {
      System.out.println("About button was pressed!");
    }
    else if (button_name == "Exit") {
      dispose();
    }
  }
}