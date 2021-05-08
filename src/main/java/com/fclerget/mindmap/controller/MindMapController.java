package com.fclerget.mindmap.controller;

import com.fclerget.mindmap.model.Leaf;
import com.fclerget.mindmap.service.MindMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Leaf> createLeaf(@RequestBody Leaf leaf) {

        leaf = mindMapService.putLeaf(leaf);

        return new ResponseEntity<>(leaf, HttpStatus.CREATED);
    }

    @GetMapping(value = "/**",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Leaf> getLeaf(HttpServletRequest request) {

        String requestURL = request.getRequestURL().toString();

        String path = extractPathFromURL(requestURL);

        Optional<Leaf> leafOptional = mindMapService.getLeaf(path);

        return leafOptional
                .map(leaf -> new ResponseEntity<>(leaf, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Leaf> putLeaf(@RequestBody Leaf leaf) {

        if (!mindMapService.exists(leaf.getPath())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        leaf = mindMapService.putLeaf(leaf);

        return new ResponseEntity<>(leaf, HttpStatus.OK);
    }

    @DeleteMapping(value = "/**",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteLeaf(HttpServletRequest request) {

        String requestURL = request.getRequestURL().toString();

        String path = extractPathFromURL(requestURL);

        if (!mindMapService.exists(path)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        mindMapService.deleteLeaf(path);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String extractPathFromURL(String requestURL) {
        return requestURL.split("/mindmap/")[1];
    }
}
