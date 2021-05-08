package com.fclerget.mindmap.repository;

import com.fclerget.mindmap.model.Leaf;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MindMapRepository extends CrudRepository<Leaf, String> {

    public List<Leaf> findByPathStartingWith(String path);
}
