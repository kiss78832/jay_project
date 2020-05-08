package com.jay.strategy;

import java.util.HashSet;
import java.util.Set;

public class Customer {
	
	private Integer customerId;
	private String customerName;
	
	//(1).�o�����l�ơA�i�H����o��NullPointException
	//(2).�n�����X�����ɡA�ݨϥ�"��������"�A�]��Hibernate�b������X�����ɡA��^���OHibernate���m�����X�����A�Ӥ��OJavaSE�@�ӼзǪ����X��{�C
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
