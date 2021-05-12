package com.fclerget.mindmap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fclerget.mindmap.model.MindMap;
import com.fclerget.mindmap.service.MindMapService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = MindMapController.class)
class MindMapControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MindMapService mindMapService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void getMindMapWithoutId() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/mindmap")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(200));

        Mockito.verify(mindMapService, Mockito.times(1)).findAll();
    }

    @Test
    public void getMindMapWithId() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/mindmap")
                .queryParam("id", "mindmap")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(200));

        Mockito.verify(mindMapService, Mockito.times(1)).findById("mindmap");
    }

    @Test
    public void createMindMap() throws Exception {

        MindMap mindMap = new MindMap();
        mindMap.setId("mindmap");

        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/mindmap")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mindMap))
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(201));

        Mockito.verify(mindMapService, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    public void deleteMindMap() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/mindmap")
                .queryParam("id", "mindmap")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mvc.perform(request).andReturn();

        int status = mvcResult.getResponse().getStatus();

        MatcherAssert.assertThat(status, Matchers.is(200));

        Mockito.verify(mindMapService, Mockito.times(1)).delete("mindmap");
    }
}