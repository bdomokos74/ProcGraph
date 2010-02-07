package org.bds.graph;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Feb 6, 2010
 * Time: 7:33:13 PM
 */
public class LibSeaWriter {
    private Graph<String, ContigEdge> graph;
    //UndirectedGraph<String,ContigEdge> ungraph;

    private BufferedWriter writer;

    public LibSeaWriter(Graph<String, ContigEdge> graph, BufferedWriter writer) {
        this.graph = graph;
        //this.ungraph = Graphs.undirectedGraph(graph);
        this.writer = writer;
    }

    public void write(String startVertex) throws IOException {
        writeHeader("ContigGraph", "Denovo contigs.", graph.vertexSet().size(), graph.edgeSet().size());
        writeLinks();
        writePaths();
        writeSpanningTree(startVertex);
        writeTail();
        writer.close();
    }

    private void writeLinks() throws IOException {
        writer.write("@links=[\n");
        int numEdges = graph.edgeSet().size();
        int currEdge = 0;
        for (ContigEdge e : graph.edgeSet()) {
            if (currEdge < numEdges - 1)
                writer.write(String.format("\t{ @source=%s; @destination=%s; },\n", graph.getEdgeSource(e), graph.getEdgeTarget(e)));
            else
                writer.write(String.format("\t{ @source=%s; @destination=%s; }\n", graph.getEdgeSource(e), graph.getEdgeTarget(e)));
            currEdge++;
        }
        writer.write("];\n");
    }

    private void writePaths() throws IOException {
        writer.write("@paths=;\n");
    }

    private void writeSpanningTree(String rootEdge) throws IOException {
        String template1 = "### attribute data ###\n" +
                "   @enumerations=;\n" +
                "   @attributeDefinitions= [\n" +
                "  {\n" +
                "   $root;\n" +
                "   bool;\n" +
                "   || false ||;\n" +
                "   [\n" +
                "    { %s; T; }\n" +
                "   ];\n" +
                "   ;\n" +
                "   ;\n" +
                "  },\n" +
                "  {\n" +
                "   $tree_link;\n" +
                "   bool;\n" +
                "   || false ||;\n" +
                "   ;\n" +
                "   [\n";
        writer.write(String.format(template1, rootEdge));


        Set<ContigEdge> tree = new SpanningTree(graph, true).getSpanningTree(rootEdge);
        int max = tree.size();
        int curr = 0;
        for (ContigEdge e : tree) {
            if(curr<max-1) {
                //writer.write(String.format("\t### %s -> %s ###\n",  graph.getEdgeSource(e),graph.getEdgeTarget(e)));
                writer.write(String.format("\t{ %s; T; },\n", e.getId() ));
            }
            else {
                //writer.write(String.format("\t### %s -> %s ###\n",  graph.getEdgeSource(e),graph.getEdgeTarget(e)));
                writer.write(String.format("\t{ %s; T; }\n", e.getId() ));
            }
            curr++;
        }

        String template2 = "];\n" +
                "   ;\n" +
                "  }\n" +
                "];\n" +
                " [\n" +
                "  {\n" +
                "   $spanning_tree;\n" +
                "   $default_spanning_tree;\n" +
                "   ;\n" +
                "   [ { 0; $root; }, { 1; $tree_link; } ];\n" +
                "  }\n" +
                " ];";
        writer.write(String.format(template2));
    }


    private void writeHeader(String name, String desc, int numNodes, int numLinks) throws IOException {
        String template = "Graph {\n" +
                "   @name=\"%s\";\n" +
                "   @description=\"%s\";\n" +
                "   @numNodes=%d;\n" +
                "   @numLinks=%d;\n" +
                "   @numPaths=0;\n" +
                "   @numPathLinks=0;\n" +
                "\n" +
                "### structural data ###\n";
        writer.write(String.format(template, name, desc, numNodes, numLinks));
    }

    private void writeTail() throws IOException {
        writer.write(" #@qualifiers=;\n" +
                "\n" +
                "   ### visualization hints ###\n" +
                "   @filters=;\n" +
                "   @selectors=;\n" +
                "   @displays=;\n" +
                "   @presentations=;\n" +
                "\n" +
                "   ### interface hints ###\n" +
                "   @presentationMenus=;\n" +
                "   @displayMenus=;\n" +
                "   @selectorMenus=;\n" +
                "   @filterMenus=;\n" +
                "   @attributeMenus=;\n" +
                "}");
    }
}
