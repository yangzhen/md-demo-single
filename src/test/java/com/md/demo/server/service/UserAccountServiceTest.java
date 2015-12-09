package com.md.demo.server.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.md.demo.server.bean.entry.UserAccount;
import com.md.demo.server.service.UserAccountService;
import com.md.demo.server.util.BaseTestAbstact;

public class UserAccountServiceTest extends BaseTestAbstact {

	@Autowired
	private UserAccountService service;
	
	private int userId = 1;

	private int allMoney = 1000;

	private double purchase = 24.21;

	@Test
	public void testAccount() {
		UserAccount account1 = service.findAccountByUserId(userId);
		logger.info("==============,account1:" + account1);
		service.addMoney(userId, purchase);

		// 直接比较double类型，此方法已经junit被废弃，必须制定浮点数相差范围
		// Assert.assertEquals(allMoney, account1.getAvaliableBalance());
		Assert.assertEquals("double类型计算，必须制定浮点数可允许相差范围", allMoney,
				account1.getAvaliableBalance(), 0.001F);

		UserAccount account2 = service.findAccountByUserId(userId);
		logger.info("==============,account2:" + account2);
		Assert.assertEquals("double类型计算，必须制定浮点数可允许相差范围", (allMoney + purchase),
				account2.getAvaliableBalance(), 0.001F);

		service.removeMoney(userId, purchase);

		UserAccount account3 = service.findAccountByUserId(userId);
		logger.info("==============,account3:" + account3);
		Assert.assertEquals("double类型计算，必须制定浮点数可允许相差范围", allMoney,
				account3.getAvaliableBalance(), 0.001F);
	}

}
