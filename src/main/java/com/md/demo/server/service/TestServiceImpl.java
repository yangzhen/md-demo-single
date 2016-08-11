package com.md.demo.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.md.demo.server.bean.entry.TestMap;
import com.md.demo.server.dal.manager.TestManager;

/**
 * 
 * TestServiceImpl 
 * @author chenchao
 * @date Jul 14, 2015 3:34:59 PM
 *
 */
@Service
public class TestServiceImpl implements TestService {
	
	private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

	@Autowired
	private TestManager testManager;

	@Override
	public String testResult(int id) {
		TestMap test = testManager.selectTest(id);
		if(test != null) {
		    return test.getText();
		}
		return null;
	}

}