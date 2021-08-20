/**
 *  Program 2b
 *  This program creates an ordered linked list priority queue with a fail fast iterator class.
 *  Helper methods helps insert in the correct order based on priority.
 *  CS-310
 *  10 March 2020
 *  @author  Christian James cssc1229
 **/
package date_structures;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedLinkedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	private Node<E> head;
	private int size;
	private long modificationCounter;
	
	public OrderedLinkedListPriorityQueue() {
		head = null;
		size = 0;
		modificationCounter = 0;
	}
	
	/**
	 * This method finds the correct location and inserts the object parameter within the linked list using helper methods. Returns true if successful and false otherwise
	 * @param object
	 * @return
	 */
    public boolean insert(E object) {
    	Node<E> newNode = new Node<E>(object);
    	if (isEmpty()) {
    		return insertFirst(newNode, null);
    	} else {
    		Node<E> curr = head;
    		Node<E> prev = head;
    		while (curr.next != null) {
    			if (object.compareTo(curr.data) >= 0) {
    				prev = curr;
    				curr = prev.next;
    			}
    			else
    				break;
    		}
    		if (curr.next == null) {
    			if (object.compareTo(curr.data) < 0) {
    				if (curr == head)
    					return insertFirst(newNode, curr);
    				else {
    					return insertHere(newNode, prev, curr);
    				}
    			}
    			else
    				return insertLast(newNode, curr);
    		} else {
    			if (curr == head && object.compareTo(curr.data) < 0)
    				return insertFirst(newNode, curr);
        		return insertHere(newNode, prev, curr);
    		}
    	}
    }

    /**
     * This helper method inserts the node at the head of the linked list
     * @param newNode
     * @param curr
     * @return
     */
    private boolean insertFirst(Node<E> newNode, Node<E> curr) {
    	newNode.next = curr;
    	head = newNode;
    	size++;
    	modificationCounter++;
    	return true;
    }
    
    /**
     * This helper method inserts the node between the two parameter nodes given (prev and curr)
     * @param newNode
     * @param prev
     * @param curr
     * @return
     */
    private boolean insertHere(Node<E> newNode, Node<E> prev, Node<E> curr) {
    	prev.next = newNode;
    	newNode.next = curr;
    	size++;
    	modificationCounter++;
    	return true;
    }
    
    /**
     * This helper method inserts the node at the tail of the linked list
     * @param newNode
     * @param curr
     * @return
     */
    private boolean insertLast(Node<E> newNode, Node<E> curr) {
    	curr.next = newNode;
    	newNode.next = null;
    	size++;
    	modificationCounter++;
    	return true;
    }
    
    /**
     * This method removes the highest priority Node of the linked list, returning its data
     * @return
     */
    public E remove() {
    	if (isEmpty())
    		return null;
    	E toReturn = head.data;
    	Node<E> next = head.next;
    	head = next;
    	size--;
    	modificationCounter++;
    	return toReturn;
    }
    
    /**
     * This method finds and deletes all instances of the given parameter object from the linked list, terminating once it finds the last one. It returns true if it deletes anything and false otherwise
     * @param obj
     * @return
     */
    public boolean delete(E obj) {
    	Node<E> curr = head;
    	Node<E> prev = head;
    	Node<E> start = null;
    	Node<E> end = null;
    	int numDeleted = 0;
    	boolean deleted = false;
    	if (isEmpty())
    		return deleted;
    	while (curr != null && curr.data.compareTo(obj) != 0) {
    		prev = curr;
    		curr = prev.next;
    	}
    	if (curr == null)
    		deleted = false;
    	else {
    		deleted = true;
    		if (curr.data.compareTo(obj) == 0) {
    			start = prev;
    			end = curr;
    		}
    		while (end.data.compareTo(obj) == 0) {
    			prev = end;
    			end = prev.next;
    			numDeleted++;
    		}
    		if (start == head)
    			head = end;
    		else
    			start.next = end;
    		size -= numDeleted;
    		modificationCounter++;
    	}
    	return deleted;
    }
    
    /**
     * This method finds and returns the data of the highest priority Node
     * @return
     */
    public E peek() {
    	if (isEmpty())
    		return null;
    	else
    		return head.data;
    }

    /**
     * This method searches for the given parameter object, returning true if it exists in the linked list and false otherwise
     * @param obj
     * @return
     */
    public boolean contains(E obj) {
    	Node<E> curr = head;
    	Node<E> prev = head;
    	if (isEmpty())
    		return false;
    	while (curr.next != null) {
    		if (curr ==  obj)
    			return true;
    		prev = curr;
    		curr = prev.next;
    	}
    	return false;
    }

    /**
     * Returns the size of the linked list
     * @return
     */
    public int size() {
    	return size;
    }

    /**
     * Clears the linked list
     */
    public void clear() {
    	head = null;
    	size = 0;
    	modificationCounter = 0;
    }

    /**
     * This method checks if the linked list is empty, return true if so and false otherwise
     * @return
     */
    public boolean isEmpty() {
    	if (head == null)
    		return true;
    	else
    		return false;
    }

    /**
     * This method checks if the linked list is full, returning false always since a linked list has no maximum number of Nodes
     * @return
     */
    public boolean isFull() {
    	return false;
    }
    
    /**
     * This is the class for the Nodes in the Linked List, storing the next and data variables
     * @author Me (see above)
     */
    private static class Node<E> { 
        E data;
        Node<E> next;
        Node(E d) { 
            data = d; 
            next = null; 
        }
    }
    
    /**
     * This method returns an instance of the SLListIterator class
     * @return
     */
    public Iterator<E> iterator() {
    	return new SLListIterator();
    }
    
    /**
     * This class implements the Iterator interface and creates a fail fast iterator class
     * @author Me again
     */
    private class SLListIterator implements Iterator<E> {
    	private Node<E> current;
    	private int count;
    	long modCounter;
    	private SLListIterator() {
    		current = head;
    		count = 1;
    		modCounter = modificationCounter;
    	}
    	
    	@Override
    	public boolean hasNext() {
    		if (modCounter != modificationCounter)
    			throw new ConcurrentModificationException();
    		return current != null;
    	}
    	
    	@Override
    	public E next() {
    		if (!hasNext())
    			throw new NoSuchElementException();
    		E tmp = current.data;
    		current = current.next;
    		count++;
    		return tmp;
    	}
    	
    	public void remove() {
    		throw new UnsupportedOperationException();
    	}
    }
}
