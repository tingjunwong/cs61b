package byow.proj2ab;



import java.util.HashMap;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private class Node<T> {             // A Node to store item and priority
        private T item;
        private double priority;
        private int index;
        Node(T item, double priority, int index) {
            this.item = item;
            this.priority = priority;
            this.index = index;
        }
    }

    private ArrayList<Node> pq;         // priority queue implemented by ArrayList
    private int size = 0;
    private HashMap<T, Node> items;           // A HashSet to store items

    public ArrayHeapMinPQ() {
        pq = new ArrayList<Node>();
        pq.add(null);                   // Leave spot 0 empty to compute children and parents nicer
        items = new HashMap<T, Node>();
    }

    private int leftChild(int k) {
        return k * 2;
    }
    private int rightChild(int k) {     // helper functions help get Children/parents index
        return k * 2 + 1;
    }
    private int parent(int k) {
        return k / 2;
    }
    private void swap(Node n1, Node n2) {
        T tempItem = (T) n1.item;
        double tempPriority = n1.priority;
        n1.item = n2.item;
        n1.priority = n2.priority;
        n2.item = tempItem;
        n2.priority = tempPriority;
        items.put((T) n1.item, n1);
        items.put((T) n2.item, n2);

    }
    private void swim(int index) {
        if (parent(index) == 0) {
            return;
        }
        Node n1 = pq.get(index);
        Node n2 = pq.get(parent(index));
        if (n2.priority > n1.priority) {
            swap(n1, n2);
            swim(parent(index));
        } else {
            return;
        }
    }
    private void sink(int index) {
        if (leftChild(index) > size && rightChild(index) > size) {
            return;             // if both child out of bound, exit
        }
        if (leftChild(index) > size && rightChild(index) <= size) {
            Node n1 = pq.get(index);    //if only left child out of bound, examine right child
            Node n1rc = pq.get(rightChild(index));
            if (n1.priority > n1rc.priority) {
                swap(n1, n1rc);
                sink(rightChild(index));
            } else {
                return;
            }
        } else if (leftChild(index) <= size && rightChild(index) > size) {
            Node n1 = pq.get(index);    // if only right child out of bound, examine left child
            Node n1lc = pq.get(leftChild(index));
            if (n1.priority > n1lc.priority) {
                swap(n1, n1lc);
                sink(leftChild(index));
            } else {
                return;
            }
        } else {                     // none child out of bound
            Node n1 = pq.get(index);
            Node n1lc = pq.get(leftChild(index));
            Node n1rc = pq.get(rightChild(index));
            if (n1.priority <= n1lc.priority && n1.priority <= n1rc.priority) {
                return;
            } else if (n1.priority > n1lc.priority && n1.priority <= n1rc.priority) {
                swap(n1, n1lc);
                sink(leftChild(index));
            } else if (n1.priority <= n1lc.priority && n1.priority > n1rc.priority) {
                swap(n1, n1rc);
                sink(rightChild(index));
            } else if (n1.priority > n1lc.priority && n1.priority > n1rc.priority) {
                if (n1lc.priority < n1rc.priority) {
                    swap(n1, n1lc);
                    sink(leftChild(index));
                } else {
                    swap(n1, n1rc);
                    sink(rightChild(index));
                }
            }
        }



    }
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        Node n = new Node(item, priority, size + 1);
        items.put(item, n);
        pq.add(n);
        size++;
        swim(size);
    }

    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (pq.get(1) == null) {
            throw new NoSuchElementException();
        }
        return (T) pq.get(1).item;
    }

    @Override
    public T removeSmallest() {
        T smallest = getSmallest();
        swap(pq.get(1), pq.get(size));
        pq.remove(size);
        size--;
        items.remove(smallest);
        sink(1);
        return smallest;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        Node n = items.get(item);
        n.priority = priority;
        sink(n.index);
        swim(n.index);
    }

//    public static void main(String[] args) {
//        ArrayHeapMinPQ<Double> a = new ArrayHeapMinPQ<>();
//        NaiveMinPQ<Double> b = new NaiveMinPQ<>();
//        int quntanty = 1000000;
//        Random r = new Random();
//
//        while (quntanty <= 20000000)
//        {       a = new ArrayHeapMinPQ<>();
//                b = new NaiveMinPQ<>();
//        //Stopwatch sw = new Stopwatch();
//        for (int i = 1000000000; i < 1000010000; i += 1) {
//            a.add((double) i, i);
//        }
//            for (int i = 0; i < quntanty; i++) {
//                a.add((double) i, i);
//            }
////            for (int i = 0; i < quntanty; i++) {
////                b.add((double) i, i);
////            }
////        System.out.println("Total time elapsed: " + sw.elapsedTime() +  " seconds.");
//
//
////            Stopwatch sw3 = new Stopwatch();
////
////            for (int i = 0; i < quntanty; i++) {
////                b.removeSmallest();
////            }
////            System.out.println("naive Total time elapsed: " + sw3.elapsedTime() +  " seconds.");
//
//
//
//        Stopwatch sw1 = new Stopwatch();
//
//        for (int i = 0; i < quntanty; i++) {
//            a.removeSmallest();
//        }
//        System.out.println("Total time elapsed: " + sw1.elapsedTime() +  " seconds.");
//        quntanty += 1000000;
//}
//    }
}
