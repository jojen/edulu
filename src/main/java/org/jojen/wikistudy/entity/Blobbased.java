package org.jojen.wikistudy.entity;

import javax.activation.MimeType;
import javax.persistence.*;
import java.io.Serializable;

/**
 * User: jochen
 * Date: 6/4/13
 * Time: 3:11 PM
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class Blobbased extends Content implements Serializable {
    private static final long serialVersionUID = 1L;


    private String name;
    private String storageKey;
    private MimeType mimeType;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return storageKey;
    }

    public void setKey(String key) {
        this.storageKey = key;
    }
}
