/**************************************************************************
 * @author Avinash Gupta
 * CS310 Spring 2018
 * Project 2
 * George Mason University
 * 
 * File Name: Board.java
 *
 * Description: This is a class where we define what can happen in the puzzle tent tree.
 ***************************************************************************/
//this is the class for a simplified Tent-Tree puzzle

class TentTree{

	private int numRows, numCols;	// size of the 2D board
	private HashMap<Position, String> grid; // the board stored in a hash table
	private String treeSymbol, tentSymbol;  // the string representing tree/tent on board
	
	/**
	 * This is the constructor that initializes the variables
	 * @param numRows Keeps track of the number of rows
	 * @param numCols Keeps track of the number of columns
	 * @param tent This is the symbol that represents the tent in the grid (these are custom)
	 * @param tree This is the symbol that represents the tree in the grid (these are custom)
	 */
	public TentTree(int numRows, int numCols, String tent, String tree){
		// constructor that initializes attributes
		this.numRows = numRows;
		this.numCols = numCols;
		this.tentSymbol = tent;
		this.treeSymbol = tree;
		this.grid = new HashMap<Position,String>();
	}
	
	/**
	 * This is the constructor that initializes the variables
	 * @param numRows Keeps track of the number of rows
	 * @param numCols Keeps track of the number of columns
	 * @param tent This is the symbol that represents the tent in the grid (these are default)
	 * @param tree This is the symbol that represents the tree in the grid (these are default)
	 */
	public TentTree(int numRows, int numCols){
		// overloaded constructor that by default uses "X" for tent 
		// and "O" (capital O not 0) for tree
		this.numRows = numRows;
		this.numCols = numCols;
		this.tentSymbol = "X";
		this.treeSymbol = "O";
		this.grid = new HashMap<Position,String>();
	}
	
	// accessors that return tree/tent representation, O(1)
	/**
	 * 
	 *@return Returns the symbols of the tent
	 */
	public String getTentSymbol(){ return tentSymbol;}
	
	/**
	 * 
	 * @return Returns the symbol of the tree
	 */
	public String getTreeSymbol(){ return treeSymbol;}
	
	// accessors that return number of rows/columns, O(1)
	/**
	 * 
	 *@return Returns the number of rows
	 */
	public int numRows(){ return numRows;}
	
	/**
	 * 
	 *@return Returns the number of columns
	 */
	public int numCols(){ return numCols;}

