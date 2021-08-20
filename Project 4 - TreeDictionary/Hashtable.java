/**
 *  Program 4a
 *  ///////////
 *  CS-310
 *  26 April 2020
 *  @author  Christian James cssc1229
 **/
package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class Hashtable<K extends Comparable<K>, V> implements DictionaryADT<K, V> {
    private LinkedListDS<DictionaryNode<K, V>>[] list;
    private int currSize;
    private int tableSize;
    private long modificationCounter;

    public void printTree() {
    }

    public Hashtable(int size) {
        tableSize = size;
        modificationCounter = 0;
        clear();
    } //maybe done

    public boolean add(K key, V value) { return insert(key, value); } //done

    public boolean put(K key, V value) {
        return insert(key, value);
    } //done

    public boolean insert(K key, V value) {
        DictionaryNode<K,V> newNode = new DictionaryNode<>(key, value);
        int index = findHashIndex(key);
        if (list[index].contains(newNode))
            return false;
        list[index].addLast(newNode);
        currSize++;
        modificationCounter++;
        return true;
    } //done

    private int findHashIndex(K key) {
        return key.hashCode() % tableSize;
    } //done

    public boolean delete(K key) {
        DictionaryNode<K,V> toDelete = new DictionaryNode<>(key, null);
        int location = findHashIndex(key);
        boolean deleted = list[location].remove(toDelete);
        if (deleted)
            currSize--;
        modificationCounter++;
        return deleted;
    }

    public V get(K key) {
        return getValue(key);
    } //done

    public V getValue(K key) {
        DictionaryNode<K,V> keyWrapper = new DictionaryNode<>(key, null);
        int location = findHashIndex(key);
        DictionaryNode<K,V> tempDictionaryNode = list[location].search(keyWrapper);
        if(tempDictionaryNode != null)
            return tempDictionaryNode.value;
        return null;
    } //done

    public K getKey(V value) {
        DictionaryNode<K,V> valueWrapper = new DictionaryNode<>(null, value);
        DictionaryNode<K,V> tempDictionaryNode;
        for(int i = 0; i < tableSize; i++) {
            tempDictionaryNode = list[i].search(valueWrapper);
            if(tempDictionaryNode != null)
                return tempDictionaryNode.key;
        }
        return null;
    } //done

    public int size() {
        return currSize;
    } //done

    public boolean isFull() {
        return false;
    } //done

    public boolean isEmpty() {
        return currSize == 0;
    } //done

    public void clear() {
        currSize = 0;
        list = new LinkedListDS[tableSize];
        for(int i = 0; i < tableSize; i++)
            list[i] = new LinkedListDS<>();
    } //done

    public Iterator<K> keys() {
        return new KeyIteratorHelper();
    } //done

    public Iterator<V> values() {
        return new ValueIteratorHelper();
    } //done

    private class DictionaryNode<K extends Comparable<K>, V> implements Comparable<DictionaryNode<K, V>> { //Header from textbook
        private K key;
        private V value;

        public DictionaryNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public int compareTo(DictionaryNode<K, V> node) {
            if (node.key == null) {
                if(value != null && value.equals(node.value))
                    return 0;
                return 1;
            }
            return key.compareTo(node.key);
        }
    } //maybe done

    private class KeyIteratorHelper extends IteratorHelper<K> { //from book
        public KeyIteratorHelper () {
            super();
        }

        public K next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return (K) nodes[idx++].key;
        }
    } //maybe done

    private class ValueIteratorHelper extends IteratorHelper<V> { //from book
        public ValueIteratorHelper() {
            super();
        }

        public V next() {
            return (V) nodes[idx++].value;
        }
    } //maybe done

    private abstract class IteratorHelper<E> implements Iterator<E> {
        protected DictionaryNode<K,V>[] nodes;
        protected int idx;
        long modCounter;

        public IteratorHelper() {
            nodes = new DictionaryNode[currSize];
            idx = 0;
            modCounter = modificationCounter;
            int j = 0;
            for (int i = 0; i < tableSize; i++)
                for (DictionaryNode<K,V> n : list[i])
                    nodes[j++] = n;
        }

        public boolean hasNext() {
            if (modCounter != modificationCounter)
                throw new ConcurrentModificationException();
            return idx < currSize;
        }

        public abstract E next();

        public void remove() {
            throw new UnsupportedOperationException();
        }
    } //from textbook
}
