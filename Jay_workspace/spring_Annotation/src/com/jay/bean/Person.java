package com.jay.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {
	
	/*
	 *	�ϥ�@Value��� : 
	 * 		(1).�򥻼ƭ�
	 * 		(2).�i�H�gSpEL,#{}
	 * 		(3).�i�H�g${}�A���X�t�m���[properties]����(�b�B�������ܶq������)
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
