package com.md.demo.server.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.demo.server.bean.entry.UserAccount;
import com.md.demo.server.bean.vo.Result;
import com.md.demo.server.common.util.RES_STATUS;
import com.md.demo.server.service.UserAccountService;

@Controller
@RequestMapping("account")
public class AccountController {
	
	@Autowired
	private UserAccountService service;
	
	@RequestMapping("show")
	@ResponseBody
	public Result<UserAccount> show(@RequestParam("userId")int userId, HttpServletRequest request) {
		Result<UserAccount> result = new Result<UserAccount>();
		if(userId <= 0 ) {
			result.setStatus(RES_STATUS.BAD_PARAM);
		} else {
				UserAccount userAccount = service.findAccountByUserId(userId);
				result = new Result<UserAccount>(RES_STATUS.SUCCESS);
				result.setStatus(RES_STATUS.SUCCESS);
				result.setData(userAccount);
		}
		return result;
	}
	
	
}
