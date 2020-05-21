package com.jay.bean;

import org.springframework.stereotype.Component;

@Component
public class Car {

	public Car() {
		System.out.println("(Car.java)Car Constructor...");
	}
	
	public void init() {
		System.out.println("(Car.java)car...init");
	}
	
	public void destory(){
		System.out.println("(Car.java)car...destory");
	}
}
