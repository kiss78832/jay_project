package com.jay.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//�q�{�[�bIOC�e����������A�e���Ұʷ|�եεL�Ѽƫغc�l�A�b�i���ȵ��ާ@�C
@Component
public class Boss {

	//�е��b��k�ASpring�e���Ыط�e��H�A�N�|�եΤ�k�A�������
	//��k�ϥΪ��ѼơA�۩w�q�������ȱqIOC�e�������

	private Car car;
	
	@Autowired
	public Boss(Car car) {
		this.car = car;
		System.out.println("(Boss.java)�u���榳Boos�Ѽƫغc�l�v ");
	}
	
	public Boss() {
		System.out.println("(Boss.java)�u���榳Boos�L�Ѽƫغc�l�v ");
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
