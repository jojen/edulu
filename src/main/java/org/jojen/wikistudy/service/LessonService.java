package org.jojen.wikistudy.service;

import org.jojen.wikistudy.entity.Lesson;
import org.springframework.data.domain.Page;

public interface LessonService {
	Page<Lesson> findAll(int page, int size);


	Lesson findById(Integer id);

	Lesson insert(Lesson lesson);

	Lesson update(Lesson lesson);

	void deleteById(Integer id);

}
