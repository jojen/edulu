package org.jojen.wikistudy.domain;


import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public abstract class Module {
	@GraphId
	Long nodeId;


}
