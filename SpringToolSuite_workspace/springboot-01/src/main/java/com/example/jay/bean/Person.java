package com.example.jay.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 將配置文件中配置的每一個屬性的值，映射到這個元件中(可以搭配[Bean分類方式.txt]):
 * @ConfigurationProperties:告訴SpringBoot將本類中的所有屬性和配置文件中相關的配置進行排定 
 *  	1.perfix ="XXX" ,配置文件中哪個下面所有屬性進行一一映射。
 *  	2.會自動搜尋resources底下的application.properties or application.yml)
 * 		3.與JSR303可以一起搭配使用，但必須要記得 + @Validated在class上
 * */
@PropertySource(value = {"classpath:person.properties"}) //指定單個或多個配置文件
@Component//必需加上才會加入Spring管理
@ConfigurationProperties(prefix = "person") //SpringBoot注入Bean的方式
@Validated //JSR303驗證方式
public class Person {
	
	
	/**
	 * @Value:
	 *  	1.${person.last-name} -> 從配置文件中取得
	 *  	2.#{11*2} -> 計算數值
	 *  	3.@Value("true") -> 也可以支援boolean
	 *  	4.不支持JSR303數據校驗，@Value 跟 JSR303 搭配會無法幫變數驗證。
	 * */
//	@Value("${person.last-name}")
	private String lastName;
//	@Value("#{11*2}")
	private Integer age;
//	@Value("true")
	private Boolean boss;
	private Date birth;
	
	private Map<String,Object> maps;
	private List<Object> lists;
	private Dog dog;
	
	@Override
	public String toString() {
		return "Person [lastName=" + lastName + ", age=" + age + ", boss=" + boss + ", birth=" + birth + ", maps="
				+ maps + ", lists=" + lists + ", dog=" + dog + "]";
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Boolean getBoss() {
		return boss;
	}
	public void setBoss(Boolean boss) {
		this.boss = boss;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Map<String, Object> getMaps() {
		return maps;
	}
	public void setMaps(Map<String, Object> maps) {
		this.maps = maps;
	}
	public List<Object> getLists() {
		return lists;
	}
	public void setLists(List<Object> lists) {
		this.lists = lists;
	}
	public Dog getDog() {
		return dog;
	}
	public void setDog(Dog dog) {
		this.dog = dog;
	}
}
