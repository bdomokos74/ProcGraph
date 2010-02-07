package org.bds.graph;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.bds.test.TestHelper;
import org.jgrapht.DirectedGraph;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Feb 6, 2010
 * Time: 9:13:25 PM
 */
public class GraphWriterTest {
    @Test
    public void testWrite() throws IOException {
        GraphReader reader = new GraphReader(new TestHelper().getResourceReader("/spanningtree_test.gv"));
        final DirectedGraph<String, ContigEdge> graph = reader.read();
        StringWriter strWriter = new StringWriter();
        LibSeaWriter writer = new LibSeaWriter(graph, new BufferedWriter(strWriter));
        writer.write("0");

        assertEquals(new TestHelper().resourceToString("/walrustest.graph"), strWriter.toString());
    }
}
