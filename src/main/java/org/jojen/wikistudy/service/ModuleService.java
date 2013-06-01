package org.jojen.wikistudy.service;

import org.jojen.wikistudy.entity.Module;
import org.springframework.data.domain.Page;

public interface ModuleService {
	Page<Module> findAll(int page, int size);


	Module findById(Integer id);

	Module insert(Module module);

	Module update(Module module);

	void deleteById(Integer id);

}
