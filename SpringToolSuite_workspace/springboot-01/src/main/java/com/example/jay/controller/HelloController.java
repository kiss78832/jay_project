package com.example.jay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller + @ResponseBody 就可以直接用Url訪問到hello() 或者直接用@RestController
@RestController
public class HelloController {

//	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "Hello World!";
	}
}
