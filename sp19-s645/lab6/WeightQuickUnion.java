public class UnionFind {
    private int[] parent;
    private int[] size;
    /* @source reference from  https://algs4.cs.princeton.edu/15uf/QuickFindUF.java.html*/
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }
    public void validate(int vertex) {
        int n = parent.length;
        if (vertex < 0 || vertex >= n) {
            throw new IllegalArgumentException();
        }
    }
    public int sizeOf(int v1) {
        return size[v1];
    }
    public int parent(int v1) {
        return parent[v1];
    }
    public boolean isConnected(int v1, int v2) {
        if (find(v1) != find(v2)) {
            return false;
        }
        else {
            return true;
        }
    }
    public void union(int v1, int v2) {
        int v1root = find(v1);
        int v2root = find(v2);
        if (v1root == v2root) {
            return;
        }
        if (size[v1root] <= size[v2root]) {
            parent[v1root] = v2root;
            size[v2root] += size[v1root];
        } else {
            parent[v2root] = v1root;
            size[v1root] += size[v2root];
        }
    }
    /* @source reference from  https://algs4.cs.princeton.edu/15uf/QuickFindUF.java.html*/
    public int find(int vertex) {
        validate(vertex);
        while (vertex != parent[vertex]) {
            vertex = parent[vertex];
        }
        return vertex;
    }
}

// Weighted Quick union
以Quick union为基础，我们额外利用一个sz[]保存每一个联通集中对象的数量。在调用union()的时候，我们总是把对象数目较少的联通集连接到对象数目较多的联通集中。通过这种方式，我们可以在一定程度上缓解树的高度太大的问题，从而改善Quick union的时间复杂度。

算法
Union：在Quick union的基础上，将较小的联通集并入较大的联通集中。并且在合并之后更新sz[]数组中对应的联通集的大小。
Find：与Quick union相同。



所以 weighted quick union 可以视作在quick  union 上加了一条规则  即对象数目较少的联通集连接到对象数目较多的联通集   

所以在quick union  的基础上需要再创建一个新数组    用来保存每一个联通集中对象的数量