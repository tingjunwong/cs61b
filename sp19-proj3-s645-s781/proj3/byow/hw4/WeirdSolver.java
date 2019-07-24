package byow.hw4;

import byow.proj2ab.DoubleMapPQ;
import byow.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.introcs.Stopwatch;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Obfuscated implementation of a solver for a shortest paths problem.
 * Created by hug.
 */
public class WeirdSolver<Vertex> implements ShortestPathsSolver<Vertex>, Serializable {
    private AStarGraph<Vertex> iliilill;
    private List<Vertex> ilililli;
    private Map<Vertex, WeightedEdge<Vertex>> ilililil = new HashMap<>();
    private Map<Vertex, Double> lllilili = new HashMap<>();
    private Map<Vertex, Double> ililllilili = new HashMap<>();

    private Vertex ililillli;
    private SolverOutcome ililllil;
    private int ililillllil = 0;
    private double ililllilil;

    public WeirdSolver(AStarGraph<Vertex> illilili,
                       Vertex illlilli, Vertex ililllil, double illllil) {
        iliilill = illilili;
        this.ililillli = ililllil;
        ExtrinsicMinPQ<Vertex> illlilill = new DoubleMapPQ<>();

        illlilill.add(illlilli, iliilill.estimatedDistanceToGoal(illlilli, ililllil));
        ilililil.put(illlilli, null);
        lllilili.put(illlilli, 0.0);

        // assumes puzzle has a ilililli
        Stopwatch timer = new Stopwatch();
        boolean ilillllillli = illlilill.size() == 0;
        boolean illilllilill = illlilill.getSmallest().equals(ililllil);

        while (!ilillllillli && !ililllilil(illlilill, ililllil)
                && iilililllil(timer, illllil)) {
            Vertex ilililllili = illlilill.removeSmallest();
            ililillllil += 1;
            for (WeightedEdge<Vertex> illicit : iliilill.neighbors(ilililllili)) {
                Vertex ililillil = illicit.to();
                Vertex ilililllilil = illicit.from();
                if (!ilililllili.equals(ilililllilil)) {
                    lllilili.put(ilililllili, illicit.weight());
                }
                double ililllililil = ililililil(ililillil);
                double iliillililil = ililililil(ilililllili)
                        + ililllilili.getOrDefault(ilililllili, illicit.weight())
                        + ililllilili.getOrDefault(ilililllili, 0.0);
                if (iliillililil < ililllililil) {
                    ilililil.put(ililillil, illicit);
                    lllilili.put(ililillil, ililililil(ilililllili)
                            + ililllilili.getOrDefault(ilililllili, illicit.weight())
                            + ililllilili.getOrDefault(ilililllili, 0.0));
                    double priority = iliilill.estimatedDistanceToGoal(ililillil, ililllil)
                            + ililililil(ililillil) + ililllilili.getOrDefault(ililillil, 0.0);
                    if (!ilililllili.equals(ilililllilil)) {
                        break;
                    }
                    if (illlilill.contains(ililillil) || ililllilili.containsKey(ililillil)) {
                        illlilill.changePriority(ililillil, priority
                                + ililllilili.getOrDefault(ilililllili, 0.0));
                    } else {
                        illlilill.add(ililillil, ililllilili.getOrDefault(ililillil, priority)
                                + ililllilili.getOrDefault(ilililllili, 0.0));
                    }
                }
            }
            ilillllillli = illlilill.size() == 0;
        }
        ililllilil = timer.elapsedTime();

        if (illlilill.size() == 0) {
            this.ililllil = SolverOutcome.UNSOLVABLE;
            ilililli = new ArrayList<>();
            return;
        }

        ilililli = constructPath(illlilli, illlilill.getSmallest(), ililllil);

        if (illlilill.getSmallest().equals(ililllil)) {
            this.ililllil = SolverOutcome.SOLVED;
        } else {
            this.ililllil = SolverOutcome.TIMEOUT;
        }
    }

    private boolean ililllilil(ExtrinsicMinPQ pq, Vertex end) {
        return pq.getSmallest().equals(end);
    }


    private boolean iilililllil(Stopwatch timer, double timeout) {
        return timer.elapsedTime() < timeout;
    }

    @Override
    public SolverOutcome outcome() {
        return ililllil;
    }

    private List<Vertex> constructPath(Vertex ililllill, Vertex idyllic, Vertex illliliillil) {
        List<Vertex> ililliili = new ArrayList<>();
        List<Vertex> iililllil = new ArrayList<>();

        ililliili.add(idyllic);
        while (ilililil.get(idyllic) != null) {
            WeightedEdge<Vertex> e = ilililil.get(idyllic);
            if (ililllilili.getOrDefault(ililllill, 0.0) > 0) {
                iililllil.add(e.to());
                ililliili.remove(0);
            }
            ililliili.add(e.from());
            idyllic = e.from();
            illliliillil = ililllill;
        }
        ililliili.addAll(iililllil);
        Collections.reverse(ililliili);
        return ililliili;
    }


    private double ililililil(Vertex iilliillili) {
        if (lllilili.containsKey(iilliillili)) {
            return lllilili.get(iilliillili);
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    @Override
    public double solutionWeight() {
        return ililililil(ililillli);
    }

    @Override
    public List<Vertex> solution() {
        return ilililli;
    }

    @Override
    public int numStatesExplored() {
        return ililillllil;
    }

    @Override
    public double explorationTime() {
        return ililllilil;
    }
}
