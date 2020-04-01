package com.jay.springRestful.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jay.springRestful.crud.DAO.EmployeeDao;
import com.jay.springRestful.crud.entities.Employee;

@Controller
public class SpringMVCTest {

	@Autowired
	private EmployeeDao employeeDao;
	
	//	新增員工，利用轉換器來新增。
	@RequestMapping("/testCoversionServiceConerter")
	public String testConverter(@RequestParam("employee") Employee employee) {
		System.out.println("svae: " + employee);
		employeeDao.save(employee);
		return "redirect:/emps";
	}
}
