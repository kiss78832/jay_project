package com.jay.springRestful.crud.entities;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 * 數據驗證:
 * 	 (1).使用JSR300標準驗證
 * 	 (2).加入hibernate validator 驗證框架的jar包，EL的jar選好有時會有衝突。
 *   (3).在SpringMVC配置文件中添加<mvc:annotation-driven/>
 *   (4).需要在bean的屬性上添加對應的註解:
 *	    	@Null	被註釋的元素必須為 null
 *			@NotNull	被註釋的元素必須不為 null
 *			@AssertTrue	被註釋的元素必須為 true
 *			@AssertFalse	被註釋的元素必須為 false
 *			@Min(value)	被註釋的元素必須是一個數字，其值必須大於等於指定的最小值
 *			@Max(value)	被註釋的元素必須是一個數字，其值必須小於等於指定的最大值
 *			@DecimalMin(value)	被註釋的元素必須是一個數字，其值必須大於等於指定的最小值
 *			@DecimalMax(value)	被註釋的元素必須是一個數字，其值必須小於等於指定的最大值
 *			@Size(max, min)	被註釋的元素的大小必須在指定的範圍內
 *			@Digits (integer, fraction)	被註釋的元素必須是一個數字，其值必須在可接受的範圍內
 *			@Past	被註釋的元素必須是一個過去的日期
 *			@Future	被註釋的元素必須是一個將來的日期
 *			@Pattern(value)	被註釋的元素必須符合指定的正則表達式
 *   (5).在目標方法bean類型的前面添加@Valid註解
 * 
 * */
public class Employee {
	
	private Integer id;
	
	@NotEmpty
	private String lastName;
	
	@Email
	private String email;
	private Integer gender;
	private Department department;
	
	/**
	 *	FormattingConversionServiceFactoryBean內部已經註冊
	 *		(1).NumberFormatAnnotationFormatterFactory，所以可以支持@NumberFormat
	 *		(2).JodaDateTimeFormatAnnotationFormatterFactroy，所以可以支持@DateTimeFormat
	 *		(3)	使用此方式也必須在xml註冊，<mvc:annotation-driven/>
	 */
	
	//	設定Date日期格式，可以使用@DateTimeFormat
	@Past
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birth;
	
	//	設定數字格式，可以使用@NumberFormat
	@NumberFormat(pattern="#,###,###.#")
	private Float salary;
	
	
	public Employee(Integer id, String lastName, String email, Integer gender, Department department) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender
				+ ", department=" + department + ", birth=" + birth + ", salary=" + salary + "]";
	}

	public Employee() {
		super();
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	};
	
	
	
}
