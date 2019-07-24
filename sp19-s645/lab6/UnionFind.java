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
    public boolean connected(int v1, int v2) {
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



