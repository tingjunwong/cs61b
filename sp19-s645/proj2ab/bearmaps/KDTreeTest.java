//package bearmaps;


import org.junit.Test;


import java.util.List;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {




    /* @source reference from  Professor Josh Hog's slides and pseudo walkthrough video*/

    private static KDTree buildLectureTree() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        return kd;
    }
    private static void buildTreeWithDoubles() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(2, 3);
        KDTree kd = new KDTree(List.of(p1, p2));

    }
    @Test
    public void testNearestDemoSlides() {
        KDTree kd = buildLectureTree();
        Point actual = kd.nearest(4, 6);
        Point expected = new Point(4, 5);
        assertEquals(expected, actual);
    }




}
