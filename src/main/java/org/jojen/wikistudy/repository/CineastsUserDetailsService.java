package org.jojen.wikistudy.repository;

import org.jojen.wikistudy.domain.User;
import org.jojen.wikistudy.service.CineastsUserDetails;
import org.jojen.wikistudy.domain.Course;
import org.jojen.wikistudy.domain.Rating;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mh
 * @since 08.11.11
 */
public interface CineastsUserDetailsService extends UserDetailsService {
    @Override
	CineastsUserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException;

    User getUserFromSession();

    @Transactional
    Rating rate(Course course, User user, int stars, String comment);

    @Transactional
    User register(String login, String name, String password);

    @Transactional
    void addFriend(String login, final User userFromSession);
}
