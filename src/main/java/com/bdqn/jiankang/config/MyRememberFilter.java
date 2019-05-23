package com.bdqn.jiankang.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyRememberFilter extends FormAuthenticationFilter {

    protected boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response, Object mappedValue){
        Subject subject=getSubject(request,response);
        if(!subject.isAuthenticated() && subject.isRemembered()){
            if(subject.getSession().getAttribute("user")==null &&subject.getPrincipal()!=null){
                subject.getSession().setAttribute("user",subject.getPrincipal());
            }

        }

        return subject.isAuthenticated() || subject.isRemembered();
    }
}
