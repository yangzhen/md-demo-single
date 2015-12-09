package com.md.demo.server.util;

import java.util.Collections;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http接口demo
 * 
 * @author yangzhen 2015年12月9日 上午10:33:51
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
