package com.md.demo.server.dal.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.md.demo.server.bean.entry.UserAccount;
import com.md.demo.server.common.exception.MdException;
import com.md.demo.server.common.util.RES_STATUS;
import com.md.demo.server.dal.dao.UserAccountDAO;

@Component
public class UserAccountManager {

	@Autowired
	private UserAccountDAO dao;

	private static final Logger logger = LoggerFactory.getLogger(UserAccountManager.class);

	public UserAccount findAccountByUserId(int userId) {
		try {
			return dao.findAccountByUserId(userId);
		} catch (Exception e) {
			logger.error("findAccountByUserId error,userId:{}", userId, e);
			throw new MdException(RES_STATUS.SERVER_UNKONW_ERROR);
		}
	}

	public void removeMoney(int userId, double money) {
		try {
			int result = dao.removeMoney(userId, money);
			if (result == 1) {
				return;
			} else {
				throw new MdException(RES_STATUS.ACCOUNT_NOT_ENOUGH);
			}
		} catch (MdException e) {
			throw e;
		} catch (Exception e) {
			logger.error("removeMoney error,userId:{},money:{}", userId, money, e);
		}
		throw new MdException(RES_STATUS.SERVER_UNKONW_ERROR);
	}

	public void addMoney(int userId, double money) {
		try {
			int result = dao.addMoney(userId, money);
			if(result == 1) {
				return;
			} else {
				throw new MdException(RES_STATUS.SERVER_UNKONW_ERROR);
			}
		} catch(MdException e) {
			throw e;
		} catch (Exception e) {
			logger.error("addMoney error,userId:{},money:{}", userId, money, e);
			throw new MdException(RES_STATUS.SERVER_UNKONW_ERROR);
		}
	}

}
