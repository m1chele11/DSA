package graph.withweight.traversal;

import graph.noweight.Edge;
import graph.withweight.WeightedDirectedGraph;

import java.util.HashMap;
/**
 * TIER III TODO
 * <p>
 * This class implements Prim's algorithm to find the minimum spanning tree
 * of an undirected weighted graph. This information is represented as a
 * HashMap that maps each node to its parent in the minimum spanning tree.
 * <p>
 * The traversal itself is implemented by customizing the generic iterative
 * traversal by defining the relaxEdge method. (See below.)
 */
public class MinimumSpanningTree extends WeightedIterativeGraphTraversal {

    private int weight; // Track the total weight of the minimum spanning tree

    private final HashMap<String,String> previousNodes;

    public MinimumSpanningTree(WeightedDirectedGraph graph, String source) {
        this(graph, new Heap(graph.getNodes()), source);
    }
    public MinimumSpanningTree(WeightedDirectedGraph graph, Heap heap, String source) {
        super(graph, heap);
        this.previousNodes = new HashMap<>();
        heap.setWeight(source, new Weight(0));
    }

    public HashMap<String,String> getPreviousNodes() {
        return previousNodes;
    }

    /**
     * TIER III TODO
     * <p>
     * This method is called whenever a node is visited. It checks all outgoing
     * edges from the node and performs the following actions:
     * <ul>
     *     <li> If the destination node has been visited, we skip it
     *     <li> Otherwise, we calculate the new way of reaching the
     *     destination using the weight of the current edge
     *     <li> If this new way is shorter than the previous way, we update
     *     the weight of the destination node and record the current node as
     *     the parent of the destination node in the minimum spanning tree
     * </ul>
     */

    // Todo
    public void relaxEdge(Edge edge) {

        String sourceNode = edge.source();
        String destinationNode = edge.destination();


        if (!visited.contains(destinationNode)) {

            Weight sourceWeight = heap.getWeight(sourceNode);
            Weight edgeWeight = weights.get(edge);
            Weight destinationWeight = heap.getWeight(destinationNode);

            Weight newWeight = weights.get(edge);

            if (newWeight.compareTo(destinationWeight) < 0) {
                heap.setWeight(destinationNode, newWeight);
                previousNodes.put(destinationNode, sourceNode);
            }
        }


    }

    /**
     * TIER III TODO
     * <p>
     * Return the total weight of the minimum spanning tree.
     */
    public int getWeight() {
        Weight w = Weight.ZERO;
        for(String node : previousNodes.keySet()) {
            w = w.add(weights.get(new Edge(previousNodes.get(node), node)));//getting the prev node
        }
        return w.value();
    }

}
