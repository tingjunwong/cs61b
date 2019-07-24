package bearmaps;


import org.junit.Test;






public class ArrayHeapMinPQTest {






    @Test
    public void removeSmallestTest() {
        ArrayHeapMinPQ<Integer> b = new ArrayHeapMinPQ<>();
        b.add(0, 9);
        b.add(8, 3);
        b.add(7, 1);
        b.add(4, 5);
        b.add(4, 8);
        b.removeSmallest();
        b.removeSmallest();
    }
    @Test
    public void addTest() {
        ArrayHeapMinPQ<Integer> b = new ArrayHeapMinPQ<>();
        b.add(0, 9);
        b.add(8, 3);
        b.add(7, 1);
        b.add(4, 5);
        b.add(4, 8);
    }
    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<Integer> b = new ArrayHeapMinPQ<>();
        b.add(0, 9);
        b.add(8, 3);
        b.add(7, 1);
        b.add(4, 5);
        b.add(4, 8);
        b.changePriority(8, 9);
        b.changePriority(1, 2);
    }


}


