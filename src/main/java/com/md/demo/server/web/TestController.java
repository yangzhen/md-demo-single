package com.md.demo.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
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

	private static final Logger login = LoggerFactory.getLogger("login");
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping(value = "getDemo", method = RequestMethod.GET)
	@ResponseBody
	public Result<TestGetResult> getDemo(TestGetParam param,
			HttpServletRequest request) {

		long start = System.currentTimeMillis();

		Result<TestGetResult> ret = null;
		String ip = IPUtil.getIp(request);

		if (!checkTestParam(param)) {
			ret = new Result<TestGetResult>(null, RES_STATUS.ERROR_PARAM.code,
					RES_STATUS.ERROR_PARAM.name());
		} else {
			int id = param.getId();
			try {
				//text = testService.testResult(id);
				TestGetResult data = new TestGetResult();
				List<String> list = new ArrayList();
				list.add("aa");
				list.add("bb");
				data.setStrList(list);
				data.setText(param.getText());
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

		long methodCost = System.currentTimeMillis() - start;

		StringBuilder sb = new StringBuilder();
		sb.append(request.getRequestURI());
		sb.append(Constant.LOG_SPLIT);
		sb.append(param.getId());
		sb.append(Constant.LOG_SPLIT);
		sb.append(ip);
		sb.append(Constant.LOG_SPLIT);
		sb.append(ret.getMsg());
		sb.append(Constant.LOG_SPLIT);
		sb.append(methodCost);
		login.info(sb.toString());
		return ret;
	}

	@RequestMapping("test")
	@ResponseBody
	public String testDuoble() {
		double d = 133.23;
		double d_three = 3 * d;
		String str3d = "399.69";
		JSONObject json = new JSONObject();
		json.put("d", d);
		json.put("3d", d_three);
		json.put("3d_true",str3d);
		json.put("desc", "double calc call problem");
		return json.toJSONString();
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
