package org.bds.graph;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.Graph;

import java.io.BufferedInputStream;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Astrid Research
 * Author: balint
 * Created: Feb 4, 2010 3:10:26 PM
 */
public class GraphReaderTest {
    @Test
    public void testRegex() {
        String str = "1716 -> 1230;";
        String arr[] = str.split("( -> )|(;)");
        assertEquals(2, arr.length);
        assertEquals("1716", arr[0]);
        assertEquals("1230", arr[1]);
    }

    @Test
    public void testSimple() throws IOException {
        BufferedReader stringInput = new BufferedReader(new StringReader(
                "1716 -> 1230;\n"+
                "1716 -> 367;"));
        Graph<String,ContigEdge> graph = new GraphReader(stringInput).read();
        assertEquals(true, graph.containsVertex("1716"));
        assertEquals(true, graph.containsVertex("1230"));
        assertEquals(true, graph.containsVertex("367"));
        assertEquals(true, graph.containsEdge("1716", "1230"));
        assertEquals(true, graph.containsEdge("1716", "367"));
        assertEquals(false, graph.containsEdge( "367", "1716"));
    }
}
