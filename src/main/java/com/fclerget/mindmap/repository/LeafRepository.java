package com.fclerget.mindmap.repository;

import com.fclerget.mindmap.model.Leaf;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LeafRepository extends CrudRepository<Leaf, String> {

    List<Leaf> findByMindMapIdAndPathStartingWith(String mindMapId, String path);

    Optional<Leaf> findByMindMapIdAndPath(String mindMapId, String path);

    boolean existsByMindMapIdAndPath(String mindMapId, String path);
}
