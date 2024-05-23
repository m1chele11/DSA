package graph.noweight;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TIER I TODO:
 * <p>
 * This class represents a directed graph. The graph is represented as a
 * collection of nodes and a mapping from each node to its outgoing edges.
 */
public class DirectedGraph {
    protected final Set<String> nodes;
    protected final HashMap<String,Set<Edge>> adjacencyLists;

    public DirectedGraph(HashMap<String,Set<Edge>> adjacencyLists) {
        this.nodes = adjacencyLists.keySet();
        this.adjacencyLists = adjacencyLists;
    }

    public Set<String> getNodes () { return nodes; }
    public HashMap<String,Set<Edge>> getAdjacencyLists () { return adjacencyLists; }

    /**
     * TIER I TODO:
     * Return a set of the outgoing edges from the given node.
     */
    public Set<Edge> outgoingEdges (String node) {
        return adjacencyLists.get(node);
    }

    /**
     * TIER I TODO:
     * Return a set of the neighbors of the given node.
     */
    public Set<String> neighbors(String node) {

        return outgoingEdges(node).stream().map(Edge::destination).collect(Collectors.toSet());
//        if (adjacencyLists.containsKey(node)) {
//            Set<String> neighbors = new HashSet<>();
//            for (Edge edge : adjacencyLists.get(node)) {
//                neighbors.add(edge.flip().source());
//            }
//            return neighbors;
//        } else {
//            return new HashSet<>(); // Return an empty set if the node doesn't exist
//        }
    }

    /**
     * TIER I TODO:
     * Remove the given edge from the graph.
     */
    public void removeEdge (Edge edge) {
        String sourceNode = edge.source();
//        if (adjacencyLists.containsKey(sourceNode)) {
            adjacencyLists.get(sourceNode).remove(edge);
//        }
    }

    /**
     * TIER I TODO:
     * Insert the given edge into the graph.
     */
    public void insertEdge (Edge edge) {
        String sourceNode = edge.source();
        adjacencyLists.get(sourceNode).add(edge);

    }

    /**
     * TIER I TODO
     * <p>
     * This method should return a new DirectedGraph that is a copy of this one.
     * It is important to create a new copy of the adjacency lists, rather than
     * just returning the existing ones, because otherwise the caller could
     * modify the adjacency lists of the returned graph, which would also modify
     * the original lists.
     */
    public DirectedGraph copy () {
        HashMap<String, Set<Edge>> copiedAdjacencyLists = new HashMap<>();
        for (String node : getNodes()) {
            copiedAdjacencyLists.put(node, new HashSet<>(outgoingEdges(node)));
        }
        return new DirectedGraph(copiedAdjacencyLists);
    }

    /**
     * TIER I TODO:
     * Return a new DirectedGraph that is the transpose of this graph. In
     * a transpose graph, the direction of every edge is reversed.
     */
    public DirectedGraph transpose () {
        HashMap<String, Set<Edge>> transposedAdjacencyLists = new HashMap<>();



        for (String node : nodes) {
            transposedAdjacencyLists.put(node, new HashSet<>());
        }
        for (Set <Edge> outgoingEdges : adjacencyLists.values()) {
            for (Edge edge : outgoingEdges) {

                Edge reversedEdge = edge.flip();
                String source = reversedEdge.source();

                transposedAdjacencyLists.get(source).add(reversedEdge);
            }
        }

        return new DirectedGraph(transposedAdjacencyLists);
    }

    /**
     * TIER I TODO:
     * Return a new DirectedGraph that is the bidirectional version of this
     * graph. In a bidirectional graph, for every edge A -> B, there is also
     * an edge B -> A.
     */
    public DirectedGraph bidirectional () {
        HashMap<String, Set<Edge>> bidirectionalAdjacencyLists = new HashMap<>();


        HashMap<String, Set<Edge>> transposedAdjacencyLists = transpose().getAdjacencyLists();
        for(String node : nodes){
            Set<Edge> edges = new HashSet<>();
            adjacencyLists.get(node);
            edges.addAll(adjacencyLists.get(node));
            edges.addAll(transposedAdjacencyLists.get(node));
            bidirectionalAdjacencyLists.put(node, edges);
        }

        return new DirectedGraph(bidirectionalAdjacencyLists);
    }
}
