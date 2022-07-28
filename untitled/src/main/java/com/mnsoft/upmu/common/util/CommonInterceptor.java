package com.mnsoft.upmu.common.util;


import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mnsoft.upmu.common.dao.CommonDAO;
import com.mnsoft.upmu.common.vo.UserInfo;
import com.mnsoft.upmu.system.vo.Role;

public class CommonInterceptor  extends HandlerInterceptorAdapter{
	private transient Log logger = LogFactory.getLog(getClass());
	private long startTime;
	private DecimalFormat formatter = new DecimalFormat("0000");
	
	 private void writeLog(String log)
	    {
	    	System.out.println("log="+log);
	            logger.debug(log);
	    }
	 
	@Autowired CommonDAO commonDAO;
	 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		StringBuffer message = new StringBuffer();
        message = new StringBuffer();
        startTime = System.currentTimeMillis();
        String urlParamater;
        System.out.println(request.getParameterNames());
        if(!StringUtil.isNullToString(request.getParameter("menuId")).equals("") || !StringUtil.isNullToString(request.getParameter("menuType")).equals("")){
        	urlParamater = request.getServletPath()+"?menuId="+request.getParameter("menuId")+"&menuType="+request.getParameter("menuType");
        	System.out.println("urlParamater : " + urlParamater);
        }else {
        	urlParamater = request.getServletPath();
        	System.out.println("urlParamater : " + urlParamater);
        }
        
        message.append("[[CONTROLLER LAYER STARTED]")
               .append(handler.getClass().getSimpleName())
               .append("(")
               .append(request.getServletPath())
               .append(")]");
        
        this.writeLog(message.toString());
        
        String ajaxCall = (String) request.getHeader("AJAX");
        
		//세션체크
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("sess_user_info");
		
		if(userinfo == null){
			if(StringUtil.isNullToString(ajaxCall).equals("true")) {
				response.setStatus(500); 
				return false;
			}else {
				 ModelAndView modelAndView = new ModelAndView("redirect:/sessionError.do?url_path="+urlParamater);
		         throw new ModelAndViewDefiningException(modelAndView);
			}
		}
		else{
			String reqUrl = request.getRequestURI();
			
			int urlCnt = commonDAO.selectMenuUrlCount(reqUrl);
			
			//화면 이동인지 체크(화면 이동일 경우만 체크)
			if(urlCnt > 0) {
				Role role = new Role();
				role.setUser_id(userinfo.getSabun());
				role.setMenu_url(reqUrl);
				int roleCnt = commonDAO.selectRoleMenuAuthCount(role);
				//롤에 해당하는 화면 접근 권한 있는지 체크
				if(roleCnt == 0){
					ModelAndView modelAndView = new ModelAndView("redirect:/authError.do");
			        throw new ModelAndViewDefiningException(modelAndView);
				}
				
			}
			
		}
		
		return true;
	}

	@Override
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		StringBuffer message = new StringBuffer();
        long duringTime = System.currentTimeMillis() - startTime;
        
        message.append("[[CONTROLLER LAYER FINISHED]")
               .append(handler.getClass().getSimpleName())
               .append(".(")
               .append(request.getServletPath())
               .append(")] 걸린시간:")
               .append(formatter.format(duringTime));
        
        this.writeLog(message.toString());
	}


}
