package org.bds.graph;

import org.jgrapht.Graph;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.Set;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Feb 5, 2010
 * Time: 10:46:38 PM
 */
public class SpanningTree {
    private Graph<String, ContigEdge> graph;
    private boolean traverseCrossComponent = false;

    public SpanningTree(Graph<String, ContigEdge> graph, boolean traverseCrossComponent) {
        this.graph = graph;
        this.traverseCrossComponent = traverseCrossComponent;
    }

    public void setCrossComponentTraversal(boolean traverseCrossComponent) {

        this.traverseCrossComponent = traverseCrossComponent;
    }

    public Set<ContigEdge> getSpanningTree(String startVertex) {
        final Set<String> processed = new HashSet<String>();
        final Set<ContigEdge> spanningTree = new HashSet<ContigEdge>();


        BreadthFirstIterator<String, ContigEdge> traverser = new BreadthFirstIterator(graph, startVertex);
        traverser.setCrossComponentTraversal(traverseCrossComponent);

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
                    spanningTree.add(event.getEdge());
                    processed.add(graph.getEdgeSource(event.getEdge()));
                    processed.add(graph.getEdgeTarget(event.getEdge()));
                    System.out.printf("edgetraversed %s(%s->%s)\n", event.getEdge().getId(),
                            graph.getEdgeSource(event.getEdge()), graph.getEdgeTarget(event.getEdge()));
                } else {
//                    if (graph.edgesOf(graph.getEdgeSource(event.getEdge())).size() == 1) {
//                        spanningTree.add(event.getEdge());
//                        System.out.printf("adding edge: %s\n", event.getEdge());
//                    } else {
                    System.out.printf("edgetraversed %s(%s->%s), but target already processed\n", event.getEdge().getId(),
                            graph.getEdgeSource(event.getEdge()), graph.getEdgeTarget(event.getEdge()));
//                    }
                }
            }

            @Override
            public void vertexTraversed(VertexTraversalEvent<String> event) {
            }

            @Override
            public void vertexFinished(VertexTraversalEvent<String> event) {
            }
        });

        while (traverser.hasNext()) {
            String curr = traverser.next();
            System.out.printf("traversing: %s\n", curr);
        }
        return spanningTree;
    }
}
