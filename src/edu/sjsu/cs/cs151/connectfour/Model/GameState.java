package edu.sjsu.cs.cs151.connectfour.Model;

/** GameState.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A state of the game. PLAYING is when the game is in progress,
 * WIN is when one of the players has 4 in a row, and TIE is when
 * the board is full but no one has won.
 * 
 * @author Holly Lind
 * @since 02.05.2019
 */
public enum GameState {
	PLAYING, WIN, TIE;
}
