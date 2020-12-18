package com.jay.web.exception;

public class UserNotExistException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UserNotExistException() {
		super("UserNotExistException 錯誤訊息測試 ");
	}

}
