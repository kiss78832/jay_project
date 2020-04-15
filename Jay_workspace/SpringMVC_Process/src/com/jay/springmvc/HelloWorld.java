package com.jay.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {
	
	@Autowired
	private UserService userService;
	
	public HelloWorld() {
		System.out.println("初始化，創建HelloWorld建構子");
	}
	
	@RequestMapping("/helloworld")
	public String hello() {
		System.out.println("跳轉success頁面");
		return "success";
	}
}
