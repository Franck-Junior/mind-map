package com.fclerget.mindmap.converter;

import com.fclerget.mindmap.model.Leaf;
import com.google.common.collect.Lists;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

class LeavesConverterTest {

    @Spy
    private LeavesConverter leavesConverter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void convertWithCommons() {

        List<Leaf> leaves = Lists.newArrayList();

        Leaf leaf1 = new Leaf();
        leaf1.setPath("I/like/potatoes");
        leaf1.setText("text");

        Leaf leaf2 = new Leaf();
        leaf2.setPath("I/like/fries");
        leaf2.setText("text");

        leaves.add(leaf1);
        leaves.add(leaf2);

        String convertedLeaves = leavesConverter.convert(leaves);

        MatcherAssert.assertThat(convertedLeaves, Matchers.is("root/\n" +
                "\tI/\n" +
                "\t\tlike/\n" +
                "\t\t\tpotatoes\n" +
                "\t\t\tfries\n"));
    }

    @Test
    public void convertNoCommons() {

        List<Leaf> leaves = Lists.newArrayList();

        Leaf leaf1 = new Leaf();
        leaf1.setPath("I/like/potatoes");
        leaf1.setText("text");

        Leaf leaf2 = new Leaf();
        leaf2.setPath("travel/france");
        leaf2.setText("text");

        leaves.add(leaf1);
        leaves.add(leaf2);

        String convertedLeaves = leavesConverter.convert(leaves);

        MatcherAssert.assertThat(convertedLeaves, Matchers.is("root/\n" +
                "\tI/\n" +
                "\t\tlike/\n" +
                "\t\t\tpotatoes\n" +
                "\ttravel/\n" +
                "\t\tfrance\n"));
    }

    @Test
    public void convertOnlyOneElement() {

        List<Leaf> leaves = Lists.newArrayList();

        Leaf leaf1 = new Leaf();
        leaf1.setPath("I/like/potatoes");
        leaf1.setText("text");

        leaves.add(leaf1);

        String convertedLeaves = leavesConverter.convert(leaves);

        MatcherAssert.assertThat(convertedLeaves, Matchers.is("root/\n" +
                "\tI/\n" +
                "\t\tlike/\n" +
                "\t\t\tpotatoes\n"));
    }

    @Test
    public void convertWithDifferentLength() {

        List<Leaf> leaves = Lists.newArrayList();

        Leaf leaf1 = new Leaf();
        leaf1.setPath("I/like/potatoes");
        leaf1.setText("text");

        Leaf leaf2 = new Leaf();
        leaf2.setPath("I/like/potatoes/and/fries");
        leaf2.setText("text");

        leaves.add(leaf1);
        leaves.add(leaf2);

        String convertedLeaves = leavesConverter.convert(leaves);

        MatcherAssert.assertThat(convertedLeaves, Matchers.is("root/\n" +
                "\tI/\n" +
                "\t\tlike/\n" +
                "\t\t\tpotatoes\n" +
                "\t\t\tpotatoes/\n" +
                "\t\t\t\tand/\n" +
                "\t\t\t\t\tfries\n"));
    }

}