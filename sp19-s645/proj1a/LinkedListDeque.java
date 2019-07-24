
/**
        * @author Josh Hug
        *  ref = "https://sp19.datastructur.es/materials/proj/proj1a/proj1a"
        *  References:
        *         Gitbook - Chapter 2.5: Memory Performance
 *       https://github.com/aawani/61B-Spring-2018/master/Projects/LinkedListDeque.java
        */

public class LinkedListDeque<T> {
    private  class IntNode {
        private T item;
        private IntNode next;
        private IntNode prev;

        IntNode(T i, IntNode n, IntNode p) {
            item = i;
            next = n;
            prev = p;

        }

    }

    private IntNode sentinel;

    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }
    public LinkedListDeque(LinkedListDeque other) {
        size = 0;
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }



    public void addFirst(T item) {

        sentinel.next = new IntNode(item, sentinel.next, sentinel);
        sentinel.next.next.prev = sentinel.next;
        size = size + 1;
    }





    public void addLast(T item) {
        sentinel.prev.next = new IntNode(item, sentinel, sentinel.prev);
        sentinel.prev = sentinel.prev.next;
        size = size + 1;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        IntNode first = sentinel.next;

        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size = size -  1;
        return first.item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        IntNode last = sentinel.prev;

        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size = size - 1;
        return last.item;
    }

    public void printDeque() {
        if (isEmpty()) {
            System.out.println("null");
            return;
        }
        IntNode p = sentinel;
        while (p.next != sentinel) {
            System.out.print(p.next.item + " ");
            p = p.next;
        }
    }


    public T get(int index) {

        IntNode p = sentinel.next;


        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;

    }



//    public T getRecursive(int index) {
//        if (index == 0) {
//            return sentinel.next.item;
//        } else {
//            return getRecursive(index - 1);
//        }
//    }
    public T getRecursive(int index) {
        int length = size;
        if (index > length - 1) {
            return null;
        } else {
            return reHelp(sentinel.next, index);
        }
    }


    private T reHelp(IntNode wtj, int i) {
        if (i == 0) {
            return wtj.item;
        } else {
            return reHelp(wtj.next, i - 1);
        }
    }

    public int size() {
        return size;
    }

}
