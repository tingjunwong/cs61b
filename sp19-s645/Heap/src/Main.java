import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int n = 100000;
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
//        maxheap这个数据结构 装integer
        Random random = new Random();
        for (int i = 0; i < n; i++) { // 随机一百万个数
            maxHeap.add(random.nextInt(Integer.MAX_VALUE));
        }  // 现在堆中承载了100000个随机数  但是排成了最大堆
        int[] arr = new int[n];
        for(int i = 0; i < n; i++) {
            arr[i] = maxHeap.extractMax();
//            很明显 这个数组是从大到小排序的
        }
        for(int i = 1;i < n; i++) {
            if(arr[i-1] < arr[i]) {
                throw new IllegalArgumentException();
            }
            //验证相邻的两个数 大小 从而验证  数组是从大到小排列的
        }
        System.out.println("success");
    }

}
//使用最大堆对一组数据进行排序   仍然有优化空间  可以让数据原地排序