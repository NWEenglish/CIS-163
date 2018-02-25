/***********************************************************************
 * 
 * A Java program that plays the Surround game and a bit more. A player
 * wins by surrounding the another players square. This program includes
 * additional functionality of a wrapped board option and a colorful
 * board option. Each adds a new style to the gameplay mechanics for the
 * user.
 * 
 * @author Nicholas English
 * @version 1.0
 *
 **********************************************************************/

package surroundpack;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;

public class SurroundGame{

	/** Board is used to represent each cell/button **/
	private Cell[][] board;

	/** Initializes winner to -1 for if no winner is found **/
	private int winner = -1;


	/** Used to store the total number of players **/
	private int totalPlayers;

	/** Used to store the player's number **/
	private int player;

	/** Used to store the size of the board **/
	private int BDSIZE;

	/*******************************************************************
	 *
	 * A constructor that creates the game with the given board size.
	 * 
	 * @param bdSIZE Used to set the desired board size.
	 * @param tPlayers Used to set the total player count.
	 * @param wStarts Used to set the starting player.
	 * 
	 ******************************************************************/
	public SurroundGame(int bdSIZE, int tPlayers, int wStarts) {
		BDSIZE = bdSIZE;		
		totalPlayers = tPlayers;
		player = wStarts;

		int rowEnd = BDSIZE - 1;
		int colEnd = BDSIZE - 1;

		board = new Cell[BDSIZE][BDSIZE];
	}

	/*******************************************************************
	 * 
	 * This method is called from the SurroundPanel class and is invoked
	 * when the user has selected a JButton. This method determines if
	 * the row, col that was selected was an empty space. 
	 * 
	 * @return Returns true if space is available or false if it is not.
	 * 
	 ******************************************************************/
	public boolean select(int row, int col) {
		if(board[row][col] == null)
			return true;
		else
			return false;
	}	

	/*******************************************************************
	 * 
	 * This method is called from the Surround class and is used in the
	 * by the newGameMenu action listener.
	 * 
	 ******************************************************************/
	public void reset() {
		for (int row = 0; row < BDSIZE; row++) {
			for (int col = 0; col < BDSIZE; col++) {
				board[row][col] = null;

			}
		}
	}

