import graph.noweight.Edge;
import graph.noweight.Path;
import graph.withweight.WeightedDirectedGraph;
import graph.withweight.traversal.AllShortestPaths;
import graph.withweight.traversal.MinimumSpanningTree;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TIER III TESTS
 * <p>
 * AllShortestsPaths
 * MinimumSpanningTree
 */
public class Tier3Tests {
    @Test
    public void shortestPaths() {
        WeightedDirectedGraph g = ExampleGraphs.g5();
        AllShortestPaths sp = new AllShortestPaths(g, "A");
        sp.iterativeTraversal();
        Edge ac, ce, ed, df;
        ac = new Edge("A", "C");
        ce = new Edge("C", "E");
        ed = new Edge("E", "D");
        df = new Edge("D", "F");
        List<Edge> best = List.of(ac, ce, ed, df);
        Path computed = sp.getPath("F");
        assertEquals(best,computed.edges());
        assertEquals(20, computed.weight().value());
        assertEquals(2, computed.minFlow().value());
    }

    @Test
    public void mst() {
        WeightedDirectedGraph g = ExampleGraphs.g6();
        MinimumSpanningTree mst = new MinimumSpanningTree(g, "A");
        mst.iterativeTraversal();
        HashMap<String,String> result = mst.getPreviousNodes();
        assertEquals("D", result.get("B"));
        assertEquals("A", result.get("C"));
        assertEquals("C", result.get("D"));
        assertEquals("D", result.get("E"));
        assertEquals("C", result.get("F"));
        assertEquals("E", result.get("G"));
        assertEquals(42, mst.getWeight());
    }


}
