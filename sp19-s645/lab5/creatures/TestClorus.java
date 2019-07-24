package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus p = new Clorus(1);
        assertEquals(1, p.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), p.color());
        p.move();
        assertEquals(0.97, p.energy(), 0.01);
        p.move();
        assertEquals(0.94, p.energy(), 0.01);
        p.stay();
        assertEquals(0.93, p.energy(), 0.01);
        p.stay();
        assertEquals(0.92, p.energy(), 0.01);
        Plip q = new Plip(1.5);
        p.attack(q);
        assertEquals(2.42, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        // TODO
        Clorus p = new Clorus(1);
        Clorus q = p.replicate();
        assertEquals(0.5, p.energy(), 0.01);
        assertEquals(0.5, q.energy(), 0.01);
        assertNotSame(p, q);
    }

    @Test
    public void testChoose() {

        //1. No empty adjacent spaces; stay.
        Clorus p = new Clorus(5);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // 2.Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
        p = new Clorus(1.2);
        HashMap<Direction, Occupant> anyPlip = new HashMap<Direction, Occupant>();
        anyPlip.put(Direction.TOP, new Empty());
        anyPlip.put(Direction.BOTTOM, new Empty());
        anyPlip.put(Direction.LEFT, new Plip());
        anyPlip.put(Direction.RIGHT, new Empty());

        assertEquals(Action.ActionType.ATTACK, p.chooseAction(anyPlip).type);

        // *3.Otherwise, if the Clorus has energy greater than or equal to one, it will REPLICATE
        // *to a random empty square.
        HashMap<Direction, Occupant> oneClorus = new HashMap<Direction, Occupant>();
        oneClorus.put(Direction.TOP, new Empty());
        oneClorus.put(Direction.BOTTOM, new Empty());
        oneClorus.put(Direction.LEFT, new Clorus(1));
        oneClorus.put(Direction.RIGHT, new Clorus(1));
        assertEquals(Action.ActionType.REPLICATE, p.chooseAction(oneClorus).type);

        // *4.Otherwise, the Clorus will MOVE to a random empty square.
        p = new Clorus(0.5);
        assertEquals(Action.ActionType.MOVE, p.chooseAction(oneClorus).type);

        
        
    }
}

