/**
 * @author Josh Hug
 *  ref = "https://sp19.datastructur.es/materials/proj/proj1a/proj1a"
 *  References:
 *         Gitbook - Chapter 2.5: Memory Performance
 *         https://github.com/azaar/CS61B/blob/master/proj1/ArrayDeque.java
 *         discussion with classmates about the solution of the arraylist.
 */



public class ArrayDeque<T> {
    private T[] wtj;
    private int size;
    private int nextFirst;
    private int nextLast;
    private double rfactor;

    public ArrayDeque() {
        wtj = (T[]) new Object[8];

        nextLast = 1;
    }


    public ArrayDeque(ArrayDeque other) {
        wtj = (T[]) new Object[8];
        size = 0;

        nextFirst = 0;
        nextLast = 1;
        int i = 0;
        while (i < other.size()) {
            addLast((T) other.get(i));
            i++;
        }
    }


    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    private double rfactor() {
        rfactor = (double) size / (double) wtj.length;
        return rfactor;
    }

    public void printDeque() {
        if (isEmpty()) {
            System.out.println(" ");
            return;
        }
        int i = 0;
        while (i < size()) {
            System.out.print(get(i) + " ");
            i += 1;
        }
        System.out.println(" ");
    }


    public T removeFirst() {
        if (size() == 0) {
            return null;
        }
        T b = wtj[(nextFirst + wtj.length * 99 + 1) % wtj.length];
        wtj[(nextFirst + wtj.length * 99 + 1) % wtj.length] = null;
        nextFirst++;
        size--;
        remove();

        return b;
    }


    public void addFirst(T item) {
        resize();
        wtj[(nextFirst + wtj.length * 99) % wtj.length] = item;
        add();
        nextFirst--;
    }

    public void addLast(T item) {
        resize();
        wtj[(nextLast + wtj.length * 99) % wtj.length] = item;
        add();
        nextLast++;
    }


    public T removeLast() {
        if (size() == 0) {
            return null;
        }
        T a = wtj[(nextLast + wtj.length * 99 - 1) % wtj.length];
        wtj[(nextLast + wtj.length * 99 - 1) % wtj.length] = null;
        nextLast--;
        size--;
        remove();

        return a;
    }

    private boolean shouldresize() {
        return (rfactor < 0.25 && (wtj.length >= 16));
    }


    private void resize() {
        if (shouldresize() || (size == wtj.length)) {
            int cap = wtj.length;
            if (shouldresize()) {
                cap = cap / 2;
            } else {
                cap = cap * 2;
            }
            T[] newwtj = (T[]) new Object[cap];

            int i = 0;
            while (i < size()) {
                newwtj[i] = get(i);
                i++;
            }

            nextFirst = -1;
            nextLast = size();
            wtj = newwtj;
        }
    }

    private void remove() {
        rfactor();
        resize();
    }

    private void add() {
        size++;
        rfactor();
    }

    public T get(int index) {
        if (index > (size() - 1) || index < 0) {
            return null;
        } else {
            return wtj[(nextFirst + index + 1 + wtj.length * 99) % wtj.length];
        }
    }

//    public static void main(String[] args) {
//        ArrayDeque<Integer> l = new ArrayDeque<>();
//        l.addFirst(0);
//        System.out.println(l.get(0));
//        l.addFirst(3);
//        l.addFirst(4);
//        l.addFirst(5);
//        l.addFirst(6);
//        l.removeFirst();
//        l.addLast(8);
//        l.addFirst(9);
//        l.removeLast();
//        l.removeLast();
//        l.addFirst(12);
//        l.removeLast();
//        System.out.println(l.get(0));
//        l.addFirst(16);
//        l.addLast(17);
//        l.addFirst(18);
//        l.addFirst(19);
//        l.removeLast();
//        l.addFirst(21);
//         l.addFirst(0);
//         l.removeFirst();

//         l.addLast(2);
//         l.addFirst(1);
//         l.addFirst(0);
//         l.addLast(3);
//         l.addLast(4);
//         l.addLast(5);
//         l.addLast(6);
//         l.addLast(7);
//         l.addLast(8);
//         l.addLast(9);
//         l.addLast(10);
//         l.addFirst(-1);
//         l.removeLast();
//         l.removeLast();
//         l.removeFirst();
//         l.printDeque();
//         System.out.println(l.get(0));
//         System.out.println(l.get(1));
//         System.out.println(l.size());
//     }
//    }
}
