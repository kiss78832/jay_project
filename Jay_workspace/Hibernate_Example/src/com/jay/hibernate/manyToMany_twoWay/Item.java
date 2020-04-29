package com.jay.hibernate.manyToMany_twoWay;

import java.util.HashSet;
import java.util.Set;


/**
 * 
 *	雙向多對多，需要雙邊都加Set 
 */
public class Item {

	private Integer id;
	private String name;
	
	private Set<Category> categories = new HashSet<>();
	
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
	public Set<Category> getCategories() {
		return categories;
	}
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	
}
