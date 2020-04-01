package com.jay.springRestful.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.jay.springRestful.crud.entities.Department;
import com.jay.springRestful.crud.entities.Employee;

@Component
public class EmployeeConverter implements Converter<String, Employee> {

	//	繼承Converter，去實作轉換器，選擇自己的業務邏輯轉換。aa-bb-cc-dd-ee(每個英文字代表參數，利用這些參數新增員工)
	@Override
	public Employee convert(String source) {
		if(source != null) {
			String[] vals = source.split("-");
			if(vals != null && vals.length == 4) {
				String lastName = vals[0];
				String email = vals[1];
				Integer gender = Integer.parseInt(vals[2]);
				Department department = new Department();
				department.setId(Integer.parseInt(vals[3]));
				
				Employee employee = new Employee(null,lastName,email,gender,department);
				System.out.println(source+"---convert---"+employee);
				return employee;
			}
		}
		
		return null;
	}

	
}
