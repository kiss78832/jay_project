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
 *		Spring ���ڭ̴��Ѯھڷ�e���ҡA�ʺA���ҰʩM�����@�t�C�����󪺥\��C 
 * 
 * 	�}�o����(A��Ʈw)�B��������(B��Ʈw)�B�Ͳ�����(C��Ʈw) �U�s�W���P����Ʈw�A���h�v�T�����A�z�L@Profile�h�����C
 * 	
 * 
 *	@Profile : ���w����b���Ӥ���b�������ҤU�~��Q���U��e�����A�����w�������ҤU������U�o�Ӥ���C
 *		a). �[�F���ҼХ�Bean�A�u���o�����ҳQ�Ұʮɤ~����U��e�����C�q�{�O"default"����
 *		b). @Profile("���ҦW�٥i�H�۩w�q")�C
 *		c). �g�b�t�m���W�A�u���O���w���Ҫ��ɭԡA��Ӱt�m���̭����Ҧ��t�m�~��}�l�C
 *
 */

@PropertySource("classpath:/DBconfig.properties")//�p�G��Ʈw�g�bProperties�i�H�Φ����ѾɤJ
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware{
	
	@Value("${db.user}") //�Q�� @Value���oProperties�����ȡA�i��b�ѼƸ��ܼƤW���C
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
		
		//���ը��oProperties�ɪ����
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
