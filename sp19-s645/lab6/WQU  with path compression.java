

public class QuickUnionPathCompressionUF {
    private int[] id;    // id[i] = parent of i
    
    
    public QuickUnionPathCompressionUF(int n) {
        
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    
   
    public int find(int p) {
        int root = p;
        while (root != id[root])
            root = id[root];
        while (p != root) {
            int newp = id[p];
            id[p] = root;
            p = newp;
        }
        return root;
    }

    
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) 
        	return;
        id[rootP] = rootQ;
        
    }









以Quick union为基础，在寻找对象i所对应的联通集的root的过程之后，将中途所检查过的每一个对象对应的id都改为root(i)。如下面的例子所示：