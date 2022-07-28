package com.mnsoft.upmu.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

	
/**
 * Spring 컨터이너안의 Bean 객체를 어디에서든 얻어오기
 */

public class ApplicationContextProvider implements ApplicationContextAware {
    
    private static ApplicationContext ctx = null;
 
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
 
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }
 
}


