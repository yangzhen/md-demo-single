package com.md.demo.server.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author yangzhen
 *
 */
public interface TestService {

	public String testResult(int id);
	
	public JSONObject getConfig(String key);

}
