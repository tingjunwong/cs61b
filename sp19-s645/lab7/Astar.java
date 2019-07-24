Skip to main content
Home
The Oxford Math Center
supporting and promoting the learning of mathematics everywhere
Main menu
HomePrecalculusCalculusStatisticsNumber TheoryCS / JavaAlgorithmsLaTeX
You are here
Home
Search form Search 
 Search
Navigation
Tutoring Schedule
SI Schedule
Blackboard Login
Majoring in Math
Putnam Competition
VTRMC Competition
openstudy.com
Software Resources
Dijkstra's Shortest Path Algorithm

import java.util.Scanner;

public class DijkstraShortestPath {

    private DirectedEdge[] edgeTo;   // edgeTo[v] gives last edge on shortest
                                     // known path from v to source vertex
    
    private double[] distTo;         // distTo[v] gives lenght of shortest
                                     // known path from v to source vertex
    
    private IndexMinPQ<Double> pq;   // recall, dequeue() in a priority 
                                                          // queue removes the element with
                                                          // the highest priority. Here, that
                                                          // will be the smallest path length
                                                          // to the source vertex
    
    private int source;
    
    public DijkstraShortestPath(EdgeWeightedDigraph g, int s) {
        
        this.source = s;
        
        // create/construct necessary arrays and priority queues...
        edgeTo = new DirectedEdge[g.numVertices()];
        distTo = new double[g.numVertices()];
        pq = new IndexMinPQ<Double>(g.numVertices());
        
        for (int v = 0; v < g.numVertices(); v++) {  // set all vertex distances to infinity
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;  // re-initialize source distance to 0
        
        pq.insert(s, 0.0);
        
        printTraceHeaders(); // just for the trace... (not part of the algorithm)
        
        while (! pq.isEmpty()) {
            int v = pq.delMin();   // note, this reduces the size of the queue...
            for (DirectedEdge e : g.adj(v)) {
                relax(e);
                printTraceArraysAndQueue();           // just for the trace...  (not part of the algorithm)
            }
        }
    }
    
    public void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            
            // now update priority queue...
            if (pq.contains(w))                
                pq.decreaseKey(w, distTo[w]);  // overwrite distance associated with w to distTo[w]
            else
                pq.insert(w, distTo[w]);       // add vertex w, setting its distance to distTo[w]
        }
    }
    
    public int source() {
        return this.source;
    }
    
    public Stack<DirectedEdge> pathTo(int v) {
        Stack<DirectedEdge> edges = new Stack<DirectedEdge>();
        
        int w = v;
        while (w != this.source()) {
            DirectedEdge edge = edgeTo[w];
            edges.push(edge);
            w = edge.from();
        }
        return edges;
    }
    
    public void printTraceHeaders() {
        String s = "";
        for (int i = 0; i < distTo.length; i++) {
            s += i + "\t";
        }
        
        s += "|";
        
        for (int i = 0; i < distTo.length; i++) {
            s += i + "\t";
        }
        
        s += "  Priority Queue";
        System.out.println(s);
        
        final int NUM_SPACES_PER_TAB = 5;
        for (int i = 0; i < s.length() + 2*NUM_SPACES_PER_TAB*distTo.length + 14; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
    
    public void printTraceArraysAndQueue() {
        for (int i = 0; i < distTo.length; i++) {
            String dist = (distTo[i] != Double.POSITIVE_INFINITY) ? Double.toString(distTo[i]) : "\u221E";
            System.out.print(dist + "\t");
        }
        
        System.out.print("|");
        
        for (int i = 0; i < edgeTo.length; i++) {
            System.out.print(edgeTo[i] + "\t");
        }
        
        System.out.print("   ");
        for (int i : pq) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(args[0]);
        
        System.out.print("Start? [0-"+(g.numVertices()-1)+"]");
        Scanner scanner = new Scanner(System.in);
        int s = scanner.nextInt();
        System.out.print("Destination Vertex? [0-"+(g.numVertices()-1)+"]");
        int v = scanner.nextInt();
        DijkstraShortestPath sp = new DijkstraShortestPath(g,s);
        
        System.out.println("\nshortest path from " + s + " to " + v + ":");
        Stack<DirectedEdge> path = sp.pathTo(v);
        for (DirectedEdge e : path) {
            System.out.println(e.from() + "->" + e.to() + "  " + e.weight());
        }
        
        System.out.println("path length (approx) = " + sp.distTo[v]);
    }
}
Powered by Drupal
Management
Drupal login