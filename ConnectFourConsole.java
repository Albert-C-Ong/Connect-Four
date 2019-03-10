package game;

/** ConnectFourConsole.java
 * 
 * CS 151 Spring 2019
 * Professor Katarzyna Tarnowska
 * 
 * A class that lets you play Connect Four
 * in the console. To be replaced with UI.
 * 
 * @author Holly Lind
 * @since 3/9/2019
 */
import java.util.Scanner;

public class ConnectFourConsole {

	// runs a Scanner to take user input
	public static void readInput(ConnectFourGame game)
	{
		Scanner in = new Scanner(System.in);
		String input = in.next();
		
		if (input.equals("p1"))
		{
			System.out.println(game.oneTurn(true, in.nextInt()));
			printBoard(game.getBoard());
		}
		else if (input.equals("p2"))
		{
			System.out.println(game.oneTurn(false, in.nextInt()));
			printBoard(game.getBoard());
		}
		else if (input.equals("newgame") || input.equals("reset"))
		{
			game.newGame();
			startGame();
			printBoard(game.getBoard());
		}
		else
		{
			System.out.println("must start line with p1, p2, or newgame");
		}
		
		readInput(game);
	}
	
	
	// prints the current Connect Four Board
	public static void printBoard(Board board)
	{
		System.out.println("");
		for (int row = Board.getRows() - 1; row >= 0; row--)
		{
			for (int column = 0; column < Board.getColumns(); column++)
			{
				if (!board.get(column).get(row).getFilled())
					System.out.print("* ");
				else if (board.get(column).get(row).getColor().equals(Board.getPlayerOneColor()))
					System.out.print("x ");
				else if (board.get(column).get(row).getColor().equals(Board.getPlayerTwoColor()))
					System.out.print("o ");
				else
					System.out.print("? ");
			}
			System.out.println("");
		}
		System.out.println("0 1 2 3 4 5 6\n");
	}
	
	
	// prints instructions for playing the game in the console
	public static void startGame()
	{
		System.out.println("x is player 1; o is player 2");
		System.out.println("* is empty\n");
		System.out.println("type p1 or p2 to select player, then type number for column");
		System.out.println("ex. type \"p1 3\" to put p1's piece in column 3\n");
		
		System.out.println("initial board:");
	}
	
	
	public static void main(String[] args)
	{
		ConnectFourGame game = new ConnectFourGame();
		startGame();
		printBoard(game.getBoard());
		
		readInput(game);
	}
	
}
