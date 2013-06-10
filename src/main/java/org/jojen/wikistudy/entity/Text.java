package org.jojen.wikistudy.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class Text extends Content implements Serializable {
    private static final long serialVersionUID = 1L;

    @Size(max = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
