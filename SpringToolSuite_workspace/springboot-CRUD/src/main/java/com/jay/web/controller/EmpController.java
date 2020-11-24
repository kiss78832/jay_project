package com.jay.web.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jay.web.dao.EmployeeDao;
import com.jay.web.entities.Employee;

@Controller
public class EmpController {

	@Autowired
	EmployeeDao employeeDao ;

	//查詢所有員工頁面
	@GetMapping("/emps")
	public String list(Model model) {
		Collection<Employee> employees= employeeDao.getAll();

		//放在請求域中
		model.addAttribute("emps", employees);
		//【classpath:/templates/】 thymeleaf默認
		return "emp/list";
	}
}
