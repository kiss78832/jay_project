package com.jay.springmvc.pojo;

public class User {
	private String username;
	private String password;
	private String email;
	private int age;
	private Address address;
	
	
	public User(String username,  String email, int age) {
		super();
		this.username = username;
		this.email = email;
		this.age = age;
	}
	public User() {
		super();
	}
	
	public User(String username, String password, String email, int age) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.age = age;
	}
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + ", age=" + age
				+ ", address=" + address + "]";
	}

	
	
	
}
