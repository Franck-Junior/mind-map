package com.fclerget.mindmap.service;

import com.fclerget.mindmap.model.MindMap;
import com.fclerget.mindmap.repository.MindMapRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MindMapService {

    private final MindMapRepository mindMapRepository;

    @Autowired
    public MindMapService(MindMapRepository mindMapRepository) {
        this.mindMapRepository = mindMapRepository;
    }

    public MindMap create(MindMap mindMap) {
        return mindMapRepository.save(mindMap);
    }

    public List<MindMap> findAll() {
        return Lists.newArrayList(mindMapRepository.findAll());
    }

    public Optional<MindMap> findById(String id) {
        return mindMapRepository.findById(id);
    }

    public void delete(String id) {
        mindMapRepository.deleteById(id);
    }

    public boolean exists(String id) {
        return mindMapRepository.existsById(id);
    }
}
