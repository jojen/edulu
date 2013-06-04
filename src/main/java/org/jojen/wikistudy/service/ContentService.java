package org.jojen.wikistudy.service;

import org.jojen.wikistudy.entity.Content;
import org.springframework.data.domain.Page;

public interface ContentService {
    Page<Content> findAll(int page, int size);


    Content findById(Integer id);

    Content insert(Content module);

    Content update(Content module);

    void deleteById(Integer id);

}
