package com.fclerget.mindmap.controller;

import com.fclerget.mindmap.converter.LeavesConverter;
import com.fclerget.mindmap.model.Leaf;
import com.fclerget.mindmap.service.LeafService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leaves")
public class LeavesController {

    private final LeafService leafService;
    private final LeavesConverter leavesConverter;

    @Autowired
    public LeavesController(LeafService leafService,
                            LeavesConverter leavesConverter) {

        this.leafService = leafService;
        this.leavesConverter = leavesConverter;
    }

    @GetMapping(
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> getLeaves(@RequestParam String mindMapId, @RequestParam(required = false, defaultValue = "") String path) {

        List<Leaf> leaves = leafService.findByMindMapIdAndPathStartingWith(mindMapId, path);

        String leavesStr = leavesConverter.convert(leaves);

        return new ResponseEntity<>(leavesStr, HttpStatus.OK);
    }

}
