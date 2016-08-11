/* 
 * Project: cif
 * 
 * File Created at 2016年1月13日
 *
 * @author yangxinyan
 *
 */
package com.md.demo.server.service.interceptor;


import static com.md.demo.server.common.constant.Constant.LOG_SPLIT;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.md.demo.server.bean.vo.Result;
import com.md.demo.server.common.constant.Constant;
import com.md.demo.server.common.exception.MdException;
import com.md.demo.server.common.util.RES_STATUS;
import com.md.demo.server.common.util.RequestUtil;

/**
 * 打印http请求监控日志
 * RequestMappingAspect 
 * @author yangxinyan
 * @param <T>
 * @date 2016年1月13日 上午11:31:20
 *
 */
@Aspect
@Component
public class HttpRequestLogAspect {

	private static Logger logger = LoggerFactory.getLogger(HttpRequestLogAspect.class);
	
	private static Logger stat = LoggerFactory.getLogger(Constant.STAT_LOG);
	
	
	@Around("@annotation(org.springframework.web.bind.annotation.ResponseBody)")
	public Result<?> processRequestLog(ProceedingJoinPoint jp) {
		long start = System.currentTimeMillis();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, String> map = RequestUtil.getHttpParamter(request);
		Result<?> result = new Result<>();
		int resultCode = RES_STATUS.SERVER_UNKONW_ERROR.code;
		try {
			Object object = jp.proceed();
			if(!(object instanceof Result)) {
				logger.warn("http result not Result<T> ---------------,joinPoint,{},result:{}",jp,JSONObject.toJSON(object));
				throw new RuntimeException("http request result should be Result<T>");
			}
			result = ((Result<?>) object);
			resultCode = result.getCode();
			long cost = System.currentTimeMillis() - start;
			stat.info(request.getRequestURI() + LOG_SPLIT + RequestUtil.getIp(request) + LOG_SPLIT + RES_STATUS.isSuccess(resultCode) 
					+ LOG_SPLIT + resultCode + LOG_SPLIT + cost + LOG_SPLIT + JSONObject.toJSONString(map));
			logger.info(request.getRequestURI() + LOG_SPLIT + RequestUtil.getIp(request) + LOG_SPLIT + RES_STATUS.isSuccess(resultCode) 
			+ LOG_SPLIT + resultCode + LOG_SPLIT + cost + LOG_SPLIT + JSONObject.toJSONString(map));
		} catch (MdException e) {
			long cost = System.currentTimeMillis() - start;
			resultCode = e.getErrorCode();
			result.setCode(e.getErrorCode());
			result.setMsg(e.getErrorMsg());
			stat.error(request.getRequestURI() + LOG_SPLIT + RequestUtil.getIp(request) + LOG_SPLIT + RES_STATUS.isSuccess(resultCode) 
					+ LOG_SPLIT + resultCode + LOG_SPLIT + cost + LOG_SPLIT + JSONObject.toJSONString(map), e);
			logger.error(request.getRequestURI() + LOG_SPLIT + RequestUtil.getIp(request) + LOG_SPLIT + RES_STATUS.isSuccess(resultCode) 
			+ LOG_SPLIT + resultCode + LOG_SPLIT + cost + LOG_SPLIT + JSONObject.toJSONString(map), e);
		} catch (Throwable e) {
			long cost = System.currentTimeMillis() - start;
			resultCode = RES_STATUS.SERVER_UNKONW_ERROR.code;
			result.setStatus(RES_STATUS.SERVER_UNKONW_ERROR);
			stat.error(request.getRequestURI() + LOG_SPLIT + RequestUtil.getIp(request) + LOG_SPLIT + RES_STATUS.isSuccess(resultCode) 
					+ LOG_SPLIT + resultCode + LOG_SPLIT + cost + LOG_SPLIT + JSONObject.toJSONString(map), e);
			logger.error(request.getRequestURI() + LOG_SPLIT + RequestUtil.getIp(request) + LOG_SPLIT + RES_STATUS.isSuccess(resultCode) 
			+ LOG_SPLIT + resultCode + LOG_SPLIT + cost + LOG_SPLIT + JSONObject.toJSONString(map), e);
		}
		return result; 
	}

}
