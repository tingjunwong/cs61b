public class MinHeap<E extends Comparable<E>> {//存储任意的类型
    //   所以这些节点必须具有可比较性
    private Array<E> data;//承载E

    public MinHeap(int capacity) {
        data = new Array<>(capacity);

    }

    public MinHeap() {   
        data = new Array<>();
    }

    public int size() {
        return data.getSize();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    private int parent(int index) {
//
        if (index == 0) {
            throw new IllegalArgumentException();
        }
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
//

        return index * 2 + 1;
    }

    private int rightChild(int index) {
//

        return index * 2 + 2;
    }

    public void add(E e) {
        data.addLast(e);
        siftUp(data.getSize() - 1);  //传入数组最后一个元素
//相当于排序
    }

    private void siftUp(int k) {
//        while(k > 0 && data[parent(k)] > data[k]) {
        while (k > 0 && data.get(parent(k)).compareTo(data.get(k)) > 0) {
//           当K = 0也就是到达根节点  或者所有的父亲节点比他儿子都要小
//           a = data[parent(k)];
//           data[parent(k)] = data[k];
//           data[k] = a;
            data.swap(parent(k), k);
            k = parent(k);
//删除元素  和sift down
//            添加元素 和sift up

        }

    }

    public E findMin() {
        if (data.getSize() == 0) {
            throw new IllegalArgumentException();
        }
        return data.get(0);
    }

    public E extractMin() {
        E ret = findMin();
        data.swap(0, data.getSize() - 1);
        data.removeLast();
        siftDown(0);
        return ret;
    }

    public E replace(E a) {
        E ret = findMin();
        data.set(0, a);
        siftDown(0);


        return ret;
    }

    private void siftDown(int k) {
        while (leftChild(k) < data.getSize()) {
//            如果有左孩子
//            通过了上面的验证才有意义
            //这时候正式给左孩子个名字
            int j = leftChild(k);
            if (j + 1 < data.getSize() && data.get(j + 1).compareTo(data.get(j)) < 0) {
//如果有右孩子且右孩子比左孩子小 此时j 为右孩子
                j = rightChild(k);
            }
//data[j] 是data[leftChild(k)]和data[rightChild(k)]两个节点值的最小值
            if (data.get(k).compareTo(data.get(j)) <= 0) {
                break;
            }
//   爸爸比右孩子和左孩子都小  此时符合最小堆性质
//           如果没有满足上面的if  即需要交换  需要下沉
            data.swap(k, j);
            k = j;
//   看对于新的爸爸k来说  是否仍然需要继续下沉


        }
    }

    //    用户传来一个数组
    public MinHeap(E[] arr) {
        data = new Array<>(arr);//根据用户传来的信息生成新的数组
    }
}
//    add和 extractMax 的时间复杂度 是O（logn)
//因为完全二叉树，堆永远不会退化成链表





