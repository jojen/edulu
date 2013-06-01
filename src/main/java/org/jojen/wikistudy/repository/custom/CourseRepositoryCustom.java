package org.jojen.wikistudy.repository.custom;

import org.jojen.wikistudy.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseRepositoryCustom {
    Page<Course> findByNameLike(String name, Pageable pageable);
}
