package edu.sjsu.cs.cs151.connectfour.Controller;

import javax.swing.BorderFactory;

import edu.sjsu.cs.cs151.connectfour.app.ConnectFour;

public class SetGameBorderValve implements Valve{

	@Override
	public ValveResponse execute(Message message) {
		if (message.getClass() != SetGameBorderMessage.class) return ValveResponse.MISS;
		
		ConnectFour.view.getGamePanel().setBorder(BorderFactory.createTitledBorder(
												 ((SetGameBorderMessage) message).getTitle()));
		
		return ValveResponse.EXECUTED;
	}

}