	/*******************************************************************
	 * 
	 * This method is called from the SurroundPanel class and it 
	 * determines if a player has won the game after the select method
	 * was called. Return -1 if there is no winner. Return player
	 * if there is a winner.
	 * 
	 * @return winner Returns the winning player number or -1 if none.
	 * 
	 ******************************************************************/
	public int isWinner() {
		winner = -1;

		//Loop to check each row
		for(int row = 0; row < BDSIZE; row++) {

			//Loop to check each col
			for(int col = 0; col < BDSIZE; col++) {

				//Top-Left Corner (Checks two sides)
				if (row == 0 && col == 0) {
					if (board[0][1] != null && board[1][0] != null)
						if (board[0][1].getPlayerNumber() == board[1][0].getPlayerNumber())
							if (board[0][1].getPlayerNumber() != board[0][0].getPlayerNumber())
								winner = board[0][1].getPlayerNumber();
				}

				//				//Left Side Wall (Checks three sides)
				//				else if (row != 0 && col == 0 && row != (BDSIZE - 1)) {
				//					if (board[row - 1][col] != null && board[row][col + 1] != null && board[row + 1][col] != null)
				//						if (board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber())
				//							if (board[row - 1][col].getPlayerNumber() == board[row][col + 1].getPlayerNumber())
				//								if (board[row - 1][col].getPlayerNumber() != board[row][col].getPlayerNumber())
				//									winner = board[row - 1][col].getPlayerNumber();
				//				}
				//
				//				//Bottom-Left Corner (Checks two sides)
				//				else if (row == (BDSIZE - 1) && col == 0) {
				//					if (board[BDSIZE - 2][0] != null && board[BDSIZE - 1][1] != null)
				//						if (board[BDSIZE - 2][0].getPlayerNumber() == board[BDSIZE - 1][1].getPlayerNumber())
				//							if (board[BDSIZE - 1][0].getPlayerNumber() != board[BDSIZE - 2][0].getPlayerNumber())
				//								winner = board[BDSIZE - 1][0].getPlayerNumber();
				//				}
				//
				//				//Bottom Side Wall (Checks three sides)
				//				else if (row == (BDSIZE - 1) && col != 0 && col != (BDSIZE - 1)) {
				//					if (board[BDSIZE - 1][col - 1] != null && board[BDSIZE - 1][col + 1] != null && board[BDSIZE - 2][col] != null)
				//						if (board[BDSIZE - 1][col - 1].getPlayerNumber() == board[BDSIZE - 1][col + 1].getPlayerNumber())
				//							if (board[BDSIZE - 1][col - 1].getPlayerNumber() == board[BDSIZE - 2][col].getPlayerNumber())
				//								if (board[BDSIZE - 1][col - 1].getPlayerNumber() != board[BDSIZE - 1][col].getPlayerNumber())
				//									winner = board[BDSIZE - 1][col - 1].getPlayerNumber();
				//				}
				//
				//				//Bottom-Right Corner (Checks two sides)
				//				else if (row == (BDSIZE - 1) && col == (BDSIZE - 1)) {
				//					if (board[BDSIZE - 2][BDSIZE - 1] != null && board[BDSIZE - 1][BDSIZE - 2] != null)
				//						if (board[BDSIZE - 2][BDSIZE - 1].getPlayerNumber() == board[BDSIZE - 1][BDSIZE - 2].getPlayerNumber())
				//							if (board[BDSIZE - 2][BDSIZE - 1].getPlayerNumber() != board[BDSIZE - 1][BDSIZE - 1].getPlayerNumber())
				//								winner = board[BDSIZE - 2][BDSIZE - 1].getPlayerNumber();
				//				}
				//
				//				//Right Side Wall (Checks three sides)
				//				else if (row != (BDSIZE - 1) && row != 0 && col == (BDSIZE - 1)) {
				//					if (board[row - 1][BDSIZE - 1] != null && board[row + 1][BDSIZE - 1] != null && board[row][BDSIZE - 2] != null)
				//						if (board[row - 1][BDSIZE - 1].getPlayerNumber() == board[row + 1][BDSIZE - 1].getPlayerNumber())
				//							if (board[row - 1][BDSIZE - 1].getPlayerNumber() == board[row][BDSIZE - 2].getPlayerNumber())
				//								if (board[row - 1][BDSIZE - 1].getPlayerNumber() != board[row][BDSIZE - 1].getPlayerNumber())
				//									winner = board[row - 1][BDSIZE - 1].getPlayerNumber();
				//				}
				//
				//				//Top-Right Corner (Checks two sides)
				//				else if (row == 0 && col == (BDSIZE - 1)) {
				//					if (board[0][BDSIZE - 2] != null && board[1][BDSIZE - 1] != null)
				//						if (board[0][BDSIZE - 2].getPlayerNumber() == board[1][BDSIZE - 1].getPlayerNumber())
				//							if (board[0][BDSIZE - 2].getPlayerNumber() != board[0][BDSIZE - 1].getPlayerNumber())
				//								winner = board[0][BDSIZE - 2].getPlayerNumber();
				//				}
				//
				//				//Normal Conditions (Checks four sides)
				//				else {
				//					if (board[row - 1][col] != null && board[row + 1][col] != null && board[row][col - 1] != null && board[row][col + 1] != null)
				//						if ((board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber()) && (board[row][col - 1].getPlayerNumber() == board[row][col + 1].getPlayerNumber()))
				//							if (board[row - 1][col].getPlayerNumber() == board[row][col - 1].getPlayerNumber())
				//								if (board[row - 1][col].getPlayerNumber() != board[row][col].getPlayerNumber())
				//									winner = board[row - 1][col].getPlayerNumber();
				//				}
			}
		}

		return winner;
	}

