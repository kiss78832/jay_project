package com.jay.web.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

//	@RequestMapping(value = "/user/login" ,method = RequestMethod.POST)
//	@PutMapping
//	@DeleteMapping
//	@GetMapping
	@PostMapping(value = "/user/login")
	public String login(@RequestParam("username") String username ,
						@RequestParam("password") String password,
						Map<String,Object> map,
						HttpSession session) {

		if(!StringUtils.isEmpty(username) && "123456".equals(password)) {
			//登入成功，防止表單重複提交，可以重定向到主頁，main.html 在cofig已經將dashboard.html修改為main.html
			session.setAttribute("loginUser", username);
			return "redirect:/main.html";
		}else {
			//登入失敗
			map.put("msg", "用戶名密碼錯誤");
			return "login";
		}
	}


}