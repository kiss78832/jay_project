package com.jay.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {
	
	/**
	 * 1.�ϥ�@RenderMapping �ӬM�g�ШD
	 * 2.��^�ȷ|�q�L���ϸѪR����ڪ����z���ϡA���InternalResourceViewResolver ���ϸѪR���A�|���p�U���ѪR:
	 *   �q�Lprefix + returnVal + ���o�˪��覡�o���ڪ����z���ϡA�M��|����o�ާ@�C(/WEB-INF/views/sucess.jsp)
	 * 
	 * 
	 */
	@RequestMapping("/helloworld")
	public String hello() {
		System.out.println("hello world");
		//�`�N�o�^�ǭȬO���������W��
		return "success";
	}
}
