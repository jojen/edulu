package org.jojen.wikistudy.entity;

import javax.persistence.*;

/**
 * User: jochen
 * Date: 6/4/13
 * Time: 3:45 PM
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class Content {
    @Id
    @GeneratedValue
    private Integer id;
}
