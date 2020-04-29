package com.jay.hibernate.manyToMany;

/**
 * 	單向多對多，Item這邊不需要加Set<Category> 
 * 
 */

public class Item {

	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
