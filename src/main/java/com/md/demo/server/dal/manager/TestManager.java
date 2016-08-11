package com.md.demo.server.dal.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.md.demo.server.bean.entry.TestMap;
import com.md.demo.server.common.exception.MdException;
import com.md.demo.server.common.util.RES_STATUS;
import com.md.demo.server.dal.dao.TestDAO;

@Component
public class TestManager {
	
	@Autowired
	private TestDAO dao;
	
	private static final Logger logger = LoggerFactory.getLogger(TestManager.class);
	
	public TestMap selectTest(int id) {
		try {
			return dao.selectTest(id);
		} catch(Exception e) {
			logger.error("select testMap error,id:{}", id, e);
			throw new MdException(RES_STATUS.SERVER_UNKONW_ERROR);
		}
	}
}
