package org.jojen.wikistudy.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * @author mh
 * @since 04.03.11
 */
@RelationshipEntity(type = "ACTS_IN")
public class Role {
    @GraphId Long id;
    @EndNode
	Course course;
    @StartNode Actor actor;

    String name;

    public Role() {
    }

    public Role(Actor actor, Course course, String roleName) {
        this.course = course;
        this.actor = actor;
        this.name = roleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public Actor getActor() {
        return actor;
    }

    @Override
    public String toString() {
        return String.format("%s acts as %s in %s", actor, name, course);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;
        if (id == null) return super.equals(o);
        return id.equals(role.id);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

}
