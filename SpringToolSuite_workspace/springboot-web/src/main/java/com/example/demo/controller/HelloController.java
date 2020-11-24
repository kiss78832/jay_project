package com.example.demo.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

		@ResponseBody
		@RequestMapping("/hello")
		public String hello() {
			return "Hello World";
		}

		@RequestMapping("/success")
		public String success(Map<String,Object> map) {
			map.put("hello", "你好!!!");
			map.put("users",Arrays.asList("Jay01","Jay02","Jay03"));
			return "success";
		}

}
