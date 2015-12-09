package com.md.demo.server.dal.manager;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.md.demo.server.bean.entry.TestMap;
import com.md.demo.server.dal.dao.TestDAO;
import com.md.demo.server.util.BaseTestAbstact;

/**
 * 忽略此单元测试，请连接正确的库
 * @author dev30
 *
 */
public class TestManagerTest extends BaseTestAbstact{
	
	@Autowired
	private TestDAO testDAO;
	
	@Test
	public void testGetDemo() {
		int id = 1;
		TestMap test = testDAO.selectTest(id);
		logger.info("=======" + test);
	}
}
