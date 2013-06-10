package org.jojen.wikistudy.entity;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * User: jochen
 * Date: 6/10/13
 * Time: 5:02 PM
 */
@Entity
public class User {

    private static final String SALT = "cewuiqwzie";

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String password;
    private Roles[] roles;

    public User(String name, String password, Roles... roles) {
        this.name = name;
        this.password = encode(password);
        this.roles = roles;
    }

    private String encode(String password) {
        return new Md5PasswordEncoder().encodePassword(password, SALT);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles[] getRole() {
        return roles;
    }

    public void setRoles(Roles[] roles) {
        this.roles = roles;
    }

    public enum Roles implements GrantedAuthority {
        ROLE_USER, ROLE_TEACHER, ROLE_ADMIN;

        @Override
        public String getAuthority() {
            return name();
        }
    }


}
