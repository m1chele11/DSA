import graph.noweight.DirectedGraph;
import graph.noweight.Edge;
import graph.noweight.traversal.iter.BFS;
import graph.noweight.traversal.iter.DFSiterative;
import graph.withweight.WeightedDirectedGraph;
import graph.withweight.traversal.Heap;
import graph.withweight.traversal.Weight;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/*
 * TIER II Tests
 *
 * WeightedDirectedGraph
 * DFSiterative
 * BFS
 * Heap
 *
 */
public class Tier2Tests {
    @Test
    void weightedGraph () {
        WeightedDirectedGraph g = ExampleGraphs.g4();
        HashMap<String, Set<Edge>> originalAdjacencyLists = g.getAdjacencyLists();
        HashMap<Edge, Weight> originalWeights = g.getWeights();

        WeightedDirectedGraph gCopy = g.copy();
        HashMap<String, Set<Edge>> copyAdjacencyLists = gCopy.getAdjacencyLists();
        HashMap<Edge, Weight> copyWeights = gCopy.getWeights();

        assertNotSame(gCopy, g);
        assertEquals(gCopy, g);
        assertNotSame(copyAdjacencyLists, originalAdjacencyLists);
        assertEquals(copyAdjacencyLists, originalAdjacencyLists);
        assertNotSame(copyWeights, originalWeights);
        assertEquals(copyWeights, originalWeights);

        for (String node : g.getNodes()) {
            Set<Edge> originalEdges = g.outgoingEdges(node);
            Set<Edge> copyEdges = gCopy.outgoingEdges(node);
            assertNotSame(copyEdges, originalEdges);
            assertEquals(copyEdges, originalEdges);
        }

        for (Edge edge : originalWeights.keySet()) {
            Weight originalWeight = originalWeights.get(edge);
            Weight copyWeight = copyWeights.get(edge);
            assertEquals(copyWeight, originalWeight);
        }

        // Check that modifying the copy doesn't modify the original
        copyWeights.put(new Edge("A", "B"), new Weight(100));
        assertNotEquals(copyWeights, originalWeights);
        // Check that adding an edge to the copy doesn't add it to the original
        copyAdjacencyLists.get("A").add(new Edge("A", "D"));
        assertNotEquals(copyAdjacencyLists, originalAdjacencyLists);
    }

    @Test
    void dfsIterative () {
        DirectedGraph g = ExampleGraphs.g0();
        DFSiterative dfsIter = new DFSiterative(g, "A");
        dfsIter.iterativeTraversal();
        List<String> tr = dfsIter.getTraversal();
        List<String> tr1 = List.of("A","B","D", "C");
        List<String> tr2 = List.of("A","C","D", "B");
        assertTrue(tr.equals(tr1) || tr.equals(tr2));
    }

    @Test
    void bfs () {
        DirectedGraph g = ExampleGraphs.g0();
        BFS bfs = new BFS(g, "A");
        bfs.iterativeTraversal();
        List<String> tr = bfs.getTraversal();
        List<String> tr1 = List.of("A","B","C", "D");
        List<String> tr2 = List.of("A","C","B", "D");
        assertTrue(tr.equals(tr1) || tr.equals(tr2));
    }

    @Test
    void heap () {
        Heap heap = new Heap(Set.of("A", "B", "C", "D", "E"));
        heap.setWeight("A", new Weight(4));
        heap.setWeight("B", new Weight(3));
        heap.setWeight("C", new Weight(2));
        assertEquals("C", heap.extractMin());
        heap.setWeight("D", new Weight(1));
        heap.setWeight("E", new Weight(5));
        assertEquals("D", heap.extractMin());
        assertEquals("B", heap.extractMin());
        assertEquals("A", heap.extractMin());
        heap.insert("X", new Weight(3));
        heap.insert("Y", new Weight(2));
        heap.setWeight("E", new Weight(0));
        assertEquals("E", heap.extractMin());
        assertEquals("Y", heap.extractMin());
        assertEquals("X", heap.extractMin());
        assertTrue(heap.isEmpty());
    }

}
