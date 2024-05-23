package graph.withweight;

import graph.noweight.DirectedGraph;
import graph.noweight.Edge;
import graph.withweight.traversal.Weight;

import java.util.*;

/**
 * This class extended DirectedGraph with information about the weights of
 * the edges. The weights are maintained separately from the adjacency lists
 * in a HashMap.
 */
public class WeightedDirectedGraph extends DirectedGraph {
    private final HashMap<Edge,Weight> weights;
    public WeightedDirectedGraph(HashMap<String,Set<Edge>> adjacencyLists, HashMap<Edge,Weight> weights) {
        super(adjacencyLists);
        this.weights = weights;
    }

    public HashMap<Edge,Weight> getWeights() {
        return weights;
    }

    /**
     * TIER II TODO
     * <p>
     * Subtract the given weight from the weight of the given edge. If the result is zero,
     * remove the edge from the graph.
     */
    public void subtractEdgeWeight (Edge edge, Weight diff) {
        Weight currentWeight = weights.get(edge);
        Weight updatedWeight = currentWeight.subtract(diff);

        if (updatedWeight.compareTo(Weight.ZERO) == 0) {
            super.removeEdge(edge);
            weights.remove(edge);
        } else {
            weights.put(edge, updatedWeight);
        }
    }

    /**
     * TIER II TODO
     * <p>
     * Add the given weight to the weight of the given edge. If the edge is not in the graph,
     * add it.
     */
    public void insertEdge (Edge edge, Weight weight) {
        Weight currentWeight = weights.getOrDefault(edge, Weight.INFINITY);
        Weight updatedWeight = currentWeight.add(weight);

        super.insertEdge(edge);
        weights.put(edge, updatedWeight);


//        weights.put(edge, updatedWeight);
//        adjacencyLists.get(edge.source()).add(edge);
    }
    /**
     * TIER II TODO
     * <p>
     * This method should return a new WeightedDirectedGraph that is a copy of this one.
     * It is important to create a new copy of the adjacency lists and weights, rather than
     * just returning the existing ones, because otherwise the caller could modify the
     * adjacency lists or weights of the returned graph, which would also modify
     * the original graph.
     */
    public WeightedDirectedGraph copy () {
        HashMap<String,Set<Edge>> newAdjacencyLists = new HashMap<>();
        HashMap<Edge,Weight> newWeights = new HashMap<>(weights);

        DirectedGraph var = super.copy();



        return new WeightedDirectedGraph(var.getAdjacencyLists(), newWeights);
    }

    public boolean equals (Object o) {
        if (o instanceof WeightedDirectedGraph other) {
            return adjacencyLists.equals(other.adjacencyLists) && weights.equals(other.weights);
        }
        return false;
    }
}
