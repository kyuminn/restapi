package com.mnsoft.upmu.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String dest = request.getParameter("dest");

		if(dest != null){
			request.getSession().setAttribute("dest", dest);
		}
		return super.preHandle(request, response, handler);
	}

}
