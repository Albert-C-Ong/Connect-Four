package animation;

import java.awt.*;
import javax.swing.*;

public class AnimationTester {

	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		//colors - red and black
		Color dark_red = new Color(250, 50, 50);
		Color light_red = new Color(200, 30, 30);
		Color dark_gray = new Color(90, 90, 90);
		Color light_gray = new Color(60, 60, 60);
		
		//the 4 Tiles
		final MoveableShape shape1 = new TileShape(0, 0, 100, dark_red, light_red, 600);
		TileIcon icon1 = new TileIcon(shape1, 100, 1000);
		
		final MoveableShape shape2 = new TileShape(0, 0, 100, dark_gray, light_gray, 500);
		TileIcon icon2 = new TileIcon(shape2, 100, 1000);
		
		final MoveableShape shape3 = new TileShape(0, 0, 100, dark_red, light_red, 400);
		TileIcon icon3 = new TileIcon(shape3, 100, 1000);
		
		final MoveableShape shape4 = new TileShape(0, 0, 100, dark_gray, light_gray, 300);
		TileIcon icon4 = new TileIcon(shape4, 100, 1000);
		
		//add the shapes/icons to the frame
		final JLabel label1 = new JLabel(icon1);
		frame.add(label1);
		
		final JLabel label2 = new JLabel(icon2);
		frame.add(label2);
		
		final JLabel label3 = new JLabel(icon3);
		frame.add(label3);
		
		final JLabel label4 = new JLabel(icon4);
		frame.add(label4);
		
		frame.setLayout(new FlowLayout());
		frame.setSize(new Dimension(1600, 900));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//animate the Tiles one at a time
		final int DELAY = 100;
		Timer t1 = new Timer(DELAY, event ->
		{
			shape1.move();
			label1.repaint();
		});
		t1.start();
		
		Timer t2 = new Timer(DELAY, event ->
		{
			shape2.move();
			label2.repaint();
		});
		t2.setInitialDelay(500);
		t2.start();
		
		Timer t3 = new Timer(DELAY, event ->
		{
			shape3.move();
			label3.repaint();
		});
		t3.setInitialDelay(1000);
		t3.start();
		
		Timer t4 = new Timer(DELAY, event ->
		{
			shape4.move();
			label4.repaint();
		});
		t4.setInitialDelay(1500);
		t4.start();
	}
	
	
	
}
