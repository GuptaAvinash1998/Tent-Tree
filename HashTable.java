import java.util.Iterator;

/**************************************************************************
 * @author Avinash Gupta
 * CS310 Spring 2018
 * Project 2
 * George Mason University
 * 
 * File Name: Board.java
 *
 * Description: This is the class where we define all the operations done on the hashTable. The hashing method done for the table is separate chaining.
 * In separate chaining, if there is a hashing collision, we make that location a linked list.
 ***************************************************************************/

class HashTable<T> {
	
	@SuppressWarnings("unchecked")
	private SimpleList<T>[] table = new SimpleList[11]; //This makes a table that has an initial size of 11
	private int tableSize = table.length; //This will keep track of the size of the table
	private int itemCount = 0; //Keeps track of how many items were added to the table
	private int chainLen = 0; //Keeps track of the number of the length of the chain in each index of the table
	private int activeChains = 0; //keeps track of the number of chains formed
	private double avgChainLen = 0; //keeps track of the average chain length
	private double load = 0; //keeps track of the load
	
	/**
	 * This is a helper method that generates the hash code for the value and then mods it with the length of the table in order to generate the location
	 * where the element should be added
	 * @param value This is the object that we are generating the location for
	 * @return Returns the position in the table
	 */
	private int generateHash(T value) {
		
		int hashValue = value.hashCode(); //generates the hash code
		int pos = hashValue % tableSize; //generates the position
		 
		return Math.abs(pos); //returns the position
	}
	
	/**
	 * This method is used to add values to the hash table
	 * @param value This is the object that we are adding
	 * @return Returns true or false depending on weather the element has been added or not
	 */
	public boolean add(T value) {
		// adds an item to the hash table
		// returns true if you successfully add value
		// returns false if the value can not be added
		// (i.e. the value already exists in the set)
		
		// note: if the average chain length is > 1.2
		// must rehash to the next prime number larger
		// than twice the size before returning

		// O(M) worst case, where M =  size returned by size()
		// O(1) or O(M/N) average case (where M/N is the load)
		// the average case can be amortized Big-O
		boolean decision = false;
		
		int HV = generateHash(value); //HV stands for Hash Value //Generates the hash value and gets the position
		
		if(!contains(value)) { //First we check if the value already exists in the table
			
			//If it does not, then check if the value in the index is null
			
			if(table[HV] == null) {//If the location in the table is empty
				
				table[HV] = new SimpleList<T>(); //Makes a new object
				table[HV].add(value); //Adds the value
				activeChains++; //Because a new slot is filled, we have a new chain
			}else { //If there is an element already then we have a hashing collision and we just add onto the chain
				
				table[HV].add(value);//adds the value
			}
			
			itemCount++; //since an item got added, the number will increment
			chainLen = table[HV].size();
			
			avgChainLen = getAvgChainLength(); //gets the average chain length
			if(avgChainLen > 1.2) { //if its greater than 1.2, then we rehash the table
				
				decision = rehash(nextPrime(2*tableSize)); //If successful then it will make a table with a size that is a prime number and this prime number is greater than twice the size of the old table size
			}
			return true;
		}else { //If the value already exists in the table already
			
			return false;

		}		
	}
	
	/**
	 * This is used to remove an element from the table
	 * @param value This is object that we are removing from the table
	 * @return Returns true or false depending on weather the element has been removed or not
	 */
	public boolean remove(T value) {
		// removes a value from the hash table
		// returns true if you remove the item
		// returns false if the item could not be found
		
		// O(M) worst case, where M =  size returned by size()
		// O(1) or O(M/N) average case (where M/N is the load)
		
		if(contains(value)) { //Checks if the value is found in the table, If it is.....
				
				int location = generateHash(value); //finds the location of the object in the table
				
				boolean decision = table[location].remove(value); //removes the value from the table
				
				itemCount--; //Since we removed an element, the number of items will reduce
				
				if(table[location].size() == 0) { //If there are no elements in the location, there is nothing to remove
					table[location] = null;
				}
				if(decision) {
					return true;
				}
			
		}else { //If it is not then it will return false
			return false;
		}
		
		return false;
	}
	
