package com.fclerget.mindmap.model;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class LeafTest {

    @Test
    public void testEquals() {

        Leaf leaf1 = new Leaf();

        leaf1.setPath("path");
        leaf1.setText("text");
        leaf1.setMindMap(new MindMap());

        Leaf leaf2 = new Leaf();

        leaf2.setPath("path");
        leaf2.setText("text");

        MatcherAssert.assertThat(leaf1.hashCode(), Matchers.is(leaf2.hashCode()));
    }

    @Test
    public void testMindMap() {

        MindMap mindMap = new MindMap();

        mindMap.setId("mindmap");

        Leaf leaf1 = new Leaf();

        leaf1.setPath("path");
        leaf1.setText("text");
        leaf1.setMindMap(mindMap);

        MatcherAssert.assertThat(leaf1.getMindMap().getId(), Matchers.is("mindmap"));
    }

}