package com.fclerget.mindmap.service;

import com.fclerget.mindmap.model.Leaf;
import com.fclerget.mindmap.repository.MindMapRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MindMapService {

    private final MindMapRepository mindMapRepository;

    public MindMapService(MindMapRepository mindMapRepository) {
        this.mindMapRepository = mindMapRepository;
    }

    public List<Leaf> getLeaves(String path) {
        return mindMapRepository.findByPathStartingWith(path);
    }

    public Leaf putLeaf(Leaf leaf) {

        return mindMapRepository.save(leaf);
    }

    public Optional<Leaf> getLeaf(String path) {

        return mindMapRepository.findById(path);
    }

    public void deleteLeaf(String path) {

        if (!exists(path)) {
            return;
        }

        mindMapRepository.deleteById(path);
    }

    public boolean exists(String path) {
        return mindMapRepository.existsById(path);
    }
}
