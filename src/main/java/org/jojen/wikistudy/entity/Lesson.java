package org.jojen.wikistudy.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	private Integer id;


	@OneToMany
	@OrderBy("position ASC")
	private List<Content> content;

	@Size(max = 50)
	private String name;

	public Lesson() {
	}

	int position;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public List<Content> getContent() {
		if(content == null){
			return new ArrayList<Content>(0);
		}
		return content;
	}


	public void addContent(Content c) {
		if (content == null) {
			content = new ArrayList<Content>();
		}
		content.add(c);
	}

	public boolean getHasQuiz(){
		for(Content c:getContent()){
			if(c instanceof Quiz){
				return true;
			}
		}
		return false;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Lesson lesson = (Lesson) o;

		if (!id.equals(lesson.id)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
