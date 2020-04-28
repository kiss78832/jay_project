package com.jay.hibernate.oneToOne_PK;

public class Department02 {

	private Integer deptId;
	private String deptName;
	
	private Manager02 mgr;

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Manager02 getMgr() {
		return mgr;
	}

	public void setMgr(Manager02 mgr) {
		this.mgr = mgr;
	}
	
	
}
