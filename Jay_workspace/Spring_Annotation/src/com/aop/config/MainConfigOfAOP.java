package com.aop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.aop.aop.LogAspects;
import com.aop.aop.MathCalculator;


/*
 *	AOP[動態代理] : 指在程式運行期間動態的將某段代碼切入到指定方法指定位置進行運行的編成方式
 * 		1).導入AOP模塊， Spring AOP (aspectjweaver-1.8.13) JDK是1.8就用1.8開頭的
 * 		2).定義一個業務邏輯類(MathCaluclator):在業務邏輯運行的時候將日誌進行印出(方法之前、方法運行)
 * 		3).定義一格日誌切面類別(LogAspects):切面類別裡面的方法需要動態感知MathCalculator.div運行到哪裡然後執行
 * 				通知方法:
 * 					前置通知[@Before]:在目標方法(div)執行之前運作(EX:logStart)
 * 					後置通知[@After]:在目標方法(div)執行之後運作，不論動作正常執行還是出現異常都會觸發(EX:logdEnd)
 * 					返回通知[@AfterReturning]:在目標方法(div)正常返回之後運作(EX:logReturn)
 * 					異常通知[@AfterThrowing]:在目標方法(div)出現異常以後運作(EX:logdException)
 * 					環繞通知[@Around]:動態代理，手動推進目標方法運行(joinPoint.procced())
 * 		4).給切面類別的目標方法標註何時何地的運行(通知註解)
 * 		5).將切面類別和業務邏輯類(目標方法所在類)都加入倒容器中
 * 		6).必須告訴Spring哪個類別是切面類(給切面類上加入一個註解:@Aspect)
 * 		7).給配置類中加上@EnableAspectJAutoProxy [開啟基於註解的AOP模式] ★重點!必加 ，Spring中有很多@EnalbeXXXX等類別
 * 
 * 
 * 	三步:
 * 		第一步 : 將業務邏輯元件和切面類別都加入到容器中，告訴Spring哪個式切面類(@Aspect)
 * 		第二步 : 在切面類上的每一個通知方法上標註通知註解，告訴Spring何時何地運行(切入點表達式)
 * 		第三步 : 開啟基於註解AOP模式 : @EnableAspectJAutoProxy
 * 
 * 	@EnableAspectJAutoProxy :
 * 		a).@Import(AspectJAutoProxyRegistrar.class)
 *	
 */

@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {

	//將業務邏輯加入到容器中
	@Bean
	public MathCalculator calculator() {
		return new MathCalculator();
	}
	
	//將切面類別加入到容器中
	@Bean
	public LogAspects logAspects() {
		return new LogAspects();
	}
}