	/**
	 * Checks if the given position is valid or not
	 * @param pos This is what we check is valid or not
	 * @return returns true or false depending on weather the position is valid or not
	 */
	public boolean isValidPosition(Position pos){
		// check whether the specified position is a valid position for the board
		// return true for valid positions and false for invalid ones
		// O(1)
		if((pos.getRow() >= 0 && pos.getRow() <= numRows) && (pos.getCol() >= 0 && pos.getCol() <= numCols)) { //If the row and columns are within the bounds then it returns true
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the given symbol is valid or not
	 * @param pos This is what we check is valid or not
	 * @return returns true or false depending on weather the symbol is valid or not
	 */
	public boolean isValidSymbol(String s){
		// check whether the specified string s is a valid tent or tree symbol of the game
		// O(1)
		if(s.equals(tentSymbol) || s.equals(treeSymbol)) { //If the symbol is the same as the tent or tree symbol the it will return true
			return true;
		}
		return false;
	}
	
	/**
	 * This will set the cell with the specified symbol
	 * @param pos This is the cell we are setting
	 * @param s This is the symbol that we are setting in the location
	 * @return returns true or false depending on weather the symbol has been set or not
	 */
	public boolean set(Position pos, String s){
		// set the cell at the specified position pos to be the specified string s
		// do not change the board if invalid position: return false
		// do not change the board if invalid symbol: return false
		// do not change the board if the position is already occupied (not empty): return false
		// return true if board changed successfully
		// assuming HashMap overhead constant, O(1)
		
		if(isValidPosition(pos) == false) { //If the position is not valid then it will return false
			return false;
		}
		
		if(isValidSymbol(s) == false) { //If the symbol is not valid then it will return false 
			return false;
		}
		
		if(!grid.contains(pos)) { //If the element is not in the table
			
			if(grid.getValue(pos) == null) { //If no value exists
				
				grid.add(pos, s); //the value will be added
				return true;
			}
			
			if(grid.getValue(pos) != null) { //If value exists
				
				return false;
			}
		}else {
			return false;
		}
		
		return false;
	}
	
	/**
	 * This will get the symbol that the position is mapped to
	 * @param pos This is the position that we are checking
	 * @return returns the symbol that is mapped with this position
	 */
	public String get(Position pos){
		// return the cell at the specified position pos 
		// if invalid position: return null
		// if empty cell, return null
		// assuming HashMap overhead constant, O(1)
		
		if(isValidPosition(pos) == false) { //If the position is not valid then it will return false
			return null;
		}
		return grid.get(pos); //else it will get the symbol mapped with the position
	}
	
	/**
	 * This adds the tent at the specified position
	 * @param pos This is the position where we are adding the tent to
	 * @return returns true or false depending on weather the tent has been added or not
	 */
	public boolean addTent(Position pos){
		// add another tent at the specified position pos
		// return false if a new tent cannot be added at pos 
		//     (i.e. attempt fails if pos is already occupied)
		// return true otherwise
		// assuming HashMap overhead constant, O(1)
		
		int tempRow = pos.getRow();
		int tempCol = pos.getCol();
		
		boolean condition1 = false;
		boolean condition2 = false;
		boolean condition3 = false;
		boolean condition4 = false;
		
		if(grid.has(pos, grid.get(pos))) { //If there already exists an element in that position
			
			return false;
		}
		
		if(tempRow == 0 && tempCol == 0) { //Corner case 1
			
			if( grid.has(new Position(tempRow,tempCol+1),treeSymbol) || grid.has(new Position(tempRow+1,tempCol),treeSymbol)){ //If there is a tree either next to the position or below the position
				
				condition1 = true;
			}

			
			if( !grid.has(new Position(tempRow,tempCol+1),tentSymbol) || !grid.has(new Position(tempRow+1,tempCol),tentSymbol)){ //If there is a tree either next to the position or below the position
				
				condition2 = true;
			}
		
			if(!grid.has(new Position(tempRow+1,tempCol+1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol+1),tentSymbol)) { //If there is NO tree or a tent at the diagonal to the position
				
				condition3 = true;
			}
			
			if(condition1&&condition2&&condition3) {
				
				grid.add(pos, tentSymbol);
				return true;
			}else {
				return false;
			}

		}
		
		if(tempRow == numRows-1 && tempCol == 0) { //Corner case 2
			
			if(grid.has(new Position(tempRow,tempCol+1),treeSymbol) || grid.has(new Position(tempRow-1,tempCol),treeSymbol)){ //If there is a tree either next to the position or below the position
				
				condition1 = true;
			}
			
			if(!grid.has(new Position(tempRow,tempCol+1),tentSymbol) || !grid.has(new Position(tempRow-1,tempCol),tentSymbol)){ //If there is NO tent either next to the position or below the position
				
				condition2 = true;
			}
			
			if(!grid.has(new Position(tempRow-1,tempCol+1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol+1),tentSymbol)) { //If there is NO tent or tree at the diagonal to the position
				
			}
			
			if(condition1&&condition2&&condition3) {
				
				grid.add(pos, tentSymbol);
				return true;
			}else {
				return false;
			}
		}
		
		if(tempRow == numRows-1 && tempCol == numCols-1) { //Corner case 3
			
			if(grid.has(new Position(tempRow,tempCol-1),treeSymbol) || grid.has(new Position(tempRow-1,tempCol),treeSymbol)){ //If there is a tree either next to the position or below the position
				
				condition1 = true;
			}
			
			if(!grid.has(new Position(tempRow,tempCol-1),tentSymbol) || !grid.has(new Position(tempRow-1,tempCol),tentSymbol)){ //If there is NO tent either next to the position or below the position
				
				condition2 = true;
			}
			
			if(!grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),tentSymbol)) { //If there is NO tent or tree at the diagonal of the position
				
				condition3 = true;
			}
			
			if(condition1&&condition2&&condition3) {
				
				grid.add(pos, tentSymbol);
				return true;
			}else {
				return false;
			}
		}
		
		if(tempRow == 0 && tempCol == numCols-1) { //Corner case 4
			
			if(grid.has(new Position(tempRow,tempCol-1),treeSymbol) || grid.has(new Position(tempRow+1,tempCol),treeSymbol)){ //If there is a tree either next to the position or below the position
				
				condition1 = true;
			}
			
			if(!grid.has(new Position(tempRow,tempCol-1),tentSymbol) || !grid.has(new Position(tempRow+1,tempCol),tentSymbol)){ //If there is NO tent either next to the position or below the position
				
				condition2 = true;
			}
			
			if(!grid.has(new Position(tempRow+1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol-1),tentSymbol)) { //If there is no tree or tent diagonal to the position
				
				condition3 = true;
			}
			
			if(condition1&&condition2&&condition3) { //If all three conditions are true then the tent will be added
				
				grid.add(pos, tentSymbol);
				return true;
			}else {
				return false;
			}
		}
		
		if(tempRow == 0) { //if you want to add the tent to the first row (first row check)
			
			if(grid.has(new Position(tempRow,tempCol-1),treeSymbol) || grid.has(new Position(tempRow,tempCol+1),treeSymbol) || grid.has(new Position(tempRow+1,tempCol),treeSymbol)) { //If there is a tree next to or below the position
				condition1 = true;
			}
			
			if(!grid.has(new Position(tempRow,tempCol-1),tentSymbol) || !grid.has(new Position(tempRow,tempCol+1),tentSymbol) || !grid.has(new Position(tempRow+1,tempCol),tentSymbol)) { //If there is NO tent next to or below the position
				condition2 = true;
			}
			
			if(!grid.has(new Position(tempRow+1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol+1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol-1),treeSymbol) || !grid.getValue(new Position(tempRow+1,tempCol+1)).equals(treeSymbol)){ //If there is NO tree or tent at the diagonal
				condition3 = true;
			}
			
			if(condition1&&condition2&&condition3) { //If all three conditions are true then the tent will be added
				
				grid.add(pos, tentSymbol);
				return true;
			}else {
				return false;
			}
		}
		
		if(tempRow == numRows-1) { //if you want to add the tent in the last row (last row check)
			
			if(grid.has(new Position(tempRow,tempCol-1),treeSymbol) || grid.has(new Position(tempRow,tempCol+1),treeSymbol) || grid.has(new Position(tempRow-1,tempCol),treeSymbol)) { //If there is a tree next to or below the position
				condition1 = true;
			}
			
			if(!grid.has(new Position(tempRow,tempCol-1),tentSymbol) || !grid.has(new Position(tempRow,tempCol+1),tentSymbol) || !grid.has(new Position(tempRow-1,tempCol),tentSymbol)) { //If there is NO tent next to or below the position
				condition2 = true;
			}
			
			if(!grid.has(new Position(tempRow-1,tempCol-1),tentSymbol) || !grid.has(new Position(tempRow-1,tempCol+1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol+1),treeSymbol)){ //If there is NO tree or tent at the diagonal
				condition3 = true;
			}
			
			if(condition1&&condition2&&condition3) { //If all three conditions are true then the tent will be added
				
				grid.add(pos, tentSymbol);
				return true;
			}else {
				return false;
			}
		}
		
		if(tempCol == 0) { //if you want to add the tent in the first column (first column check)
			
			if(grid.has(new Position(tempRow-1,tempCol),treeSymbol) || grid.has(new Position(tempRow+1,tempCol),treeSymbol) || grid.has(new Position(tempRow,tempCol+1),treeSymbol)) { //If there is a tree next to or below the position
				condition1 = true;
			}
			
			if(!grid.has(new Position(tempRow-1,tempCol),tentSymbol) || !grid.has(new Position(tempRow+1,tempCol),tentSymbol) || !grid.has(new Position(tempRow,tempCol+1),tentSymbol)) { //If there is NO tent next to or below the position
				condition2 = true;
			}
			
			if(!grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol+1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol+1),treeSymbol)){ //If there is NO tree or tent at the diagonal
				condition3 = true;
			}
			
