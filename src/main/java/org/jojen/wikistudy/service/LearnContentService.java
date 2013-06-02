package org.jojen.wikistudy.service;

import org.jojen.wikistudy.entity.LearnContent;
import org.springframework.data.domain.Page;

public interface LearnContentService {
	Page<LearnContent> findAll(int page, int size);


	LearnContent findById(Integer id);

	LearnContent insert(LearnContent module);

	LearnContent update(LearnContent module);

	void deleteById(Integer id);

}
