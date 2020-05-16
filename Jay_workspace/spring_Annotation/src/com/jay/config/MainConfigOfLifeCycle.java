package com.jay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jay.bean.Car;

/*
 *	Bean���ͩR�g�� : ( bean�Ы� ------> ��l�� ------> �P���L�{ )
 *		
 *	�e���޲zBean���ͩR�g��  : 
 *		(1).�ڭ̥i�H�۩w�q��l�ƩM�P����k�A�e���bBean�i����e�ͩR�g�����ɭԽեΧڭ̦۩w�q����l�ƩM�P��
 *		(2).�غc�l�Ы�:
 *				singleton:�b�e���Ұʪ��ɭԳЫءC
 *				prototype:�b�I�s��Bean�ɳЫءC
 *		(3).��l��:
 *				�غc�l�Ыا����A�ê��Ȧn  ------> �I�s��l�Ƥ�k ...
 *		(4).�P��:
 *				singleton:�e���������ɭ�
 *				prototype:�e�����|�޲z�o��Bean�A�e�����|�ե� destory() method
 *		
 *
 *	�w�q��l�ƻP�P���覡:
 * 		(A).��k�@ :���w��l�ƩM�P����k
 * 			  (a).<bean id="person" class="com.jay.bean.Person" init-method="" depends-on=""> XML�g�k
 * 			  (b).@Bean(initMethod = "init" , destroyMethod = "destory")  annotation�g�k
 * 		(B).��k�G :�q�LBean��@
 * 				initializingBean (�w�q��l���޿�)
 * 				DisposableBean (�w�q�P���޿�)
 * 
 * 
 */

//�ܽd���P�覡�޶iBean�A����@Bean
@Configuration
@ComponentScan("com.jay.bean")
public class MainConfigOfLifeCycle {
	
	@Bean(initMethod = "init" , destroyMethod = "destory") //���w ��l�Ƥ�kID=init, �P����kID=destory
	public Car car() {
		return new Car();
	}
}
