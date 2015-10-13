package com.md.demo.server.service;

import com.md.demo.server.bean.entry.UserAccount;

public interface UserAccountService {

	public UserAccount findAccountByUserId(int userId);
	
	public void removeMoney(int userId, double money);
	
	public void addMoney(int userId, double money);
}
