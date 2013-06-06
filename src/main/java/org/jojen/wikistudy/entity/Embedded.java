package org.jojen.wikistudy.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * User: jochen
 * Date: 6/5/13
 * Time: 6:07 PM
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Embedded extends Blobbased {

    private String width;
    private String height;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
