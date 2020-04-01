package com.jay.springRestful.crud.entities;

public class Department {
	
	private Integer id;
	private String departmentName;
	
	public Department(int id, String string) {
		super();
		this.id = id;
		this.departmentName = string;
	}

	public Department() {
		
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentName=" + departmentName + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
}
