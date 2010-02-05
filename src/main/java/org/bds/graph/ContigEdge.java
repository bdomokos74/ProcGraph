package org.bds.graph;

import org.jgrapht.graph.DefaultEdge;

/**
 * Astrid Research
 * Author: balint
 * Created: Feb 4, 2010 3:46:29 PM
 */
public class ContigEdge extends DefaultEdge {
    private String id;

    public ContigEdge() {
        id = null;
    }

    public ContigEdge(String id) {
        this.id = null;
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContigEdge that = (ContigEdge) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
