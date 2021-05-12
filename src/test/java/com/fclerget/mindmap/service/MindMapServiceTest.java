package com.fclerget.mindmap.service;

import com.fclerget.mindmap.model.Leaf;
import com.fclerget.mindmap.model.MindMap;
import com.fclerget.mindmap.repository.LeafRepository;
import com.fclerget.mindmap.repository.MindMapRepository;
import org.assertj.core.util.Lists;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class MindMapServiceTest {

    @Autowired
    private MindMapRepository mindMapRepository;

    @Autowired
    private LeafRepository leafRepository;

    @Autowired
    private MindMapService mindMapService;

    @BeforeEach
    public void setup() {

        mindMapRepository.deleteAll();
        leafRepository.deleteAll();

        MindMap mindMap = new MindMap();
        mindMap.setId("mindmap");
        mindMapRepository.save(mindMap);

        Leaf leaf1 = new Leaf();
        leaf1.setPath("leaf1");
        leaf1.setText("text");
        leaf1.setMindMap(mindMap);
        leafRepository.save(leaf1);

        Leaf leaf2 = new Leaf();
        leaf2.setPath("leaf2");
        leaf2.setText("text");
        leaf2.setMindMap(mindMap);
        leafRepository.save(leaf2);
    }

    @Test
    void create() {

        MindMap newMindMap = new MindMap();
        newMindMap.setId("newMindMap");

        mindMapService.create(newMindMap);

        boolean exists = mindMapRepository.existsById("newMindMap");

        MatcherAssert.assertThat(exists, Matchers.is(true));
    }

    @Test
    void findAll() {

        List<MindMap> all = mindMapService.findAll();

        MatcherAssert.assertThat(all.size(), Matchers.is(1));
    }

    @Test
    void findById() {

        Optional<MindMap> optional = mindMapService.findById("mindmap");

        MatcherAssert.assertThat(optional.isPresent(), Matchers.is(true));
    }

    @Test
    void delete() {

        mindMapService.delete("mindmap");

        boolean exists = mindMapRepository.existsById("mindmap");

        MatcherAssert.assertThat(exists, Matchers.is(false));

        ArrayList<Leaf> leaves = Lists.newArrayList(leafRepository.findAll());

        MatcherAssert.assertThat(leaves.size(), Matchers.is(0));
    }

    @Test
    void exists() {

        boolean exists = mindMapService.exists("mindmap");

        MatcherAssert.assertThat(exists, Matchers.is(true));
    }
}