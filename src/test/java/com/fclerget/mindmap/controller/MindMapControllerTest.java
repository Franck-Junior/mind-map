package com.fclerget.mindmap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fclerget.mindmap.model.Leaf;
import com.fclerget.mindmap.service.MindMapService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

@WebMvcTest(controllers = MindMapController.class)
class MindMapControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MindMapService mindMapService;

    @BeforeEach
    public void setup() {

        Leaf validLeaf = new Leaf();

        validLeaf.setPath("test/found");
        validLeaf.setText("text");

        Leaf updatedValidLeaf = new Leaf();

        updatedValidLeaf.setPath("test/found");
        updatedValidLeaf.setText("newtext");

        Mockito.when(mindMapService.getLeaf("test/found")).thenReturn(Optional.of(validLeaf));
        Mockito.when(mindMapService.exists("test/found")).thenReturn(true);
        Mockito.when(mindMapService.exists("test/not_found")).thenReturn(false);
        Mockito.when(mindMapService.putLeaf(validLeaf)).thenReturn(validLeaf);
        Mockito.when(mindMapService.putLeaf(updatedValidLeaf)).thenReturn(updatedValidLeaf);
    }

    @Test
    public void getLeafWithValidPath() throws Exception {

        String baseURI = "/api/v1/mindmap";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(baseURI + "/test/found")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.OK.value()));

        String content = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        Leaf leaf = objectMapper.readValue(content, Leaf.class);

        MatcherAssert.assertThat(leaf.getPath(), Matchers.is("test/found"));
        MatcherAssert.assertThat(leaf.getText(), Matchers.is("text"));
    }

    @Test
    public void getLeafWithInvalidPath() throws Exception {

        String baseURI = "/api/v1/mindmap";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(baseURI + "/test/no_found")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void createLeaf() throws Exception {

        Leaf validLeaf = new Leaf();
        validLeaf.setPath("test/found");
        validLeaf.setText("text");

        ObjectMapper objectMapper = new ObjectMapper();

        String leafJSON = objectMapper.writeValueAsString(validLeaf);

        String baseURI = "/api/v1/mindmap";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .post(baseURI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(leafJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.CREATED.value()));

        String content = mvcResult.getResponse().getContentAsString();

        Leaf leaf = objectMapper.readValue(content, Leaf.class);

        MatcherAssert.assertThat(leaf.getPath(), Matchers.is("test/found"));
        MatcherAssert.assertThat(leaf.getText(), Matchers.is("text"));
    }

    @Test
    public void putLeafWithValidPath() throws Exception {

        Leaf validLeaf = new Leaf();
        validLeaf.setPath("test/found");
        validLeaf.setText("newtext");

        ObjectMapper objectMapper = new ObjectMapper();

        String leafJSON = objectMapper.writeValueAsString(validLeaf);

        String baseURI = "/api/v1/mindmap";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .put(baseURI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(leafJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.OK.value()));

        String content = mvcResult.getResponse().getContentAsString();

        Leaf leaf = objectMapper.readValue(content, Leaf.class);

        MatcherAssert.assertThat(leaf.getPath(), Matchers.is("test/found"));
        MatcherAssert.assertThat(leaf.getText(), Matchers.is("newtext"));
    }

    @Test
    public void putLeafWithInvalidPath() throws Exception {

        Leaf validLeaf = new Leaf();
        validLeaf.setPath("test/not_found");
        validLeaf.setText("newtext");

        ObjectMapper objectMapper = new ObjectMapper();

        String leafJSON = objectMapper.writeValueAsString(validLeaf);

        String baseURI = "/api/v1/mindmap";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .put(baseURI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(leafJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void deleteValidLeaf() throws Exception {

        String baseURI = "/api/v1/mindmap";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(baseURI + "/test/found")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void deleteInvalidLeaf() throws Exception {

        String baseURI = "/api/v1/mindmap";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(baseURI + "/test/not_found")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.NOT_FOUND.value()));
    }
}