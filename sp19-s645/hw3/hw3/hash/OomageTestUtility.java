package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] buckets = new int[M];
        for (int i = 0; i < M; i++) {
            buckets[i] = 0;
        }
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum] += 1;
        }
        for (int j = 0; j < M; j++) {
            if (buckets[j] < oomages.size() / 50 || buckets[j] > (int) oomages.size() / 2.5) {
                return false;
            }
        }
        return true;


    }


}

