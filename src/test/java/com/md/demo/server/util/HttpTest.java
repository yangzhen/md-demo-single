package com.md.demo.server.util;

import java.util.Collections;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试登陆，及登陆拦截器
 * UserHttpTest 
 * @author xinyan.yang
 * @date 2015年8月17日 下午6:08:17
 *
 */
public class HttpTest {
    
    private static final Logger logger = LoggerFactory.getLogger(HttpTest.class);
    
    private String host = "http://localhost:8090";
    
    @Test
    public void testLogin() {
        String url = "/test_double";
        String result = HttpClientUtil.post(host + url, Collections.EMPTY_MAP, null);
        logger.info("result:" + result);
    }
    
}   
