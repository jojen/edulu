package org.jojen.wikistudy.service.impl;

import org.jojen.wikistudy.entity.Module;
import org.jojen.wikistudy.repository.ModuleRepository;
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
	protected ModuleRepository moduleRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<Module> findAll(int page, int size) {
		Pageable pageable = new PageRequest(page, size, new Sort(
																		Direction.DESC, "id"));
		Page<Module> module = moduleRepository.findAll(pageable);
		return module;
	}


	@Override
	@Transactional(readOnly = true)
	public Module findById(Integer id) {
		Module lesson = moduleRepository.findOne(id);
		return lesson;
	}

	@Override
	@Transactional
	public Module insert(Module module) {
		return moduleRepository.save(module);
	}

	@Override
	@Transactional
	public Module update(Module module) {
		return moduleRepository.save(module);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		moduleRepository.delete(id);
	}

}
