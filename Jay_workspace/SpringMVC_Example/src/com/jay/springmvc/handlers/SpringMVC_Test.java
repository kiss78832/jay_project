package com.jay.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/springmvc")
@Controller
public class SpringMVC_Test {
	
	private static final String SUCCESS = "success";
	
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
	
	/**
	 * @PathVariable �i�H�M�gURL�����e��Ũ�ؼФ�k�ѼƤ�
	 * ���Rest���檺URL
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
}
