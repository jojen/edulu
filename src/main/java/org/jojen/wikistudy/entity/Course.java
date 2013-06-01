package org.jojen.wikistudy.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "T_COURSE")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "COURSE_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "COURSE_NAME")
    @Size(min = 1, max = 50)
    @NotNull
    private String name;

    @Column(name = "AGE")
    @Min(1)
    @Max(200)
    private Integer age;

	@Column(name = "DESCRIPTION")
	private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

	public Boolean getHasDraft(){
		return true;
	}


	public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }





    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + "]";
    }
}
