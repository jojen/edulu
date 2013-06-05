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
    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
