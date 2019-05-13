package edu.sjsu.cs.cs151.connectfour.Controller;

import javax.swing.JOptionPane;

import edu.sjsu.cs.cs151.connectfour.app.ConnectFour;

public class PlayerLeftValve implements Valve{

	@Override
	public ValveResponse execute(Message message) {
		
		if(message.getClass() != PlayerLeftMessage.class) return ValveResponse.MISS;
		
		JOptionPane.showMessageDialog(ConnectFour.view.getGlassPane(), "Opponenet has left", 
									 "Match over", JOptionPane.INFORMATION_MESSAGE, null);
		
		ConnectFour.view.replacePanel(ConnectFour.view.getGamePanel(), ConnectFour.view.getMenuPanel());
		
		try {
			ConnectFour.queue.put(new QuitGameMessage(false));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return ValveResponse.EXECUTED;
	}

}
