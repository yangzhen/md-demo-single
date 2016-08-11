package com.md.demo.server.service.interceptor;

import static com.md.demo.server.common.constant.Constant.LOG_SPLIT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.md.demo.server.bean.vo.Result;
import com.md.demo.server.common.exception.MdException;
import com.md.demo.server.common.util.RES_STATUS;
import com.md.demo.server.common.util.RequestUtil;

/**
 * 拦截http请求响应异常 UcExceptionInterceptor
 * 
 * @author yangxinyan
 * @date 2016年1月11日 上午10:15:12
 *
 */
@ControllerAdvice
public class MdExceptionInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(MdExceptionInterceptor.class);

	/**
	 * 
	 * @param e
	 * @param request
	 * @param response
	 * @return
	 */
	@ExceptionHandler(MdException.class)
	@ResponseBody
	public Result<Void> handlerCloudFileException(MdException e, HttpServletRequest request,
			HttpServletResponse response) {
		Result<Void> result = new Result<Void>();
		result.setCode(e.getErrorCode());
		result.setMsg(e.getErrorMsg());
		logger.error(request.getRequestURI() + LOG_SPLIT + RequestUtil.getIp(request) + LOG_SPLIT+ RES_STATUS.isSuccess(result.getCode()) + 
				result.getCode() + "-" + LOG_SPLIT + JSONObject.toJSONString(RequestUtil.getHttpParamter(request)));
		return result;
	}

	/**
	 * 参数缺失异常aa
	 * @param e
	 * @param request
	 * @param response
	 * @return
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	public Result<Void> handlerMissParamException(MissingServletRequestParameterException e,
			HttpServletRequest request, HttpServletResponse response) {
		Result<Void> result = new Result<Void>();
		result.setCode(RES_STATUS.BAD_PARAM.code);
		result.setMsg(RES_STATUS.BAD_PARAM.msg);
		logger.error(request.getRequestURI() + LOG_SPLIT + RequestUtil.getIp(request) + LOG_SPLIT+ RES_STATUS.isSuccess(result.getCode()) + 
				result.getCode() + "-" + LOG_SPLIT + JSONObject.toJSONString(RequestUtil.getHttpParamter(request)), e);
		return result;
	}

	/**
	 * 拦截服务器未知异常
	 * 
	 * @param e
	 * @param request
	 * @param response
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result<Void> handlerException(Exception e, HttpServletRequest request,
			HttpServletResponse response) {
		Result<Void> result = new Result<Void>(RES_STATUS.SERVER_UNKONW_ERROR);
		logger.error(request.getRequestURI() + LOG_SPLIT + RequestUtil.getIp(request) + LOG_SPLIT+ RES_STATUS.isSuccess(result.getCode()) + 
				result.getCode() + "-" + LOG_SPLIT + JSONObject.toJSONString(RequestUtil.getHttpParamter(request)), e);
		return result;
	}

}
