package org.jojen.wikistudy.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: jochen
 * Date: 6/4/13
 * Time: 3:11 PM
 */
@Entity
public class Image extends Content implements Serializable {
    private static final long serialVersionUID = 1L;


    private String name;
    // TODO evtl blob
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
