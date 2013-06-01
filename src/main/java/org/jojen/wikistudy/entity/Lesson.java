package org.jojen.wikistudy.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Lesson {


	@Id
	@GeneratedValue
	private Long id;


	@OneToMany
	private List<Module> modules;


	public Lesson() {
	}

	public Long getId() {
		return id;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
}
