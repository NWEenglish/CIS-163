//Package Statement
package surroundpack;

//Import Statement(s)
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class SurroundPanel extends JPanel{

	//Initialization and Variables
	private JButton[][] board;
	private int playerOnBoard[][];

	private JMenuItem newGameItem;
	private JMenuItem quitGameItem;

	private JPanel boardPanel;
	private int totalPlayers;	
	private int BDSIZE;	
	private int player;
	private ButtonListener listener;

	SurroundGame game;

	//Constructor to create the panel
	public SurroundPanel(JMenuItem qGameItem, JMenuItem nGameItem) {

		//Sets the parameters
		quitGameItem = qGameItem;
		newGameItem = nGameItem;

		//Create and set action listeners
		listener = new ButtonListener();
		quitGameItem.addActionListener(listener);
		newGameItem.addActionListener(listener);

		//Setup for the panel
		setLayout(new BorderLayout());
		boardPanel = new JPanel();

		//Creates the panel for the frame
		getParameters();
		createBoard();
		add(boardPanel, BorderLayout.CENTER);

		//Create instance of game
		game = new SurroundGame(BDSIZE, player, totalPlayers);
	}

	/******************************************************************/

	//Fetches the parameters from user input
	private void getParameters() {

		//Temporary variable to hold the string version of totalPlayers
		String str_totalPlayers;

		//Try-Catch is used to catch if user does not enter an integer
		//This block is used to find total players
		try {
			str_totalPlayers = JOptionPane.showInputDialog("Please enter the total number of players (2 - 5): ");
			totalPlayers = Integer.parseInt(str_totalPlayers);

			if (totalPlayers >= 2 && totalPlayers <= 5) {
				JOptionPane.showMessageDialog(null, "You entered " + totalPlayers + " players.");
			}
			else {
				totalPlayers = 2;
				JOptionPane.showMessageDialog(null, "Invalid entry - Defaulting to " + totalPlayers + " players.");
			}
		}
		catch (IllegalArgumentException error) {
			totalPlayers = 2;
			JOptionPane.showMessageDialog(null, "ERROR - Invalid entry - Defaulting to " + totalPlayers + " players.");
		}

		/**************************************************************/

		//Temporary variable to hold the string version of BDSIZE
		String str_BDSIZE;

		//Try-Catch is used to catch if user does not enter an integer
		//This block us used to find board size
		try {
			str_BDSIZE = JOptionPane.showInputDialog("Please enter the board size (4 - 20): ");
			BDSIZE = Integer.parseInt(str_BDSIZE);

			if (BDSIZE >= 4 && BDSIZE <= 20) {
				JOptionPane.showMessageDialog(null, "You entered " + BDSIZE + " as the board size.");
			}
			else {
				BDSIZE = 10;
				JOptionPane.showMessageDialog(null, "Invalid entry - Defaulting to " + BDSIZE + " as the board size.");
			}
		}
		catch (IllegalArgumentException error) {
			BDSIZE = 10;
			JOptionPane.showMessageDialog(null, "ERROR - Invalid entry - Defaulting to " + BDSIZE + " as the board size.");
		}

		/**************************************************************/

		//Temporary variable to hold the string version of player
		String str_player;

		//Try-Catch is used to catch if user does not enter an integer
		//This block us used to find who starts
		try {
			str_player = JOptionPane.showInputDialog("Please enter the starting player's number (1 - " + totalPlayers + "): ");
			player = Integer.parseInt(str_player);

			if (player >= 1 && player <= totalPlayers) {
				JOptionPane.showMessageDialog(null, "You entered " + player + " as the starting player.");
			}
			else {
				player = 1;
				JOptionPane.showMessageDialog(null, "Invalid entry - Defaulting to " + player + " as the starting player.");
			}
		}
		catch (IllegalArgumentException error) {
			player = 1;
			JOptionPane.showMessageDialog(null, "ERROR - Invalid entry - Defaulting to " + player + " as the starting player.");
		}
	}

	/******************************************************************/

	//Creates the buttons and adds them to the board
	private void createBoard() {

		board = new JButton[BDSIZE][BDSIZE];
		boardPanel.setLayout(new GridLayout (BDSIZE, BDSIZE));

		//Creates buttons
		for (int i = 0; i < BDSIZE; i++)			//rows
			for (int j = 0; j < BDSIZE; j++) {		//cols
				board[i][j] = new JButton("");
				board[i][j].addActionListener(listener);
				boardPanel.add(board[i][j]);
			}
	}

	/******************************************************************/

	//Changes the buttons text to the player number that selected it
	private void displayBoard() {

		//Display buttons
		for (int row = 0; row < BDSIZE; row++)
			for (int col = 0; col < BDSIZE; col++) {
				Cell c = game.getCell(row, col);
				if (c != null)
					board[row][col].setText("" + c);
				else
					board[row][col].setText("");
			}
	}

	/******************************************************************/

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			//If they chose to quit the game
			if (e.getSource() == quitGameItem)
				System.exit(1);

			//If they chose to start a new game
			else if (e.getSource() == newGameItem) {
				repaint();
				getParameters();
				game = new SurroundGame(BDSIZE, player, totalPlayers);
				boardPanel.removeAll();
				createBoard();
				revalidate();
			}

			else {
				for (int row = 0; row < board.length; row++) {
					for (int col = 0; col < board[0].length; col++) {
						if(board [row][col] == e.getSource())
							if(game.select(row, col)) {
								playerOnBoard[row][col] = player;
								board[row][col].setText("" + game.getCell(row, col));
								game.isWinner();
								player = game.nextPlayer();
							}
							else
								JOptionPane.showMessageDialog(null, "Pick again.");

						displayBoard();
						int winner = game.isWinner();

						//Checks if a winner has been found.
						if (winner != -1) {
							JOptionPane.showMessageDialog(null, "Player " + winner + " wins!");
							game = new SurroundGame(BDSIZE, player, totalPlayers);
							displayBoard();
						}
					}

				}
			}
		}
	}
}