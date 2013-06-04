package org.jojen.wikistudy.repository;

import org.jojen.wikistudy.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Integer> {

}
