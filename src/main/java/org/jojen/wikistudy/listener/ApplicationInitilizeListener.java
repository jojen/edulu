package org.jojen.wikistudy.listener;

import org.jojen.wikistudy.repository.ContentRepository;
import org.jojen.wikistudy.repository.CourseRepository;
import org.jojen.wikistudy.repository.LessonRepository;
import org.jojen.wikistudy.util.RepositoryRefresher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationInitilizeListener implements ServletContextListener {
	private static final Logger LOGGER = LoggerFactory
												 .getLogger(ApplicationInitilizeListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.debug("initializing..");
		WebApplicationContext ctx = WebApplicationContextUtils
											.getWebApplicationContext(sce.getServletContext());
		HibernateJpaVendorAdapter jpaAdapter = ctx.getBean(HibernateJpaVendorAdapter.class);

		// kleiner hack um sicher zu stellen, dass wir nur bei ner H2 die DB frisch machen
		if (jpaAdapter != null && jpaAdapter.getJpaPropertyMap().get("hibernate.dialect").equals("org.hibernate.dialect.H2Dialect")) {

			CourseRepository courseRepository = ctx.getBean(CourseRepository.class);
			LessonRepository lessonRepository = ctx.getBean(LessonRepository.class);
			ContentRepository contentRepository = ctx.getBean(ContentRepository.class);
			RepositoryRefresher.refresh(courseRepository,lessonRepository,contentRepository);

		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
