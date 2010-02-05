import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.graph.DefaultEdge;
import org.bds.graph.GraphReader;
import org.bds.graph.ContigEdge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;
import java.util.HashSet;

import static junit.framework.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Feb 3, 2010
 * Time: 9:32:53 PM
 */
public class JGraphTTest {
    @Test
    public void testStart() throws Exception {
        GraphReader reader = new GraphReader(new BufferedReader(new FileReader("/home/balint/work/graph/edgelist-num.txt")));
        final Set<String> processed = new HashSet<String>();

        final DirectedGraph<String, ContigEdge> graph = reader.read();

        BreadthFirstIterator<String, ContigEdge> traverser = new BreadthFirstIterator(graph, "82");

        traverser.addTraversalListener(new TraversalListener<String, ContigEdge>() {
            @Override
            public void connectedComponentFinished(ConnectedComponentTraversalEvent event) {
            }

            @Override
            public void connectedComponentStarted(ConnectedComponentTraversalEvent event) {
            }

            @Override
            public void edgeTraversed(EdgeTraversalEvent<String, ContigEdge> event) {
                if (!processed.contains(graph.getEdgeTarget(event.getEdge()))) {
//                System.out.printf("{ %s; T; %s-%s },\n", event.getEdge().getId() ,graph.getEdgeSource(event.getEdge()), graph.getEdgeTarget(event.getEdge()));
                    System.out.printf("%s -> %s; %s;\n", graph.getEdgeSource(event.getEdge()), graph.getEdgeTarget(event.getEdge()),graph.edgesOf(graph.getEdgeSource(event.getEdge())).size());
                }
            }

            @Override
            public void vertexTraversed(VertexTraversalEvent<String> event) {
            }

            @Override
            public void vertexFinished(VertexTraversalEvent<String> event) {
            }
        });

        for (String curr = traverser.next(); traverser.hasNext(); curr = traverser.next()) {
            processed.add(curr);
        }
    }

    @Test
    public void testStart2() throws Exception {
        GraphReader reader = new GraphReader(new BufferedReader(new FileReader("/home/balint/work/graph/spanningtree.txt")));
        final DirectedGraph<String, ContigEdge> graph = reader.read();
        assertEquals(true, new CycleDetector(graph).detectCycles());
    }
}
