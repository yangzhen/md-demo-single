package com.md.demo.server.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.demo.server.bean.param.TestGetParam;
import com.md.demo.server.bean.vo.Result;
import com.md.demo.server.bean.vo.TestGetResult;
import com.md.demo.server.common.constant.Constant;
import com.md.demo.server.common.exception.MdException;
import com.md.demo.server.common.util.IPUtil;
import com.md.demo.server.common.util.RES_STATUS;
import com.md.demo.server.service.TestService;

/**
 * 
 * TestController 
 * @author chenchao
 * @date Jul 14, 2015 10:40:02 AM
 *
 */

@Controller
@RequestMapping("/")
public class TestController {

	@Autowired
	private TestService testService;

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping(value = "getDemo", method = RequestMethod.GET)
	@ResponseBody
	public Result<TestGetResult> getDemo(TestGetParam param,
			HttpServletRequest request) {
		Result<TestGetResult> ret = null;
		String ip = IPUtil.getIp(request);
		if (!checkTestParam(param)) {
			ret = new Result<TestGetResult>(null, RES_STATUS.ERROR_PARAM.code,
					RES_STATUS.ERROR_PARAM.name());
		} else {
			int id = param.getId();
			try {
			    String text = testService.testResult(id);
				TestGetResult data = new TestGetResult();
				data.setText(text);
				data.setId(id);
				ret = new Result<TestGetResult>(data, RES_STATUS.SUCCESS.code,
						RES_STATUS.SUCCESS.name());
			} catch (MdException e) {
				ret = new Result<TestGetResult>(null, e.getErrorCode(),
						e.getErrorMsg());
			} catch (Exception e) {
				logger.error(param.getId() + ",getDemo error" , e);
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(request.getRequestURI());
		sb.append(Constant.LOG_SPLIT);
		sb.append(param.getId());
		sb.append(Constant.LOG_SPLIT);
		sb.append(ip);
		sb.append(Constant.LOG_SPLIT);
		sb.append(ret.getMsg());
		logger.info(sb.toString());
		return ret;
	}

	@RequestMapping("hello")
	public String hello(@RequestParam("uid")Integer uid, HttpServletRequest request, Model model) {
	    logger.info("uid:" + uid + ",ip:" + IPUtil.getIp(request));
	    model.addAttribute("uid", uid);
	    model.addAttribute("ip", IPUtil.getIp(request));
	    return "hello";
	}
	
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	private boolean checkTestParam(TestGetParam param) {
		return param.getId() >= 0 ? true : false;
	}

}
