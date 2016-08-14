package com.md.demo.server.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.md.demo.server.common.http.MdRestTemplate;

/**
 * http接口demo
 * 
 * @author yangzhen 2015年12月9日 上午10:33:51
 */
public class HttpTest {
    
    private static final Logger logger = LoggerFactory.getLogger(HttpTest.class);
    
    private String host = "http://www.baidu.com";
    
    @Test
    public void testLogin() {
        String url = "/";
        String result = MdRestTemplate.getNewInstance().sendHttpsGet(url, String.class);
        logger.info("result:" + result);
    }
    
}   
