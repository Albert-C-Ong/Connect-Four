package edu.sjsu.cs.cs151.connectfour.Controller;

import javax.swing.JOptionPane;

import edu.sjsu.cs.cs151.connectfour.app.ConnectFour;

public class PlayerRestartValve implements Valve{

	
	
	@Override
	public ValveResponse execute(Message message) {
		
		if(message.getClass() != PlayerRestartMessage.class) return ValveResponse.MISS;
		
		JOptionPane.showMessageDialog(ConnectFour.view.getGamePanel(), "Opponenet has restarted. YOU WIN!", 
									 "Match over", JOptionPane.INFORMATION_MESSAGE, null);
		
		ConnectFour.model.newGame();
		
		
		//if local game, just clear board
		ConnectFour.view.getGamePanel().clearBoard();
		
		return ValveResponse.EXECUTED;
	}

}
