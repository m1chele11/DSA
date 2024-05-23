package graph.withweight.traversal;

import graph.noweight.Edge;
import graph.noweight.Path;
import graph.withweight.WeightedDirectedGraph;

import java.util.HashMap;

/**
 * TIER III TODO
 * <p>
 * This class implements Dijkstra's algorithm to find the shortest path from a
 * source node to all other nodes in a graph. This information is represented
 * as a HashMap that maps each node to the previous node along the shortest
 * path back to the source.
 * <p>
 * The traversal itself is implemented by customizing the generic iterative
 * traversal by defining the relaxEdge method. (See below.)
 */

public class AllShortestPaths extends WeightedIterativeGraphTraversal {
    private final HashMap<String,String> previousNodes;

    public AllShortestPaths(WeightedDirectedGraph graph, String source) {
        this(graph, new Heap(graph.getNodes()), source);
    }
    public AllShortestPaths(WeightedDirectedGraph graph, Heap heap, String source) {
        super(graph, heap);
        this.previousNodes = new HashMap<>();
        heap.setWeight(source, new Weight(0));
    }


    /**
     * TIER III TODO
     * <p>
     * This method is called whenever a node is visited. It checks all outgoing
     * edges from the node and performs the following actions:
     * <ul>
     *     <li> If the destination node has been visited, we skip it
     *     <li> Otherwise, we calculate the new way of reaching the
     *     destination node by adding the weight of the edge to the weight of
     *     the current node.
     *     <li> If this new way is shorter than the previous way, we update
     *     the weight of the destination node and record the current node as
     *     the predecessor of the destination node along the shortest path so far.
     * </ul>
     */

    //todo
    public void relaxEdge(Edge edge) {

        String sourceNode = edge.source();
        String destinationNode = edge.destination();

        if (!visited.contains(destinationNode)) {
            Weight sourceW = heap.getWeight(sourceNode);
            Weight edgeW = weights.get(edge);
            Weight destinationW = heap.getWeight(destinationNode);
            System.out.println("W: "+edgeW);
            Weight weight = sourceW.add(edgeW);
            if (weight.compareTo(destinationW) < 0) {
                heap.setWeight(destinationNode, weight);
                previousNodes.put(destinationNode, sourceNode);
            }
        }

    }








    /**
     * ITER III TODO
     * <p>
     * This method follows the previousNodes map to reconstruct the shortest
     * path from the source to the destination node.
     */
    public Path getPath (String destination) {
        Path path = new Path();
        String currentNode = destination;
        //System.out.println(previousNodes);
        while (previousNodes.containsKey(currentNode)) {
            //System.out.println("hi");
            String previousNode = previousNodes.get(currentNode);
            Edge edge = new Edge(previousNode, currentNode);
            path.add(edge, weights.get(edge));
            currentNode = previousNode;
        }
        path.reverse();
        return path;
    }

}
