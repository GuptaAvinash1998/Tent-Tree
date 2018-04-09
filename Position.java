/**************************************************************************
 * @author Avinash Gupta
 * CS310 Spring 2018
 * Project 2
 * George Mason University
 * 
 * File Name: Position.java
 *
 * Description: This is a class that defines what is in each position in the grid. A position will have a row and a column
 * 
 ***************************************************************************/

class Position{

	// row and column
	private int row;
	private int col;
	
	/**
	 * This is the constructor of the class, we define row and column here
	 * @param row is the row
	 * @param col is the col
	 */
	public Position(int row, int col){
		// constructor to initialize your attributes
		this.row = row;
		this.col = col;
	}
	
	// accessors of row and column
	/**
	 * Returns the row
	 * @return row
	 */
	public int getRow(){ return row;}
	
	/**
	 * Returns the column
	 * @return col
	 */
	public int getCol(){ return col;}
	
	/**
	 * Returns the string representation of the position
	 */
	public String toString(){
		// return string representation of a position
		// row R and col C must be represented as <R,C> with no spaces
		return "<" + row + "," + col + ">";
	}
	
	/**
	 * Checks if the 2 objects are equal or not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	/**
	 * Creates a unique hashCode for every position
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	

	//----------------------------------------------------
	// example testing code... make sure you pass all ...
	// and edit this as much as you want!


	public static void main(String[] args){
		Position p1 = new Position(3,5);
		Position p2 = new Position(3,6);
		Position p3 = new Position(2,6);
		
		if (p1.getRow()==3 && p1.getCol()==5 && p1.toString().equals("<3,5>")){
			System.out.println("Yay 1");
		}

		if (!p1.equals(p2) && !p2.equals(p3) && p1.equals(new Position(3,5))){
			System.out.println("Yay 2");
		}
		
		if (p1.hashCode()!=p3.hashCode() && p1.hashCode()!=(new Position(5,3)).hashCode() &&
			p1.hashCode() == (new Position(3,5)).hashCode()){
			System.out.println("Yay 3");			
		}
		
		
	}
	
}