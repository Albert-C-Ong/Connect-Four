
/** ConnectFourMenuWindow.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Menu window for Connect Four. 
 * 
 * @author Krish Ghiya, Holly Lind, and Albert Ong
 * @since 11.03.2019
 * 
 * TODO:
 *   Finish implementation and functionality. 
 */

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
    
    setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    
    
    JLabel logo = new JLabel(new ImageIcon(cwd + "\\images\\ConnectFour_logo.png"));
    add(logo);
    
    add(new JButton("Button 1"));
    add(new JButton("Button 2"));
    add(new JButton("Button 3"));
    add(new JButton("Button 4"));
    add(new JButton("Button 5"));
    
    setVisible(true);
  }
  
  
  public void actionPerformed(ActionEvent event) {
    
    // Retrieves the button object that was pressed. 
    Tile button = (Tile)event.getSource();
    
    System.out.println("Hello world!");
  }
}
