package com.fclerget.mindmap.model;

import org.assertj.core.util.Lists;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class MindMapTest {

    @Test
    void testEquals() {

        MindMap mindMap1 = new MindMap();

        mindMap1.setId("mindmap");

        MindMap mindMap2 = new MindMap();

        mindMap2.setId("mindmap");

        MatcherAssert.assertThat(mindMap1.equals(mindMap2), Matchers.is(true));
    }

    @Test
    void testId() {

        MindMap mindMap1 = new MindMap();

        mindMap1.setId("mindmap");

        MatcherAssert.assertThat(mindMap1.getId(), Matchers.is("mindmap"));
    }

    @Test
    void testLeaves() {

        Leaf leaf = new Leaf();
        leaf.setPath("path");
        leaf.setText("text");

        MindMap mindMap1 = new MindMap();

        mindMap1.setId("mindmap");
        mindMap1.setLeaves(Lists.newArrayList(leaf));

        MatcherAssert.assertThat(mindMap1.getLeaves().get(0).getPath(), Matchers.is("path"));
    }

    @Test
    void testHashCode() {

        MindMap mindMap1 = new MindMap();

        mindMap1.setId("mindmap");

        MindMap mindMap2 = new MindMap();

        mindMap2.setId("mindmap");

        MatcherAssert.assertThat(mindMap1.hashCode(), Matchers.is(mindMap2.hashCode()));
    }
}