	/*******************************************************************
	 * 
	 * This method is called from the SurroundPanel class and it 
	 * determines if a player has won the game after the select method
	 * was called. Return -1 if there is no winner. Return player
	 * if there is a winner. This is the additional function version
	 * that uses a wrapped board.
	 * 
	 * @return winner Returns the winning player number or -1 if none.
	 * 
	 ******************************************************************/

	public int isWrapWinner() {
		winner = -1;
		//
		//		//Loop to check each row
		//		for(int row = 0; row < BDSIZE; row++) {
		//
		//			//Loop to check each col
		//			for(int col = 0; col < BDSIZE; col++) {
		//
		//				//Top-Left Corner (Checks two sides)
		//				if (row == 0 && col == 0) {
		//					if (board[0][1] != null && board[1][0] != null && board[BDSIZE - 1][0] != null && board[0][BDSIZE - 1] != null)
		//						if (board[0][1].getPlayerNumber() == board[1][0].getPlayerNumber() && board[BDSIZE - 1][0].getPlayerNumber() == board[0][BDSIZE - 1].getPlayerNumber())
		//							if (board[0][1].getPlayerNumber() != board[0][0].getPlayerNumber())
		//								winner = board[0][1].getPlayerNumber();
		//				}
		//
		//				//Left Side Wall (Checks three sides)
		//				else if (row != 0 && col == 0 && row != (BDSIZE - 1)) {
		//					if (board[row - 1][col] != null && board[row][col + 1] != null && board[row + 1][col] != null && board[row][BDSIZE - 1] != null)
		//						if (board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber() && board[row][col + 1].getPlayerNumber() == board[row][BDSIZE - 1].getPlayerNumber())
		//							if (board[row - 1][col].getPlayerNumber() == board[row][col + 1].getPlayerNumber())
		//								if (board[row - 1][col].getPlayerNumber() != board[row][col].getPlayerNumber())
		//									winner = board[row - 1][col].getPlayerNumber();
		//				}
		//
		//				//Bottom-Left Corner (Checks two sides)
		//				else if (row == (BDSIZE - 1) && col == 0) {
		//					if (board[BDSIZE - 2][0] != null && board[BDSIZE - 1][1] != null && board[BDSIZE - 1][BDSIZE - 1] != null && board[0][0] != null)
		//						if (board[BDSIZE - 2][0].getPlayerNumber() == board[BDSIZE - 1][1].getPlayerNumber() && board[BDSIZE - 1][BDSIZE - 1].getPlayerNumber() == board[0][0].getPlayerNumber())
		//							if (board[BDSIZE - 2][0].getPlayerNumber() == board[0][0].getPlayerNumber())
		//								if (board[BDSIZE - 1][0].getPlayerNumber() != board[BDSIZE - 2][0].getPlayerNumber())
		//									winner = board[BDSIZE - 1][0].getPlayerNumber();
		//				}
		//
		//				//Bottom Side Wall (Checks three sides)
		//				else if (row == (BDSIZE - 1) && col != 0 && col != (BDSIZE - 1)) {
		//					if (board[BDSIZE - 1][col - 1] != null && board[BDSIZE - 1][col + 1] != null && board[BDSIZE - 2][col] != null && board[0][col] != null)
		//						if (board[BDSIZE - 1][col - 1].getPlayerNumber() == board[BDSIZE - 1][col + 1].getPlayerNumber() && board[BDSIZE - 2][col].getPlayerNumber() == board[0][col].getPlayerNumber())
		//							if (board[BDSIZE - 1][col - 1].getPlayerNumber() == board[BDSIZE - 2][col].getPlayerNumber())
		//								if (board[BDSIZE - 1][col - 1].getPlayerNumber() != board[BDSIZE - 1][col].getPlayerNumber())
		//									winner = board[BDSIZE - 1][col - 1].getPlayerNumber();
		//				}
		//
		//				//Bottom-Right Corner (Checks two sides)
		//				else if (row == (BDSIZE - 1) && col == (BDSIZE - 1)) {
		//					if (board[BDSIZE - 2][BDSIZE - 1] != null && board[BDSIZE - 1][BDSIZE - 2] != null && board[BDSIZE - 1][0] != null && board[0][BDSIZE - 1] != null)
		//						if (board[BDSIZE - 2][BDSIZE - 1].getPlayerNumber() == board[BDSIZE - 1][BDSIZE - 2].getPlayerNumber() && board[BDSIZE - 1][0].getPlayerNumber() == board[0][BDSIZE].getPlayerNumber())
		//							if (board[BDSIZE - 2][BDSIZE - 1].getPlayerNumber() == board[BDSIZE - 1][0].getPlayerNumber())
		//								if (board[BDSIZE - 2][BDSIZE - 1].getPlayerNumber() != board[BDSIZE - 1][BDSIZE - 1].getPlayerNumber())
		//									winner = board[BDSIZE - 2][BDSIZE - 1].getPlayerNumber();
		//				}
		//
		//				//		//Right Side Wall (Checks three sides)
		//				else if (row != (BDSIZE - 1) && row != 0 && col == (BDSIZE - 1)) {
		//					if (board[row - 1][BDSIZE - 1] != null && board[row + 1][BDSIZE - 1] != null && board[row][BDSIZE - 2] != null && board[row][0] != null)
		//						if (board[row - 1][BDSIZE - 1].getPlayerNumber() == board[row + 1][BDSIZE - 1].getPlayerNumber() && board[row][BDSIZE - 2].getPlayerNumber() == board[row][0].getPlayerNumber())
		//							if (board[row - 1][BDSIZE - 1].getPlayerNumber() == board[row][BDSIZE - 2].getPlayerNumber())
		//								if (board[row - 1][BDSIZE - 1].getPlayerNumber() != board[row][BDSIZE - 1].getPlayerNumber())
		//									winner = board[row - 1][BDSIZE - 1].getPlayerNumber();
		//				}
		//
		//				//		//Top-Right Corner (Checks two sides)
		//				else if (row == 0 && col == (BDSIZE - 1)) {
		//					if (board[0][BDSIZE - 2] != null && board[1][BDSIZE - 1] != null && board[0][0] != null && board[BDSIZE - 1][BDSIZE - 1] != null)
		//						if (board[0][BDSIZE - 2].getPlayerNumber() == board[1][BDSIZE - 1].getPlayerNumber() && board[0][0].getPlayerNumber() == board[BDSIZE - 1][BDSIZE - 1].getPlayerNumber())
		//							if (board[0][BDSIZE - 2].getPlayerNumber() == board[0][0].getPlayerNumber())
		//								if (board[0][BDSIZE - 2].getPlayerNumber() != board[0][BDSIZE - 1].getPlayerNumber())
		//									winner = board[0][BDSIZE - 2].getPlayerNumber();
		//				}
		//
		//				//	//Normal Conditions (Checks four sides)
		//				else {
		//					if (board[row - 1][col] != null && board[row + 1][col] != null && board[row][col - 1] != null && board[row][col + 1] != null)
		//						if ((board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber()) && (board[row][col - 1].getPlayerNumber() == board[row][col + 1].getPlayerNumber()))
		//							if (board[row - 1][col].getPlayerNumber() == board[row][col - 1].getPlayerNumber())
		//								if (board[row - 1][col].getPlayerNumber() != board[row][col].getPlayerNumber())
		//									winner = board[row - 1][col].getPlayerNumber();
		//				}
		//			}
		//		}
		return winner;
	}

