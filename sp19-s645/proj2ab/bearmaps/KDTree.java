

import java.util.List;
public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;

    private class Node {
        private Point p;
        private boolean orientation;
        private Node leftChild;  //   也是 downchild
        private Node rightChild; // 也是   upchild

        Node(Point givenP, boolean o) {

            p = givenP;
            orientation = o;
        }
    }
    public KDTree(List<Point> points) {
        for (Point p: points) {
            root = add(p, root, HORIZONTAL);
        }       //每一个新加进来的数都要从这里开始，   遍历一遍树  最后找到一个null 的位置  新建 new node

    }
    /* @source reference from  Professor Josh Hog's slides and pseudo walkthrough video*/
    private Node add(Point p, Node n, boolean orientation) {

        if (n == null) {
            return new Node(p, orientation);
        }
        if (p.equals(n.p)) {
            return n;
        }
        int cmp = comparePoints(n.p, p, orientation);
        if (cmp > 0) {
            n.leftChild = add(p, n.leftChild, !orientation);
        } else if (cmp < 0) {
            n.rightChild = add(p, n.rightChild, !orientation);
        }
        return n;
    }




    public int comparePoints(Point a, Point b, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(a.getX(), b.getX());

        } else {
            return Double.compare(a.getY(), b.getY());

        }
    }
    public double keydistance(Node n, Point goal) {
        if (n.orientation == HORIZONTAL) {
            return ((n.p.getX() - goal.getX()) * (n.p.getX() - goal.getX()));

        } else {
            return ((n.p.getY() - goal.getY()) * (n.p.getY() - goal.getY()));

        }
    }


    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearest(root, goal, root).p;

         //给定初始值root  从 root 开始向下查找
    }
    private Node nearest(Node n, Point goal, Node best) {

        if (n == null) {
            return best;
        }
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }
        int cmp = comparePoints(n.p, goal, n.orientation);
        //   root的 orientation初始值为horizontal
        if (cmp > 0) {   //看x坐标  大于goal  goal在左边  先遍历左边
            best = nearest(n.leftChild, goal, best);
            //到了第二层， node n 的leftchild 自动变成了vertical
            if (Point.distance(best.p, goal) > keydistance(n, goal)) {
                //这一步 应该是  比较   垂直   y方向的
                best = nearest(n.rightChild, goal, best);   // 有迹可循  值得再去看一下
            }

        } else if (cmp < 0) {    //反之 先看右边节点
            best = nearest(n.rightChild, goal, best);
            if (Point.distance(best.p, goal) > keydistance(n, goal)) {
                best = nearest(n.leftChild, goal, best);
            }

        }
        return best;
    }



    public static void main(String[] args) {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));

        kd.nearest(4, 6);
    }

}
