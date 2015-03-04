package nstuff.world.entity.pathfinding;

import nstuff.world.entity.MapPoint;

/**
 * Created by Ivan.Ochincenko on 24.02.15.
 */
public class Node implements Comparable {
    private int x;

    private int y;

    private int cost;

    private int heuristic;

    private Node parent;

    public Node(int x,int y){
        this.x= x;
        this.y= y;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }
    public int compareTo(Object other) {
        Node o = (Node) other;

        float f = heuristic + cost;
        float of = o.heuristic + o.cost;

        if (f < of) {
            return -1;
        } else if (f > of) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    static Node[][] nodeMap;
    public static void clear(int width, int height) {
        nodeMap = new Node[width][height];

    }

    public static Node getInstance(MapPoint start) {
        if(nodeMap[start.getX()][start.getY()]==null){
            nodeMap[start.getX()][start.getY()] = new Node(start.getX(),start.getY());
        }
        return nodeMap[start.getX()][start.getY()];
    }

    public Node getParent() {
        return parent;
    }
}