	/*******************************************************************
	 * 
	 * This method is called from the SurroundPanel class and in the 
	 * ButtonListener inner-class. This is used to set a color to the 
	 * risk level of any particular cell.
	 * 
	 ******************************************************************/
	public void cellColor() {

		//Loop to check each row
		for(int row = 0; row < BDSIZE; row++) {

			//Loop to check each col
			for(int col = 0; col < BDSIZE; col++) {

				/** Initializes the risk value (risk factor) to zero **/
				int risk = 0;

				if (board[row][col - 1] != null && board[row][col - 1] != board[row][col])
					risk++;
				if (board[row][col + 1] != null && board[row][col + 1] != board[row][col])
					risk++;
				if (board[row - 1][col] != null && board[row - 1][col] != board[row][col])
					risk++;
				if (board[row +1][col] != null && board[row + 1][col] != board[row][col])
					risk++;

				//				if (risk <= 1) {
				//					board[row][col].setBackground(Color.green);
				//				}
				//				if (risk > 1) {
				//					board[row][col].setBackground(Color.red);
				//				}


			}
		}
	}


	/*******************************************************************
	 * 
	 * This method is called from the SurroundPanel class and in the 
	 * ButtonListener inner-class. This is used to get what is selected
	 * by the player on the board.
	 * 
	 * @param row Used to represent the row.
	 * @param col Used to represent the column.
	 * 
	 * @return Location in the board with row and col.
	 * 
	 ******************************************************************/
	public Cell getCell(int row, int col) {

		//If cell already has a value
		if(board[row][col] != null && select(row, col)) {
			board[row][col] = new Cell(player);
			return board[row][col];
		}
		else
			return null;
	}

