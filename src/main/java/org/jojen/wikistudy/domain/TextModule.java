package org.jojen.wikistudy.domain;


import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class TextModule extends Module {

	private String title;
	private String text;

	public TextModule() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