			if(condition1&&condition2&&condition3) { //If all three conditions are true then the tent will be added
				
				grid.add(pos, tentSymbol);
				return true;
			}else {
				return false;
			}
		}
		
		if(tempCol == numCols-1) { //if you want to add the tent in the last column (last column check)
			
			if(grid.has(new Position(tempRow-1,tempCol),treeSymbol) || grid.has(new Position(tempRow+1,tempCol),treeSymbol) || grid.has(new Position(tempRow,tempCol-1),treeSymbol)) { //If there is a tree next to or below the position
				condition1 = true;
			}
			
			if(!grid.has(new Position(tempRow-1,tempCol),tentSymbol) || !grid.has(new Position(tempRow+1,tempCol),tentSymbol) || !grid.has(new Position(tempRow,tempCol-1),tentSymbol)) { //If there is NO tent next to or below the position
				condition2 = true;
			}
			
			if(!grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),treeSymbol)){ //If there is NO tree or tent at the diagonal
				condition3 = true;
			}
			
			if(condition1&&condition2&&condition3) { //If all three conditions are true then the tent will be added
				
				grid.add(pos, tentSymbol);
				return true;
			}else {
				return false;
			}
		}else {
			
			//CHECK FOR ALL THE DIRECTIONS
			
			if(posHasNbr(pos,treeSymbol)) { //If there is a tree either up, down, left or right to the position
				
				condition1 = true;
			}
			
			if(!posHasNbr(pos,tentSymbol)) { //If there is no tent either up, down, left or right to the position
				
				condition2 = true;
			}
			
			if(!posTouching(pos,treeSymbol)) { //If there is no tree in all directions to the position
				
				condition3 = true;
			}
			
			if(!posTouching(pos,tentSymbol)) { //If there is no tent in all directions to the position
				
				condition4 = true;
			}
			
			if(condition1&&condition2&&condition3&&condition4) { //If all four conditions are true then the tent will be added
				
				grid.add(pos, tentSymbol);
				return true;
			}else {
				return false;
			}
		}
		
		//return false;
	}
	 /**
	  * This method removes a tent at this position
	  * @param pos This is where we are removing the tent
	  * @return returns true or false if the deletion is successful or not
	  */
	public boolean removeTent(Position pos){
		// remove the tent from position pos
		// return false if the attempt of removal cannot be performed
		// return true otherwise
		// assuming HashMap overhead constant, O(1)
		
		boolean decision = false;
		
		if(isValidPosition(pos)) { //If the position is valid
			
			if(grid.has(pos, treeSymbol) || grid.has(pos, tentSymbol)) { //if the element exists in the grid
				
				decision = grid.remove(pos); //The element will be removed
				
				if(decision) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
		return false;

	}
	 /**
	  * Removes a tree at the specified position
	  * @param pos This is where we are removing the tree
	  * @return returns true or false if the adding is successful or not
	  */
	public boolean addTree(Position pos){
		// add another tree at the specified position pos
		// return false if a new tree cannot be added at pos
		//     (i.e. attempt fails if pos is already occupied)
		// return true otherwise
		// assuming HashMap overhead constant, O(1)
		
		if(isValidPosition(pos)) { //If the position is valid
			
			if(!grid.contains(pos)) { //If the table does not contain the position
				if(grid.getValue(pos) == null) { //If the value gotten from that position is null
					grid.add(pos, treeSymbol); //adds the tree at the position
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * This checks if there is a tent at the specified position
	 * @param pos This is position where we are checking if it has a tent or not
	 * @return returns true or false if the search is successful or not
	 */
	public boolean hasTent(Position pos){
		// check whether there is a tent at position pos
		// return true if yes and false otherwise
		// return false for invalid positions
		// assuming HashMap overhead constant, O(1)
		
		if(isValidPosition(pos)) { //If the position is valid
			
			if(grid.get(pos).equals(tentSymbol)) { //If the symbol gotten from the position is a tent
				return true;
			}else {
				return false;
			}
		}
		return false;

	}
	
	/**
	 * This checks if the the specified symbol is present in four directions around the specified position
	 * @param pos This is the position that we are checking
	 * @param s This is the symbol that we are checking
	 * @return returns true or false if the search is successful or not
	 */
	public boolean posHasNbr(Position pos, String s){
		// check whether at least one of the 4-way neighbors 
		// of the specified position pos has a symbol as the incoming string s
		//
		// The four direct neighbors of a pos is shown as below: up/down/left/right
		//       ---   U   ---
		//        L   pos   R
		//       ---   D   ---
		// 
		// if at least one of the four cells has string s as the symbol, return true;
		// return false otherwise
		// assuming HashMap overhead constant, O(1)
		
		if(isValidPosition(pos) && isValidSymbol(s)) {
			int tempRow = pos.getRow();
			int tempCol = pos.getCol();
			
			boolean condition1 = false;
			boolean condition2 = false;
			boolean condition3 = false;
			
			if(tempRow == 0) { //if you want to add the tent to the first row (first row check)
				
				if(grid.has(new Position(tempRow,tempCol-1),treeSymbol) || grid.has(new Position(tempRow,tempCol+1),treeSymbol) || grid.has(new Position(tempRow+1,tempCol),treeSymbol)) { //If there is a tree next to or below the position
					condition1 = true;
				}
				
				if(!grid.has(new Position(tempRow,tempCol-1),tentSymbol) || !grid.has(new Position(tempRow,tempCol+1),tentSymbol) || !grid.has(new Position(tempRow+1,tempCol),tentSymbol)) { //If there is NO tent next to or below the position
					condition2 = true;
				}
				
				if(!grid.has(new Position(tempRow+1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol+1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol-1),treeSymbol) || !grid.getValue(new Position(tempRow+1,tempCol+1)).equals(treeSymbol)){ //If there is NO tree or tent at the diagonal
					condition3 = true;
				}
				
				if(condition1&&condition2&&condition3) {
					
					grid.add(pos, tentSymbol);
					return true;
				}else {
					return false;
				}
			}
			
			if(tempRow == numRows-1) { //if you want to add the tent in the last row (last row check)
				
				if(grid.has(new Position(tempRow,tempCol-1),treeSymbol) || grid.has(new Position(tempRow,tempCol+1),treeSymbol) || grid.has(new Position(tempRow-1,tempCol),treeSymbol)) { //If there is a tree next to or below the position
					condition1 = true;
				}
				
				if(!grid.has(new Position(tempRow,tempCol-1),tentSymbol) || !grid.has(new Position(tempRow,tempCol+1),tentSymbol) || !grid.has(new Position(tempRow-1,tempCol),tentSymbol)) { //If there is NO tent next to or below the position
					condition2 = true;
				}
				
				if(!grid.has(new Position(tempRow-1,tempCol-1),tentSymbol) || !grid.has(new Position(tempRow-1,tempCol+1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol+1),treeSymbol)){ //If there is NO tree or tent at the diagonal
					condition3 = true;
				}
				
				if(condition1&&condition2&&condition3) {
					
					grid.add(pos, tentSymbol);
					return true;
				}else {
					return false;
				}
			}
			
			if(tempCol == 0) { //if you want to add the tent in the first column (first column check)
				
				if(grid.has(new Position(tempRow-1,tempCol),treeSymbol) || grid.has(new Position(tempRow+1,tempCol),treeSymbol) || grid.has(new Position(tempRow,tempCol+1),treeSymbol)) { //If there is a tree next to or below the position
					condition1 = true;
				}
				
				if(!grid.has(new Position(tempRow-1,tempCol),tentSymbol) || !grid.has(new Position(tempRow+1,tempCol),tentSymbol) || !grid.has(new Position(tempRow,tempCol+1),tentSymbol)) { //If there is NO tent next to or below the position
					condition2 = true;
				}
				
				if(!grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol+1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol+1),treeSymbol)){ //If there is NO tree or tent at the diagonal
					condition3 = true;
				}
				
				if(condition1&&condition2&&condition3) {
					
					grid.add(pos, tentSymbol);
					return true;
				}else {
					return false;
				}
			}
			
			if(tempCol == numCols-1) { //if you want to add the tent in the last column (last column check)
				
				if(grid.has(new Position(tempRow-1,tempCol),treeSymbol) || grid.has(new Position(tempRow+1,tempCol),treeSymbol) || grid.has(new Position(tempRow,tempCol-1),treeSymbol)) { //If there is a tree next to or below the position
					condition1 = true;
				}
				
				if(!grid.has(new Position(tempRow-1,tempCol),tentSymbol) || !grid.has(new Position(tempRow+1,tempCol),tentSymbol) || !grid.has(new Position(tempRow,tempCol-1),tentSymbol)) { //If there is NO tent next to or below the position
					condition2 = true;
				}
				
				if(!grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),treeSymbol)){ //If there is NO tree or tent at the diagonal
					condition3 = true;
				}
				
				if(condition1&&condition2&&condition3) {
					
					grid.add(pos, tentSymbol);
					return true;
				}else {
					return false;
				}
			}else{
				
				if(grid.has(new Position(tempRow-1,tempCol),s) || grid.has(new Position(tempRow+1,tempCol),s) || grid.has(new Position(tempRow,tempCol-1),s) || grid.has(new Position(tempRow,tempCol+1),s)) { //If there is a tree either next to or below or above the position
					
					return true;
				}else {
					return false;
				}
			}
			
		}
		
		return false;
	}
	 /**
	  	This checks if the the specified symbol is present in eight directions around the specified position
	  * @param pos This is the position that we are checking
	  * @param s This is the symbol that we are checking
	  * @return returns true or false if the search is successful or not
	  */
	public boolean posTouching(Position pos, String s){
		// check whether at least one of the 8 (horizontal/vertical/diagonal) neighbors 
		// of the specified position pos has a symbol as the incoming string s
		//
		// The eight horizontal/vertical/diagonal neighbors of a pos is shown as below: 
		// up left / up / up right / left / right / down left/ down/ down right
		//
		//        UL   U   UR
		//        L   pos   R
		//        DL   D   DR
		// 
		// if at least one of the eight cells has string s as the symbol, return true;
		// return false otherwise
		// assuming HashMap overhead constant, O(1)
		
		
		if(isValidPosition(pos) && isValidSymbol(s)) {
			
			if(!grid.has(pos, s)) {
				int tempRow = pos.getRow();
				int tempCol = pos.getCol();
				
				boolean condition1 = false;
				boolean condition2 = false;
				boolean condition3 = false;
				
				if(tempRow == 0) { //if you want to add the tent to the first row (first row check)
					
					if(grid.has(new Position(tempRow,tempCol-1),treeSymbol) || grid.has(new Position(tempRow,tempCol+1),treeSymbol) || grid.has(new Position(tempRow+1,tempCol),treeSymbol)) { //If there is a tree next to or below the position
						condition1 = true;
					}
					
					if(!grid.has(new Position(tempRow,tempCol-1),tentSymbol) || !grid.has(new Position(tempRow,tempCol+1),tentSymbol) || !grid.has(new Position(tempRow+1,tempCol),tentSymbol)) { //If there is NO tent next to or below the position
						condition2 = true;
					}
					
					if(!grid.has(new Position(tempRow+1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol+1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol-1),treeSymbol) || !grid.getValue(new Position(tempRow+1,tempCol+1)).equals(treeSymbol)){ //If there is NO tree or tent at the diagonal
						condition3 = true;
					}
					
					if(condition1&&condition2&&condition3) {
						
						grid.add(pos, tentSymbol);
						return true;
					}else {
						return false;
					}
				}
				
				if(tempRow == numRows-1) { //if you want to add the tent in the last row (last row check)
					
					if(grid.has(new Position(tempRow,tempCol-1),treeSymbol) || grid.has(new Position(tempRow,tempCol+1),treeSymbol) || grid.has(new Position(tempRow-1,tempCol),treeSymbol)) { //If there is a tree next to or below the position
						condition1 = true;
					}
					
					if(!grid.has(new Position(tempRow,tempCol-1),tentSymbol) || !grid.has(new Position(tempRow,tempCol+1),tentSymbol) || !grid.has(new Position(tempRow-1,tempCol),tentSymbol)) { //If there is NO tent next to or below the position
						condition2 = true;
					}
					
					if(!grid.has(new Position(tempRow-1,tempCol-1),tentSymbol) || !grid.has(new Position(tempRow-1,tempCol+1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol+1),treeSymbol)){ //If there is NO tree or tent at the diagonal
						condition3 = true;
					}
					
					if(condition1&&condition2&&condition3) {
						
						grid.add(pos, tentSymbol);
						return true;
					}else {
						return false;
					}
				}
				
				if(tempCol == 0) { //if you want to add the tent in the first column (first column check)
					
					if(grid.has(new Position(tempRow-1,tempCol),treeSymbol) || grid.has(new Position(tempRow+1,tempCol),treeSymbol) || grid.has(new Position(tempRow,tempCol+1),treeSymbol)) { //If there is a tree next to or below the position
						condition1 = true;
					}
					
					if(!grid.has(new Position(tempRow-1,tempCol),tentSymbol) || !grid.has(new Position(tempRow+1,tempCol),tentSymbol) || !grid.has(new Position(tempRow,tempCol+1),tentSymbol)) { //If there is NO tent next to or below the position
						condition2 = true;
					}
					
					if(!grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol+1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol+1),treeSymbol)){ //If there is NO tree or tent at the diagonal
						condition3 = true;
					}
					
					if(condition1&&condition2&&condition3) {
						
						grid.add(pos, tentSymbol);
						return true;
					}else {
						return false;
					}
				}
				
				if(tempCol == numCols-1) { //if you want to add the tent in the last column (last column check)
					
					if(grid.has(new Position(tempRow-1,tempCol),treeSymbol) || grid.has(new Position(tempRow+1,tempCol),treeSymbol) || grid.has(new Position(tempRow,tempCol-1),treeSymbol)) { //If there is a tree next to or below the position
						condition1 = true;
					}
					
					if(!grid.has(new Position(tempRow-1,tempCol),tentSymbol) || !grid.has(new Position(tempRow+1,tempCol),tentSymbol) || !grid.has(new Position(tempRow,tempCol-1),tentSymbol)) { //If there is NO tent next to or below the position
						condition2 = true;
					}
					
					if(!grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow+1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),treeSymbol) || !grid.has(new Position(tempRow-1,tempCol-1),treeSymbol)){ //If there is NO tree or tent at the diagonal
						condition3 = true;
					}
					
					if(condition1&&condition2&&condition3) {
						
						grid.add(pos, tentSymbol);
						return true;
					}else {
						return false;
					}
				}else {
				
					if(grid.has(new Position(tempRow-1,tempCol-1),s) || grid.has(new Position(tempRow+1,tempCol-1),s) || grid.has(new Position(tempRow-1,tempCol+1),s) || grid.has(new Position(tempRow+1,tempCol+1),s)) { //If there is NO tree diagonally to position
						
						return true;
					}else {
						return false;
					}
				}
			}
			
		}
		
		return false;	
	}
	
	/***
	 * methods that return a string of the board representation
	 * this has been implemented for you: DO NOT CHANGE
	 * @param no parameters
	 * @return a string
	 */
	@Override
	public String toString(){
		// return a string of the board representation following these rules:
		// - if printed, it shows the board in R rows and C cols
		// - every cell should be represented as a 5-character long right-aligned string
		// - there should be one space between columns
		// - use "-" for empty cells
		// - every row ends with a new line "\n"
		
		
		StringBuilder sb = new StringBuilder("");
		for (int i=0; i<numRows; i++){
			for (int j =0; j<numCols; j++){
				Position pos = new Position(i,j);
				
				// use the hash table to get the symbol at Position(i,j)
				if (grid.contains(pos))
					sb.append(String.format("%5s ",this.get(pos)));
				else
					sb.append(String.format("%5s ","-")); //empty cell
			}
			sb.append("\n");
		}
		return sb.toString();

	} 
	
		
		
	/***
	 * EXTRA CREDIT:
	 * methods that checks the status of the grid and returns:
	 * 0: if the board is empty or with invalid symbols
	 * 1: if the board is a valid and finished puzzle
	 * 2: if the board is valid but not finished
	 *     - should only return 2 if the grid missing some tent but otherwise valid
	 *       i.e. no tent touching other tents; no 'orphan' tents not attached to any tree, etc. 
	 * 3: if the board is invalid
	 *     - note: only one issue need to be reported when the grid is invalid with multiple issues
	 * @param no parameters
	 * @return an integer to indicate the status
	 * 
	 * assuming HashMap overhead constant, O(R*C) 
	 * where R is the number of rows and C is the number of columns
	 * Note: feel free to add additional output to help the user locate the issue
	 */
	public int checkStatus(){
		
		int status = 0;
		
		if(numRows != numCols || !isValidSymbol(treeSymbol) || !isValidSymbol(tentSymbol)) {
			status = 0;
			return status;
		}
		
		if(numRows != numCols) {
			status = 3;
			return status;
		}
		
		return 2;
		
	}
	
	
	
	//----------------------------------------------------
	// example testing code... make sure you pass all ...
	// and edit this as much as you want!

	// Note: you will need working Position and SimpleMap classes to make this class working
	
	public static void main(String[] args){
	
		TentTree g1 = new TentTree(4,5,"Tent","Tree");
		if (g1.numRows()==4 && g1.numCols()==5 && g1.getTentSymbol().equals("Tent")
			&& g1.getTreeSymbol().equals("Tree")){
			System.out.println("Yay 1");
		}

		TentTree g2 = new TentTree(3,3);
		
		//g2.toString();

		if (g2.set(new Position(1,0), "O") && !g2.set(new Position(1,0),"O") &&
				g2.addTree(new Position(1,2)) && !g2.addTree(new Position(1,5))){
				System.out.println("Yay 2");
			}
		
		if (g2.addTent((new Position(0,0))) && !g2.addTent(new Position(0,1)) &&
				!g2.addTent((new Position(1,0))) && g2.get((new Position(0,0))).equals("X")){
				System.out.println("Yay 3");

			}
	
		if (g2.hasTent(new Position(0,0)) && g2.posHasNbr((new Position(0,0)),"O") &&
				g2.posTouching((new Position(1,2)),"X") && !g2.posHasNbr((new Position(1,2)),"X")){
				System.out.println("Yay 4");

			}
		
		if (!g2.removeTent(new Position(0,1)) && !g2.removeTent(new Position(2,1))
			&& g2.get(new Position(2,2))==null){
			System.out.println("Yay 5");
		}
	
	}


}
