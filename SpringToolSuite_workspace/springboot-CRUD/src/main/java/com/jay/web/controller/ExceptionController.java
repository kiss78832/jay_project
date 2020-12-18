package com.jay.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jay.web.exception.UserNotExistException;

@Controller
public class ExceptionController {

	@RequestMapping("/userError")
	public String userException(@RequestParam("user") String user) {
		System.out.println("測試是否進入");
		if("errorTest".equals(user)) {
			throw new UserNotExistException();
		}
		return "testError";
	}
}
