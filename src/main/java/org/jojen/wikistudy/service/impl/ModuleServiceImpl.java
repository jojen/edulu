package org.jojen.wikistudy.service.impl;

import org.jojen.wikistudy.entity.LearnContent;
import org.jojen.wikistudy.repository.LearnContentRepository;
import org.jojen.wikistudy.service.ModuleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class ModuleServiceImpl implements ModuleService {
	@Inject
	protected LearnContentRepository moduleRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<LearnContent> findAll(int page, int size) {
		Pageable pageable = new PageRequest(page, size, new Sort(
																		Direction.DESC, "id"));
		Page<LearnContent> learnContent = moduleRepository.findAll(pageable);
		return learnContent;
	}


	@Override
	@Transactional(readOnly = true)
	public LearnContent findById(Integer id) {
		LearnContent learnContent = moduleRepository.findOne(id);
		return learnContent;
	}

	@Override
	@Transactional
	public LearnContent insert(LearnContent learnContent) {
		return moduleRepository.save(learnContent);
	}

	@Override
	@Transactional
	public LearnContent update(LearnContent learnContent) {
		return moduleRepository.save(learnContent);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		moduleRepository.delete(id);
	}

}
