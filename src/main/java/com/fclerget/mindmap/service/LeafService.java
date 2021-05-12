package com.fclerget.mindmap.service;

import com.fclerget.mindmap.model.Leaf;
import com.fclerget.mindmap.model.MindMap;
import com.fclerget.mindmap.repository.LeafRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeafService {

    private final MindMapService mindMapService;
    private final LeafRepository leafRepository;

    @Autowired
    public LeafService(MindMapService mindMapService,
                       LeafRepository leafRepository) {
        this.mindMapService = mindMapService;
        this.leafRepository = leafRepository;
    }

    public List<Leaf> findByMindMapIdAndPathStartingWith(String mindMapId, String path) {
        return leafRepository.findByMindMapIdAndPathStartingWith(mindMapId, path);
    }

    @Nullable
    public Leaf putLeaf(String mindMapId, Leaf leaf) {

        Optional<MindMap> mindMapOptional = mindMapService.findById(mindMapId);

        if (!mindMapOptional.isPresent()) {
            return null;
        }

        MindMap mindMap = mindMapOptional.get();

        leaf.setMindMap(mindMap);

        return leafRepository.save(leaf);
    }

    public Optional<Leaf> findBymindMapIdAndPath(String mindMapId, String path) {
        return leafRepository.findByMindMapIdAndPath(mindMapId, path);
    }

    public void deleteLeaf(String path) {

        if (!exists(path)) {
            return;
        }

        leafRepository.deleteById(path);
    }

    public boolean exists(String path) {
        return leafRepository.existsById(path);
    }
}
