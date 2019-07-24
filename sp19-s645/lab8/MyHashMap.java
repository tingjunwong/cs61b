import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Iterator;


public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int INITIALSIZE = 16;
    private static final double MAX_LF = 0.75;
    private Set<K> hs;
    private ArrayList<Entry>[] map;
    private int size;
    private double loadFactor;
    private HashSet<K> keys;
    @Override
    public void clear() {
        this.size = 0;

        this.map = (ArrayList<Entry>[]) new ArrayList[INITIALSIZE];
        this.keys = new HashSet<K>();
    }
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    public V get(K key) {
        Entry e = getEntry(key);
        if (e == null) {
            return null;
        }
        return e.value;
    }
    /* @source reference from  TA  Jihan Yin 's Lab 8 solution PPT slides*/
    private Entry getEntry(K key) {
        int index = hash(key);
        ArrayList<Entry> bucket = this.map[index];
        if (bucket != null) {
            for (Entry entry : bucket) {
                if (entry.key.equals(key)) {
                    return entry;
                }
            }
        }
        return null;
    }
    private class Entry {
        private K key;
        private V value;
        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    private int hash(K key) {
        return hash(key, this.map.length);
    }
    private int hash(K key, int mapSize) {
        return Math.floorMod(key.hashCode(), mapSize);
    }
    /* @source reference from  TA  Jihan Yin 's Lab 8 solution PPT slides*/
    public void put(K key, V value) {
        Entry e = getEntry(key);
        if (e != null) {
            e.value = value;
            return;
        }
        if (((double) this.size) / this.map.length > this.loadFactor) {
            this.rehash(this.map.length * 2);
        }

        this.size++;
        this.keys.add(key);
        int index = hash(key);
        ArrayList<Entry> bucket = this.map[index];
        if (bucket == null) {
            bucket = new ArrayList<Entry>();
            map[index] = bucket;
        }
        bucket.add(new Entry(key, value));
    }
    /* @source reference from  TA  Jihan Yin 's Lab 8 solution PPT slides*/
    private void rehash(int targetSize) {
        ArrayList<Entry>[] newMap = (ArrayList<Entry>[]) new ArrayList[targetSize];
        for (K key : this.keys) {
            int index = hash(key, newMap.length);
            ArrayList<Entry> bucket = newMap[index];
            if (bucket == null) {
                bucket = new ArrayList<Entry>();
                newMap[index] = bucket;
            }
            bucket.add(getEntry(key));
        }
        this.map = newMap;
    }
    public MyHashMap() {
       this(INITIALSIZE, MAX_LF);
    }
    public MyHashMap(int init) {
        this(init, MAX_LF);
    }
    public MyHashMap(int init, double loadFactor) {
        this.loadFactor = loadFactor;
        this.map = (ArrayList<Entry>[]) new ArrayList[init];
        this.size = 0;
        this.keys = new HashSet<K>();
    }



    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Set<K> keySet() {
        return hs;

    }
    @Override
    public Iterator<K> iterator() {
        return this.keys.iterator();
    }
    @Override
    public int size() {
        return size;

    }
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }





}




