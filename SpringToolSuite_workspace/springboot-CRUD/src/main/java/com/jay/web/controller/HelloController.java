package com.jay.web.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

		@RequestMapping({"/","/index.html"})
		public String index() {
			return "login";
		}

		@RequestMapping("/success")
		public String success(Map<String,Object> map) {
			map.put("hello", "你好!!!");
			map.put("users",Arrays.asList("Jay01","Jay02","Jay03"));
			return "login";
		}

}
