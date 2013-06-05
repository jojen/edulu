package org.jojen.wikistudy.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: jochen
 * Date: 6/4/13
 * Time: 3:45 PM
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class Content implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    public String getType(){
        return this.getClass().getSimpleName();
    }

    public Integer getId() {
        return id;
    }
}