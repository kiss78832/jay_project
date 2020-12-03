package com.jay.web.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.jay.web.dao.DepartmentDao;
import com.jay.web.dao.EmployeeDao;
import com.jay.web.entities.Department;
import com.jay.web.entities.Employee;

@Controller
public class EmpController {

	@Autowired
	EmployeeDao employeeDao ;

	@Autowired
	DepartmentDao departmentDao;

	//查詢所有員工頁面
	@GetMapping("/emps")
	public String list(Model model) {
		Collection<Employee> employees= employeeDao.getAll();

		//放在請求域中
		model.addAttribute("emps", employees);
		//【classpath:/templates/】 thymeleaf默認
		return "emp/list";
	}

	//來到員工添加頁面
	@GetMapping("/emp")
	public String toAddPage(Model model) {
		Collection<Department> departments= departmentDao.getDepartments();
		model.addAttribute("depts",departments);
		return "emp/add";
	}

	//SpringMVC 自動將請求參數對應javaBean的屬性名稱。前台 name = key ; value = value
	@PostMapping("/emp")
	public String addEmp(Employee employee) {

		System.out.println("新增員工 : "+employee);
		employeeDao.save(employee);
		//【redirect:】 重定向到一個地址
		//【forward:】 轉發到一個地址
		return "redirect:/emps";
	}

	//修改頁面，查出當前員工，並顯示在頁面
	//Model 如果要用來傳遞物件時用到的
	//@PathVariable 取得路徑變量 ，也就是說頁面上<a>路徑的變量。
	@GetMapping("/emp/{id}")
	public String toEditPage(@PathVariable ("id") Integer id,Model model) {
		Employee employee = employeeDao.get(id);
		System.out.println("修改員工1"+employee);
		model.addAttribute("emp",employee);

		//頁面要顯示所有部門列表
		Collection<Department> departments = departmentDao.getDepartments();
		model.addAttribute("depts",departments);
		//回到頁面修改畫面 (把add拿來當修改頁面)
		return "emp/add";
	}

	//刪除員工
	@DeleteMapping("/emp/{id}")
	public String deleteEmp(@PathVariable("id") Integer id) {
		System.out.println("刪除員工編號: "+id);
		employeeDao.delete(id);
		return "redirect:/emps";
	}

	//修改員工
	@PutMapping("/emp")
	public String updateEmp(Employee employee) {
		System.out.println("修改員工2"+employee);
		//存儲已修改員工
		employeeDao.save(employee);
		return "redirect:/emps";
	}



}
