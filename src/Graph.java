

// I don't think this is being used? 
//import sun.awt.image.ImageWatched;

import java.lang.reflect.Array;
import java.util.*;


public class Graph  implements GraphInterface {

    protected boolean directed;
    protected Map<Vertex, LinkedList<Edge>> outgoingEdges;
    protected int numV = 0;

    //creates Graph w/o any verticies
    Graph(boolean directed) {
        this.directed = directed;
        outgoingEdges = new HashMap<>(100);
    }

    /******************************************
     * Creates a graph w/ generated verticies and edges
     * @param directed
     * @param startingState
     * *********************/
    Graph(boolean directed, int[][] startingState) {
        this.directed = directed;
        outgoingEdges = new HashMap<>(100);
    /*    for(int i = 0; i < 100; i++){
            outgoingEdges[i] = new LinkedHashMap<>();
        } */
        Vertex firstVertex = new Vertex(startingState);
        generateAllPossibleEdgesFromGivenVertex(firstVertex);
    }
    /********************************************************
     * Generates verticies of all possible moves from Vertex v,
     * then inserts as edges into graph
     * @param v Vertex to generate edges for
     *******************************/
    protected void generateAllPossibleEdgesFromGivenVertex(Vertex v) {
        if (v.canMoveLeft()) {
            Vertex v1 = new Vertex(v.moveLeft());
            this.insert(new Edge(v, v1));
        }
        if (v.canMoveRight()) {
            Vertex v2 = new Vertex(v.moveRight());
            this.insert(new Edge(v, v2));
        }
        if (v.canMoveUp()) {
            Vertex v3 = new Vertex(v.moveUp());
            this.insert(new Edge(v, v3));
        }
        if (v.canMoveDown()) {
            Vertex v4 = new Vertex(v.moveDown());
            this.insert(new Edge(v, v4));
        }
    }

    @Override
    public boolean isDirected() {
        return directed;
    }


    @Override
    public void insert(Edge edge) {
        Vertex source = edge.getSource();

        if (outgoingEdges.get(source) != null) {
            outgoingEdges.get(source).push(edge);
        } else {
            LinkedList<Edge> temp = new LinkedList<>();
            temp.add(edge);
            outgoingEdges.put(source, temp);
        }
        numV += 1;

        if (!isDirected()) {
            Vertex dest = edge.getDest();
            Edge reverseEdge = new Edge(dest, source);
            if (outgoingEdges.get(dest) != null) {
                outgoingEdges.get(dest).push(reverseEdge);
            } else {
                LinkedList<Edge> temp2 = new LinkedList<>();
                temp2.add(reverseEdge);
                outgoingEdges.put(dest, temp2);
            }
            numV += 1;

        }
    }


    @Override
    public boolean isEdge(Vertex source, Vertex dest) {
        if (outgoingEdges.containsKey(source)) {
            for (Edge e : outgoingEdges.get(source)) {
                if (e.getDest().containsMatrix(dest.getTiles())) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public Edge getEdge(Vertex source, Vertex dest) {
        for (int i = 0; i < numV; i++) {
            for (Edge e : outgoingEdges.get(source)) {
                if (e.equals(new Edge(source, dest))) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IS THIS GRAPH DIRECTED? : " + directed + "\n");
        sb.append("GRAPH CONTAINS THE FOLLOWING EDGES : \n");
        for (Vertex v : outgoingEdges.keySet()) {
            for (Edge e : outgoingEdges.get(v)) {
                sb.append(e.toString());
                sb.append("\n");
            }
        }
        return sb.toString();

    }

}


