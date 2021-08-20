/**
 *  Program 4c
 *  ///////////
 *  CS-310
 *  26 April 2020
 *  @author  Christian James cssc1229
 **/
package data_structures;
import java.util.Iterator;
import java.util.TreeMap;

//USE LECTURE FROM 4/16

public class BalancedTreeDictionary<K extends Comparable<K>,V> implements DictionaryADT<K,V> {
    private TreeMap<K,V> mapRB;

    public BalancedTreeDictionary() {
        mapRB = new TreeMap<>();
    }

    public boolean add(K key, V value) {
        return put(key, value);
    } //done

    public boolean insert(K key, V value) {
        return put(key,value);
    } //done

    public boolean put(K key, V value) {
        return mapRB.put(key, value) == null;
    }

    public boolean delete(K key) {
        return mapRB.remove(key) != null;
    }

    public V getValue(K key) {
        return mapRB.get(key);
    }

    public K getKey(V value) {
        java.util.Set<K> keys = mapRB.keySet();
        for(K key : keys){
            if(mapRB.get(key).equals(value))
                return key;
        }
        return null;
    }

    public int size() {
        return mapRB.size();
    } //done

    public boolean isFull() {
        return false;
    } //done

    public boolean isEmpty() {
        return mapRB.size() == 0;
    } //done

    public void clear() {
        mapRB.clear();
    } //done

    public Iterator<K> keys() {
        return mapRB.keySet().iterator();
    }

    public Iterator<V> values() {
        return mapRB.values().iterator();
    }
}
