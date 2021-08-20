/**
 *  Program 1a
 *  This program creates an unordered array priority queue. Private helper methods
 *  such as shiftDelete, linearSearch, and findHighPriority help the methods overridden from
 *  the Priority Queue interface.
 *  CS-310
 *  18 February 2020
 *  @author  Christian James cssc1229
 **/
 
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnorderedArrayPriorityQueue<E extends Comparable <E>> implements PriorityQueue<E> {
    protected E[] array;
    protected int currentSize, maxCapacity;

    /**
     * Constructor for default max capacity
     */
    public UnorderedArrayPriorityQueue() {
        maxCapacity = DEFAULT_MAX_CAPACITY;
        currentSize = 0;
        array = (E[]) new Comparable[maxCapacity];
    }

    /**
     * Constructor for custom max capacity
     * @param maxCapacity
     */
    public UnorderedArrayPriorityQueue(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        currentSize = 0;
        array = (E[]) new Comparable[maxCapacity];
    }

    /**
     * This method accepts an object then inserts it at the end of the priority queue
     * @param object
     * @return
     */
    public boolean insert(E object) {
        if(isFull()) {
            return false;
        } else {
            array[size()] = object;
            currentSize++;
            return true;
        }
    }

    /**
     * This method finds the highest priority item in the queue, returns it, and removes it, shifting the rest of the array to fill the gap
     * @return
     */
    public E remove() {
        if (isEmpty())
            return null;
        else {
            int hLocation = findHighPriority();
            E hPriority = array[hLocation];
            deleteShift(hLocation);
            return hPriority;
        }
    }

    /**
     * This method searches the priority queue for each instance of the given object's priority and removes them, shifting the rest of the array to fill the gaps
     * @param object
     * @return
     */
    public boolean delete(E object) {
        int i = 0;
        int search;
        int deleted = 0;
        while (i < size()) {
            search = linearSearch(object, i);
            if (search == -1)
                i = size();
            else {
                deleteShift(search);
                deleted++;
                i = search;
            }
        }
        if (deleted == 0)
            return false;
        else
            return true;
    }

    /**
     * This method find the highest priority item in the queue and returns it
     * @return
     */
    public E peek() {
        if (isEmpty())
            return null;
        else
            return array[findHighPriority()];
    }

    /**
     * This method searches the priority queue for a given object, returning true if it finds it or false otherwise
     * @param obj
     * @return
     */
    public boolean contains (E obj) {
        if (linearSearch(obj, 0) >= 0)
            return true;
        else
            return false;
    }

    /**
     * This helper method searches the priority queue for an object starting at a given index
     * @param obj
     * @param start
     * @return
     */
    private int linearSearch (E obj, int start) {
        for (int i = start; i < size(); i++) {
            if (obj.compareTo(array[i]) == 0)
                return i;
        }
        return -1;
    }

    /**
     * This method searches for the object with the highest priority in the queue and returns its index
     * @return
     */
    private int findHighPriority () {
        if (isEmpty())
            return -1;
        E highPriority = array[0];
        int highLocation = 0;
        for (int i = 1; i < size(); i++) {
            if (array[i].compareTo(highPriority) < 0) {
                highPriority = array[i];
                highLocation = i;
            }
        }
        return highLocation;
    }

    /**
     * This helper method deletes the item at the given index, shifting the array to fill the gap
     * @param start
     */
    private void deleteShift (int start) {
        int value = start;
        E temp;
        while (value + 1 < size()) {
            temp = array[value + 1];
            array[value] = temp;
            value++;
        }
        currentSize--;
    }

    /**
     * This method returns the priority queue's current size
     * @return
     */
    public int size() {
        return currentSize;
    }

    /**
     * This method clears the priority queue
     */
    public void clear() {
        currentSize = 0;
    }

    /**
     * This method checks if the priority queue is empty
     * @return
     */
    public boolean isEmpty() {
        if (size() == 0)
            return true;
        else
            return false;
    }

    /**
     * This method checks if the priority queue is full
     * @return
     */
    public boolean isFull() {
        if (size() == maxCapacity)
            return true;
        else
            return false;
    }

    /**
     * This method creates an instance of the IteratorHelper private class
     * @return
     */
    public Iterator<E> iterator() {
        return new IteratorHelper();
    }

    private class IteratorHelper implements Iterator {
        int counter = 0;
        public boolean hasNext() {
            return counter < currentSize;
        }
        public E next() {
            if(!hasNext())
                throw new NoSuchElementException();
            return array[counter++];
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}