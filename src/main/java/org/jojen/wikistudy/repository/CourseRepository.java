package org.jojen.wikistudy.repository;

import org.jojen.wikistudy.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Page<Course> findByNameLike(String name, Pageable pageable);
}
