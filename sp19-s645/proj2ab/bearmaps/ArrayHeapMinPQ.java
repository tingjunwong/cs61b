package bearmaps;

import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.HashMap;



public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<PriorityNode> data;

    private HashMap<T, Integer> map = new HashMap<>();

    public ArrayHeapMinPQ() {
        data = new ArrayList<>();

    }

    @Override
    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public boolean contains(T item) {


        return map.containsKey(item);

    }


    @Override
    public void add(T item, double priority) {
        if (!contains(item)) {

            data.add(new PriorityNode(item, priority));
            map.put(item, data.size() - 1);


            siftUp(data.size() - 1);


        } else {
            throw new IllegalArgumentException();
        }
    }

    public T getSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return data.get(0).item;
        }
    }

    public T removeSmallest() {
        T a = getSmallest();
        swap(0, data.size() - 1);

        data.remove(data.size() - 1);

        map.put(data.get(data.size() - 1).item, 0);
        map.remove(data.get(0).item);
        if (data.size() == 0) {
            return a;
        }
        siftDown(0);
        return a;
    }

    public void changePriority(T item, double priority) {
        if (contains(item)) {
            Integer index = map.get(item);
            if (index == null) {
                throw new NoSuchElementException();
            }
            double prePriority = data.get(index).getPriority();
            data.get(index).setPriority(priority);
            if (priority > prePriority) {

                siftDown(index);
            } else if (priority < prePriority) {
                siftUp(index);
            }
        } else {
            throw new NoSuchElementException();
        }

    }

    public int parent(int index) {
        if (index == 0 || index > data.size() - 1) {
            throw new IllegalArgumentException();
        } else {
            return ((index - 1) / 2);
        }
    }

    public int leftChild(int index) {
        return index * 2 + 1;
    }

    private int rightChild(int index) {
        return index * 2 + 2;
    }

    public void siftUp(int k) {    //index


        while (k > 0 && data.get(parent(k)).priority > data.get(k).priority) {


            swap(parent(k), k);
            k = parent(k);


        }

    }

    public void swap(int i, int j) {
        if (i == j) {
            return;
        }
        if (i < 0 || i >= data.size() || j < 0 || j >= data.size()) {
            throw new IllegalArgumentException();
        }
        double t = data.get(i).priority;
        data.get(i).priority = data.get(j).priority;
        data.get(j).priority = t;
        map.put(data.get(i).item, j);
        map.put(data.get(j).item, i);

    }

    private void siftDown(int k) {
        while (leftChild(k) < data.size()) {
//            如果有左孩子
//            通过了上面的验证才有意义
            //这时候正式给左孩子个名字
            int j = leftChild(k);
            if (j + 1 < data.size() && data.get(j + 1).priority < data.get(j).priority) {
//如果有右孩子且右孩子比左孩子小 此时j 为右孩子
                j = rightChild(k);
            }
//data[j] 是data[leftChild(k)]和data[rightChild(k)]两个节点值的最小值
            if (data.get(k).priority <= data.get(j).priority) {
                break;
            }
//   爸爸比右孩子和左孩子都大  此时符合最大堆性质
//           如果没有满足上面的if  即需要交换  需要下沉
            swap(k, j);
            k = j;
//   看对于新的爸爸k来说  是否仍然需要继续下沉


        }
    }


    private class PriorityNode implements Comparable<PriorityNode> {
        T item;
        double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}





