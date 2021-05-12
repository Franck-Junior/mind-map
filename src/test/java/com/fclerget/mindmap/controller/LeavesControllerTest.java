package com.fclerget.mindmap.controller;

import com.fclerget.mindmap.converter.LeavesConverter;
import com.fclerget.mindmap.model.Leaf;
import com.fclerget.mindmap.model.MindMap;
import com.fclerget.mindmap.service.LeafService;
import org.assertj.core.util.Lists;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = LeavesController.class)
class LeavesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LeafService leafService;

    @MockBean
    private LeavesConverter leavesConverter;

    @BeforeEach
    public void setup() {

        MindMap mindMap = new MindMap();
        mindMap.setId("mindmap");

        Leaf validLeaf = new Leaf();

        validLeaf.setPath("test/found");
        validLeaf.setText("text");
        validLeaf.setMindMap(mindMap);

        Leaf newLeaf = new Leaf();

        newLeaf.setPath("test/newleaf");
        newLeaf.setText("text");
        newLeaf.setMindMap(mindMap);

        Leaf updatedValidLeaf = new Leaf();

        updatedValidLeaf.setPath("test/found");
        updatedValidLeaf.setText("newtext");
        updatedValidLeaf.setMindMap(mindMap);

        Mockito.when(leafService.findByMindMapIdAndPathStartingWith(Mockito.anyString(), Mockito.anyString())).thenReturn(Lists.newArrayList());
        Mockito.when(leavesConverter.convert(Mockito.anyList())).thenReturn("test");
    }

    @Test
    public void getLeaves() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/leaves")
                .queryParam("mindMapId", "mindmap")
                .queryParam("path", "")
                .accept(MediaType.TEXT_PLAIN_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.OK.value()));

        String content = mvcResult.getResponse().getContentAsString();

        MatcherAssert.assertThat(content, Matchers.is("test"));

    }

}