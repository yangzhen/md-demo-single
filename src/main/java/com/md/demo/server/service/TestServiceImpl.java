package com.md.demo.server.service;

import com.hwl.themis.log.annoation.LogItem;
import com.hwl.themis.log.annoation.LogMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.md.demo.server.bean.entry.TestMap;
import com.md.demo.server.dal.manager.TestManager;

/**
 * 
 * TestServiceImpl
 * 
 * @author chenchao
 * @date Jul 14, 2015 3:34:59 PM
 *
 */
@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestManager testManager;

	@Value("${sync.data.queue}")
	private String queueName;
	
	private static Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
	
	@Override
	@LogMapping(item = LogItem.ALL)
	public String testResult(int id) {
		TestMap test = testManager.selectTest(id);
		if (test != null) {
			return test.getText();
		}
		return null;
	}
	
	@Override
	public JSONObject getConfig(String key) {
		logger.info("[test spring 常量注入],sync.data.queue:{},brokerURL:{}",this.queueName);
		JSONObject json = new JSONObject();
		json.put("key", key);
		json.put("sync.data.queue", queueName);
		return json;
	}


}