	/**
	 * This checks if the value exists in the table or not
	 * @param value This is the object that we are checking exists or not
	 * @return Returns true or false depending on weather the element exists or not
	 */
	public boolean contains(T value) {
		
		int location = generateHash(value); //Finding position of value in the table
		
		if(location < tableSize) {
			
			SimpleList<T> temp = table[location]; //Is a pointer to the location
			
			if(temp == null) { //If there are no elements in this location then it will return false
				return false;
			}
			return temp.contains(value); //else it will check if the element is in this position or not
		}else {
			return false;
		}
		// O(M) worst case, where M = size returned by size()
		// O(1) or O(M/N) average case (where M/N is the load)
	}
	
	/**
	 * This returns the item from the table
	 * @param value
	 * @return
	 */
	public T get(T value) {
		// return null if the item could not be found in hash table;
		// return the item FROM THE HASH TABLE if it was found.
		// NOTE: do NOT return the parameter value!!
		//       While "equal" they may not be the same.
		//       For example, When value is a PAIR<K,V>, 
		//       its "equals" methods returns true if just the keys are equal.

		// O(M) worst case, where M = size returned by size()
		// O(1) or O(M/N) average case (where M/N is the load)
		
		if(contains(value)) { //If the element is in the table
			
			for(int i=0;i<tableSize;i++) { //loops through the table
				
				if(table[i] != null) { //If the there is a list in this location
					
					if(table[i].contains(value)) { //If the element is in the linked list at this index in the table
						
						return table[i].get(value); //Then it will get the value from the linked list
					}else {
						continue;
					}
				}else {
					continue;
				}
			}
		}else {
			return null;
		}
		return null;			
	}

	/**
	 * This will return a new table with a size as a prime number that is more than twice the size of the old table length and all the elements would have been rehashed into their new positions
	 * @param newCapacity This is the new size
	 * @return Returns true or false depending on weather the new table has been made and all the elements have been rehashed to their new positions or not
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int newCapacity) {
		// rehash to a larger table size (specified as the
		// parameter to this method)
		// O(M) where M = size returned by size()
		
		// - return true if table gets resized
		// - if the newCapacity will make the load to be more than 0.7, do not resize
		//   and return false 
		
		SimpleList<T>[] newTable = new SimpleList[newCapacity]; //Makes a table that has the size of the new capacity
		activeChains = 0; //Since the table will be rehashed, the active chains will be set to 0
		
		int oldTableLen = tableSize;//stores the old table size
		
		tableSize = newTable.length; //updates the table size to the new table size
		
		for(int i=0;i<oldTableLen;i++) { //loops through each element in the old table
			
			if(table[i] == null) { //If the element in the table is null
				continue;
			}else {
				
				Iterator<T> temp = table[i].iterator(); //makes the iterator for that local list
				
				if(table[i].size() > 1) { //if there is more than one element in that location
					
					while(temp.hasNext()) { //If there is a next element
						
						T entry = temp.next(); //stores the element in that position in the linked list
						int location = generateHash(entry); //makes the new location for that element
						
						if(newTable[location] == null) { //if the value in that location is null
							
							newTable[location] = new SimpleList<T>();
							newTable[location].add(entry); //the new location will act as a pointer to the new element
							activeChains++; //since a new element will be added, the number of active chains will increase
							
						}else {
							
							newTable[location].add(entry); //if an element already exists, then it will increase the chain length at that location
						}
					}
					
					
				}else {
				//If the size is not greater than 1 then it finds the new location for that one element
					T pass = temp.next(); //stores the element in that position in the linked list
					int location = generateHash(pass); //makes a new location
					
					if(location > newTable.length) {
						return false;
					}
					
					if(newTable[location] == null) { //If there is nothing in that location
						newTable[location] = new SimpleList<T>(); //makes a new object
						newTable[location].add(pass);//adds it to the list
						activeChains++;//since a new element will be added, the number of active chains will increase
					}else {
						
						while(temp.hasNext()) {
							newTable[location].add(temp.next());
						}
						
						
					}
				}
			}
		}
		
		if(getLoad() > 0.7) { //after doing this if the load factor is greater than 0.7, then the old table will remain the same and the method will return false
			
			tableSize = table.length; //The table size will remain the same and the garbage collector will collect the new table
			return false; //so the table will not get updated
		}else {
			table = newTable; //else the table will be updated to the new table
			avgChainLen = getAvgChainLength(); //updates the average chain length
			load = getLoad(); //updates the load
			return true;
		}
	}
	
	/**
	 * Returns the number of elements currently in the table
	 * @return The number of elements in the table
	 */
	public int size() {
		// return the number of items in the table
		// O(1)
		return itemCount;
	}
	
