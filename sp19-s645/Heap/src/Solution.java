import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;



public class Solution {

    /*     private class Array<E> {.....}
            private class MaxHeap<E extends Comparable<E>> {...}
            private interface Queue<E> {....}
            private class priorityQueue<E extends Comparable<E>> implements Queue<E> {....}
    *
    *
    *
    *
    * */

    private class Freq implements Comparable<Freq> {
        int e, freq;

        public Freq(int e, int freq) {
            this.e = e;
            this.freq = freq;

        }

        @Override
        public int compareTo(Freq another) {
            if (this.freq < another.freq)
                return 1;

            else if (this.freq > another.freq)


                return -1;

            else
                return 0;


        }
    }

    public List<Integer> topKFrequent(int[] nums, int k) {


        TreeMap<Integer, Integer> map = new TreeMap<>(); // 前一个是元素 后一个是频次
        for (int num : nums) { //遍历一遍nums这个数组
            if (map.containsKey(num))  //  包含了这个数
                map.put(num, map.get(num) + 1);
            else
                map.put(num, 1);
        }

    //统计频次




        PriorityQueue<Freq> pq = new PriorityQueue<>();
        for(int key:map.keySet()) {
            if(pq.getSize() < k)
                pq.enqueue(new Freq(key, map.get(key)));
            else if(map.get(key) > pq.getFront().freq) {
                pq.dequeue();
                pq.enqueue(new Freq(key,map.get(key)));
            }
            //对映射中所有的键进行遍历
        }


        //利用优先队列求出前k个元素
        LinkedList<Integer> res = new LinkedList<>();
        while(!pq.isEmpty())
            res.add(pq.dequeue().e);
        return res;
        //然后将优先队列的这些元素放进一个Linkedlist里
    }
}
//这个算法是O（n * logk)这个级别的算法）
