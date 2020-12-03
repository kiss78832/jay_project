package com.jay.web.dao;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jay.web.entities.Department;
import com.jay.web.entities.Employee;

@Repository
public class EmployeeDao {

	private static Map<Integer,Employee> employees = null;

	@Autowired
	private DepartmentDao departmentDao;

	static {
		employees = new HashMap<Integer,Employee>();

		employees.put(1001, new Employee(1001,"E-AA","aaa@gmail.com",1,new Department(101,"D-AA"), new Date()));
		employees.put(1002, new Employee(1002,"E-BB","bbb@gmail.com",1,new Department(102,"D-BB"), new Date()));
		employees.put(1003, new Employee(1003,"E-CC","ccc@gmail.com",0,new Department(103,"D-CC"), new Date()));
		employees.put(1004, new Employee(1004,"E-DD","eee@gmail.com",0,new Department(104,"D-DD"), new Date()));
		employees.put(1005, new Employee(1005,"E-EE","ddd@gmail.com",1,new Department(105,"D-EE"), new Date()));
	}

	private static Integer initId = 1006;

	public void save(Employee employee) {

		if(employee.getId() == null) {
			employee.setId(initId++);
		}

		employee.setDepartment(departmentDao.getDepartment(employee.getDepartment().getId()));
		employees.put(employee.getId(),employee);
	}

	//查詢所有員工
	public Collection<Employee> getAll(){
		return employees.values();
	}

	public Employee get(Integer id) {
		return employees.get(id);
	}

	public void delete(Integer id) {
		employees.remove(id);
	}

}
