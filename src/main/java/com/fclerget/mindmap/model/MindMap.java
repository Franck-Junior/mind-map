package com.fclerget.mindmap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity(name = "mindmap")
public class MindMap {

    @Id
    private String id;

    @JsonIgnore
    @OneToMany(mappedBy = "mindMap", cascade = CascadeType.REMOVE)
    private List<Leaf> leaves;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MindMap mindMap = (MindMap) o;
        return Objects.equal(id, mindMap.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
