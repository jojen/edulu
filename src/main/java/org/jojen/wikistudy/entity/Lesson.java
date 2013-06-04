package org.jojen.wikistudy.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	private List<Content> content;


	public Lesson() {
	}

	public Integer getId() {
		return id;
	}

	public List<Content> getContent() {
		return content;
	}

    // TODO typisieren
	public void addContent(Content c) {
		if (content == null) {
			content = new ArrayList<Content>();
		}
		content.add(c);
	}
}
