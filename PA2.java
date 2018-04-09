import java.util.*;
import java.io.*;

/**************************************************************************
 * @author Avinash Gupta
 * CS310 Spring 2018
 * Project 2
 * George Mason University
 * 
 * File Name: Board.java
 *
 * Description: This is a class where we solve the puzzle Tent Tree.
 ***************************************************************************/

// this is a simple interface to play the tent-tree game interactively
// TO RUN: java PA2 PUZZLE_FILE
// For example, java PA2 puzzles/puzzle1.txt

class PA2{

	static TentTree game; //pointer to the game
	
	public static void main(String[] args){
			
		if (args.length !=1){ //checks if we made the right file name and that there is an argyment after main
			System.out.println("Run the file as: java PA2 PUZZLE_FILE_NAME");
			return;			
		}
		
		initGame(args[0]); //input file name as the only argument

		if (game==null){ //checks if the game is initialized
			System.out.println("Cannot initialize the puzzle!");
			return;
		}
		
		int check = game.checkStatus(); //checks the status of the game, if the game has not been created or solved yet
		
		// invalid puzzle that cannot be played: no tree or invalid symbols
		if (check == 0){
			//System.out.println(game);	
			System.out.println("not a valid initial setting of tree-tent game!");
			return;
		}
		
		// puzzle already solved
		if (check ==1){
			System.out.println(game);	
			return;
		}
	
		// puzzle that can be played
		Scanner in = new Scanner(System.in);
		int choice = 0;
		while (choice != 1){
			System.out.println(game);	
			choice = doMenu(in);
			
			switch (choice){
				case 1: break;
				case 2: addTent(); break;
				case 3: removeTent(); break;
				case 4: int status = game.checkStatus();
						if (status==3){
							System.out.println("Invalid puzzle! Double check your tent numbers and locations!");
						}

						if (status==2){
							System.out.println("Unfinished puzzle: tree missing tent!");
						}

						if (status==1){
							//choice = 1 ; // exit when we are done
							System.out.println("Puzzle solved! Congratulations!!!");
						}

						if (status==0){
							System.out.println("Invalid puzzle! No tree or invalid symbol!");
						}
						break;
			}
		} 
			
	}
	
	// method that returns the next line not starting with '#' from the scanner
	// any line starts with '#' will be considered as comments and skipped
	// if no more line from the scanner, return an empty string
	public static String nextLine(Scanner scanner){
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				if (line.startsWith("#"))
					continue;
				return line;
			}	
			return "";
	}
	
	
	// method that accept a file name, open/read from the file 
	// and initialize a tent-tree puzzle based on file contents
	
	public static void initGame(String fileName){
		
		try {
					
			String tentSymbol = "X", treeSymbol = "O"; //default value of tent is "X", default value of tree is "O"
			
			// open the file
			Scanner fileReader = new Scanner(new File(fileName));	
			
			// first two lines specify the size of the puzzle
			String line = nextLine(fileReader);			
			int numRows = Integer.parseInt(line);
			line = nextLine(fileReader);	
			int numCols = Integer.parseInt(line);
			
			// the next two lines specify the tent and tree symbol (optional)
			// if not specified, use default values: "O" for tree and "X" for tent
			
			line = nextLine(fileReader);
			int numCommas = line.split(",").length;
			if (numCommas!=3){
				tentSymbol = line;
				
				line = nextLine(fileReader);
				numCommas = line.split(",").length;
				if (numCommas!=3){
					treeSymbol = line;
					line = nextLine(fileReader);
				}
			}
				
			// init a 2D grid
			game = new TentTree(numRows, numCols, tentSymbol, treeSymbol); 
			
			// rest of lines specify initial positions of trees and tents
			// one cell per line in this format: "row,col,symbol"
			while (!line.equals("")){
				String[] oneCellString = line.split(",");
				if (oneCellString.length!=3)
					throw new RuntimeException("invalid file format!");
					
				Position pos = new Position(Integer.parseInt(oneCellString[0]),
						Integer.parseInt(oneCellString[1]));
						
				// exception (and abort) if row/col index not valid
				if (!game.isValidPosition(pos))
					throw new RuntimeException("invalid index in file!");
				
				// set a tree/tent at position <row, col>
				if (treeSymbol.equals(oneCellString[2]))
					game.addTree(pos);
				else
					if (tentSymbol.equals(oneCellString[2]))
						game.addTent(pos);
					// exception (and abort) if symbol not recognized
					else
						throw new RuntimeException("invalid symbol in file!");
					
				// read next line
				line = nextLine(fileReader);
			}
		
			fileReader.close();	
		}
		catch(IOException e) {
			System.out.println("Invalid file");
			game = null;
		}
		catch(RuntimeException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			game = null;
		}
		
	}

	public static int doMenu(Scanner in)
	{
		while(true) {
			try {
				System.out.println("\nPlease select what you'd like to do:");
				System.out.println("1) Exit");
				System.out.println("2) Add a tent");
				System.out.println("3) Remove a tent");
				System.out.println("4) Check whether the puzzle has been solved");
				int choice = in.nextInt();
				in.nextLine();
				
				if(choice < 1 || choice > 4) {
					System.out.println("Invalid selection!");
					continue;
				}
				
				return choice;
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid selection!");
				in.nextLine();
			}
		}
		
	}
	
	// attempt to add one tent to board
	public static void addTent(){
		Scanner in = new Scanner(System.in);

		// keep asking until a valid position <row, col> is received from user
		while (true){
			try {
				System.out.println("\nPlease select where you'd like to add the tent ("+game.getTentSymbol()+"):");
				System.out.println("which row (0-"+(game.numRows()-1)+"): ");
				int row = in.nextInt();
				in.nextLine();
				System.out.println("which col (0-"+(game.numCols()-1)+"): ");
				int col = in.nextInt();
				in.nextLine();
				
				Position pos = new Position(row, col);
				if (game.isValidPosition(pos)){
				
					// if valid postion, try to add a tent at position
					// return to main menu if the attempt to add fails
					if (!game.addTent(pos)){
						System.out.println("Cannot add a tent ("+game.getTentSymbol()+") at row " + row+" col "+col+"!");
					}
					return;
				}
				else{
					System.out.println("Invalid row/col selection!");
					in.nextLine();				
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid row/col selection!");
				in.nextLine();
			}
		}
	
	}
	
	// attempt to remove a tent
	public static void removeTent(){
		Scanner in = new Scanner(System.in);

		// keep asking until a valid position <row, col> is received from user
		while (true){
			try {
				System.out.println("\nPlease select where you'd like to remove a tent ("+game.getTentSymbol()+"):");
				System.out.println("which row (0-"+(game.numRows()-1)+"): ");
				int row = in.nextInt();
				in.nextLine();
				System.out.println("which col (0-"+(game.numCols()-1)+"): ");
				int col = in.nextInt();
				in.nextLine();
								
				Position pos = new Position(row, col);
				if (game.isValidPosition(pos)){
					// if valid postion, try to add a tent at position
					// return to main menu if the attempt to add fails
					if (!game.removeTent(pos)){
						System.out.println("Cannot remove tent("+game.getTentSymbol()+") at row " + row+" col "+col+"!");
					}
					return;
				}
				else{
					System.out.println("Invalid row/col selection!");
					in.nextLine();				
				}
				
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid row/col selection!");
			
			}
		}
	}
	
	
}
