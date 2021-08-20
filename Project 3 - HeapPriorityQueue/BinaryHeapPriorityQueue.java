package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BinaryHeapPriorityQueue<E extends Comparable <E>> implements PriorityQueue<E> {
    private int lastPosition;
    private int modificationCounter;
    private int entryNumber;
    Wrapper<E>[] array;

    @SuppressWarnings("unchecked")
    public BinaryHeapPriorityQueue() {
        array = new Wrapper[DEFAULT_MAX_CAPACITY];
        modificationCounter = 0;
        lastPosition = -1;
        entryNumber = 0;
    }

    @SuppressWarnings("unchecked")
    public BinaryHeapPriorityQueue(int maxCapacity) {
        array = new Wrapper[maxCapacity];
        modificationCounter = 0;
        lastPosition = -1;
        entryNumber = 0;
    }

    // Inserts a new object into the priority queue. Returns true if
    // the insertion is successful. If the PQ is full, the insertion
    // is aborted, and the method returns false.
    public boolean insert(E obj) {
        if(isFull())
            return false;
        else {
            array[++lastPosition] = new Wrapper<>(obj);
            modificationCounter++;
            return trickleUp(lastPosition);
        }
    }

    // Removes the object of highest priority that has been in the
    // PQ the longest, and returns it. Returns null if the PQ is empty.
    public E remove() {
        if (isEmpty())
            return null;
        else {
            E temp = array[0].getData();
            array[0] = array[lastPosition--];
            trickleDown(0);
            modificationCounter++;
            return temp;
        }
    }

    // Deletes all instances of the parameter obj from the PQ if found, and
    // returns true. Returns false if no match to the parameter obj is found.
    public boolean delete(E obj) {
        return delete(obj, 0, 0);
    }

    // Returns the object of highest priority that has been in the
    // PQ the longest, but does NOT remove it.
    // Returns null if the PQ is empty.
    public E peek() {
        return array[0].getData();
    }

    // Returns true if the priority queue contains the specified element
    // false otherwise.
    public boolean contains(E obj) {
        if (isEmpty())
            return false;
        return contains(obj, 0);
    }

    // Returns the number of objects currently in the PQ.
    public int size() {
        return lastPosition + 1;
    }

    // Returns the PQ to an empty state.
    public void clear() {
        lastPosition = -1;
        modificationCounter = 0;
    }

    // Returns true if the PQ is empty, otherwise false
    public boolean isEmpty() {
        return size() == 0;
    }

    // Returns true if the PQ is full, otherwise false. List based
    // implementations should always return false.
    public boolean isFull() {
        return size() == array.length;
    }

    private void swap(int from, int to) {
        Wrapper<E> temp = array[to];
        array[to] = array[from];
        array[from] = temp;
    }

    private boolean trickleUp(int curPos) {
        int parentPos = findParent(curPos);
        if (curPos == 0 || array[curPos].compareTo(array[parentPos]) > 0) {
            return true;
        }
        else {
            swap(curPos, parentPos);
            trickleUp(parentPos);
        }
        return true;
    }

    private void trickleDown(int lastPos) {
        int favChild = findFavChild(lastPos);
        if (favChild == -1 || array[favChild].compareTo(array[lastPos]) > 0)
            return;
        else {
            swap(lastPos, favChild);
            trickleDown(favChild);
        }
    }

    private int findParent(int childPos) {
        return (childPos - 1) / 2;
    }

    private int findFavChild(int parentPos) {
        int left = leftChild(parentPos);
        int right = left + 1;
        if (lastPosition < left)
            return -1;
        else if (lastPosition == left)
            return left;
        else {
            int comparingChildren = array[left].compareTo(array[right]);
            if (comparingChildren < 0)
                return left;
            return right;
        }
    }

    private int leftChild(int parent) {
        return (parent << 1) + 1;
    }

    private void deleteInstance(int start) {
        swap(start, lastPosition--);
        trickleDown(start);
    }

    private boolean delete(E obj, int location, int deleted) {
        if (array[location].getData().compareTo(obj) == 0) {
            deleteInstance(location);
            deleted++;
            return delete(obj, location, deleted);
        }
        else {
            if (searchLeftDel(leftChild(location), obj, deleted))
                return true;
            return searchRightDel(leftChild(location) + 1, obj, deleted);
        }
    }

    private boolean searchLeftDel(int left, E obj, int deleted) {
        if(left < size()) {
            if(obj.compareTo(array[left].getData()) < 0)
                return false;
            else
                return delete(obj, left, deleted);
        }
        return false;
    }

    private boolean searchRightDel(int right, E obj, int deleted) {
        if (right < size()) {
            if(obj.compareTo(array[right].getData()) < 0)
                return false;
            else
                return delete(obj, right, deleted);
        }
        return false;
    }

    private boolean contains(E obj, int root) {
        if (array[root].getData().compareTo(obj) == 0)
            return true;
        if (searchLeft(leftChild(root), obj))
            return true;
        return searchRight(leftChild(root) + 1, obj);
    }

    private boolean searchLeft(int left, E obj) {
        if(left < size()) {
            if(obj.compareTo(array[left].getData()) < 0)
                return false;
            else
                return contains(obj, left);
        }
        return false;
    }

    private boolean searchRight(int right, E obj) {
        if (right < size()) {
            if(obj.compareTo(array[right].getData()) < 0)
                return false;
            else
                return contains(obj, right);
        }
        return false;
    }

    private class Wrapper<E extends Comparable <E>> implements Comparable<Wrapper<E>> {
        int number;
        private E data;

        public Wrapper(E d) {
            number = entryNumber++;
            data = d;
        }

        public E getData() {
            return data;
        }

        public int compareTo(Wrapper<E> o) {
            if (data.compareTo(o.data) == 0)
                return (int) (number - o.number);
            return (data.compareTo(o.data));
        }
    }

    // Returns an iterator of the objects in the PQ, in no particular
    // order.
    public Iterator<E> iterator() {
        return new IteratorHelper();
    }

    private class IteratorHelper implements Iterator<E> {
        int counter;
        int modCounter;

        public IteratorHelper() {
            counter = 0;
            modCounter = modificationCounter;
        }
        @Override
        public boolean hasNext() {
            if (modCounter != modificationCounter)
                throw new ConcurrentModificationException();
            return counter < size();
        }
        public E next() {
            if(!hasNext())
                throw new NoSuchElementException();
            return array[counter++].getData();
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
