import graph.noweight.DirectedGraph;
import graph.noweight.Edge;
import graph.noweight.Path;
import graph.noweight.traversal.dfs.*;
import graph.noweight.traversal.iter.QueueCollection;
import graph.noweight.traversal.iter.StackCollection;
import graph.withweight.WeightedDirectedGraph;
import graph.withweight.traversal.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class MyTest {


    @Test
    void customGraph1() {
        DirectedGraph g = ExampleGraphs.g1();

        DFS dfs = new DFS(g);
        dfs.traverseFromSource("X");
        assertEquals(List.of("X", "Y", "Z", "W", "V"), dfs.getCurrentTraversal());

        CycleDetection cd = new CycleDetection(g);
        cd.traverseFromSource("X");
        assertFalse(cd.hasCycle());

        Reachability r = new Reachability(g);
        r.traverseFromSource("X");
        HashMap<String, Set<String>> table = r.getTable();
        assertEquals(Set.of(), table.get("X"));
        assertEquals(Set.of("X", "Y", "Z", "W", "V"), table.get("Y"));
        assertEquals(Set.of("X", "Y", "Z", "W", "V"), table.get("Z"));
        assertEquals(Set.of("X", "Y", "Z", "W", "V"), table.get("W"));
        assertEquals(Set.of("X", "Y", "Z", "W", "V"), table.get("V"));
    }

    @Test
    void customGraph2() {
        DirectedGraph g = ExampleGraphs.g2();

        DFS dfs = new DFS(g);
        dfs.traverseFromSource("P1");
        assertEquals(List.of("P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P12", "P9", "P10", "P11"), dfs.getCurrentTraversal());

        CycleDetection cd = new CycleDetection(g);
        cd.traverseFromSource("P1");
        assertFalse(cd.hasCycle());

        TopologicalSort ts = new TopologicalSort(g);
        ts.traverseFromSource("P1");
        assertEquals(List.of("P1", "P8", "P9", "P11", "P10", "P12", "P7", "P2", "P6", "P3", "P5", "P4"), ts.getTraversal());

        Reachability r = new Reachability(g);
        r.traverseFromSource("P1");
        HashMap<String, Set<String>> table = r.getTable();
        assertEquals(Set.of(), table.get("P1"));
        assertEquals(Set.of("P1"), table.get("P2"));
        assertEquals(Set.of("P1", "P2"), table.get("P3"));
        assertEquals(Set.of("P1", "P2", "P3"), table.get("P4"));
        assertEquals(Set.of("P1", "P2", "P3"), table.get("P5"));
        assertEquals(Set.of("P1", "P2"), table.get("P6"));
        assertEquals(Set.of("P1"), table.get("P7"));
        assertEquals(Set.of("P1"), table.get("P8"));
        assertEquals(Set.of("P1", "P8"), table.get("P9"));
        assertEquals(Set.of("P1", "P8", "P9"), table.get("P10"));
        assertEquals(Set.of("P1", "P8", "P9"), table.get("P11"));
        assertEquals(Set.of("P1", "P8"), table.get("P12"));
    }

    @Test
    void customGraph3() {
        DirectedGraph g = ExampleGraphs.g3();

        StronglyConnectedComponents scc = new StronglyConnectedComponents(g);
        HashMap<String, List<String>> components = scc.computeSCC();
        assertEquals(List.of("A"), components.get("A"));
        assertEquals(List.of("B"), components.get("B"));
        assertEquals(List.of("C", "J", "F", "D"), components.get("C"));
        assertEquals(List.of(), components.get("D"));
        assertEquals(List.of("E"), components.get("E"));
        assertEquals(List.of(), components.get("F"));
        assertEquals(List.of(), components.get("G"));
        assertEquals(List.of("H", "I", "G"), components.get("H"));
        assertEquals(List.of(), components.get("I"));
        assertEquals(List.of(), components.get("J"));
    }




    @Test
    public void directedGraphTest() {
        DirectedGraph g = ExampleGraphs.g1();
        /*
        A -> B
        A -> C
        B -> C
        B -> D
        B -> E
        D -> E
        E -> B
        */

        // outgoing edges
        assertEquals(2, g.outgoingEdges("E").size());
        assertEquals(3, g.outgoingEdges("D").size());
        assertEquals(0, g.outgoingEdges("C").size());
        assertEquals(1, g.outgoingEdges("B").size());
        assertEquals(1, g.outgoingEdges("A").size());
        assertTrue(g.outgoingEdges("A").contains(new Edge("A", "B")));
        assertTrue(g.outgoingEdges("A").contains(new Edge("A", "C")));
        // neighbors
        assertEquals(2, g.neighbors("A").size());
        assertEquals(3, g.neighbors("B").size());
        assertEquals(0, g.neighbors("C").size());
        assertEquals(1, g.neighbors("D").size());
        assertEquals(1, g.neighbors("E").size());
        assertTrue(g.neighbors("A").contains("B"));
        assertTrue(g.neighbors("A").contains("C"));
        // transpose
        DirectedGraph gT = g.transpose();
        assertEquals(0, gT.outgoingEdges("A").size());
        assertEquals(2, gT.outgoingEdges("B").size());//this is the line it fails at
        assertEquals(2, gT.outgoingEdges("C").size());
        assertEquals(1, gT.outgoingEdges("D").size());
        assertEquals(2, gT.outgoingEdges("E").size());
        assertTrue(gT.outgoingEdges("B").contains(new Edge("B", "A")));
        assertTrue(gT.outgoingEdges("B").contains(new Edge("B", "E")));
        assertTrue(gT.outgoingEdges("C").contains(new Edge("C", "A")));
        assertTrue(gT.outgoingEdges("C").contains(new Edge("C", "B")));
        assertTrue(gT.outgoingEdges("D").contains(new Edge("D", "B")));
        assertTrue(gT.outgoingEdges("E").contains(new Edge("E", "B")));
        assertTrue(gT.outgoingEdges("E").contains(new Edge("E", "D")));
        assertEquals(0, gT.neighbors("A").size());
        assertEquals(2, gT.neighbors("B").size());
        assertEquals(2, gT.neighbors("C").size());
        assertEquals(1, gT.neighbors("D").size());
        assertEquals(2, gT.neighbors("E").size());
        assertTrue(gT.neighbors("B").contains("A"));
        assertTrue(gT.neighbors("B").contains("E"));
        assertTrue(gT.neighbors("C").contains("A"));
        assertTrue(gT.neighbors("C").contains("B"));
        assertTrue(gT.neighbors("D").contains("B"));
        assertTrue(gT.neighbors("E").contains("B"));
        assertTrue(gT.neighbors("E").contains("D"));
        // bidirectional
        DirectedGraph gB = g.bidirectional();
        assertEquals(2, gB.outgoingEdges("A").size());
        assertEquals(4, gB.outgoingEdges("B").size());
        assertEquals(2, gB.outgoingEdges("C").size());
        assertEquals(2, gB.outgoingEdges("D").size());
        assertEquals(2, gB.outgoingEdges("E").size());
        assertTrue(gB.outgoingEdges("A").contains(new Edge("A", "B")));
        assertTrue(gB.outgoingEdges("A").contains(new Edge("A", "C")));
        assertTrue(gB.outgoingEdges("B").contains(new Edge("B", "A")));
        assertTrue(gB.outgoingEdges("B").contains(new Edge("B", "C")));
        assertTrue(gB.outgoingEdges("B").contains(new Edge("B", "D")));
        assertTrue(gB.outgoingEdges("B").contains(new Edge("B", "E")));
        assertTrue(gB.outgoingEdges("C").contains(new Edge("C", "A")));
        assertTrue(gB.outgoingEdges("C").contains(new Edge("C", "B")));
        assertTrue(gB.outgoingEdges("D").contains(new Edge("D", "B")));
        assertTrue(gB.outgoingEdges("D").contains(new Edge("D", "E")));
        assertTrue(gB.outgoingEdges("E").contains(new Edge("E", "B")));
        assertTrue(gB.outgoingEdges("E").contains(new Edge("E", "D")));
        assertEquals(2, gB.neighbors("A").size());
        assertEquals(4, gB.neighbors("B").size());
        assertEquals(2, gB.neighbors("C").size());
        assertEquals(2, gB.neighbors("D").size());
        assertEquals(2, gB.neighbors("E").size());
        assertTrue(gB.neighbors("A").contains("B"));
        assertTrue(gB.neighbors("A").contains("C"));
        assertTrue(gB.neighbors("B").contains("A"));
        assertTrue(gB.neighbors("B").contains("C"));
        assertTrue(gB.neighbors("B").contains("D"));
        assertTrue(gB.neighbors("B").contains("E"));
        assertTrue(gB.neighbors("C").contains("A"));
        assertTrue(gB.neighbors("C").contains("B"));
        assertTrue(gB.neighbors("D").contains("B"));
        assertTrue(gB.neighbors("D").contains("E"));
        assertTrue(gB.neighbors("E").contains("B"));
        assertTrue(gB.neighbors("E").contains("D"));
    }

    @Test
    void heap () {
        Heap heap = new Heap(Set.of("E", "D", "C", "B", "A"));
        heap.setWeight("E", new Weight(4));
        heap.setWeight("D", new Weight(3));
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

    @Test
    public void shortestPathsTest() {
        WeightedDirectedGraph g = ExampleGraphs.g5();
        AllShortestPaths sp = new AllShortestPaths(g, "A");
        sp.iterativeTraversal();

        Edge ac = new Edge("A", "C");
        Edge ce = new Edge("C", "E");
        Edge ed = new Edge("E", "D");
        Edge df = new Edge("D", "F");
        List<Edge> best = List.of(ac, ce, ed, df);

        Path computed = sp.getPath("F");

        assertEquals(best, computed.edges());
        assertEquals(20, computed.weight().value());
        assertEquals(2, computed.minFlow().value());
    }

    @Test
    public void minimumSpanningTreeTest() {
        WeightedDirectedGraph g = ExampleGraphs.g5();
        MinimumSpanningTree mst = new MinimumSpanningTree(g, "A");
        mst.iterativeTraversal();

        HashMap<String, String> result = mst.getPreviousNodes();

        assertEquals("D", result.get("B"));
        assertEquals("A", result.get("C"));
        assertEquals("C", result.get("D"));
        assertEquals("D", result.get("E"));
        assertEquals("C", result.get("F"));
        assertEquals("E", result.get("G"));
        assertEquals(42, mst.getWeight());
    }

    @Test
    public void maxFlowTest() {
        WeightedDirectedGraph g = ExampleGraphs.g7();
        MaximumFlow mf = new MaximumFlow(g);
        int flow = mf.run("S", "T");
        assertEquals(15, flow);

        HashMap<Edge, Weight> result = mf.getFlow();

        Edge sa = new Edge("S", "A");
        Edge sc = new Edge("S", "C");
        Edge ab = new Edge("A", "B");
        Edge ac = new Edge("A", "C");
        Edge bt = new Edge("B", "T");
        Edge cd = new Edge("C", "D");
        Edge db = new Edge("D", "B");
        Edge dt = new Edge("D", "T");

        assertEquals(8, result.get(sc).value());
        assertEquals(7, result.get(sa).value());
        assertEquals(5, result.get(ab).value());
        assertEquals(2, result.get(ac).value());
        assertEquals(5, result.get(bt).value());
        assertEquals(10, result.get(cd).value());
        assertEquals(0, result.get(db).value());
        assertEquals(10, result.get(dt).value());
    }




}
