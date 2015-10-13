package com.md.demo.server.common.util;

import javax.servlet.http.HttpServletRequest;


public class IPUtil {

	public static String getClientIp(HttpServletRequest request) {
	    String ip = request.getHeader("NS-Client-IP");

	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	public static String getIp(HttpServletRequest request) {
	    String ip = getClientIp(request);
	    
        /* 注册ip长度限制为15 */
        if(ip!=null && ip.length()>15){
        	//**.***.***.***".length() = 15 
            if(ip.indexOf(",")>0){
            	ip = ip.substring(0,ip.indexOf(","));
            }
        }
        
        return ip;
	}
}
