package com.md.demo.server.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.demo.server.bean.entry.UserAccount;
import com.md.demo.server.bean.vo.Result;
import com.md.demo.server.common.constant.Constant;
import com.md.demo.server.common.exception.MdException;
import com.md.demo.server.common.util.IPUtil;
import com.md.demo.server.common.util.RES_STATUS;
import com.md.demo.server.service.UserAccountService;

@Controller
@RequestMapping("account")
public class AccountController {
	
	@Autowired
	private UserAccountService service;
	
	private static final Logger login = LoggerFactory.getLogger("login");
	
	@RequestMapping("show")
	@ResponseBody
	public Result<UserAccount> show(@RequestParam("userId")int userId, HttpServletRequest request) {
		long start = System.currentTimeMillis();
		Result<UserAccount> result = new Result<UserAccount>();
		String ip = IPUtil.getIp(request);
		if(userId <= 0 ) {
			result.setStatus(RES_STATUS.ERROR_PARAM);
		} else {
			try {
				UserAccount userAccount = service.findAccountByUserId(userId);
				result = new Result<UserAccount>(RES_STATUS.SUCCESS);
				result.setStatus(RES_STATUS.SUCCESS);
				result.setData(userAccount);
			} catch(MdException e) {
				result.setCode(e.getErrorCode());
				result.setMsg(e.getErrorMsg());
			} catch (Exception e) {
				result.setStatus(RES_STATUS.SERVICE_ERROR);
			}
		}
		long methodCost = System.currentTimeMillis() - start;

		StringBuilder sb = new StringBuilder();
		sb.append(request.getRequestURI());
		sb.append(Constant.LOG_SPLIT);
		sb.append(userId);
		sb.append(Constant.LOG_SPLIT);
		sb.append(ip);
		sb.append(Constant.LOG_SPLIT);
		sb.append(result.getMsg());
		sb.append(Constant.LOG_SPLIT);
		sb.append(result.getCode());
		sb.append(Constant.LOG_SPLIT);
		sb.append(methodCost);
		login.info(sb.toString());
		return result;
	}
	
	
}
