package com.fclerget.mindmap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Leaf {

    @Id
    private String path;

    private String text;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mindmap_id", nullable = false)
    private MindMap mindMap;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leaf leaf = (Leaf) o;
        return Objects.equal(path, leaf.path) && Objects.equal(text, leaf.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path, text);
    }
}
