package org.jojen.wikistudy.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jochen
 * Date: 06.06.13
 * Time: 21:00
 */
@Entity
public class Quiz extends Content implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	@Lob
	private String quizContent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuizContent() {
		return quizContent;
	}

	public void setQuizContent(String quizContent) {
		this.quizContent = quizContent;
	}

}
