package org.jojen.wikistudy.service;

import org.jojen.wikistudy.entity.Course;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
	public static final int DEFAULT_PAGE_SIZE = 10;

    Page<Course> findAll(int page, int size);

	List<Course> findAll();

    Page<Course> findByNameLike(String name, int page, int size);

    Course findById(Integer id);

    Course insert(Course course);

    Course update(Course course);

    void deleteById(Integer id);

	void deleteAll();

	int getSize();

	void move(Integer page, Integer from, Integer to);
}
