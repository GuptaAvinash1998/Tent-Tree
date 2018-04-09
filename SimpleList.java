import java.util.Iterator;

/**************************************************************************
 * @author Avinash Gupta
 * CS310 Spring 2018
 * Project 2
 * George Mason University
 * 
 * File Name: Board.java
 *
 * Description: This is the class where we define the linked list that exists in each index in the hash table
 * The linked list described here is a doubly linked list
 ***************************************************************************/

class SimpleList<T> implements Iterable<T>{
	
	// a linked list class 
	// you decide the internal attributes and node structure
	// but they should all be private
//-----------------------------------------------------------------------------------------------------------	
	/**
	 * This is a class that represents each node in the linked list
	 * @author Avinash Gupta
	 *
	 */
	private class Node{ 
		
		T val; //The data field in the node
		Node next; //The pointer to the next element
		Node prev; //The pointer to the previous element
		
		/**
		 * This is the constructor
		 * @param value
		 */
		public Node(T value) {
			this.val = value;
			this.next = null;
			this.prev = null;
		}
		
		/**
		 * Here we set what the next element should be
		 * @param newNode This is the node that next is being set to
		 */
		private void setNext(Node newNode) {
			next = newNode;
		}
	}
//-----------------------------------------------------------------------------------------------------------	

//-----------------------------------------------------------------------------------------------------------
	/**
	 * This is the class where we define what the iterator does
	 * @author Avinash Gupta
	 *
	 */
	private class SimpleListIterator implements Iterator<T>{

		private Node current;//Pointer to a node
		
		/**
		 * This is the constructor, where we initialize the pointer
		 */
		public SimpleListIterator() { //constructor
			
			current = head; //we initialize the pointer to the head of each linked list
			int loc = 0; //loc is the 
		}
		
		/**
		 * This method checks if there are any more elements to iterate through
		 */
		@Override
		public boolean hasNext() {
			
			
			if(loc < getSize()) { //Documentation says "Returns true if the iteration has more elements. 
				//(In other words, returns true if next() would return an element rather than throwing an exception.)
				
				return true;
			}else {
				return false;
			}
		}
	
		/**
		 * This method returns the element that the iterator is over in the list
		 */
		@Override
		public T next() {
			
			if(current == null) { //If the pointer hit the end of the list
				throw new RuntimeException("No elements left to iterate over");
			}
			
			T temp = current.val; //stores the data element of the current node
			current = current.next; //goes to the next element
			loc++;
			
			return temp;
		}
		
	}
//-----------------------------------------------------------------------------------------------------------
	private Node head = null;//This is the head of the linked list
	private Node last = null;//This is the tail of the linked list
	private Node temp = head;//This is a temporary pointer used for iterating
	
	private int loc = 0; //This is the location in the list
	
	private int list_size = 0; //Store the size of each linked list
	
	/**
	 * This method adds an element to the linked list
	 * @param value This is the value that is being added to the list
	 */
	public void add(T value){
		// add a new node to the end of the linked list to hold value
		// O(1) 
		if(head == null) { //If there are no elements in the list
			head = new Node(value); //The new value becomes the head
			temp = head;
			last = head; //last gets assigned to the head
			list_size++; //node just got added so the size will increase
		}else { //If there is at least one
			Node latest_node = new Node(value); //new Node gets created
			temp.setNext(latest_node); //temp gets connected to the new Node
			latest_node.prev = temp; //setting the backward connection
			temp = temp.next; //temp gets updated to the new node
			last = temp; //Last gets updated to the last element in the list
			list_size++; //node just got added so the size will increase
		}
	}
	
	/**
	 * This method removes an element from the linked list
	 * @param value This is the element that we are removing
	 * @return returns true or false if the deletion is successful or not
	 */
	public boolean remove(T value){
		// given a value, remove the first occurrence of that value
		// return true if value removed
		// return false if value not present
		// O(N) where N is the number of nodes returned by size()
		
		Node cntr = head; //This is a counter that starts at the beginning of the list
		Node new_head;
		Node new_last;
		
		if(contains(value) == true) { //If the element exists in the list....
			for(int i=0;i<list_size;i++) { //Loops through the list
				
				if(head.val.equals(value)) { //If the value to remove is the head;
	                
	                new_head = head.next;//The head will be updated
	                head.next = null;
	                head = new_head;
	                list_size--; //The element was removed so the size of the list will decrease
	                return true;
	            }
	            
	            if(last.val.equals(value)) { //If the value to remove is the last element in the list
	                
	                new_last = last.prev;//Last will be updated
	                new_last.next = null;
	                last.prev = null;
	                last = new_last;
	                list_size--;//The element was removed so the size of the list will decrease
	                return true;
	            }
	            
	            if(cntr.next != null) { 
		            if(cntr.next.val.equals(value)) {//If we are the element right before the element to be deleted 
		                
		                if(cntr.next.next != null) { //If there is a node after the node to be deleted
		                    
		                    Node temp1 = cntr.next.next;//gets set to that element and removes the element in the middle
		                    cntr.next = temp1;
		                    temp1.prev = temp1.prev.prev;
		                    list_size--; //The element was removed so the size of the list will decrease
		                    return true;
		                    
		                }else {
		                    
		                    Node temp1 = cntr.next;
		                    temp1.prev = null;
		                    cntr.next = null;
		                    last = cntr;
		                    list_size--;
		                    return true;
		                }
		            }
	            }
	            cntr = cntr.next;
			}
		}else {
			return false;
		}
		return false;
	}
	
