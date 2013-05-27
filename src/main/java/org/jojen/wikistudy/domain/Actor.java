package org.jojen.wikistudy.domain;

import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Collection;

/**
 * @author mh
 * @since 10.11.11
 */
public class Actor extends Person {
    public Actor(String id, String name) {
        super(id, name);
    }

    public Actor() {
    }

    @RelatedToVia
    Collection<Role> roles;

    public Actor(String id) {
        super(id,null);
    }

    public Iterable<Role> getRoles() {
        return roles;
    }

    public Role playedIn(Course course, String roleName) {
        final Role role = new Role(this, course, roleName);
        roles.add(role);
        return role;
    }
}
