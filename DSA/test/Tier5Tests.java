import graph.noweight.DirectedGraph;
import graph.noweight.traversal.dfs.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class Tier5Tests {

    @Test
    void g1 () {
        DirectedGraph g = ExampleGraphs.g1();

        DFS dfs = new DFS(g);
        dfs.traverseFromSource("A");
        assertEquals(List.of("A","B","C","D","E"), dfs.getCurrentTraversal());

        CycleDetection cd = new CycleDetection(g);
        cd.traverseFromSource("A");
        assertTrue(cd.hasCycle());

        Reachability r = new Reachability(g);
        r.traverseFromSource("A");
        HashMap<String,Set<String>> table = r.getTable();
        assertEquals(Set.of(), table.get("A"));
        assertEquals(Set.of("A","B","D","E"), table.get("B"));
        assertEquals(Set.of("A","B","D","E"), table.get("C"));
        assertEquals(Set.of("A","B","D","E"), table.get("D"));
        assertEquals(Set.of("A","B","D","E"), table.get("E"));
    }

    @Test
    void g2 () {
        DirectedGraph g = ExampleGraphs.g2();

        DFS dfs = new DFS(g);
        dfs.traverseFromSource("A1");
        assertEquals(List.of("A1","A2","A3","A4","A5","A6","A7","A8","A12","A9","A10","A11"), dfs.getCurrentTraversal());

        CycleDetection cd = new CycleDetection(g);
        cd.traverseFromSource("A1");
        assertFalse(cd.hasCycle());

        TopologicalSort ts = new TopologicalSort(g);
        ts.traverseFromSource("A1");
        assertEquals(List.of("A1","A8","A9","A11","A10","A12","A7","A2","A6","A3","A5","A4"), ts.getTraversal());

        Reachability r = new Reachability(g);
        r.traverseFromSource("A1");
        HashMap<String,Set<String>> table = r.getTable();
        assertEquals(Set.of(), table.get("A1"));
        assertEquals(Set.of("A1"), table.get("A2"));
        assertEquals(Set.of("A1","A2"), table.get("A3"));
        assertEquals(Set.of("A1","A2","A3"), table.get("A4"));
        assertEquals(Set.of("A1","A2", "A3"), table.get("A5"));
        assertEquals(Set.of("A1","A2"), table.get("A6"));
        assertEquals(Set.of("A1"), table.get("A7"));
        assertEquals(Set.of("A1"), table.get("A8"));
        assertEquals(Set.of("A1","A8"), table.get("A9"));
        assertEquals(Set.of("A1","A8","A9"), table.get("A10"));
        assertEquals(Set.of("A1","A8","A9"), table.get("A11"));
        assertEquals(Set.of("A1","A8"), table.get("A12"));
    }

    @Test
    void g3 () {
        DirectedGraph g = ExampleGraphs.g3();

        StronglyConnectedComponents scc = new StronglyConnectedComponents(g);
        HashMap<String,List<String>> components = scc.computeSCC();
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
}
