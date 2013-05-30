package org.jojen.wikistudy.domain;


import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Container {
	@GraphId
	Long nodeId;

	@Indexed(unique = true)
	String id;

	@Fetch
	@RelatedTo(type = "INCLUDES", direction = Direction.OUTGOING)
	Set<Course> content;

	public Container() {
	}

	public Container(String id) {
		this.id = id;
	}

	public Collection<Course> getContent() {
		return content;
	}

	public void addContent(Course c) {
		if (content == null) {
			content = new HashSet<Course>();
		}
		content.add(c);
	}


}