	/**
	 * Returns the load
	 * @return The load
	 */
	public double getLoad() {
		// return the load on the table
		// O(1)
		load = (double) itemCount / tableSize;
		return load;
	}
	
	/**
	 * Returns the average chain length
	 * @return The average chain length
	 */
	public double getAvgChainLength(){
		// return the average length of non-empty chains in the hash table
		// O(1)
		double avgChainLen = (double)itemCount / activeChains; //The average chain length = the item count divided by the number of active chains
		return avgChainLen;
	}
	
	/**
	 * Returns an array containing all the elements in the table
	 * @return The array of elements
	 */
	public Object[] valuesToArray() {
		// take all the values in the table and put them
		// into an array (the array should be the same
		// size returned by the size() method -- no extra space!).
		// Note: it doesn't matter what order the items are
		// returned in, this is a set rather than a list.
		
		// O(M) where M = size returned by size()
		
		int size = size(); //stores the size
		Object[] array = new Object[size]; //Creates an array that is of the same size as the hash table
		int j = 0;
		
		for(int i=0;i<this.table.length;i++) {//loops through the table
			
			if(table[i] == null) { //If the element is null it is not added
				continue;
			}else {
				
				Iterator<T> iter = table[i].iterator();//loops through each element in the linked list in index in the table
				while(iter.hasNext()) {
				
					array[j] = iter.next();//adds the element
					j++;
				}
			}
		}
		return array;
	}
	
	/**
	 * Finds the next prime number
	 * @param x This is number where we find a prime after
	 * @return Returns the prime number after x
	 */
	// inefficiently finds the next prime number >= x
	// this is written for you
	public int nextPrime(int x) {
		while(true) {
			boolean isPrime = true;
			for(int i = 2; i <= Math.sqrt(x); i++) {
				if(x % i == 0) { //If the number is a prime number then the decision is true
					isPrime = false;
					break;
				}
			}
			if(isPrime) return x;
			x++;
		}
	}
	
	//------------------------------------
	// example test code... edit this as much as you want!
	public static void main(String[] args) {
		HashTable<String> names = new HashTable<>();
		
		if(names.add("Alice") && names.add("Bob") && !names.add("Alice")
				&&names.size() == 2 && names.getAvgChainLength() == 1.0) {
			System.out.println("Yay 1");
		}
		
		if(names.remove("Bob") && names.contains("Alice") && !names.contains("Bob")
				&& names.valuesToArray()[0].equals("Alice")) {
				System.out.println("Yay 2");
			}
		
		boolean loadOk = true;
		if(names.getLoad() != 1/11.0 || !names.rehash(10)) {
			loadOk = false;
		}
		
		if(names.getLoad() != 1/10.0 || names.rehash(1)) {
			loadOk = false;
		}
		
		
		boolean avgOk = true;
		HashTable<Integer> nums = new HashTable<>();
		for(int i = 1; i <= 70 && avgOk; i++) {
			nums.add(i);
			double avg = nums.getAvgChainLength();
			if(avg> 1.2 || (i < 12 && avg != 1) || (i >= 14 && i <= 23 && avg != 1) || 
				(i >= 28 && i <= 47 && avg != 1) || (i >= 57 && i <= 70 && avg!= 1)) {
				avgOk = false;
			}
			
		}
		if(loadOk && avgOk) {
			System.out.println("Yay 3");
			
		}
		
	}
}
