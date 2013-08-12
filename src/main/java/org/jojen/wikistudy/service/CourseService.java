package org.jojen.wikistudy.service;

import org.jojen.wikistudy.entity.Course;
import org.springframework.data.domain.Page;

public interface CourseService {
    Page<Course> findAll(int page, int size);

    Page<Course> findByNameLike(String name, int page, int size);

    Course findById(Integer id);

    Course insert(Course course);

    Course update(Course course);

    void deleteById(Integer id);

	void deleteAll();

}
