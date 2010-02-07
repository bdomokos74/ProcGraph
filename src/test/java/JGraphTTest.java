import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.alg.ConnectivityInspector;
import org.bds.graph.*;
import org.bds.test.TestHelper;

import java.io.*;
import java.util.*;

import static junit.framework.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Feb 3, 2010
 * Time: 9:32:53 PM
 */
public class JGraphTTest {

    @Test
    public void testSpanningtree() throws Exception {

        GraphReader reader = new GraphReader(new TestHelper().getResourceReader("/spanningtree_test.gv"));
        DirectedGraph<String, ContigEdge> graph = reader.read();
        Set<ContigEdge> tree = new SpanningTree(graph, true).getSpanningTree("1");

        StringWriter strWriter = new StringWriter();
        DotWriter writer = new DotWriter(new BufferedWriter(strWriter), DotWriter.Type.Directed);
        for (ContigEdge edge : tree) {
            writer.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
        }
        writer.close();

        assertEquals(new TestHelper().resourceToString("/spanningtree_result.gv"), strWriter.toString());
    }

    @Test
    public void testSpanningtree2() throws Exception {

        GraphReader reader = new GraphReader(new TestHelper().getResourceReader("/example.gv"));
        DirectedGraph<String, ContigEdge> graph = reader.read();
        Set<ContigEdge> tree = new SpanningTree(graph, true).getSpanningTree("1");

        StringWriter strWriter = new StringWriter();
        DotWriter writer = new DotWriter(new BufferedWriter(strWriter), DotWriter.Type.Directed);
        for (ContigEdge edge : tree) {
            writer.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
        }
        writer.close();

        assertEquals(new TestHelper().resourceToString("/example_spanningtree.gv"), strWriter.toString());
    }

    @Test
    public void test_libsea() throws Exception {
        GraphReader reader = new GraphReader(new TestHelper().getResourceReader("/spanningtree_test.gv"));
        DirectedGraph<String, ContigEdge> graph = reader.read();
        StringWriter strWriter = new StringWriter();
        LibSeaWriter writer = new LibSeaWriter(graph, new BufferedWriter(strWriter));
        writer.write("1");
        assertEquals(new TestHelper().resourceToString("/spanningtree_result.graph"), strWriter.toString());
    }

    @Test
    public void test_libsea2() throws Exception {
        GraphReader reader = new GraphReader(new TestHelper().getResourceReader("/example.gv"));
        DirectedGraph<String, ContigEdge> graph = reader.read();
        StringWriter strWriter = new StringWriter();
        LibSeaWriter writer = new LibSeaWriter(graph, new BufferedWriter(strWriter));
        writer.write("1");
        assertEquals(new TestHelper().resourceToString("/example_gen.graph"), strWriter.toString());
    }

    public DirectedGraph<String, ContigEdge> getGraph() throws Exception {
        GraphReader reader = new GraphReader(new BufferedReader(new FileReader("/Users/bds/Documents/projects/ProcGraph/edgelist-num0.txt")));
        DirectedGraph<String, ContigEdge> graph = reader.read();
        return graph;
    }

    @Test
    public void testContigGraph() throws Exception {
        GraphReader reader = new GraphReader(new BufferedReader(new FileReader("mapped1.gv")));
        DirectedGraph<String, ContigEdge> graph = reader.read();

        FileWriter fileWriter = new FileWriter("ww2.graph");
        LibSeaWriter writer = new LibSeaWriter(graph, new BufferedWriter(fileWriter));
        writer.write("1000");
    }

    @Test
    public void testContigGraph_missingEdges() throws Exception {

        DirectedGraph<String, ContigEdge> graph = getGraph();

        ConnectivityInspector inspector = new ConnectivityInspector(graph);
        List components = inspector.connectedSets();
        assertEquals(true, inspector.isGraphConnected());

        System.out.printf("components=%d", components.size());
    }


    @Test
    public void testConvert() throws Exception {
        DirectedGraph<String, ContigEdge> graph = getGraph();
        List<Integer> list = new ArrayList<Integer>();
        for (ContigEdge e : graph.edgeSet()) {

            System.out.printf("%d -> %d;\n", Integer.valueOf(graph.getEdgeSource(e)).intValue() - 1, Integer.valueOf(graph.getEdgeTarget(e)).intValue() - 1);
        }

//        for(int i =0; i<=)
    }

    @Test
    public void testEdges() throws Exception {
        DirectedGraph<String, ContigEdge> graph = getGraph();
        Set<String> vertexSet = graph.vertexSet();
        Map<String, String> vertexMap = new HashMap<String, String>();
        int i = 0;
        for (String vertex : vertexSet) {
            vertexMap.put(vertex, String.valueOf(i));
            i++;
        }


//        DotWriter writer = new DotWriter(new BufferedWriter(new FileWriter("mapped.gv")), DotWriter.Type.Directed);
//        for (ContigEdge edge: graph.edgeSet()) {
//            writer.addEdge(vertexMap.get(graph.getEdgeSource(edge)), vertexMap.get(graph.getEdgeTarget(edge)));
//        }
//        writer.close();
    }

    @Test
    public void testEdges2() throws Exception {
        GraphReader reader = new GraphReader(new BufferedReader(new FileReader("mapped1.gv")));

        DirectedGraph<String, ContigEdge> graph = reader.read();
        Set<ContigEdge> tree = new SpanningTree(graph, true).getSpanningTree("0");

        Set<String> vertexSet = graph.vertexSet();
        Set<String> allNodes = new HashSet<String>();
        allNodes.addAll(vertexSet);

        for (ContigEdge e : tree) {
            allNodes.remove(graph.getEdgeSource(e));
            allNodes.remove(graph.getEdgeTarget(e));
        }

        System.out.printf("remaining:\n");
        for (String node : allNodes) {
            System.out.printf("%s, ", node);
        }

    }

    @Test
    public void testEdges3() throws Exception {
        GraphReader reader = new GraphReader(new BufferedReader(new FileReader("mapped.gv")));

        DirectedGraph<String, ContigEdge> graph = reader.read();
        List<String> list = Arrays.asList("2108", "2208", "2215", "2273");
        for (String node : list) {
            System.out.printf("node %s:\n\tnum edges: %d\n", node, graph.edgesOf(node).size());
            printNeighbours(graph, node);
        }
    }

    private void printNeighbours(DirectedGraph<String, ContigEdge> graph, String node) {
        for (ContigEdge edge : graph.edgesOf(node)) {
            System.out.printf("\t%s  -> %s\n", graph.getEdgeSource(edge),graph.getEdgeTarget(edge));
        }
    }
}
