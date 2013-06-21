package org.jojen.wikistudy.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: jochen
 * Date: 21.06.13
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class AuthSuccessHandler  implements AuthenticationSuccessHandler {
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {		   String next = request.getParameter("nextUrl").toString();
		// TODO URL check
		if(next!=null){
			try {
				response.sendRedirect(next);
			} catch (IOException e) {
			//
			}
		}
	}
}