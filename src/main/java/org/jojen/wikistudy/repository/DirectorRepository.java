package org.jojen.wikistudy.repository;

import org.jojen.wikistudy.domain.Director;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author mh
 * @since 02.04.11
 */
public interface DirectorRepository extends GraphRepository<Director> {
    Director findById(String id);
}
