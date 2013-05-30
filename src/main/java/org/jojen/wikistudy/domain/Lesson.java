package org.jojen.wikistudy.domain;


import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Lesson {
	@GraphId
	Long nodeId;


	@Fetch
	@RelatedTo(type = "CONTAINS", direction = Direction.OUTGOING)
	Set<Module> content;

	public Lesson() {
	}


	public Collection<Module> getContent() {
		return content;
	}

	public void addContent(Module c) {
		// TODO das sollte noch sortiert möglich sein
		if (content == null) {
			content = new HashSet<Module>();
		}
		content.add(c);
	}


}
