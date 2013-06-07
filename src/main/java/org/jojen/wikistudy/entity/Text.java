package org.jojen.wikistudy.entity;


import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Text extends Content implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
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
