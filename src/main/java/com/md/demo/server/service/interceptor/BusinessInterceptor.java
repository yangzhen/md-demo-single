package com.md.demo.server.service.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * BusinessInterceptor url拦截器
 * 
 * @author xinyan.yang
 * @date 2015年8月17日 下午2:26:45
 */
public class BusinessInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(BusinessInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler) throws Exception {
        logger.info(request.getRequestURI() + ",======================in preHandle");
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception {
        logger.info(request.getRequestURI() + ",======================in postHandle,remove context");
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
        HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        logger.info(request.getRequestURI() + ",======================in afterCompletion");
        super.afterCompletion(request, response, handler, ex);
    }

}
