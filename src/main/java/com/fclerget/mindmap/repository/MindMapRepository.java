package com.fclerget.mindmap.repository;

import com.fclerget.mindmap.model.MindMap;
import org.springframework.data.repository.CrudRepository;

public interface MindMapRepository extends CrudRepository<MindMap, String> {
}
