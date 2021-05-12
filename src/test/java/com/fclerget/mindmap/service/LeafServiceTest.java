package com.fclerget.mindmap.service;

import com.fclerget.mindmap.model.Leaf;
import com.fclerget.mindmap.model.MindMap;
import com.fclerget.mindmap.repository.LeafRepository;
import com.fclerget.mindmap.repository.MindMapRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class LeafServiceTest {

    @Autowired
    private LeafRepository leafRepository;

    @Autowired
    private LeafService leafService;

    @Autowired
    private MindMapRepository mindMapRepository;

    @BeforeEach
    public void setup() {

        MindMap mindMap = new MindMap();
        mindMap.setId("mindmap");

        mindMap = mindMapRepository.save(mindMap);

        MindMap singleMindMap = new MindMap();
        singleMindMap.setId("singleMindMap");

        singleMindMap = mindMapRepository.save(singleMindMap);

        Leaf leafToGet = new Leaf();
        leafToGet.setPath("test/leafToGet");
        leafToGet.setText("text");
        leafToGet.setMindMap(mindMap);
        leafRepository.save(leafToGet);

        Leaf leafToDelete = new Leaf();
        leafToDelete.setPath("test/leafToDelete");
        leafToDelete.setText("text");
        leafToDelete.setMindMap(singleMindMap);
        leafRepository.save(leafToDelete);

        Leaf existingLeaf = new Leaf();
        existingLeaf.setPath("test/existingLeaf");
        existingLeaf.setText("text");
        existingLeaf.setMindMap(mindMap);
        leafRepository.save(existingLeaf);

        Leaf partialLeaf1 = new Leaf();
        partialLeaf1.setPath("partial/leaf1");
        partialLeaf1.setText("text");
        partialLeaf1.setMindMap(mindMap);
        leafRepository.save(partialLeaf1);

        Leaf partialLeaf2 = new Leaf();
        partialLeaf2.setPath("partial/leaf2");
        partialLeaf2.setText("text");
        partialLeaf2.setMindMap(mindMap);
        leafRepository.save(partialLeaf2);
    }

    @Test
    public void putLeafNewLeaf() {
        Leaf leaf = new Leaf();
        leaf.setPath("test/newleaf");
        leaf.setText("text");
        Leaf savedLeaf = leafService.putLeaf("mindmap", leaf);

        MatcherAssert.assertThat(savedLeaf, Matchers.is(leaf));
    }

    @Test
    public void putLeafExistingLeaf() {
        Leaf leaf = new Leaf();
        leaf.setPath("test/existingLeaf");
        leaf.setText("newtext");
        Leaf savedLeaf = leafService.putLeaf("mindmap", leaf);

        MatcherAssert.assertThat(savedLeaf, Matchers.is(leaf));
    }

    @Test
    public void putLeafWithInvalidMindMap() {
        Leaf leaf = new Leaf();
        leaf.setPath("test/existingLeaf");
        leaf.setText("newtext");
        Leaf savedLeaf = leafService.putLeaf("invalidMindmap", leaf);

        boolean isNull = savedLeaf == null;

        MatcherAssert.assertThat(isNull, Matchers.is(true));
    }

    @Test
    public void getLeaf() {
        Optional<Leaf> leafOptional = leafService.findBymindMapIdAndPath("mindmap", "test/leafToGet");

        MatcherAssert.assertThat(leafOptional.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat(leafOptional.get().getPath(), Matchers.is("test/leafToGet"));
    }


    @Test
    public void deleteValidLeaf() {

        boolean exists = leafRepository.existsById("test/leafToDelete");

        MatcherAssert.assertThat(exists, Matchers.is(true));

        leafService.deleteLeaf("test/leafToDelete");

        exists = leafRepository.existsById("test/leafToDelete");

        MatcherAssert.assertThat(exists, Matchers.is(false));

        boolean mindMapExists = mindMapRepository.existsById("singleMindMap");

        MatcherAssert.assertThat(mindMapExists, Matchers.is(true));
    }

    @Test
    public void deleteInvalidLeaf() {

        boolean exists = leafRepository.existsById("test/invalidLeafToDelete");

        MatcherAssert.assertThat(exists, Matchers.is(false));

        leafService.deleteLeaf("test/invalidLeafToDelete");

        exists = leafRepository.existsById("test/invalidLeafToDelete");

        MatcherAssert.assertThat(exists, Matchers.is(false));

    }

    @Test
    public void existLeafExceptTrue() {

        boolean exists = leafService.exists("test/leafToGet");

        MatcherAssert.assertThat(exists, Matchers.is(true));
    }

    @Test
    public void existLeafExceptFalse() {

        boolean exists = leafService.exists("test/leafNotPresent");

        MatcherAssert.assertThat(exists, Matchers.is(false));
    }

    @Test
    public void findNoLeaves() {

        List<Leaf> leaves = leafService.findByMindMapIdAndPathStartingWith("mindmap", "test/leafNotPresent");

        MatcherAssert.assertThat(leaves.isEmpty(), Matchers.is(true));
    }

    @Test
    public void findOnlyOneLeaves() {

        List<Leaf> leaves = leafService.findByMindMapIdAndPathStartingWith("mindmap", "test/leafToGet");

        MatcherAssert.assertThat(leaves.size(), Matchers.is(1));
    }

    @Test
    public void findAllLeavesWithPartialPath() {

        List<Leaf> leaves = leafService.findByMindMapIdAndPathStartingWith("mindmap", "partial/");

        MatcherAssert.assertThat(leaves.size(), Matchers.is(2));
    }

    @Test
    public void findAllLeaves() {

        List<Leaf> leaves = leafService.findByMindMapIdAndPathStartingWith("mindmap", "");

        MatcherAssert.assertThat(leaves.size(), Matchers.is(4));
    }

}