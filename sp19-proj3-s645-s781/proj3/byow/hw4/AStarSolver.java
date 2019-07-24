package byow.hw4;

import byow.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private ArrayHeapMinPQ<Vertex> pq;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Node> edgeTo;
    private AStarGraph<Vertex> input;
    private Vertex start;
    private Vertex end;
    private SolverOutcome result;
    private ArrayList<Vertex> solution;
    private double solutionWeight;
    private int numExplored;
    private double time;

    private class Node {
        private double weight;
        private Vertex from;

        Node(double weight, Vertex from) {
            this.from = from;
            this.weight = weight;
        }
    }

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        numExplored = 0;
        this.input = input;
        this.start = start;
        this.end = end;
        distTo = new HashMap<>();
        distTo.put(start, 0.0);
        edgeTo = new HashMap<>();
        pq = new ArrayHeapMinPQ<>();
        pq.add(start, input.estimatedDistanceToGoal(start, end));

        while (pq.size() != 0 && !pq.getSmallest().equals(end) && sw.elapsedTime() < timeout) {
            Vertex p = pq.removeSmallest();
            numExplored++;
            List<WeightedEdge<Vertex>> neighbors = input.neighbors(p);
            for (WeightedEdge<Vertex> neighbor : neighbors) {
                relax(neighbor);
            }
        }

        if (pq.size() == 0) {
            result = SolverOutcome.UNSOLVABLE;
        } else if (pq.getSmallest().equals(end)) {
            result = SolverOutcome.SOLVED;
        } else {
            result = SolverOutcome.TIMEOUT;
        }

        solution = new ArrayList<>();
        solutionWeight = 0.0;
        if (result == SolverOutcome.SOLVED) {
            Vertex currentKey = end;
            solution.add(end);
            while (!currentKey.equals(start)) {
                solutionWeight += edgeTo.get(currentKey).weight;
                solution.add(0, edgeTo.get(currentKey).from);
                currentKey = edgeTo.get(currentKey).from;
            }
        }
        time = sw.elapsedTime();
    }

    private void relax(WeightedEdge e) {
        Vertex p = (Vertex) e.from();
        Vertex q = (Vertex) e.to();
        double w = e.weight();

        if (distTo.containsKey(q) && distTo.containsKey(p)) {
            if (distTo.get(p) + w < distTo.get(q)) {
                relaxNearest(p, q, w);
            }
        } else if (!distTo.containsKey(q) && distTo.containsKey(p)) {
            relaxNearest(p, q, w);
        }

    }

    private void relaxNearest(Vertex p, Vertex q, double w) {
        distTo.put(q, distTo.get(p) + w);
        if (pq.contains(q)) {
            pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
        } else {
            pq.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
        }
        edgeTo.put(q, new Node(w, p));
    }

    @Override
    public SolverOutcome outcome() {
        return result;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numExplored;
    }

    @Override
    public double explorationTime() {
        return time;
    }
}
