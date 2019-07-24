package es.datastructur.synthesizer;

import java.util.Iterator;


public class ArrayRingBuffer<T> implements BoundedQueue<T>  {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    private int capacity;



    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }
    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    public void enqueue(T x) {

        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        else {
        rb[last] = x;
        last += 1;
        if (last == capacity) {
            last = 0;
        }
    }

    fillCount += 1;

}


    public T dequeue() {

        T y;
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }

        y = rb[first];
        rb[first] = null;
        first += 1;

        if (first == this.capacity) {
            first = 0;
        }


    fillCount -= 1;
        return y;

}





    public T peek() {

        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];

    }

    public boolean equals(Object o) {
        if ((o == null)||(this.getClass() != o.getClass())) {
            return false;
        }
        else {
            ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
            if(this.fillCount() != other.fillCount()) {
                return false;

            }
            int i = first;
            while (i < this.fillCount()) {
                if (!other.rb[i].equals(this.rb[i])) {
                    return false;
                }
                i = i + 1;
            }
            return true;
        }
    }


    @Override
    public Iterator<T> iterator() {
        return new WtjIterator();
    }

    private class WtjIterator implements Iterator<T> {
        private int seer = 0;

        @Override
        public boolean hasNext() {
            return seer == fillCount();
        }

        @Override
        public T next() {
            T next =  rb[seer];
            seer += 1;
            return next;
        }
    }



}





