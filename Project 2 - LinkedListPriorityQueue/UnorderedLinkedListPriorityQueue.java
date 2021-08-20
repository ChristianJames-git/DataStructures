/**
 *  Program 2a
 *  This program creates an unordered linked list priority queue with a fail fast iterator class.
 *  CS-310
 *  10 March 2020
 *  @author  Christian James cssc1229
 **/
package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class UnorderedLinkedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	private Node<E> head;
	private int size;
	private int modificationCounter;
	
	public UnorderedLinkedListPriorityQueue() {
		head = null;
		size = 0;
		modificationCounter = 0;
	}
	
	/**
	 * This method inserts the given parameter object onto the front of the linked list, returning true if it successfully inserts and false otherwise
	 * @param object
	 * @return
	 */
    public boolean insert(E object) {
    	Node<E> newNode = new Node<E>(object);
    	newNode.next = head;
    	head = newNode;
    	if (head.data.compareTo(object) != 0)
    		return false;
    	size++;
    	modificationCounter++;
    	return true;
    		
    }

    /**
     * This method finds and removes the highest priority Node in the linked list, returning the Node's data
     * @return
     */
    public E remove() {
    	if (isEmpty())
    		return null;
    	else {
    		Node<E> high = head;
    		Node<E> highPrev = head;
    		Node<E> curr = head;
    		Node<E> prev = head;
    		E toReturn;
    		if (curr.next == null) {
    			toReturn = head.data;
    			head = null;
    			size--;
    			modificationCounter++;
    			return toReturn;
    		}
        	while (curr != null) {
        		if (curr.data.compareTo(high.data) <= 0) {
        			high = curr;
        			highPrev = prev;
        		}
        		prev = curr;
        		curr = prev.next;
        	}
    		toReturn = high.data;
    		if (high == highPrev)
    			head = high.next;
    		else
    			highPrev.next = high.next;
    		size--;
    		modificationCounter++;
    		return toReturn;
    	}
    }

    /**
     * This method finds and deletes every instance of the given parameter object in the linked list, returning true if one or more is deleted and false otherwise
     * @param obj
     * @return
     */
    public boolean delete(E obj) {
    	Node<E> curr = head;
    	Node<E> prev = head;
    	boolean deleted = false;
    	if (isEmpty())
    		return deleted;
    	while (curr != null) {
    		if (head.data.compareTo(obj) == 0) {
    			head = curr.next;
    			deleted = true;
    			size--;
    			modificationCounter++;
    		}
    		else if (curr.data.compareTo(obj) == 0) {
    			prev.next = curr.next;
    			curr = prev.next;
    			deleted = true;
    			modificationCounter++;
    			size--;
    		}
    		prev = curr;
    		curr = prev.next;
    	}
    	
    	return deleted;
    }

    /**
     * This method finds and returns the data of the highest priority node in the linked list
     * @return
     */
    public E peek() {
    	if (isEmpty())
    		return null;
    	else {
    		Node<E> high = head;
    		Node<E> curr = head;
    		if (curr.next == null)
        		return head.data;
        	while (curr != null) {
        		if (curr.data.compareTo(high.data) <= 0)
        			high = curr;
        		curr = curr.next;
        	}
        	return high.data;
    	}
    }
    
    /**
     * This method searches the linked list for the given parameter object, return true if it exists in the list and false otherwise
     * @param obj
     * @return
     */
    public boolean contains(E obj) {
    	Node<E> curr = head;
    	if (isEmpty())
    		return false;
    	while (curr.next != null) {
    		if (curr ==  obj)
    			return true;
    		curr = curr.next;
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
     * This method checks if the linked list is full, returning false since linked lists don't have a maximum node capacity
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
