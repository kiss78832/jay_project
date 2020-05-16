package com.jay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jay.bean.Car;

/*
 *	Bean的生命週期 : ( bean創建 ------> 初始化 ------> 銷毀過程 )
 *		
 *	容器管理Bean的生命週期  : 
 *		(1).我們可以自定義初始化和銷毀方法，容器在Bean進行到當前生命週期的時候調用我們自定義的初始化和銷毀
 *		(2).建構子創建:
 *				singleton:在容器啟動的時候創建。
 *				prototype:在呼叫該Bean時創建。
 *		(3).初始化:
 *				建構子創建完成，並附值好  ------> 呼叫初始化方法 ...
 *		(4).銷毀:
 *				singleton:容器關閉的時候
 *				prototype:容器不會管理這個Bean，容器不會調用 destory() method
 *		
 *
 *	定義初始化與銷毀方式:
 * 		(A).方法一 :指定初始化和銷毀方法
 * 			  (a).<bean id="person" class="com.jay.bean.Person" init-method="" depends-on=""> XML寫法
 * 			  (b).@Bean(initMethod = "init" , destroyMethod = "destory")  annotation寫法
 * 		(B).方法二 :通過Bean實作
 * 				initializingBean (定義初始化邏輯)
 * 				DisposableBean (定義銷毀邏輯)
 * 
 * 
 */

//示範不同方式引進Bean，不用@Bean
@Configuration
@ComponentScan("com.jay.bean")
public class MainConfigOfLifeCycle {
	
	@Bean(initMethod = "init" , destroyMethod = "destory") //指定 初始化方法ID=init, 銷毀方法ID=destory
	public Car car() {
		return new Car();
	}
}
