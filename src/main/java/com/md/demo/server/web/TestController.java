package com.md.demo.server.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.md.demo.server.bean.param.TestGetParam;
import com.md.demo.server.bean.vo.Result;
import com.md.demo.server.bean.vo.TestGetResult;
import com.md.demo.server.common.util.RES_STATUS;
import com.md.demo.server.service.TestService;

/**
 * 
 * TestController
 * 
 * @author chenchao
 * @date Jul 14, 2015 10:40:02 AM
 *
 */

@Controller
@RequestMapping("/")
public class TestController {

	@Autowired
	private TestService testService;

	@RequestMapping(value = "getDemo", method = RequestMethod.GET)
	@ResponseBody
	public Result<TestGetResult> getDemo(TestGetParam param, HttpServletRequest request) {

		Result<TestGetResult> ret = new Result<>();
		if (param.getId() > 0) {
			ret.setStatus(RES_STATUS.BAD_PARAM);
		} else {
			int id = param.getId();
			String text = testService.testResult(id);
			TestGetResult data = new TestGetResult();
			data.setText(text);
			data.setId(id);
			ret = new Result<TestGetResult>(data, RES_STATUS.SUCCESS);
		}
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
		json.put("3d_true", str3d);
		json.put("desc", "double calc call problem");
		return json.toJSONString();
	}

}
