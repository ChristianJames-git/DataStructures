/**
 *  Program 4b
 *  ///////////
 *  CS-310
 *  26 April 2020
 *  @author  Christian James cssc1229
 **/
package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BinarySearchTree<K extends Comparable<K>,V> implements DictionaryADT<K,V> {
    private Tnode<K,V> root;
    private int currSize;
    private int modificationCounter;

    public BinarySearchTree () {
        clear();
    }

    public boolean insert (K key, V value) {
        return add(key, value);
    } //this is dumb
    public boolean put (K key, V value) {
        return add(key, value);
    } //this is really dumb
    public boolean add (K key, V value) {
        if (root == null) {
            root = new Tnode<K, V>(key, value, null);
            currSize++;
            modificationCounter++;
            return true;
        }
        else
            return add(key, value, root);
    } //Professor Kraft's Lecture

    private boolean add (K key, V value, Tnode<K,V> currNode) {
        if((key).compareTo(currNode.key) == 0)
            return false;
        if((key).compareTo(currNode.key) > 0) {
            if(currNode.right == null) {
                currNode.right = new Tnode<K, V>(key, value, currNode);
                currSize++;
                modificationCounter++;
                return true;
            }
            else
                return add(key, value, currNode.right);
        }
        if (currNode.left == null) {
            currNode.left = new Tnode<K, V>(key, value, currNode);
            currSize++;
            modificationCounter++;
            return true;
        }
        else
            return add(key, value, currNode.left);
    } //Professor Kraft's Lecture but added upon

    public boolean delete(K key) { //The worst method I have ever written. Fully from scratch since no resources worked
        boolean deleted = deleteKey(key);
        if (deleted) {
            currSize--;
            modificationCounter++;
        }
        return deleted;
    }

    private boolean deleteKey(K key) {
        Tnode<K, V> toDelete = findValueOrKey(key, null, root);
        if (toDelete == null)
            return false;
        if (toDelete == root) {
            if (root.left == null && root.right == null) {
                root = null;
                return true;
            }
        }
        if (toDelete.left == null && toDelete.right == null)
            return deleteLeaf(toDelete);
        else if (toDelete.left != null && toDelete.right != null)
            return deleteTwoChildren(toDelete);
        else
            return deleteOneChild(toDelete);
    }

    private boolean deleteLeaf(Tnode<K,V> toDelete) {
        if (toDelete.parent.left == toDelete)
            toDelete.parent.left = null;
        if (toDelete.parent.right == toDelete)
            toDelete.parent.right = null;
        return true;
    }

    private boolean deleteOneChild(Tnode<K,V> toDelete) {
        if (toDelete.left != null) {
            if (toDelete == root) {
                root = toDelete.left;
                root.parent = null;
                return true;
            }
            if (toDelete.parent.left == toDelete) {
                toDelete.parent.left = toDelete.left;
            } else {
                toDelete.parent.right = toDelete.left;
            }
            if (toDelete.right != null)
                toDelete.right.parent = toDelete.parent;
            else
                toDelete.left.parent = toDelete.parent;
        } else {
            if (toDelete == root) {
                root = toDelete.right;
                root.parent = null;
                return true;
            }
            if (toDelete.parent.left == toDelete) {
                toDelete.parent.left = toDelete.right;
            } else {
                toDelete.parent.right = toDelete.right;
            }
            toDelete.right.parent = toDelete.parent;
        }
        return true;
    }

    private boolean deleteTwoChildren(Tnode<K,V> toDelete) { //4-5 hours of debugging and rewriting finally DONE
        if(toDelete == root && root.right == null) {
                root = root.left;
                root.parent = null;
                return true;
        }
        Tnode<K,V> replaceDeleted = findMinimum(toDelete.right);
        if (replaceDeleted.parent != toDelete) {
            replaceDeleted.parent.left = replaceDeleted.right;
            if (replaceDeleted.right != null)
                replaceDeleted.right.parent = replaceDeleted.parent;
            replaceDeleted.right = toDelete.right;
            replaceDeleted.right.parent = replaceDeleted;
        }
        replaceDeleted.left = toDelete.left;
        replaceDeleted.left.parent = replaceDeleted;
        if (toDelete == root) {
            root = replaceDeleted;
            replaceDeleted.parent = null;
        }
        else {
            if (toDelete.parent.left == toDelete) {
                toDelete.parent.left = replaceDeleted;
                replaceDeleted.parent = toDelete.parent;
            } else {
                toDelete.parent.right = replaceDeleted;
                replaceDeleted.parent = toDelete.parent;
            }
        }
        return true;
    }

    private Tnode<K,V> findMinimum(Tnode<K,V> currNode) {
        while (currNode.left != null)
            currNode = currNode.left;
        return currNode;
    }

    public V getValue(K key) {
        Tnode<K,V> node = findValueOrKey(key, null, root);
        if (node != null)
            return node.value;
        else
            return null;
    }

    public K getKey(V value) {
        Tnode<K,V> node = findValueOrKey(null, value, root);
        if (node != null)
            return node.key;
        else
            return null;
    }

    public int size() {
        return currSize;
    }

    public boolean isFull() {
        return false;
    }

    public boolean isEmpty() {
        return currSize == 0;
    }

    public void clear() {
        root = null;
        currSize = 0;
        modificationCounter = 0;
    }

//    public void printTree() {
//        printTree(root);
//        System.out.println();
//    }
//
//    private void printTree(Tnode<K,V> currNode) {
//        if (currNode == null) {
//            return;
//        }
//        if(currNode.left != null)
//            printTree(currNode.left);
//        System.out.print(currNode + ", ");
//        if(currNode.right != null)
//            printTree(currNode.right);
//    }
    private Tnode<K,V> findValueOrKey(K key, V value, Tnode<K,V> currNode) {
        if (currNode == null)
            return null;
        if (value == null) {
            int compareKey = (key).compareTo(currNode.key);
            if (compareKey == 0)
                return currNode;
            if (compareKey > 0)
                return findValueOrKey(key, null, currNode.right);
            return findValueOrKey(key, null, currNode.left);
        }
        boolean compareValue = value.equals(currNode.value);
        if (compareValue)
            return currNode;
        Tnode<K,V> keyHolder = findValueOrKey(null, value, currNode.left);
        if (keyHolder != null)
            return keyHolder;
        return findValueOrKey(null, value, currNode.right);
    }

    private Tnode<K,V> findNextMinimum(Tnode<K,V> currMin, int counter, int location) {
        if (currMin == null) {
            return null;
        }
        if (currMin.left != null)
            return findNextMinimum(currMin.left, counter, location);
        if (counter == location)
            return currMin;
        else
            location++;
        return findNextMinimum(currMin.right, counter, location);
    }

    public Iterator<K> keys() {
        return new KeyIteratorHelper();
    }

    public Iterator<V> values() {
        return new ValueIteratorHelper();
    }

    private class KeyIteratorHelper extends IteratorHelper<K> {
        public KeyIteratorHelper () {
            super();
        }

        public K next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return findNextMinimum(root, counter++, 0).key;
        }
    }

    private class ValueIteratorHelper extends IteratorHelper<V> {
        public ValueIteratorHelper () {
            super();
        }

        public V next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return findNextMinimum(root, counter++, 0).value;
        }
    }

    private abstract class IteratorHelper<E> implements Iterator<E> {
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

        public abstract E next();

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static class Tnode<K extends Comparable<K>, V> {
        K key;
        V value;
        Tnode<K,V> left, right, parent;
        public Tnode(K key, V value, Tnode<K,V> parent) {
            this.key = key;
            this.value = value;
            right = left = null;
            this.parent = parent;
        }

//        public String toString() {
//            K leftKey = null, rightKey = null, parentKey = null;
//            if (left != null)
//                leftKey = left.key;
//            if (right != null)
//                rightKey = right.key;
//            if (parent != null)
//                parentKey = parent.key;
//            return "(" + key + "," + parentKey + "," + leftKey + "," + rightKey + ")";
//        }
    } //Professor Kraft's Lecture
}
