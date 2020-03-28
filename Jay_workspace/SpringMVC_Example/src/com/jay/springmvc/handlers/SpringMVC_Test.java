package com.jay.springmvc.handlers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jay.springmvc.pojo.User;

@RequestMapping("/springmvc")
@Controller
public class SpringMVC_Test {
	
	private static final String SUCCESS = "success";
	
	
	/**************@RequestMapping�d�ҡAparams and headers �᭱��@annotation����**************/
	/**
	 * 1.@RequestMapping �i�׹���k�P���O
	 * 2.���O�w�q�B:���Ѫ�B�ШD�M�g�T���A����WEB��ؿ��C�ҥH�O�o�s���]�n���W/springmvc
	 * 3.��k�B:���Ѷi�@�B���Ӥ��M�g�T���A�]�N�O/sprimgmvc�i��@�@�Ӹ�Ƨ��A/testRequestMapping�i��@�ɮרӬݫݡC
	 */
	@RequestMapping("/testRequestMapping")
	public String testRequestMapping() {
		System.out.println("testRequestMapping()");
		return SUCCESS;
	}
	
	/**
	 * �`��:�ϥ�method�ݩʨӫ��w�ШD�覡
	 * */
	@RequestMapping(value="/testMethod" , method=RequestMethod.POST)
	public String testMethod() {
		System.out.println("testMethod()");
		return SUCCESS;
	}
	
	/**
	 * �`��:���wGet�᭱?�X�{�b�ѼơA�i����C
	 * */
	@RequestMapping(value="testParamsAnHeaders",params= {"username","age!=0"})
	public String testParamsAnHeaders() {
		System.out.println("Test ������params���Ѽ�");
		return SUCCESS;
	}
	
	/**
	 * �̷Ӻ����ШD�Y����ƶi����A���ثe�����X�ӡC
	 * */
	@RequestMapping(value="testParamsAnHeaders02",headers = {"Accept-Language=zh-TW,zh;q=0.9"})
	public String testParamsAnHeaders02() {
		System.out.println("Test ������Headers���Ѽ�");
		return SUCCESS;
	}
	
	/**
	 * @RequestMapping ���ant����  1.* �q�t��   2.? ��@�r��   3.**�ǰt�h�h���| 
	 * ex:
	 * 		/springmvc/(*���N��@�r��)/abc
	 * 		/springmvc/abc??? ->/springmvc/abcQQQ �᭱�T�Ӧr���i�H��
	 * 		/springmvc/(**�h�h)/abc  -> /springmvc/ddd/eee/abc �����h�ּh���S���Y
	 * */
	@RequestMapping("/antStyle/*/abc")
	public String antStyle() {
		System.out.println("antStyle()");
		return SUCCESS;
	}
	
	
	
	/********************************Rest����d��***********************************/

	/**
	 * Rest ���檺URL 
	 * �HCRUD���� :                 �����g�k:
	 * �s�W: /order POST                  �@�� 
	 * �ק�: /order/{id} PUT              update?id={id}
	 * �d��: /order/{id} GET				 get?id={id}
	 * �R��: /order/{id} DELETE			 delete?id={id}
	 * 
	 * �p��o�ePUT �ШD�M DELETE �ШD�O?
	 * 1.�ݭn�t�mHiddenHttpMethodFilter
	 * 2.�ݭn�o�ePOST�ШD
	 * 3.�ݭn�b�o�ePOST�ШD��a�@�� name=_method �����áA�Ȭ�DELETE��PUT
	 * 
	 * �bSpringMVC ���ؼФ�k���p��o��id�O?
	 * �ϥ�@PathVariable����
	 * 
	 * */

	/**
	 * @PathVariable �i�H�M�gURL�����e��Ũ�ؼФ�k�ѼƤ�
	 * 	���Rest���檺URL
	 * */
	@RequestMapping("/pathVariable/{id}")
	public String pathVariable(@PathVariable("id") String id) {
		System.out.println("Test @PathVariable �e���");
		return SUCCESS;
	}

	/**
	 * REST����AGet��k
	 * */
	@RequestMapping(value="/rest01/{id}",method=RequestMethod.GET)
	@ResponseBody
	public String rest01(@PathVariable String id) {
		System.out.println("Test Rest���� Get()��k: "+id);
		return SUCCESS;
	}

	/**
	 * REST����Adelete��k
	 * */
	@RequestMapping(value="/rest02/{id}",method=RequestMethod.DELETE)
	public String rest02(@PathVariable String id) {
		System.out.println("Test Rest���� Delete()��k: "+id);
		return SUCCESS;
	}

	/**
	 * REST����Aput��k
	 * */
	@RequestMapping(value="/rest03/{id}",method=RequestMethod.PUT)
	public String rest03(@PathVariable String id) {
		System.out.println("Test Rest���� Put()��k"+id);
		return SUCCESS;
	}


	/*********************@RequestParam�d�� :�ШD�M�g�Ѽ�	***********************/

	//defaultValue �q�{�� �Arequired���ȬO�_������ �w�]��true�A�Yint��Integer�N���ιw�](�^��null)
	@RequestMapping(value="/requestParam")
	public String requestParam(@RequestParam(value="username") String username,
			@RequestParam(value="age" ,defaultValue = "0",required = false) int age) {
		System.out.println("@RequestParam�Ѽ� -> username: "+username+" age: "+age);
		return SUCCESS;
	}

	/*********************@RequestHeader�d�� :�ШD�M�g����Cookie���e	***********************/

	@RequestMapping(value="/cookieValue")
	public String cookieValue(@CookieValue("JSESSIONID") String jsessionId) {
		System.out.println("@CookieValue�Ѽ� -> JSESSIONID: "+jsessionId);
		return SUCCESS;
	}

	/*********************@CookieValue�d�� :�ШD�M�g�Ѽ�	***********************/

	@RequestMapping(value="/requestHeader")
	public String requestHeader(@RequestHeader(value="Accept-Language") String language) {
		System.out.println("@RequestHeader�Ѽ� -> Accept_Language: "+language);
		return SUCCESS;
	}
	
	/*********************POJO�d�� :�ШDPOJO�Ѽ�	***********************/
	/*
	 * SpringMVC �|���ӽШD�ѼƦW��POJO�ݩm�W�i��۰ʰt��C 
	 * �۰ʬ��ӹ�H��R�ݩʭȡC������p�ݩʡC�p:address.city�Baddress.province...����
	 */
	@RequestMapping(value="/pojoParams")
	public String pojoParams(User user) {
		System.out.println("POJO�Ѽ� -> User: "+user);
		return SUCCESS;
	}
	
	/*********************Servlet���API�d��	 ***********************/
	/*
	 * ���Servlet���API
	 * 1.HttpServletRequest
	 * 2.HttpservletResponse
	 * 3.HttpSession
	 * 4.java.security.Principal
	 * 5.Local InputStream
	 * 6.OutputStream
	 * 7.Reader
	 * 8.Writer
	 */
	@RequestMapping(value="/servletAPI")
	public String servletAPI(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("Request�Ѽ� -> "+request+" Response�Ѽ� -> "+response);
		return SUCCESS;
	}
	
	
}