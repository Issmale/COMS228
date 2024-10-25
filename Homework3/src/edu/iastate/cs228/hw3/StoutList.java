package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;




import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * @author issmale bekri
 */



/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  @Override
  public int size()
  {
    return size;
  }
  
  @Override
  public boolean add(E item)
  {
	// Exception handling
	    if (item == null) {
	        throw new NullPointerException();
	    }
	    // If it is an empty list
	    if (size == 0) {
	        Node newNode = new Node();
	        newNode.addItem(item);
	        link(head, newNode);
	    } else {
	        // If the last node is not full just add the item to the last node
	        if (tail.previous.count < nodeSize) {
	            tail.previous.addItem(item);
	        } else {
	            // If the last node is full create another node at the end and add the item
	            Node newNode = new Node();
	            newNode.addItem(item);
	            link(tail.previous, newNode);
	        }
	    }
	    
	    // Increase the size of the list since the item has been added
	    size++;
	    return true;
  }
  
  /**
   * Helper method to link the specified new node to the current node, 
   * updating the previous and next pointers accordingly.
   * @author issmale bekri
   *
   * @param currentNode the current node to which the new node will be linked
   * @param newNode the new node to be added
   */
  private void link(Node currentNode, Node newNode) {
      currentNode.next = newNode;
      newNode.previous = currentNode;
      newNode.next = tail;
      tail.previous = newNode;
  }
  
  /**
   * Helper method for splitting a node into two nodes. This method splits the provided node
   * into two nodes at the specified offset, and inserts the given item into the appropriate
   * node.
   * @author issmale bekri
   *
   * @param temp   the node to split
   * @param offset the offset within the node where the split should occur
   * @param item   the item to be inserted after the split
   * @return a NodeInfo representing the new node created after the split
   */
  private NodeInfo splitNode(Node temp, int offset, E item) {
      Node newFrontNode = new Node();
      int halfPoint = nodeSize / 2;
      int count = 0;

      // Copy the elements to the new node
      while (count < halfPoint) {
          newFrontNode.addItem(temp.data[halfPoint]);
          temp.removeItem(halfPoint);
          count++;
      }

      Node oldFrontNode = temp.next;

      temp.next = newFrontNode;
      newFrontNode.previous = temp;
      newFrontNode.next = oldFrontNode;
      oldFrontNode.previous = newFrontNode;

      // If the offset is less than or equal to M/2 put X in node n at offset off
      if (offset <= nodeSize / 2) {
          temp.addItem(offset, item);
      }
      // If the offset is greater than M/2 put X in node n at offset (off - M/2)
      if (offset > nodeSize / 2) {
          newFrontNode.addItem((offset - nodeSize / 2), item);
      }

      return new NodeInfo(newFrontNode, 0);
  }
  
  /**
   * Helper class to represent a specific point of the list. This class holds
   * information about a node and an offset within that node, allowing tracking
   * of positions within the list.
   * 
   * @author issmale bekri
   */
  private class NodeInfo {
      /**
       * The node being tracked.
       */
      public Node node;
      
      /**
       * The offset within the node.
       */
      public int offset;

      /**
       * constructor of a new NodeInfo object with the specified node and offset.
       *
       * @param node    the node to be represented
       * @param offset  the offset within the node
       */
      public NodeInfo(Node node, int offset) {
          this.node = node;
          this.offset = offset;
      }
  }

	/**
	 * Helper method to locate an specific item
	 * 
	 * @author issmale bekri
	 * 
	 * @param pos position of item that needs a info
	 * @return NodeInfo of specific point of the list
	 */
	private NodeInfo find(int pos) {
		Node newNode = head.next;
		int currentPos = 0;
		while (newNode != tail) {
			if (currentPos + newNode.count <= pos) {
				currentPos += newNode.count;
				newNode = newNode.next;
				continue;
			}

			NodeInfo nodeInfo = new NodeInfo(newNode, pos - currentPos);
			return nodeInfo;

		}
		return null;
	}
 
  @Override
  public void add(int pos, E item)
  {
	// if pos is out of bound throw IndexOutOfBoundsException
	    if (item == null) {
	        throw new NullPointerException();
	    }
	    if (pos < 0 || pos > size) {
	        throw new IndexOutOfBoundsException();
	    }

	    // if the list is empty create a new node and put X at offset 0
	    if (head.next == tail) {
	        add(item);
	    }

	    NodeInfo nodeInfo = find(pos);
	    Node tempNode = nodeInfo.node;
	    int offset = nodeInfo.offset;

	    // otherwise if offset = 0 and one of the following two cases occurs
	    if (offset == 0) {
	        // if n has a predecessor which has fewer than M elements and is not the head
	        // put X in n's predecessor
	        if (tempNode.previous.count < nodeSize && tempNode.previous != head) {
	        	tempNode.previous.addItem(item);
	        	size++;
	            return;
	        }
	        // if n is the tail node and n's predecessor has M elements create a new node
	        // and put X at offset 0
	        else if (tempNode == tail) {
	            add(item);
	            size++;
	            return;
	        }
	    }

	    // otherwise if there is space in node n put X in node n at offset off
	    // shifting array elements as necessary
	    if (tempNode.count < nodeSize) {
	    	tempNode.addItem(offset, item);
	    }
	    // else perform a split operation: move the last M/2 elements of node n into
	    // a new successor node n and then 
	    else {
	        splitNode(tempNode, offset, item);
	    }

	    // increase the size of the list, since the item has been added
	    size++;
  }

  @Override
  public E remove(int pos)
  {		
	  // if pos is out of bound throw IndexOutOfBoundsException
	  if (pos < 0 || pos > size) {
	        throw new IndexOutOfBoundsException();
	    }

	    NodeInfo nodeInfo = find(pos);
	    Node newNode = nodeInfo.node;
	    int offset = nodeInfo.offset;
	    E removedNode = newNode.data[offset];

	    // if the node n containing X is the last node and has only one element delete it
	    if (tail.previous == newNode && newNode.count == 1) {
	        unlink(newNode);
	    }
	    // else if n is the last node thus with two or more elements
	    // or if n has more than M/2 elements, remove X from n shifting elements as necessary;
	    else if (tail.previous == newNode || newNode.count > nodeSize / 2) {
	    	newNode.removeItem(offset);
	    }
	    // else  the node n must have at most elements look at its successor n'
	    //   we don't look at the predecessor of n and perform a merge operation as follows:
	    else {
	    	newNode.removeItem(offset);
	        Node successor = newNode.next;

	        // if the successor node n' has more than elements, move the first element from n' to n 
	        if (successor.count > nodeSize / 2) {
	        	newNode.addItem(successor.data[0]);
	            successor.removeItem(0);
	        }
	        // if the successor node n' has or fewer elements, then move all elements from n' to n and delete n' 
	        else if (successor.count <= nodeSize / 2) {
	            for (int i = 0; i < successor.count; i++) {
	            	newNode.addItem(successor.data[i]);
	            }
	            unlink(newNode.next);
	        }
	    }

	    // decrease the size of the list since an item has been removed
	    size--;
	    return removedNode;
  }

  /**
   * Helper method that unlinks a node from the list. This method updates the references of the previous and
   * next nodes of the given node, effectively removing it from the list.
   *
   *@author issmale bekri
   * @param node The node to be unlinked
   */
  private void unlink(Node node) {
      // Update the next node of the previous node to skip the given node
      node.previous.next = node.next;
      
      // Update the previous node of the next node to skip the given node
      node.next.previous = node.previous;
  }
  
  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
	  // Create an array to store the sorted elements
	    E[] sortedList = (E[]) new Comparable[size];

	    int index = 0;
	    Node newNode = head.next;

	    // Copy elements from the nodes into the sortedList
	    while (newNode != tail) {
	        for (int i = 0; i < newNode.count; i++) {
	            sortedList[index] = newNode.data[i];
	            index++;
	        }
	        newNode = newNode.next;
	    }

	    // Update list pointers to create an empty list
	    head.next = tail;
	    tail.previous = head;

	    // Sort the elements using insertion sort
	    insertionSort(sortedList, new ItemComparator());

	    // Clear the size of the list
	    size = 0;

	    // Add the sorted elements back to the list
	    this.addAll(Arrays.asList(sortedList));
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
	  // Create an array to store the reversed elements
	    E[] reverseList = (E[]) new Comparable[size];

	    int index = 0;
	    Node newNode = head.next;

	    // Copy elements from the nodes into the reverseList
	    while (newNode != tail) {
	        for (int i = 0; i < newNode.count; i++) {
	            reverseList[index] = newNode.data[i];
	            index++;
	        }
	        newNode = newNode.next;
	    }

	    // Update list pointers to create an empty list
	    head.next = tail;
	    tail.previous = head;

	    // Sort the elements in descending order using bubble sort
	    bubbleSort(reverseList);

	    // Clear the size of the list
	    size = 0;

	    // Add the reversed elements back to the list
	    this.addAll(Arrays.asList(reverseList));
  }
  
  @Override
  public Iterator<E> iterator()
  {
    // TODO Auto-generated method stub
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {
    // TODO Auto-generated method stub
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
    // TODO Auto-generated method stub
    return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      //useful for debugging 
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
 
  private class StoutListIterator implements ListIterator<E>
  {

	  /**
	   * Constants to represent the direction of the iterator in a StoutListIterator.
	   */
	  private static final int AHEAD = 1; //points ahead
	  private static final int BEHIND = -1; //points behind
	  private static final int NONE = 0; // no direction

		/**
		 * pointer of iterator
		 */
		int currentIndex;
		
		/**
		 * data structure of iterator in array form
		 *
		 */
		public E[] dataArr;
		
		/**
		 * tracks the lastAction taken by the program
		 * it is mainly used for remove() and set() method to determine
		 * which item to remove or set
		 */
		int direction;

		/**
		 * Default constructor
		 * Sets the pointer of iterator to the beginning of the list
		 */
		public StoutListIterator() {
			currentIndex = 0;
			direction = NONE;
			dataListArr();
		}

		/**
		 * Constructor finds node at a given position.
		 * Sets the pointer of iterator to the specific index of the list
		 * 
		 * @param pos
		 */
		public StoutListIterator(int pos) {
			// TODO
			if (pos < 0 || pos > size) { 
	    		throw new IndexOutOfBoundsException();
	    	}
			currentIndex = pos;
			direction = NONE;
			dataListArr();
		}


		/**
		 * @return whether iterator has next available value or not
		 */
		@Override
		public boolean hasNext() {
				return currentIndex < size;
		}

		/**
		 * Returns the next ready value and shifts the pointer by 1
		 * 
		 * @return the next ready value of the iterator
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			direction = BEHIND;
			return dataArr[currentIndex++];
		}

		/**
		 * Removes from the list the last element returned by next() or previous().
		 * Can only be called once per call of next() or previous()
		 * Also removes the element from the StoutList
		 */
		@Override
		public void remove() {
			if (direction == NONE) {
	            throw new IllegalStateException();
	        }
			if (direction == BEHIND) {
				StoutList.this.remove(currentIndex);
				dataListArr();
				currentIndex--;
				if (currentIndex < 0) {
					currentIndex = 0;
				}
			} else if (direction == AHEAD) {
				StoutList.this.remove(currentIndex);
				dataListArr();
			} 
			direction = NONE;
		}

		/**
		 * @return whether iterator has previous available value or not
		 */
		@Override
		public boolean hasPrevious() {
			return currentIndex > 0;
		}

		/**
		 * @return index of next available element
		 */
		@Override
		public int nextIndex() {
			return currentIndex;
		}
		
		/**
		 * Returns previous available element and shifts pointer by -1
		 * 
		 * @return previous available element
		 */
		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			direction = AHEAD;
			currentIndex--;
			return dataArr[currentIndex];
		}

		/**
		 * @return index of previous element
		 */
		@Override
		public int previousIndex() {
			return currentIndex - 1;
		}

		/**
		 * Replaces the element at the current pointer
		 * 
		 * @param e replacing element
		 */
		@Override
		public void set(E e) {
			if (direction == NONE) { 
				throw new IllegalStateException();
			}
			if (e == null) {
				throw new NullPointerException();
			}
			if (direction == BEHIND) {
				NodeInfo nodeInfo = find(currentIndex - 1);
				nodeInfo.node.data[nodeInfo.offset] = e;
				dataArr[currentIndex - 1] = e;
			} else  {
				NodeInfo nodeInfo = find(currentIndex);
				nodeInfo.node.data[nodeInfo.offset] = e;
				dataArr[currentIndex] = e;
			
			}
		}

		/**
		 * Adds an element to the end of the list
		 * 
		 * @param arg0 adding element
		 */
		@Override
		public void add(E arg0) {
			StoutList.this.add(currentIndex, arg0);
			currentIndex++;
			dataListArr();
			direction = -1;

		}

		// Other methods you may want to add or override that could possibly facilitate
		// other operations, for instance, addition, access to the previous element,
		// etc.
		//
		// ...
		//
		/**
		 * Helper method Takes the StoutList and put its data into dataList in an array form
		 * @author issmale bekri
		 */
		private void dataListArr() {
			dataArr = (E[]) new Comparable[size];

			int index = 0;
			Node newNode = head.next;
			while (newNode != tail) {
				for (int i = 0; i < newNode.count; i++) {
					dataArr[index] = newNode.data[i];
					index++;
				}
				newNode = newNode.next;
			}
		}
	}
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  for(int i = 1; i < arr.length; i++)
		{
			E key = arr[i];
			int j = i - 1;  // element in front of the key
			while(j >= 0 && comp.compare(arr[j],key) > 0) 
			{
				arr[j + 1] = arr[j];
	            j -= 1;
			}
			arr[j + 1] = key;
		}
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
	  ItemComparator itemComparator = new ItemComparator();
	  int n = arr.length;
      
	  for (int i = 0; i < n - 1; i++) {
          for (int j = 0; j < n - i - 1; j++) {
              if (itemComparator.compare(arr[j], arr[j + 1]) < 0) 
              {   // do the swap
                  E temp = arr[j];
                  arr[j] = arr[j + 1];
                  arr[j + 1] = temp;
              }
          }
	  }
  }
 
  /*
   * Comparator that helps with insertion sort
   */
  class ItemComparator<E extends Comparable<E>> implements Comparator<E> {
		@Override
		public int compare(E e, E e1) {
			return e.compareTo(e1);
		}

	}
}
