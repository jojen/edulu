package org.jojen.wikistudy.repository;

import org.jojen.wikistudy.domain.Module;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

/**
 * @author mh
 * @since 02.04.11
 */
public interface ModuleRepository extends GraphRepository<Module>,
												  NamedIndexRepository<Module>,
												  RelationshipOperationsRepository<Module> {

}
