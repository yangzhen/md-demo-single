package com.md.demo.server.common.exception;

import com.md.demo.server.common.util.RES_STATUS;


/**
 * 
 * MdException 
 * @author chenchao
 * @date Jul 14, 2015 8:22:16 PM
 *
 */
public class MdException extends RuntimeException {

	private static final long serialVersionUID = 2195068675053227207L;

	private int errorCode;
	
	private String errorMsg;

	public MdException() {
		super();
	}

	public MdException(String message, int errorCode, String errorMsg) {
		super(message);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public MdException(String message, RES_STATUS status) {
		super(message);
		this.errorCode = status.code;
		this.errorMsg = status.name();
	}
	
	public MdException(RES_STATUS status) {
		super(status.name());
		this.errorCode = status.code;
		this.errorMsg = status.name();
	}


	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	
	
}
