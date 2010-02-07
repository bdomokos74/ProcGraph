package org.bds.graph;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Feb 7, 2010
 * Time: 4:08:35 PM
 */
public class DotWriter {
    private BufferedWriter writer;
    private boolean started;
    private Type graphType = Type.Directed;
    public enum Type {
        Directed("digraph", "->"), Undirected("graph", "--");
        private String type;
        private String separator;

        Type(String type, String separator) {
            this.type = type;
            this.separator = separator;
        }

        public String getType() {
            return type;
        }

        public String getSeparator() {
            return separator;
        }
    };

    public DotWriter(BufferedWriter writer, Type graphType) {
        this.writer = writer;
        this.graphType = graphType;
        this.started = false;
    }

    public void addEdge(String a, String b) throws IOException {
        if (!started) {
            writer.write(String.format("%s G {\n", graphType.getType()));
            writer.write(String.format("node [shape=circle, fixedsize=true];\n"));
            started = true;
        }
        writer.write(String.format("%s %s %s;\n", a, graphType.getSeparator(), b));
    }

    public void close() throws IOException {
        writer.write("}");
        writer.close();
    }
}
