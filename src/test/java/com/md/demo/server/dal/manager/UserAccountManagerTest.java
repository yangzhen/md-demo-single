package com.md.demo.server.dal.manager;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.md.demo.server.bean.entry.UserAccount;
import com.md.demo.server.dal.manager.UserAccountManager;
import com.md.demo.server.util.BaseTestAbstact;

/**
 * 
 * @author xinyan.yang
 *
 */
public class UserAccountManagerTest extends BaseTestAbstact {

	@Autowired
	private UserAccountManager accountManager;

	private int userId = 1;

	private int allMoney = 1000;

	private double purchase = 24.21;

	@Test
	@Ignore
	public void testAccount() {
		UserAccount account1 = accountManager.findAccountByUserId(userId);
		logger.info("==============,account1:" + account1);
		accountManager.addMoney(userId, purchase);
		
		// 直接比较double类型，此方法已经junit被废弃，必须制定浮点数相差范围
		// Assert.assertEquals(allMoney, account1.getAvaliableBalance());
		Assert.assertEquals("double类型计算，必须制定浮点数可允许相差范围", allMoney,
				account1.getAvaliableBalance(), 0.001F);

		UserAccount account2 = accountManager.findAccountByUserId(userId);
		logger.info("==============,account2:" + account2);
		Assert.assertEquals("double类型计算，必须制定浮点数可允许相差范围", (allMoney + purchase),
				account2.getAvaliableBalance(), 0.001F);

		accountManager.removeMoney(userId, purchase);

		UserAccount account3 = accountManager.findAccountByUserId(userId);
		logger.info("==============,account3:" + account3);
		Assert.assertEquals("double类型计算，必须制定浮点数可允许相差范围", allMoney,
				account3.getAvaliableBalance(), 0.001F);
	}

	@Test
	@Ignore
	public void testDouble() {
		double d = 133.23;
		double d_three = 3 * d;
		double d_three_plus = d + d + d;
		String str3d = "399.69";

		logger.info("*******,d:" + d);
		logger.info("*******,3*d:" + d_three+",3_d_plus:" + d_three_plus);

		BigDecimal bigDecimal = new BigDecimal(d_three);
		bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);

		Assert.assertEquals("comple 3*d,bigDecimal", str3d,
				bigDecimal.toString());
		Assert.assertEquals("comple 3*d,double", str3d, d_three + "");

	}
}
