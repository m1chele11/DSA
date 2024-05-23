import graph.noweight.Edge;
import graph.withweight.WeightedDirectedGraph;
import graph.withweight.traversal.MaximumFlow;
import graph.withweight.traversal.Weight;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TIER IV TESTS
 * <p>
 * MaximumFlow
 */

public class Tier4Tests {
    @Test
    public void maxFlow() {
        WeightedDirectedGraph g = ExampleGraphs.g7();
        MaximumFlow mf = new MaximumFlow(g);
        int flow = mf.run("S", "T");
        assertEquals(15, flow);
        HashMap<Edge, Weight> result = mf.getFlow();
        Edge sa, sc, ab, ac, bt, cd, db, dt;
        sa = new Edge("S", "A");
        sc = new Edge("S", "C");
        ab = new Edge("A", "B");
        ac = new Edge("A", "C");
        bt = new Edge("B", "T");
        cd = new Edge("C", "D");
        db = new Edge("D", "B");
        dt = new Edge("D", "T");
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
