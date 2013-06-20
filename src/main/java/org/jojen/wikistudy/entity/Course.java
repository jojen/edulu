package org.jojen.wikistudy.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;


    @Size(min = 1, max = 50)
    @NotNull
    private String name;


    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany
    private List<Lesson> lessons;

    public Integer getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasDraft() {
        return false;
    }

    public List<Lesson> getLessons() {
        if (lessons == null) {
            lessons = new ArrayList();
        }
        return lessons;
    }

    public void addLessons(Lesson lesson) {
        if (lessons == null) {
            lessons = new ArrayList<Lesson>();
        }
        if (lesson != null) {
            lessons.add(lesson);
        }
    }


    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + "]";
    }
}
