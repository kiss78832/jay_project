package com.jay.springRestful.crud.handlers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jay.springRestful.crud.DAO.DepartmentDao;
import com.jay.springRestful.crud.DAO.EmployeeDao;
import com.jay.springRestful.crud.entities.Employee;

@Controller
public class EmployeeHandler {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	//刪除頁面
	@RequestMapping(value = "/emp/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id) {
		employeeDao.delete(id);
		return "redirect:/emps";
	}
	
	//input頁面跳轉到emps(@RequestMapping("/emps"))
//	@RequestMapping(value = "/emp",method=RequestMethod.POST)
//	public String save(Employee employee) {
//		System.out.println("save: "+employee);
//		employeeDao.save(employee);
//		return "redirect:/emps";
//	}
	
	/**
	 * 若要獲取錯誤的訊息，參數記得加上BindingResult得屬性(注意:BindingResult是繼承Errors屬性)。
	 * 	  (1).ConversionService和Validator有錯誤都會存在BindingResult裡頭。
	 * 	  (2).@Valid (參閱Employee.java)
	 * 	  (3).驗證錯誤跳轉頁面注意: 
	 * 			需驗證的Bean對象和其綁定結果對象或錯誤對象時成對錯誤，他們之間不允許聲明其他參數。(不是很懂...)
	 * */
	@RequestMapping(value = "/emp",method=RequestMethod.POST)
	public String save(@Valid Employee employee,BindingResult result,Map<String,Object> map) {
		System.out.println("save: "+employee);
		
		if(result.getErrorCount() > 0 ) {
			System.out.println("出錯了!");
			
			for(FieldError error: result.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			//若出現錯誤，將返回input頁面，也就是原頁面。
			map.put("departments", departmentDao.getDepartments());
			return "input";
		}
		
		employeeDao.save(employee);
		return "redirect:/emps";
	}
	
	/*
	 * 新增按鈕頁面:
	 *	這邊input是新增頁面，映射的網址是/emp。
	 *	因為修改頁面跟新增頁面是同一個，所以input()方法有兩個，一個可以接id參數。
	 */
	@RequestMapping(value="/emp",method=RequestMethod.GET)
	public String input(Map<String,Object> map) {
		map.put("departments", departmentDao.getDepartments());
		map.put("employee", new Employee());
		return "input";
	}
	
	//若沒有加這段，欄位LastName會變空，所以利用@ModelAttribute把空的資料補回。注意"employee"要對應update(Employee XX)的方法內參數類別。
	@ModelAttribute
	public void getEmployee(@RequestParam(value="id",required=false) Integer id,Map<String,Object> map) {
		if(id != null) {
			map.put("employee",employeeDao.get(id));
		}
	}
	
	//修改頁面
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	public String input(@PathVariable("id") Integer id , Map<String,Object> map) {
		map.put("employee", employeeDao.get(id));
		map.put("departments", departmentDao.getDepartments());
		return "input";
	}
	
	//修改功能
	@RequestMapping(value="/emp",method=RequestMethod.PUT)
	public String update(Employee employee) {
		employeeDao.save(employee);
		return "redirect:/emps";
	}
	
	//員工列表
	@RequestMapping("/emps")
	public String list(Map<String,Object> map) {
		map.put("employees", employeeDao.getAll());
		return "list";
	}
	
	//新增員工時，lastName不會被複製。[記住要與path設置的名稱一樣才行<form:input path="lastName"/>]
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.setDisallowedFields("lastName");
//	}
}
