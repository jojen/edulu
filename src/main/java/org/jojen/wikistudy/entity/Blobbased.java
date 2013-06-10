package org.jojen.wikistudy.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;

/**
 * User: jochen
 * Date: 6/4/13
 * Time: 3:11 PM
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Blobbased extends Content implements Serializable {
    private static final long serialVersionUID = 1L;


    private String name;
    private String contentType;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public Boolean getIsEditable() {
        return false;
    }
}
