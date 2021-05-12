package com.fclerget.mindmap.repository;

import com.fclerget.mindmap.model.Leaf;
import com.fclerget.mindmap.model.MindMap;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class LeafRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LeafRepository repository;

    @Test
    public void findLeafById() {

        MindMap mindMap = new MindMap();
        mindMap.setId("mindmap");

        mindMap = entityManager.persistAndFlush(mindMap);

        Leaf leaf = new Leaf();
        leaf.setPath("path");
        leaf.setText("text");
        leaf.setMindMap(mindMap);
        leaf = entityManager.persistAndFlush(leaf);

        MatcherAssert.assertThat(repository.findById(leaf.getPath()).isPresent(), Matchers.is(true));
    }

    @Test
    public void findLeafByPath() {

        MindMap mindMap = new MindMap();
        mindMap.setId("mindmap");

        mindMap = entityManager.persistAndFlush(mindMap);

        Leaf leaf = new Leaf();
        leaf.setPath("path");
        leaf.setText("text");
        leaf.setMindMap(mindMap);
        leaf = entityManager.persistAndFlush(leaf);

        MatcherAssert.assertThat(repository.findByMindMapIdAndPathStartingWith("mindmap", leaf.getPath()).size(), Matchers.is(1));
    }
}