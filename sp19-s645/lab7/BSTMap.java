

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private class Node {
        private K key;
        private V value;
        private Node(K k, V v) {
            key = k;
            value = v;
        }
        private Node left;
        private Node right;
    }
    private Node root;
    private int size;
    public BSTMap() {
        this.clear();
    }
    @Override
    public void clear() {
        root = null;
        size = 0;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }


    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
    @Override
    public void put(K key, V value) {

        root = putHelper(key, value, root);
    }
    @Override
    public V get(K key) {

        return getHelper(key, root);
    }
    /* @source reference from  https://algs4.cs.princeton.edu/
    *32bst/BST.java.html
    * and https://github.com/Zhenye-Na/cs61b-ucb/
    * blob/master/lab/lab9/lab9/BSTMap.java*/
    private V getHelper(K key, Node p) {

        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (p == null) {
            return null;
        } else {

            int cmp = key.compareTo(p.key);
            if (cmp < 0) {
                return getHelper(key, p.left);
            } else if (cmp > 0) {
                return getHelper(key, p.right);
            } else {
                return p.value;
            }
        }
    }
    /* @source reference from  https://algs4.cs.princeton.edu/
    *32bst/BST.java.html
    * https://github.com/Zhenye-Na/cs61b-ucb/blob/
    * master/lab/lab9/lab9/BSTMap.java*/
    private Node putHelper(K key, V value, Node p) {

        if (p == null) {
            size += 1;
            p = new Node(key, value);
            return p;
        } else {

            int cmp = key.compareTo(p.key);
            if (cmp < 0) {
                p.left = putHelper(key, value, p.left);
            } else if (cmp > 0) {
                p.right = putHelper(key, value, p.right);
            } else {
                p.value = value;
            }
            return p;

        }
    }









}
