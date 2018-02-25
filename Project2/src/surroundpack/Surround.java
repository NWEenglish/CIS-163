package surroundpack;

import javax.swing.*;

public class Surround {
	
	/*******************************************************************
	 * 
	 * The main method of the Surround package.
	 * 
	 * @param args
	 *
	 ******************************************************************/
		
	public static void main(String[] args){
		
		int row, col;
		
		//Creates the JFrame
		JFrame frame = new JFrame("Surround");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar;
		JMenu fileMenu;
		JMenuItem quitGameItem;
		JMenuItem newGameItem;
		
		//Creates all the items for the menu
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		quitGameItem = new JMenuItem("Quit Game");
		newGameItem = new JMenuItem("New Game");
			
		//Adds the menu items together
		fileMenu.add(quitGameItem);
		fileMenu.add(newGameItem);
		menuBar.add(fileMenu);
		
		//Adds the JMenuBar to the JFrame
		frame.setJMenuBar(menuBar);
		
		//Creates a new instance of the SurroundPanel
		SurroundPanel panel = new SurroundPanel(quitGameItem, newGameItem);
		
		//Settings for the JFrame
		frame.add(panel);
		frame.setSize(600, 500);
		frame.setVisible(true);
	}
}