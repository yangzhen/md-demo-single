package com.md.demo.server.common.util;

import com.md.demo.server.common.constant.Constant;

/**
 * 
 * @author yangzhen
 *
 */
public enum RES_STATUS {

	SUCCESS(0, "操作成功"),

	SERVER_UNKONW_ERROR(500, "服务器开小差了,请稍后再试"),

	BAD_PARAM(400, "bad param"),
	
	USER_NOT_EXIST(10100, "用户不存在"),
	
	ACCOUNT_NOT_ENOUGH(10200, "余额不足"),

	;

	RES_STATUS(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public final int code;
	public final String msg;

	public static RES_STATUS findStatusByCode(int code) {
		for (RES_STATUS status : RES_STATUS.values()) {
			if (status.code == code) {
				return status;
			}
		}
		return null;
	}

	/**
	 * success:Y not success:N
	 *
	 * @param code
	 * @return
	 */
	public static String isSuccess(int code) {
		if (code == RES_STATUS.SUCCESS.code) {
			return Constant.METHOD_SUCCESS;
		} else {
			return Constant.METHOD_FAIL;
		}
	}

}
