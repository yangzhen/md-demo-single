package com.md.demo.server.service.interceptor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.md.demo.server.bean.vo.Result;
import com.md.demo.server.common.exception.MdException;
import com.md.demo.server.common.util.RES_STATUS;

import static com.md.demo.server.common.constant.Constant.LOG_SPLIT; 

/**
 * 全局异常拦截器
 * GlobalExceptionInterceptor 
 * @author xinyan.yang
 * @date 2015年8月17日 下午2:40:26
 *
 */
@ControllerAdvice
public class MdExceptionInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MdExceptionInterceptor.class);
    
    private static final Logger login = LoggerFactory.getLogger("stat");

    /**
     * 拦截md已知异常
     * @param e
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(MdException.class)
    @ResponseBody
    public Result<Void> handlerCloudFileException(MdException e,
            HttpServletRequest request, HttpServletResponse response) {
        Result<Void> result = new Result<Void>();
        result.setCode(e.getErrorCode());
        result.setMsg(e.getErrorMsg());
        login.info(result.getMsg() + LOG_SPLIT + JSONObject.toJSONString(getHttpParamter(request)) + LOG_SPLIT
                + JSONObject.toJSONString(result) + LOG_SPLIT + request.getRequestURI());
        return result;
    }
    
    /**
     * 拦截服务器未知异常
     * @param e
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<Void> handlerException(Exception e, HttpServletRequest request,
            HttpServletResponse response) {
        Result<Void> result = new Result<Void>(RES_STATUS.SERVICE_ERROR);
        login.info(result.getMsg() + LOG_SPLIT + JSONObject.toJSONString(getHttpParamter(request)) + LOG_SPLIT
                + JSONObject.toJSONString(result) + LOG_SPLIT + request.getRequestURI());
        return result;
    }

    private Map<String, String> getHttpParamter(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        Map<String, String> paramMap = new HashMap<String, String>();
        for(Entry<String, String[]> entry :map.entrySet()) {
            String name = entry.getKey();
            String value[] = entry.getValue();
            if(value.length > 1) {
                logger.warn("http request param has too many value,name:{},value:{}", name, Arrays.asList(value));
                continue;
            }
            paramMap.put(name, value[0]);
        }
        return paramMap;
    }
}
