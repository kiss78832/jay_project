package com.jay.springRestful.test;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//FORBIDDEN = 500錯誤
@ResponseStatus(reason = "自定義Exception，@ResponseStatus(測試註解在『類別』的作用)。",value = HttpStatus.FORBIDDEN)
public class UserNameNotMatchPasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
