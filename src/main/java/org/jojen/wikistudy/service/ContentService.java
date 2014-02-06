package org.jojen.wikistudy.service;

import org.jojen.wikistudy.entity.Content;
import org.jojen.wikistudy.entity.Lesson;
import org.springframework.data.domain.Page;

public interface ContentService {
    Page<Content> findAll(int page, int size);


    Content findById(Integer id);

    void add(Content c, Lesson l);

    Content update(Content module);

    void deleteById(Integer id);

	void deleteAll();

	void move(Lesson l, Integer from, Integer to);

}
