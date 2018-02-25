package CampingReg;

public class MyLinkedList<T> {

	/* Node that points to the top of the list */
	private Node top; 

	/* Node that points to the bottom of the list */
	private Node tail;

	/* Default length of the list before finding its size */
	private int length = 0;

	/* A regular Node */
	Node node;


	/****************************************************************
	 * 
	 * Default constructor for the MyLinkedList that initializes the
	 * top and tail of the list to null.
	 * 
	 ****************************************************************/
	public MyLinkedList() {
		top = null;
		tail = null;
	}


	/****************************************************************
	 * 
	 * Determines the size, that is, the number of elements in the list
	 *
	 * @return Returns the size of the list
	 * 
	 ****************************************************************/
	public int size() {

		// Case 0 : List is empty
		if ( top == null )
			length = 0;

		// Case 1 : Single Item List
		else if ( ( top != null ) && ( top.getNext() == tail ) )
			length = 1;

		// Case 2 : Mutli List
		// Loops until it finds the end of the list
		else {
			Node current = top;
			while ( current != null ) {
				length++;
				current = current.getNext();
			}
		}

		return length;
	}

	/****************************************************************
	 * 
	 * Inserts a node before a specific index.  When the list is empty
	 * that is, top = null, then the index must be 0. After the first
	 * element is added, index must be:  0 <= index < size of list  
	 * 
	 * @param index a specific index into the list.
	 * 
	 * @throws IllegalArgumentStringxception if index < 0 or 
	 * 			index >= size of the list
	 *
	 ****************************************************************/
	public void insertBefore (int index, Site data) {
		Node temp = new Node();

		// Checks if valid index
		if ( index < 0 || index > size() )
			throw new IllegalArgumentException();

		// Case 0 : No List
		if ( top == null ) {
			top = new Node();
			top.setData( data );
			top.setNext( null );
			return ;
		}

		// Case 1 : Single Item List
		if ( size() == 1 ) {
			temp = new Node();
			temp.setData( top.getData() );
			top.setNext( temp );
			top.setData( data );
			return ;
		}


		// Case 2 : Multi List
		if ( size() > 1 ) {
			temp = top;

			// Loops until at the specified index
			for (int i = 1; i < index; i++)
				temp = temp.getNext();

			temp.setNext( new Node ( data, temp.getNext() ) );
			return ;
		}
	}

	/****************************************************************
	 * 
	 * Inserts a node after a specific index.  When the list is empty
	 * that is, top = null, then the index must be 0. After the first
	 * element is added, index must be:  0 <= index < size of list  
	 * 
	 * @param index a specific index into the list.
	 * 
	 * @throws IllegalArgumentStringxception if index < 0 or 
	 * 			index >= size of the list
	 *
	 ****************************************************************/
	public void add ( Site data) {
		insertBefore( size(), data );
	}

	/****************************************************************
	 * 
	 * This Method removes a node at the specific index position.  The
	 * first node is index 0.
	 * 
	 * @param index the position in the linked list that is to be 
	 * 			removed.  The first position is zero.  
	 * 
	 * @throws IllegalArgumentStringxception if index < 0 or 
	 * 			index >= size of the list
	 * 
	 ****************************************************************/
	public boolean remove(int index) {
		
		/* A temp Node to be used for cycling through the list */
		Node temp = null;
		
		/* A boolean variable that holds if the item could be deleted */
		boolean del = false;

		// Checks if valid index
		if ( index < 0 || index > size() )
			throw new IllegalArgumentException();

		// Case 0 : No List
		if ( size() == 0 )
			del = true;

		// Case 1 : Single Item List
		if ( size() == 1 && index == 0 ) {
			removeTop();
			del = true;
		}

		// Case 2 : Multi Item List
		if ( size() > 1 ) {
			node = top;

			if ( index == 0 )
				removeTop();

			// Loops until at the index
			else
				for (int k = 1; k <= index; k++) {

					node = node.getNext();

					// Runs the if statement once at the index
					if ( ( k + 1 ) == index ) {
						temp = node.getNext().getNext();
						node.setNext( temp );
						del = true;
					}
				}	
		}
		return del;
	}

	/****************************************************************
	 * 
	 * Removes the top element of the list
	 * 
	 * @throws RuntimeStringxception if top == null, that is,
	 * 			 there is no list.
	 * 
	 ****************************************************************/
	public void removeTop () {

		// Case 0 : no list
		if ( top == null )
			throw new RuntimeException();

		// Case 1 : Single Item List
		else if ( ( top.getData() != null ) && ( top.getNext().getData() == null ) ) {
			top = tail;

		}

		// Case 2 : Multi Item List
		else {
			top = top.getNext();

		}
	}

	/****************************************************************
	 * 
	 * Removes all sites in the list
	 * 
	 ****************************************************************/
	public void clear() {

		// Case 0 : no list
		if ( top == null )
			throw new RuntimeException();

		// Case 1 : Single Item List
		else if ( ( top.getData() != null ) && ( top.getNext().getData() == null ) ) {
			top = tail;

		}

		// Case 2 : Multi Item List
		else
			while ( top != null )
				removeTop();
	}

	/****************************************************************
	 * 
	 * Loops through the list until at the index.
	 * 
	 * @param index is the location the user wants information on
	 * 
	 * @return Returns the site
	 * 
	 * @throws RuntimeStringxception if top == null, that is,
	 * 			 there is no list.
	 * 
	 ****************************************************************/
	public Site get( int index ) {

		// Case 0 : no list
		if ( top == null )
			throw new RuntimeException();

		// Case 1 : Single Item List
		else if ( ( top.getData() != null ) && ( top.getNext().getData() == null ) )
			return top.getData();

		// Case 2 : Multi Item List
		else {

			// Creates a temp node
			Node temp = new Node();
			temp = top;

			// Loops until at the index
			for ( int i = 0; i <= index ; i++ ) {

				// Runs once pointer is at the index
				if ( i == index )
					return temp.getNext().getData();

				// Cycles to the next Site
				temp = temp.getNext();
			}
		}
		return null;
	}
}
