package bearmaps.hw4;
import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;
//import org.junit.rules.Timeout;

//import java.util.*;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private HashMap<Vertex, Double> distTo = new HashMap<>();
    private HashMap<Vertex, Vertex> edgeTo = new HashMap<>();

    private double timeSpent;
    private int n = 0;
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {


        DoubleMapPQ<Vertex> pq = new DoubleMapPQ<>();

        pq.add(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);
        Stopwatch sw = new Stopwatch();
        while ((pq.size() != 0) && sw.elapsedTime() < timeout) {
            Vertex minVertex = pq.removeSmallest();
            n = n + 1;



            if (!minVertex.equals(end)) {
                for (int i = 0; i < input.neighbors(minVertex).size(); i++) {
                    WeightedEdge<Vertex> e = input.neighbors(minVertex).get(i);
//                    Vertex nextVertex = e.to();
//                PQ.add(nextVertex, input.estimatedDistanceToGoal(nextVertex, end));
//                distTo.put(nextVertex, distTo.get(minVertex) + e.weight());
//                edgeTo.put(nextVertex, minVertex);
                    relax(e, end, input, pq);
                }
            }


            if (minVertex.equals(end)) {
//                solution = List.of(start, end);
                solution = solution(end, start);


                solutionWeight = distTo.get(end);
                outcome = SolverOutcome.SOLVED;
                timeSpent = sw.elapsedTime();
                return;
            }
            outcome = SolverOutcome.UNSOLVABLE;
            timeSpent = sw.elapsedTime();

        }
        if (sw.elapsedTime() > timeout) {
            outcome = SolverOutcome.TIMEOUT;
            timeSpent = sw.elapsedTime();
        }
    }

    public List<Vertex> solution(Vertex end, Vertex start) {
        Vertex v = end;
        List<Vertex> lst = new LinkedList<>();
        ((LinkedList<Vertex>) lst).addFirst(v);
        while (!v.equals(start)) {
            v = edgeTo.get(v);

            ((LinkedList<Vertex>) lst).addFirst(v);
        }
        return lst;
    }


    private void relax(WeightedEdge e, Vertex end, AStarGraph<Vertex> input,
                        DoubleMapPQ<Vertex> pq) {
        Vertex v = (Vertex) e.from();
        Vertex w = (Vertex) e.to();
        if (!distTo.containsKey(w)) {
            distTo.put(w, distTo.get(v) + e.weight());
            edgeTo.put(w, v);
            pq.add(w, distTo.get(w) + input.estimatedDistanceToGoal(w, end));
        } else if (distTo.get(v) + e.weight() < distTo.get(w)) {
            distTo.put(w, distTo.get(v) + e.weight());
            edgeTo.put(w, v);
            pq.changePriority(w, distTo.get(w) + input.estimatedDistanceToGoal(w, end));
//    nextVertex = distTo[nextVertex] + input.estimatedDistanceToGoal(nextVertex, end);
//                if (!visited.contains(w)) {   //w在队列中
//
//                    pq.changePriority(w, distTo.get(w) + input.estimatedDistanceToGoal(w, end));
//                } else {   //nextvertex不在队列中
//                    pq.add(w, distTo.get(w) + input.estimatedDistanceToGoal(w, end));
//
//                }

        }
    }



    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        return solution;
    }
    public double solutionWeight() {
        return solutionWeight;
    }
    public int numStatesExplored() {
        return n;
    }
    public double explorationTime() {
        return timeSpent;
    }
}
