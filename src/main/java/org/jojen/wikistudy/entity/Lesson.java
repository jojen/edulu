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
	private Integer id;


	@OneToMany
	private List<LearnContent> learnContents;


	public Lesson() {
	}

	public Integer getId() {
		return id;
	}

	public List<LearnContent> getLearnContents() {
		return learnContents;
	}

	public void setLearnContents(List<LearnContent> learnContents) {
		this.learnContents = learnContents;
	}
}
