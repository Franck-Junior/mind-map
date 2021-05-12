package com.fclerget.mindmap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fclerget.mindmap.model.Leaf;
import com.fclerget.mindmap.model.MindMap;
import com.fclerget.mindmap.service.LeafService;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

@WebMvcTest(controllers = LeafController.class)
class LeafControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LeafService leafService;

    @MockBean
    private MindMapService mindMapService;

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

        Mockito.when(leafService.findBymindMapIdAndPath("mindmap", "test/found")).thenReturn(Optional.of(validLeaf));
        Mockito.when(leafService.exists("test/found")).thenReturn(true);
        Mockito.when(leafService.exists("test/not_found")).thenReturn(false);
        Mockito.when(leafService.putLeaf("mindmap", validLeaf)).thenReturn(validLeaf);
        Mockito.when(leafService.putLeaf("mindmap", updatedValidLeaf)).thenReturn(updatedValidLeaf);
        Mockito.when(leafService.putLeaf("mindmap", newLeaf)).thenReturn(newLeaf);

        Mockito.when(mindMapService.exists(Mockito.anyString())).thenReturn(false);
        Mockito.when(mindMapService.exists("mindmap")).thenReturn(true);
    }

    // GET /api/v1/leaf?mindMapId=&path=
    // POST /api/v1/leaf?mindMapId=
    // PUT /api/v1/leaf?mindMapId=&leafId=
    // DELETE /api/v1/leaf?mindMapId=&leafId=

    @Test
    public void getLeafWithValidPath() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/leaf")
                .queryParam("mindMapId", "mindmap")
                .queryParam("path", "test/found")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

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

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/leaf")
                .queryParam("mindMapId", "mindmap")
                .queryParam("path", "test/not_found")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void createLeaf() throws Exception {

        Leaf validLeaf = new Leaf();
        validLeaf.setPath("test/newleaf");
        validLeaf.setText("text");

        ObjectMapper objectMapper = new ObjectMapper();

        String leafJSON = objectMapper.writeValueAsString(validLeaf);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/leaf")
                .queryParam("mindMapId", "mindmap")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(leafJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.CREATED.value()));

        String content = mvcResult.getResponse().getContentAsString();

        Leaf leaf = objectMapper.readValue(content, Leaf.class);

        MatcherAssert.assertThat(leaf.getPath(), Matchers.is("test/newleaf"));
        MatcherAssert.assertThat(leaf.getText(), Matchers.is("text"));
    }

    @Test
    public void createLeafWithInvalidMindMap() throws Exception {

        Leaf validLeaf = new Leaf();
        validLeaf.setPath("test/found");
        validLeaf.setText("text");

        ObjectMapper objectMapper = new ObjectMapper();

        String leafJSON = objectMapper.writeValueAsString(validLeaf);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/leaf")
                .queryParam("mindMapId", "invalidMindmap")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(leafJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void createLeafWithInvalidLeafPath() throws Exception {

        Leaf validLeaf = new Leaf();
        validLeaf.setPath("test/found");
        validLeaf.setText("text");

        ObjectMapper objectMapper = new ObjectMapper();

        String leafJSON = objectMapper.writeValueAsString(validLeaf);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/leaf")
                .queryParam("mindMapId", "mindmap")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(leafJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void putLeafWithValidPath() throws Exception {

        Leaf validLeaf = new Leaf();
        validLeaf.setPath("test/found");
        validLeaf.setText("newtext");

        ObjectMapper objectMapper = new ObjectMapper();

        String leafJSON = objectMapper.writeValueAsString(validLeaf);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/api/v1/leaf")
                .queryParam("mindMapId", "mindmap")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(leafJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.OK.value()));

        String content = mvcResult.getResponse().getContentAsString();

        Leaf leaf = objectMapper.readValue(content, Leaf.class);

        MatcherAssert.assertThat(leaf.getPath(), Matchers.is("test/found"));
        MatcherAssert.assertThat(leaf.getText(), Matchers.is("newtext"));
    }

    @Test
    public void putLeafWithValidPathAndInvalidMindMap() throws Exception {

        Leaf validLeaf = new Leaf();
        validLeaf.setPath("test/found");
        validLeaf.setText("newtext");

        ObjectMapper objectMapper = new ObjectMapper();

        String leafJSON = objectMapper.writeValueAsString(validLeaf);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/api/v1/leaf")
                .queryParam("mindMapId", "invalidMindmap")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(leafJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void putLeafWithInvalidPath() throws Exception {

        Leaf validLeaf = new Leaf();
        validLeaf.setPath("test/not_found");
        validLeaf.setText("newtext");

        ObjectMapper objectMapper = new ObjectMapper();

        String leafJSON = objectMapper.writeValueAsString(validLeaf);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/api/v1/leaf")
                .queryParam("mindMapId", "mindmap")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(leafJSON)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void deleteValidLeaf() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/leaf")
                .queryParam("mindMapId", "mindmap")
                .queryParam("path", "test/found")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    public void deleteInvalidLeaf() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/leaf")
                .queryParam("mindMapId", "mindmap")
                .queryParam("path", "test/not_found")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(HttpStatus.NOT_FOUND.value()));
    }
}