package com.jay.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//默認加在IOC容器中的元件，容器啟動會調用無參數建構子，在進行賦值等操作。
@Component
public class Boss {

	//標註在方法，Spring容器創建當前對象，就會調用方法，完成賦值
	//方法使用的參數，自定義類型的值從IOC容器中獲取

	private Car car;
	
	@Autowired
	public Boss(Car car) {
		this.car = car;
		System.out.println("(Boss.java)「執行有Boos參數建構子」 ");
	}
	
	public Boss() {
		System.out.println("(Boss.java)「執行有Boos無參數建構子」 ");
	}

	@Override
	public String toString() {
		return "Boss [car=" + car + "]";
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	
}
