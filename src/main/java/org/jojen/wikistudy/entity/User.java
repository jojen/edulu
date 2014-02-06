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
    //private Roles[] roles;

    public User(String name, String password /*, Roles... roles*/) {
        this.name = name;
        this.password = encode(password);
        //this.roles = roles;
    }

	// TODO hier scheit es noch zu haken
	// [2014/02/02 16:17:28] [org.hibernate.tool.hbm2ddl.SchemaUpdate] [DEBUG] create table user (id int4 not null, name varchar(255), password varchar(255), roles int4, primary key (id))
	// [2014/02/02 16:17:28] [org.hibernate.tool.hbm2ddl.SchemaUpdate] [ERROR] HHH000388: Unsuccessful: create table user (id int4 not null, name varchar(255), password varchar(255), roles int4, primary key (id))
	// [2014/02/02 16:17:28] [org.hibernate.tool.hbm2ddl.SchemaUpdate] [ERROR] ERROR: syntax error at or near "user"

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
 /*
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
     */

}
