package org.jojen.wikistudy.domain;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.support.index.IndexType;

public class CourseContent {
	private String description;

	@Indexed(indexType = IndexType.FULLTEXT, indexName = "search")
	private String title;

	public String getDescription() {
		return getCourseContent().description;
	}

	public void setDescription(String description) {
		getCourseContent().description = description;
	}

	public String getTitle() {
		return getCourseContent().title;
	}

	public void setTitle(String title) {
		getCourseContent().title = title;
	}

	public CourseContent getCourseContent() {
		return this;
	}
}
