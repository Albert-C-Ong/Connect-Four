
/** ConnectFourMenuWindow.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * About window for Connect Four. 
 * 
 * @author Albert Ong
 * @since 23.03.2019
 * 
 * TODO:
 *   Implement functionality for play, online play, and about buttons. 
 */

package edu.sjsu.cs.cs151.connectfour.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ConnectFourAboutWindow extends JFrame implements ActionListener {
  
  // Retrieves the current working directory. 
  // This is used primarily for accessing image files. 
  private String cwd = System.getProperty("user.dir");
  
  public ConnectFourAboutWindow() {
    
    // Assigns the title, size, and background color of the window. 
    setTitle("Connect Four");
    setSize(new Dimension(1600, 900));
    setResizable(false);
    getContentPane().setBackground(Color.WHITE);
    
    // Retrieves and sets the window icon. 
    ImageIcon icon = new ImageIcon(cwd + "\\images\\window_icon.png");
    setIconImage(icon.getImage());
    
    // Adds the grid layout and sets the constraints. 
    GridBagLayout layout = new GridBagLayout();
    setLayout(layout);
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.insets = new Insets(0, 0, 20, 0);
    
    // Adds the about page text
    JLabel text = new JLabel(new ImageIcon(cwd + "\\images\\ConnectFourAboutWindow_text.png"));
    add(text, gbc);
    
    // Creats the okay button. 
    JButton button = new JButton();
    button.setPreferredSize(new Dimension(400, 80));
    button.setIcon(new ImageIcon(cwd + "\\images\\menu_button_okay_deselect.png"));
    button.setRolloverIcon(new ImageIcon(cwd + "\\images\\menu_button_okay_select.png"));
    button.addActionListener(this);
    
    // Makes the border and content of the button opqaue. 
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setBorderPainted(false);
    
    // Adds the okay button. 
    add(button, gbc);
    

    setVisible(true);
  }
  
  public void actionPerformed(ActionEvent event) {
    
    // Retrieves the button object that was pressed. 
    JButton button = (JButton)event.getSource();
    
    dispose();
    new ConnectFourMenuWindow();
  }

}
