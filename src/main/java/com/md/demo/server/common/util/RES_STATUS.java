package com.md.demo.server.common.util;

import com.md.demo.server.common.constant.Constant;

/**
 * 
 * RES_STATUS 
 * @author chenchao
 * @date Jul 14, 2015 8:19:32 PM
 *
 */
public enum RES_STATUS {
    
	//10000 ~ 10199 通用异常码
	ERROR_PARAM(10001),
	SERVICE_ERROR(10011), 
	
	//10200 ~ 10299 account相关
	ACCOUNT_NOT_ENOUGH(10240),
	
	//10300 ~ 10399 user相关
	USER_NOT_EXIST(20344),
	
	
	
	SUCCESS(0);
	
	RES_STATUS(int code) {
		this.code = code;
	}

	public final int code;
	
	public static RES_STATUS findStatusByCode(int code) {
		for(RES_STATUS status : RES_STATUS.values()) {
			if(status.code == code) {
				return status;
			}
		}
		return null;
	}
	
	public static String isSuccess(int code) {
        if (code == RES_STATUS.SUCCESS.code) {
            return Constant.METHOD_SUCCESS;
        } else {
            return Constant.METHOD_FAIL;
        }
    }

}
