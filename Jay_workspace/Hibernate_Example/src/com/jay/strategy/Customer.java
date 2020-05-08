package com.jay.strategy;

import java.util.HashSet;
import java.util.Set;

public class Customer {
	
	private Integer customerId;
	private String customerName;
	
	//(1).這邊先初始化，可以防止發生NullPointException
	//(2).聲明集合類型時，需使用"介面類型"，因為Hibernate在獲取集合類型時，返回的是Hibernate內置的集合類型，而不是JavaSE一個標準的集合實現。
	private Set<Order> orders = new HashSet<>();
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	
}
