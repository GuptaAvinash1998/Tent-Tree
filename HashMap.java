//This class has been implemented for you!
//Do not change it!

import java.util.*;

/**************************************************************************
 * @author Yutao Zhong and Jitin Krishnan
 * CS310 Spring 2018
 * Project 2
 * George Mason University
 * 
 * File Name: Board.java
 *
 * Description: This class uses the hash table methods in a map
 ***************************************************************************/


class HashMap<K,V> {
	// This class defines a dictionary that maps key->value. 
	// A hash table is used that decides the entry based on key
	// and stores <key, value> pair in the table entry.
	
	// keys in a dictionary must be unique.
	
	private HashTable<Pair<K,V>> hashTable = new HashTable<>();
	
	/**
	 * This class represents the key value pair
	 * @author Yutao Zhong and Jitin Krishnan
	 *
	 * @param <K> This is the key
	 * @param <V> This is the value
	 */
	// The class representing <key,value> pair
	private static class Pair<K,V> {
		K key;
		V value;
		
		/**
		 * Initializes the variables
		 * @param key This is the key
		 * @param value This is the value
		 */
		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Checks if the 2 objects are equal
		 */
		@SuppressWarnings("unchecked")
		public boolean equals(Object o) {
			// return true if two pairs have matching keys
			// i.e. <"Alice", 1> is considered as equal to <"Alice", 2>

			if(o instanceof Pair) {
				Pair<K,V> pair = (Pair<K,V>)o;
				return pair.key.equals(key);  
			}
			return false;
		}
		
		/**
		 * Generates the hash code for the key
		 */
		public int hashCode() {
			// In order to make sure that keys in a dictionary must be unique,
			// hash code is only determined by key.hashCode().
			
			return key.hashCode();
		}
		
		/**
		 * Returns the string representation of the key and value pair
		 */
		public String toString() {
			return "<" + key + "," + value + ">";
		}
		
		/**
		 * 
		 * @return Returns the key
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * 
		 * @return Returns the value
		 */
		public V getValue() {
			return value;
		}
	}
	
	/**
	 * Adds a value to the Map
	 * @param key The key to be added
	 * @param value The value to be added
	 * @return Returns true or false depending on weather the addition was successful or not
	 */
	public boolean add(K key, V value) {
		// add a new <key, value> pair into the dictionary
		// return false if cannot be added (i.e. key already there)
		Pair<K,V> pair = new Pair<>(key, value);
		return hashTable.add(pair);
	}
	
	/**
	 * Updates the value of the key value pair at the specified location
	 * @param key
	 * @param value
	 * @return Returns true or false depending on weather the addition was successful or not
	 */
	public boolean update(K key, V value) {
		// update the mapping of key in the dictionary to be a new value
		// if key not present in dictionary, return false
		// if fail to update, return false
		Pair<K,V> pair = new Pair<>(key, value);
		if(!remove(key)) {
			return false;
		}
		return hashTable.add(pair);
	}
	
	/**
	 * Removes a key value pair from the Map
	 * @param key The key we want to remove
	 * @return Returns true or false depending on weather the deletion was successful or not
	 */
	@SuppressWarnings("unchecked")
	public boolean remove(K key) {
		// remove <key, value> pair from dictionary and return true
		// if key not present, return false and no change to dictionary
		Pair<K,V> pair = new Pair<>(key, null);
		return hashTable.remove(pair);
	}
	
	/**
	 * Gets the value that the key is mapped to
	 * @param key The key we want to get the value
	 * @return Returns the value
	 */
	@SuppressWarnings("unchecked")
	public V get(K key) {
		// return the value that the key maps to in dictionary
		// if key not present, return null
		if (!contains(key))
			return null;
		Pair<K,V> pair = new Pair<>(key, null);
		return hashTable.get(pair).getValue();
	}
	
	/**
	 * 
	 * @return Returns the size of the Hash Map
	 */
	public int size() {
		// return how many <key, value> pairs are there
		return hashTable.size();
	}
	
	/**
	 * Checks if the key value pair exists in the Map or not
	 * @param key This is the key we are checking exists or not
	 * @return Returns true or false depending on weather the pair exists or not
	 */
	public boolean contains(K key){
		// return true if key is present
		// return false otherwise
		return hashTable.contains(new Pair<>(key,null));
	}
	
	/**
	 * Checks if the key value pair already exists in the Map or not
	 * @param key The key want to check
	 * @param value The value we wat to check
	 * @return Returns true or false depending on weather the pair exists or not
	 */
	public boolean has(K key, V value){
		// return true if <key, value> pair is present in dictionary
		// return false otherwise	
		Pair<K, V> pair = new Pair<>(key, null);
		
		return this.contains(key) && (hashTable.get(pair).getValue().equals(value));
		
	}
	 /**
	  * Gets the value that the key is mapped to
	  * @param key The key we want to get the value of
	  * @return Returns the value mapped
	  */
	public V getValue(K key){
		// return the value this key mapped to from the dictionary
		// if key not present, return null
		if (this.contains(key)){
			Pair<K, V> pair = new Pair<>(key, null);
			return hashTable.get(pair).getValue();
		}
		else
			return null;
	}
	 /**
	  * Puts all the pairs in an array
	  * @return Returns an array with all the pairs
	  */
	@SuppressWarnings("unchecked")
	public Object[] pairsToArray() {
		// return an object array as the contents of the dictionary
		// use the valueToArray() of hash table
		// every item in the array should be a <key,value> pair

		Object[] hashTableValues = hashTable.valuesToArray();
		Object[] arr = new Object[hashTableValues.length];
		

		for(int i = 0; i < arr.length; i++) {
			arr[i] = ((Pair<K,V>)hashTableValues[i]);
		}
		
		return arr;
	}
	
	//-----------------------------------------------
	// example testing code... edit this as much as you want!
	// This is actually more testing of HashTable
	public static void main(String[] args) {
		
		HashMap<String,Integer> nameDict = new HashMap<>();
		if ( nameDict.add("Alice",1) && nameDict.contains("Alice") 
			&& !nameDict.contains("Bob") && nameDict.size()==1){
			System.out.println("Yay 1!");
		}
		
		if ( nameDict.has("Alice", 1) && !nameDict.has("Alice", 2) &&
				 nameDict.update("Alice", 10) && !nameDict.update("Bob",2)){
				System.out.println("Yay 2!");
			}
		
		if ( nameDict.getValue("Alice")==10) {
			System.out.println("Alice's value is 10");
		}
		
		if(nameDict.add("Chris", 20)) {
			System.out.println("Chris has been added");
		}
		
		if(nameDict.remove("Chris")) {
			System.out.println("Chris has been removed");
		}
		
		if(!nameDict.has("Chris",20)){
			System.out.println("Yay 3!");
		}
		
		nameDict.add("David",32);
		nameDict.add("Eddie",1);
		nameDict.add("Frank",2);
		System.out.println(Arrays.toString(nameDict.pairsToArray()));
		

	}

}
