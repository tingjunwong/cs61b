package es.datastructur.synthesizer;
import java.util.Iterator;
public interface BoundedQueue<T> extends Iterable<T> {


    Iterator<T> iterator();
    int capacity();
    int fillCount();
    void enqueue(T x);
    T dequeue();
    boolean equals(Object o);
    T peek();
    default boolean isEmpty() {
        return fillCount() == 0;
    }
    default boolean isFull() {
        return fillCount() == capacity();
    }

}





