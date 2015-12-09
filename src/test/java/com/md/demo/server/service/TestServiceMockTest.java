package com.md.demo.server.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import com.md.demo.server.bean.entry.TestMap;
import com.md.demo.server.dal.dao.TestDAO;

@RunWith(MockitoJUnitRunner.class)
public class TestServiceMockTest {

	private TestServiceImpl testService;

	@Mock
	private TestDAO testDAO;

	@Before
	public void init() {
		testService = new TestServiceImpl();
		testService.setTestDAO(testDAO);
	}

	@After
	public void destroy() {
		testDAO = null;
		testService = null;
	}

	@Test
	public void testResult() {
		int id = 23;
		String name = "xxx-name";
		String text = "xxx-text";
		TestMap testMap = new TestMap();
		testMap.setId(id);
		testMap.setName(name);
		testMap.setText(text);
		when(testDAO.selectTest(id)).thenReturn(testMap);
		String a = testService.testResult(id);
		Assert.assertEquals(text, a);
	}
}
