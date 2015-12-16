package com.md.demo.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.md.demo.server.common.exception.MdException;
import com.md.demo.server.common.util.RES_STATUS;
import com.md.demo.server.dal.dao.TestDAO;

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
	private TestDAO testDAO;

	@Override
	public String testResult(int id) {
		String text = null;
		try {
			text = testDAO.selectTest(id).getText();
		} catch (Exception e) {
			logger.error("test getResult error,id:{}", id, e);
			throw new MdException("testResult failed", RES_STATUS.SUCCESS.code, RES_STATUS.SUCCESS.name());
		}
		return text;
	}

	public void setTestDAO(TestDAO testDAO) {
		this.testDAO = testDAO;
	}
	
}