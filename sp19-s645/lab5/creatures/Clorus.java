package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.*;


public class Clorus extends Creature {


    private int r;
    private int g;
    private int b;



    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }


    public Clorus() {
        this(1);
    }


    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }


    public void attack(Creature d) {
        energy += d.energy();
    }


    public void move() {
        energy = energy - 0.03;
    }


    public void stay() {
        energy = energy - 0.01;
    }

    /**
     *  When a Clorus replicates, it keeps 50% of its energy.
     *  The other 50% goes to its offspring.
     *  No energy is lost in the replication process.
     */
    public Clorus replicate() {
        energy = energy * 0.5;
        Clorus replicated = new Clorus(energy);
        return replicated;
    }


    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
//        boolean anyPlip = neighbors.containsValue("plip");;
        Deque<Direction> anyplipNeighbors = new ArrayDeque<>();

        for (Direction d1: neighbors.keySet()) {
          if  (neighbors.get(d1).name().equals("empty")) {
              emptyNeighbors.add(d1);
          }
        }
        for (Direction d2: neighbors.keySet()) {
            if  (neighbors.get(d2).name().equals("plip")) {
                anyplipNeighbors.add(d2);
            }
        }
        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else if (!anyplipNeighbors.isEmpty()) {
            Direction anyplipDirection = HugLifeUtils.randomEntry(anyplipNeighbors);
            return new Action(Action.ActionType.ATTACK, anyplipDirection);
        } else if (energy >= 1) {
            Direction moveDirection = HugLifeUtils.randomEntry(emptyNeighbors);
            return new Action(Action.ActionType.REPLICATE, moveDirection);
        } else {
                Direction moveDirection = HugLifeUtils.randomEntry(emptyNeighbors);
                return new Action(Action.ActionType.MOVE, moveDirection);
        }

        }

    }


