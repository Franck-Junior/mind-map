package com.fclerget.mindmap.service;

import com.fclerget.mindmap.model.Leaf;
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
class MindMapServiceTest {

    @Autowired
    private MindMapRepository mindMapRepository;

    @Autowired
    private MindMapService mindMapService;

    @BeforeEach
    public void setup() {
        Leaf leafToGet = new Leaf();
        leafToGet.setPath("test/leafToGet");
        leafToGet.setText("text");
        mindMapRepository.save(leafToGet);
        Leaf leafToDelete = new Leaf();
        leafToDelete.setPath("test/leafToDelete");
        leafToDelete.setText("text");
        mindMapRepository.save(leafToDelete);
        Leaf existingLeaf = new Leaf();
        existingLeaf.setPath("test/existingLeaf");
        existingLeaf.setText("text");
        mindMapRepository.save(existingLeaf);
        Leaf partialLeaf1 = new Leaf();
        partialLeaf1.setPath("partial/leaf1");
        partialLeaf1.setText("text");
        mindMapRepository.save(partialLeaf1);
        Leaf partialLeaf2 = new Leaf();
        partialLeaf2.setPath("partial/leaf2");
        partialLeaf2.setText("text");
        mindMapRepository.save(partialLeaf2);
    }

    @Test
    public void putLeafNewLeaf() {
        Leaf leaf = new Leaf();
        leaf.setPath("test/newleaf");
        leaf.setText("text");
        Leaf savedLeaf = mindMapService.putLeaf(leaf);

        MatcherAssert.assertThat(savedLeaf, Matchers.is(leaf));
    }

    @Test
    public void putLeafExistingLeaf() {
        Leaf leaf = new Leaf();
        leaf.setPath("test/existingLeaf");
        leaf.setText("newtext");
        Leaf savedLeaf = mindMapService.putLeaf(leaf);

        MatcherAssert.assertThat(savedLeaf, Matchers.is(leaf));
    }

    @Test
    public void getLeaf() {
        Optional<Leaf> leafOptional = mindMapService.getLeaf("test/leafToGet");

        MatcherAssert.assertThat(leafOptional.isPresent(), Matchers.is(true));
        MatcherAssert.assertThat(leafOptional.get().getPath(), Matchers.is("test/leafToGet"));
    }


    @Test
    public void deleteValidLeaf() {

        boolean exists = mindMapRepository.existsById("test/leafToDelete");

        MatcherAssert.assertThat(exists, Matchers.is(true));

        mindMapService.deleteLeaf("test/leafToDelete");

        exists = mindMapRepository.existsById("test/leafToDelete");

        MatcherAssert.assertThat(exists, Matchers.is(false));
    }

    @Test
    public void deleteInvalidLeaf() {

        boolean exists = mindMapRepository.existsById("test/invalidLeafToDelete");

        MatcherAssert.assertThat(exists, Matchers.is(false));

        mindMapService.deleteLeaf("test/invalidLeafToDelete");

        exists = mindMapRepository.existsById("test/invalidLeafToDelete");

        MatcherAssert.assertThat(exists, Matchers.is(false));

    }

    @Test
    public void existLeafExceptTrue() {

        boolean exists = mindMapService.exists("test/leafToGet");

        MatcherAssert.assertThat(exists, Matchers.is(true));
    }

    @Test
    public void existLeafExceptFalse() {

        boolean exists = mindMapService.exists("test/leafNotPresent");

        MatcherAssert.assertThat(exists, Matchers.is(false));
    }

    @Test
    public void findNoLeaves() {

        List<Leaf> leaves = mindMapService.getLeaves("test/leafNotPresent");

        MatcherAssert.assertThat(leaves.isEmpty(), Matchers.is(true));
    }

    @Test
    public void findOnlyOneLeaves() {

        List<Leaf> leaves = mindMapService.getLeaves("test/leafToGet");

        MatcherAssert.assertThat(leaves.size(), Matchers.is(1));
    }

    @Test
    public void findAllLeavesWithPartialPath() {

        List<Leaf> leaves = mindMapService.getLeaves("partial/");

        MatcherAssert.assertThat(leaves.size(), Matchers.is(2));
    }

    @Test
    public void findAllLeaves() {

        List<Leaf> leaves = mindMapService.getLeaves("");

        MatcherAssert.assertThat(leaves.size(), Matchers.is(5));
    }

}