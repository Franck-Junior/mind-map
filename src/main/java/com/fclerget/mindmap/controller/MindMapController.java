package com.fclerget.mindmap.controller;

import com.fclerget.mindmap.model.MindMap;
import com.fclerget.mindmap.service.MindMapService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mindmap")
public class MindMapController {

    private final MindMapService mindMapService;

    @Autowired
    public MindMapController(MindMapService mindMapService) {
        this.mindMapService = mindMapService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MindMap> create(@RequestBody MindMap mindMap) {

        mindMap = mindMapService.create(mindMap);

        return new ResponseEntity<>(mindMap, HttpStatus.CREATED);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MindMap>> get(@RequestParam(required = false, defaultValue = "") String id) {

        List<MindMap> mindMaps = Lists.newArrayList();

        if (StringUtils.isBlank(id)) {
            mindMaps = mindMapService.findAll();
        } else {
            mindMapService
                    .findById(id)
                    .ifPresent(mindMaps::add);
        }

        return new ResponseEntity<>(mindMaps, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String id) {

        mindMapService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
