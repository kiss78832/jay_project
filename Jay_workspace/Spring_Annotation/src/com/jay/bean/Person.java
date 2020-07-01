package com.jay.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {
	
	/*
	 *	使用@Value賦值 : 
	 * 		(1).基本數值
	 * 		(2).可以寫SpEL,#{}
	 * 		(3).可以寫${}，取出配置文件[properties]的值(在運行環境變量面的值)
	 * 
	 */
	
	@Value("Jay")
	private String name;
	@Value("#{30-6}")
	private Integer age;
	@Value("${person.nickName}")
	private String nickName;
	
	String getNickName() {
		return nickName;
	}
	void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Person(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", nickName=" + nickName + "]";
	}
	
	
}
