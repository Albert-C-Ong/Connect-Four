
package edu.sjsu.cs.cs151.connectfour.View;


import java.awt.*;
import java.net.URL;
import javax.swing.*;
import edu.sjsu.cs.cs151.connectfour.View.animation.*;


/** LoadingPanel.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * Loading panel. 
 * Used when player that's hosting server is waiting
 * for a client to join.
 * 
 * @author Albert Ong, Holly Lind
 * @since 09.05.2019
 */
public class LoadingPanel extends JPanel {

	/**
	 * Ctor - initializes panel
	 * @param parent - view that contains panel
	 */
	public LoadingPanel(View parent) {
		
		// Sets the background color to white. 
		setBackground(Color.WHITE);
		
    // Adds the grid layout and sets the constraints. 
    GridBagLayout layout = new GridBagLayout();
    setLayout(layout);
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.insets = new Insets(0, 0, 20, 0);
	    
	    // Adds loading circle 
		MoveableShape shape = new LoadingShape();
		ShapeIcon icon = new ShapeIcon(shape, 100, 100);
		JLabel loadingCircle = new JLabel(icon);
    add(loadingCircle, gbc);
	    
    // Creates the exit button. 
    JButton button = new JButton();
    button.setName("LOADING_EXIT");
    button.setPreferredSize(new Dimension(400, 80));
    URL url_select = View.class.getResource("/menu_button_exit_select.png");
		URL url_deselect = View.class.getResource("/menu_button_exit_deselect.png");
		button.setIcon(new ImageIcon(url_deselect));
		button.setRolloverIcon(new ImageIcon(url_select));
    button.addActionListener(parent);
    
    // Makes the border and content of the button opaque. 
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setBorderPainted(false);
    
    // Adds the exit button. 
    add(button, gbc);
    
    //start animation
    Timer t = new Timer(100, event ->
    {
      shape.move();
      loadingCircle.repaint();
    });
    t.start();
    
    setVisible(true); 
	}
	
}
