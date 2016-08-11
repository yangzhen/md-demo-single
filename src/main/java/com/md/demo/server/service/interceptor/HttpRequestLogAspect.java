package com.md.demo.server.service.interceptor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import com.md.demo.server.common.exception.MdException;
import com.md.demo.server.common.util.IPUtil;
import com.md.demo.server.common.util.RES_STATUS;

import static com.md.demo.server.common.constant.Constant.LOG_SPLIT;

/**
 * 打印http请求监控日志 RequestMappingAspect
 * 
 * @author yangxinyan
 * @date 2016年1月13日 上午11:31:20
 */
@Aspect
@Component
public class HttpRequestLogAspect {

    private static Logger logger = LoggerFactory.getLogger(HttpRequestLogAspect.class);
    
    private static Logger stat = LoggerFactory.getLogger("stat");

    @Around("@annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public Result<?> processRequestLog(ProceedingJoinPoint jp)
        throws Throwable {
        long start = System.currentTimeMillis();
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> map = getHttpParamter(request);
        Result<?> result = null;
        int resultCode = RES_STATUS.SERVICE_ERROR.code;
        try {
            Object object = jp.proceed();
            if (!(object instanceof Result)) {
                logger.warn(
                    "http result not Result<T> ---------------,joinPoint,{},result:{}",
                    jp, JSONObject.toJSON(object));
                throw new RuntimeException(
                    "http request result should be Result<T>");
            }
            result = ((Result<?>) object);
            resultCode = result.getCode();
            return result;
        } catch (MdException e) {
            resultCode = e.getErrorCode();
            throw e;
        } catch (Throwable e) {
            resultCode = RES_STATUS.SERVICE_ERROR.code;
            throw e;
        } finally {
            long cost = System.currentTimeMillis() - start;
            stat.info(
                request.getRequestURI() + LOG_SPLIT + IPUtil.getIp(request)
                      + LOG_SPLIT + RES_STATUS.isSuccess(resultCode) + LOG_SPLIT
                      + resultCode + LOG_SPLIT + cost + LOG_SPLIT
                      + JSONObject.toJSONString(map));
            logger.info(
                request.getRequestURI() + LOG_SPLIT + IPUtil.getIp(request)
                        + LOG_SPLIT + RES_STATUS.isSuccess(resultCode)
                        + LOG_SPLIT + resultCode + LOG_SPLIT + cost + LOG_SPLIT
                        + JSONObject.toJSONString(map));
        }
    }

    private Map<String, String> getHttpParamter(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        Map<String, String> paramMap = new HashMap<String, String>();
        for (Entry<String, String[]> entry : map.entrySet()) {
            String name = entry.getKey();
            String value[] = entry.getValue();
            if (value.length > 1) {
                logger.warn(
                    "http request param has too many value,name:{},value:{}",
                    name, Arrays.asList(value));
                continue;
            }
            paramMap.put(name, value[0]);
        }
        return paramMap;
    }
}
