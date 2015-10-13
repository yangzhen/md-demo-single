package com.md.demo.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.md.demo.server.bean.entry.UserAccount;
import com.md.demo.server.common.exception.MdException;
import com.md.demo.server.common.util.RES_STATUS;
import com.md.demo.server.dal.manager.UserAccountManager;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	
	@Autowired
	private UserAccountManager accountManager;
	
	@Override
	public UserAccount findAccountByUserId(int userId) {
		if(userId != 1) {
			throw new MdException(RES_STATUS.USER_NOT_EXIST);
		} else {
			return accountManager.findAccountByUserId(userId);
		}
	}

	@Override
	public void removeMoney(int userId, double money) {
		accountManager.removeMoney(userId, money);
	}

	@Override
	public void addMoney(int userId, double money) {
		accountManager.addMoney(userId, money);
	}

}
