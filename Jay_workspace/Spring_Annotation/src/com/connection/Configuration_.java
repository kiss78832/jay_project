package com.connection;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 * 	�n���ư�:
 * 
 * 	���ҷf��:
 * 		(1).�ɤJ�����̿�(��Ʈw�ƾڡB�X�ʡBspring-jdbc�ҪO)
 * 		(2).�t�m��Ʈw�ƾڡB�إ�JdbcTemplate(Spring���Ѫ�²�Ƹ�Ʈw�ާ@�u��)�ާ@�ƾ�
 * 		(3).����k�W�е�@Transactional��ܷ�e�O�@�Өư�(���)��k(��µ���@Transactional�O�L�k�}�Ҩư�(���))
 * 		(4).@EnableTransactionManagement �}�Ұ����Ѫ��ư�(���)�޲z�\��C
 *        		@EnableXXX -> �u�n�OEnable�}�Y���O�}�ҬY�Y�\��
 *      (5).�t�m�ưȺ޲z���ӱ���ư�(DataSourceTransactionManager[JDBC�Ҩϥ�]);
 *      		PlatformTransactionManager:�O�@�ө��h�ưȱ�������AHibernate�BJPA�BJTA�BJDBC...��������@���hPlatformTransactionManager
 *      
 *      	�`�� : �t�m�ư�(���)�޲z�B�J  1.��k�е�@Transactional -> 2.�}�ҨưȺ޲z�\��@EnableTransactionManagement -> 
 *      							3.�t�m�ưȺ޲z�A�N��Ʈw�t�m�ǤJ@Bean PlatformTransactionManager transactionManager()
 *      			
 *	��z:
 *		(1).@EnableTransactionManagement 
 * 				1).�Q��@Import(TransactionManagementConfigurationSelector.class)���e���ɤJ����
 * 				2).Selector �P�_��ت��p�A����Oproxy�٬Oaspectj�����CEnableTransactionManagement�w�]��PROXY[AdviceMode mode() default AdviceMode.PROXY;]
 * 				3).PROXY�|�ɤJ��Ӥ���
 * 					A.[AutoProxyRegistrar]
 * 						a).���e�������UInfrastructureAdvisorAutoProxyCreator���� :
 * 							�Q�Ϋ�m�B�z������b����ЫإH��A��^�@�ӥN�z��H(�W�j��)�A�N�z��H�����k�Q���d�I���i��եΡC
 * 							 
 * 					B.[ProxyTransactionManagementConfiguration]
 * 						a).���e�������U�ưȼW�j��
 * 							�� �ưȼW�j���n�Ψưȵ��ѰT�� : AnnotationTransactionAttributeSource �ѪR�ưȵ���
 *							�� �ư��d�I�� : transactionInterceptor �O�s�F�ư��ݩʰT���B�ưȺ޲z���A�L�O�@�ӹ�@MethodInterceptor(��k�d�I��)�A
 *										�b�ؼФ�k���檺�ɭ�[�����d�I��]�B[�ư��d�I��]�C
 *							��[�ư��d�I��]�B�J:
 *								1.������ưȬ����ݩ�
 *								2.�b���PlatformTransactionManager�A�p�G�ƥ��S���K�[���w����TransactionManager
 *								       �̲׷|�q�e���������������o�@��PlatformTransactionManager
 *								3.����ؼФ�k:
 *									�p�G���`:����ưȺ޲z��(TransactionManager)�A�Q�ΨưȺ޲z����rollback()�C
 *									�p�G���`:����ưȺ޲z���A�Q�ΨưȺ޲z����commit()
 *
 *
 *				 							
 * 
 */

@EnableTransactionManagement
@ComponentScan("com.connection")
@Configuration
public class Configuration_ {

	@Bean
	public DataSource dataSource() throws Exception{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		dataSource.setUser("SPRING_1");
		dataSource.setPassword("zzz30163");
		dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
		dataSource.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() throws Exception {
		//Spring��Configuration���f�S��B�z�A���e�����[���󪺤�k�A�h���եγ��u�O�q�e�����䤸��Cnew JdbcTemplate(dataSource()) ����dataSource()���|�A���зs�C
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}
	
	//���U�ưȺ޲z���b�e����
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}
}