	/**
	 * Returns the number of elements in the list
	 * @return the number of elements
	 */
	private int getSize() {
		
		return list_size;
	}
	
	/**
	 * Finds the index of the element in the list
	 * @param value This is the value that we are finding the index 
	 * @return Returns the index of the element
	 */
	public int indexOf(T value){
		// return index of a value (0 to size-1)
		// if value not present, return -1
		// O(N)
		
		int index = 0; //This is the index
		Node cntr = head;
		
		for(int i=0;i<list_size;i++) { //loops through the list
			
			if(cntr.val.equals(value)) { //check if each element is equal to the value, if it is then it will return the index
				return index;
			}else {
				index++;
			}
			
			cntr = cntr.next;
		}
		return -1;
	}
	
	/**
	 * Checks if the element exists in the list or not
	 * @param value This is what we are checking exists in the list or not
	 * @return Returns true or false depending on weather the element exists or not
	 */
	public boolean contains(T value){
		// return true if value is present
		// false otherwise
		// O(N) where N is the number of nodes returned by size()
		
		Node cntr = head;
		
		if(list_size == 0) { //If there no elements in the list the it will not exist
			
			return false;
		}
		for(int i=0;i<list_size;i++) {//else it loops through the list
			
			if(cntr.val.equals(value)) { //compares each value and returns true or false
				return true;
			}
			
			cntr = cntr.next;
		}
		return false;
	}
	
	/**
	 * Returns the value in the linked list
	 * @param value This is what we are getting in the list
	 * @return Returns the value in the list
	 */
	public T get(T value){
		// search for the node with the specified value:
		// if not found, return null;
		// if found, RETURN VALUE STORED from linked list, NOT the incoming value
		// Note: two values might be considered "equal" but not identical
		//       example: Pair <k,v1> and <k,v2> "equal" for different v1 and v2 
		// O(N) where N is the number of nodes returned by size()
		
		Node cntr = head;
		
		for(int i=0;i<list_size;i++) {//loops through the list
			
			if(cntr.val.equals(value)) { //compares each element and returns the element
				return cntr.val;
			}
			
			cntr = cntr.next;
		}
		return null;
	}
	
	/**
	 * Returns the number of elements in the list
	 * @return the number of elements
	 */
	public int size(){
		//return how many nodes are there
		//O(1)
		return list_size;
	}

	/**
	 * Every time this method is called, a new iterator object is created
	 */
	public Iterator<T> iterator(){
		// return a basic iterator
		// .hasNext() and .next() required 
		// both should be of O(1)
		return new SimpleListIterator();

	}
	
	/**
	 * Prints the list
	 */
	public void printList() {
		
		Node step = head;

        //step = this.head.next;

        for (int i = 0; i < list_size; i++) {

            System.out.print(step.val + ", ");

            step = step.next;

        }

        System.out.print("\n");
		
	}

	
	//----------------------------------------------------
	// example testing code... make sure you pass all ...
	// and edit this as much as you want!
	// also, consider add a toString() for your own debugging

	public static void main(String[] args){
		
		SimpleList<Integer> ilist = new SimpleList<Integer>();
		ilist.add(new Integer(11));
		ilist.add(new Integer(20));
		ilist.add(new Integer(5));
		
		if (ilist.size()==3 && ilist.contains(new Integer(5)) && 
			!ilist.contains(new Integer(2)) && ilist.indexOf(new Integer(20)) == 1){
			System.out.println("Yay 1");
		}

		if (!ilist.remove(new Integer(16)) && ilist.remove(new Integer(11)) &&
				!ilist.contains(new Integer(11)) && ilist.get(new Integer(20)).equals(new Integer(20))){
				System.out.println("Yay 2");			
			} 
		
		Iterator iter = ilist.iterator();
		if (iter.hasNext() && iter.next().equals(new Integer(20)) && iter.hasNext() &&
			iter.next().equals(new Integer(5)) && !iter.hasNext()){
			System.out.println("Yay 3");						
		}
		
	}

}
