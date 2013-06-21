package org.jojen.wikistudy.controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: jochen
 * Date: 21.06.13
 * Time: 11:26
 */
public class PreHandler implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
		// Wir setzten mal die aktuelle URL in den scope
		request.setAttribute("encodedUrl", URLEncoder.encode(request.getRequestURL().toString(), "UTF-8"));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
