package com.jay.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import com.jay.bean.Import03;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 *	Profile :
 *		Spring 為我們提供根據當前環境，動態的啟動和切換一系列的元件的功能。 
 * 
 * 	開發環境(A資料庫)、測試環境(B資料庫)、生產環境(C資料庫) 各連上不同的資料庫，不去影響彼此，透過@Profile去切換。
 * 	
 * 
 *	@Profile : 指定元件在哪個元件在哪個環境下才能被註冊到容器中，不指定任何環境下都能註冊這個元件。
 *		a). 加了環境標示Bean，只有這個環境被啟動時才能註冊到容器中。默認是"default"環境
 *		b). @Profile("環境名稱可以自定義")。
 *		c). 寫在配置類上，只有是指定環境的時候，整個配置類裡面的所有配置才能開始。
 *
 */

@PropertySource("classpath:/DBconfig.properties")//如果資料庫寫在Properties可以用此註解導入
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware{
	
	@Value("${db.user}") //利用 @Value取得Properties內的值，可放在參數跟變數上面。
	private String user;
	
	private StringValueResolver valueResolver;
	private String driverClass;
	
	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.valueResolver = resolver;
		driverClass = valueResolver.resolveStringValue("${db.driverClass}");
	}
	
	@Profile("hibernate_DB")
	@Bean(name ="dataSource_hbn")
	public DataSource dataSource_hbn(@Value("${db.password}") String password) throws Exception {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();	
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setDriverClass(driverClass);
		
		//測試取得Properties檔的資料
		System.out.println("(MainConfigOfProfile.java)User : "+user);
		System.out.println("(MainConfigOfProfile.java)Password : "+password);
		System.out.println("(MainConfigOfProfile.java)driverClass : "+driverClass);
		
		return dataSource;
	}
	
	@Profile("System_DB")
	@Bean(name ="dataSource_sys")
	public DataSource dataSource_sys(@Value("${db.user}") String password) throws Exception {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();	
		dataSource.setUser("SYSTEM");
		dataSource.setPassword(password);
		dataSource.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setDriverClass(driverClass);
		
		return dataSource;
	}

	@Bean
	public Import03 improt03() {
		return new Import03();
	}
}
