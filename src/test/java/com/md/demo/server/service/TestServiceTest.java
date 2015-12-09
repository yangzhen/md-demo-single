package com.md.demo.server.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.md.demo.server.service.TestService;
import com.md.demo.server.util.BaseTestAbstact;

public class TestServiceTest extends BaseTestAbstact{
	
	@Autowired
	private TestService service;
	
	@Test
	public void testDemo() {
		int id  = 1;
		String str = "moon";
		String text = service.testResult(id);
		Assert.assertNotNull(text);
		Assert.assertEquals(str, text);
	}
}