	/*******************************************************************
	 * 
	 * This method is called to find who is the next player that will be
	 * making a move on the board.
	 * 
	 * @return Player number of the next player.
	 * 
	 ******************************************************************/

	public int nextPlayer() {
		player = (player + 1) % totalPlayers;
		if(player == 0)
			player = totalPlayers;
		player++;
		return player;
	}

	/*******************************************************************
	 * 
	 * This method is called from the SurroundPanel class and obtains
	 * the size of the board.
	 * 
	 * @return Size of the board.
	 * 
	 ******************************************************************/
	public int getBDSIZE() {
		return this.BDSIZE;
	}

	/*******************************************************************
	 * 
	 * Obtains the total number of players in the game
	 * 
	 * @return Total number of players.
	 * 
	 ******************************************************************/
	public int getTotalPlayers() {
		return this.totalPlayers;
	}

	/*******************************************************************
	 * 
	 * Obtains the number of the player whose turn it is
	 * 
	 * @return Returns the current player number.
	 * 
	 ******************************************************************/
	public int getPlayer() {
		return this.player;
	}

	/******************************************************************
	 * Checks how many opposing players' cells surround a particular
	 * cell
	 * 
	 * @param row The row in which the cell is found
	 * @param col The column in which the cell is found
	 * 
	 * @return Returns an Integer between 0 and 4
	 * 
	 *****************************************************************/
	public int getSurround(int row, int col) {

		int count = 0;
		int rowUp = row-1;
		if(rowUp < 0)
			rowUp = BDSIZE - 1;
		int rowDown = row+1;
		if(rowDown > BDSIZE - 1)
			rowDown = 0;
		int colLeft = col -1;
		if(colLeft<0)
			colLeft = BDSIZE - 1;
		int colRight = col +1;
		if(colRight > BDSIZE - 1)
			colRight = 0;
		if( !select(row,colLeft) && 
				board[row][colLeft].getPlayerNumber() != player)
			count++;
		if( !select(row,colRight) && 
				board[row][colRight].getPlayerNumber() != player)
			count++;
		if( !select(rowUp,col) && board[rowUp][col].getPlayerNumber()
				!= player)
			count++;
		if( !select(rowDown,col) && 
				board[rowDown][col].getPlayerNumber() != player)
			count++;
		if( !select(rowDown,col) && 
				board[rowDown][col].getPlayerNumber()==player || 
				!select(rowUp,col) && 
				board[rowUp][col].getPlayerNumber()==player || 
				!select(row,colLeft) &&
				board[row][colLeft].getPlayerNumber()==player || 
				!select(row,colRight) && 
				board[row][colRight].getPlayerNumber()==player )
			count = 0;
		return count;
	}

	//NO PRINT STATEMENTS

}
