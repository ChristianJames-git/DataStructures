/**
 *  Program 1b
 *  This program creates an ordered array priority queue. Private helper methods
 *  such as shiftInsert and binarySearch help the methods overridden from
 *  the Priority Queue interface.
 *  CS-310
 *  18 February 2020
 *  @author  Christian James cssc1229
 **/
package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedArrayPriorityQueue<E extends Comparable <E>> implements PriorityQueue<E> {
    protected E[] array;
    protected int currentSize, maxCapacity;

    /**
     * Constructor for default max capacity
     */
    public OrderedArrayPriorityQueue() {
        maxCapacity = PriorityQueue.DEFAULT_MAX_CAPACITY;
        currentSize = 0;
        array = (E[]) new Comparable[maxCapacity];
    }

    /**
     * Constructor for custom max capacity
     * @param maxCapacity
     */
    public OrderedArrayPriorityQueue(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        currentSize = 0;
        array = (E[]) new Comparable[maxCapacity];
    }

    /**
     * This method accepts an object, finds the location it belongs in the ordered array, then inserts it
     * @param object
     * @return
     */
    public boolean insert(E object) {
        if (isEmpty()) {
            array[currentSize++] = object;
            return true;
        } else if (isFull()) {
            return false;
        } else {
            int search = binarySearch(object);
            switch (search) {
                case -1:
                    shiftInsert(0, object);
                    return true;
                case -2:
                    array[currentSize++] = object;
                    return true;
                default:
                    shiftInsert(search, object);
                    return true;

            }
        }
    }

    /**
     * This helper method searches through the array using a binary search,
     * @param object
     * @return
     */
    private int binarySearch(E object) {
        int begin = 0;
        int end = size() - 1;
        int mid = 0;
        while (begin <= end) {
            mid = (begin + end) / 2;
            if (object.compareTo(array[mid]) > 0) {
                end = mid - 1;
            } else if (object.compareTo(array[mid]) < 0) {
                begin = mid + 1;
            } else {
                return mid;
            }
        }
        if (end < 0)
            return -1;
        else if (begin > size() - 1)
            return -2;
        else {
            if (object.compareTo(array[mid]) < 0)
                return mid + 1;
            else
                return mid;
        }
    }

    /**
     * This helper method inserts a given object into a given index, shifting the rest of the array above it
     * @param start
     * @param object
     */
    private void shiftInsert(int start, E object) {
        int value = start;
        while (value != 0 && object.compareTo(array[value - 1]) == 0)
            value--;
        E temp;
        E temp2 = object;
        while (value <= currentSize) {
            temp = array[value];
            array[value] = temp2;
            temp2 = temp;
            value++;
        }
        currentSize++;
    }

    /**
     * This method returns and removes the highest priority item in the priority queue
     * @return
     */
    public E remove() {
        if (isEmpty())
            return null;
        else
            return array[--currentSize];
    }

    /**
     * This method searches for the given object, removing all instances of the same priority
     * @param obj
     * @return
     */
    public boolean delete(E obj) {
        int locationA, locationB;
        int deleted = 0;
        if (isEmpty())
            return false;
        else {
            locationA = binarySearch(obj);
            if(locationA < 0 || locationA >= size())
                return false;
            else if (array[locationA].compareTo(obj) == 0) {
                locationB = locationA;
                deleted++;
                while (locationA > 0) {
                    if(array[locationA].compareTo(array[locationA - 1]) == 0) {
                        locationA--;
                        deleted++;
                    }
                    else
                        break;
                }
                while (locationB < size() - 1) {
                    if(array[locationB].compareTo(array[locationB + 1]) == 0) {
                        locationB++;
                        deleted++;
                    }
                    else
                        break;
                }
                while(locationB + 1 < size()) {
                    array[locationA++] = array[++locationB];
                }
                currentSize -= deleted;
                return true;
            }
            else
                return false;
        }
    }

    /**
     * This method returns the highest priority item in the queue
     * @return
     */
    public E peek() {
        if (isEmpty())
            return null;
        else
            return array[size() - 1];
    }

    /**
     * This method returns true if the priority queue contains the given object and false otherwise
     * @param obj
     * @return
     */
    public boolean contains(E obj) {
        if (binarySearch(obj) >= 0)
            return true;
        else
            return false;
    }

    /**
     * This method returns the current size of the priority queue
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