package com.fclerget.mindmap.controller;

import com.fclerget.mindmap.model.Leaf;
import com.fclerget.mindmap.service.LeafService;
import com.fclerget.mindmap.service.MindMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/leaf")
public class LeafController {

    private final LeafService leafService;

    private final MindMapService mindMapService;

    @Autowired
    public LeafController(LeafService leafService,
                          MindMapService mindMapService) {
        this.leafService = leafService;
        this.mindMapService = mindMapService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Leaf> createLeaf(@RequestParam String mindMapId, @RequestBody Leaf leaf) {

        if (!mindMapService.exists(mindMapId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (leafService.exists(leaf.getPath())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        leaf = leafService.putLeaf(mindMapId, leaf);

        return new ResponseEntity<>(leaf, HttpStatus.CREATED);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Leaf> getLeaf(@RequestParam String mindMapId, @RequestParam String path) {

        Optional<Leaf> leafOptional = leafService.findBymindMapIdAndPath(mindMapId, path);

        return leafOptional
                .map(leaf -> new ResponseEntity<>(leaf, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Leaf> putLeaf(@RequestParam String mindMapId, @RequestBody Leaf leaf) {

        if (!mindMapService.exists(mindMapId) || !leafService.exists(leaf.getPath())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        leaf = leafService.putLeaf(mindMapId, leaf);

        return new ResponseEntity<>(leaf, HttpStatus.OK);
    }

    @DeleteMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteLeaf(@RequestParam String path) {

        if (!leafService.exists(path)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        leafService.deleteLeaf(path);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
