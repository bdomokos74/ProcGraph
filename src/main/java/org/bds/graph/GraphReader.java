package org.bds.graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.DirectedGraph;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Astrid Research
 * Author: balint
 * Created: Feb 4, 2010 2:25:09 PM
 */
public class GraphReader {
    private BufferedReader graphReader;

    public GraphReader(BufferedReader graphReader) {

        this.graphReader = graphReader;
    }

    public DirectedGraph<String, ContigEdge> read() throws IOException {
        String line;
        DirectedGraph<String, ContigEdge> result = new DefaultDirectedGraph<String, ContigEdge>(ContigEdge.class);
        int i = 0;
        ClassBasedEdgeFactory<String, ContigEdge> edgeFactory = new ClassBasedEdgeFactory(ContigEdge.class);
        while ((line = graphReader.readLine()) != null) {
            if(line.indexOf("->") == -1) {
                continue;
            }
            String vertexes[] = line.split("( -> )|(;)");
            result.addVertex(vertexes[0]);
            result.addVertex(vertexes[1]);
            ContigEdge edge = edgeFactory.createEdge(vertexes[0], vertexes[1]);
            edge.setId(String.valueOf(i));
            result.addEdge(vertexes[0], vertexes[1], edge);
            i++;
        }
        return result;
    }
}
