package com.jay.hibernate.oneToOne_PK;

public class Manager02 {
	
	private Integer mgrId;
	private String mgrName;
	
	private Department02 dept;

	public Integer getMgrId() {
		return mgrId;
	}

	public void setMgrId(Integer mgrId) {
		this.mgrId = mgrId;
	}

	public String getMgrName() {
		return mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	public Department02 getDept() {
		return dept;
	}

	public void setDept(Department02 dept) {
		this.dept = dept;
	}
	
	
}
