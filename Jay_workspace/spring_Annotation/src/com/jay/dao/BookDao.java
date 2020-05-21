package com.jay.dao;

import org.springframework.stereotype.Repository;

/*
 *	名子默認是類別名稱的首字母小寫 
 */
@Repository
public class BookDao {
	
	private String lable = "1";

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	@Override
	public String toString() {
		return "BookDao [lable=" + lable + "]";
	}
	
	
}
