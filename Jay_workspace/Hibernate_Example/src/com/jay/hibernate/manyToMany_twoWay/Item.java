package com.jay.hibernate.manyToMany_twoWay;

import java.util.HashSet;
import java.util.Set;


/**
 * 
 *	���V�h��h�A�ݭn���䳣�[Set 
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
