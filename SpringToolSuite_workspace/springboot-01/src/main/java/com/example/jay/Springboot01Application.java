package com.example.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ImportResource(locations = {"classpath:beans.xml"}) //StringBoot建議新建一個配置類，請參考MyAppConfig
//@SpringBootApplication 來標記一個主程式，說明這是一個Spring Boot應用
@SpringBootApplication
public class Springboot01Application {

    public static void main(String[] args) {
    	//不需要配置設定檔就能單純啟動整個Spring框架
        //@SpringBootApplication:標註再類別上面，說明這個類別是Springboot的主配置類，SpringBoot就應該運行這個類的main方法
        //來啟動Springboot應用。
    	//Spring應用啟動
        SpringApplication.run(Springboot01Application.class, args); //傳入的class一定要@SpringBootApplication

        System.out.println("test= "+Class.class.getResource("SLF4j.jpg"));

        /**
       // @SpringBootApplication 內部
	        @Target(ElementType.TYPE)
	        @Retention(RetentionPolicy.RUNTIME)
	        @Documented
	        @Inherited
	        @SpringBootConfiguration(Springboot配置類)  底層是 ->  @Configuration(配置類，底層是一個 @Component) + @Documented
	        @EnableAutoConfiguration(開啟自動配置功能) 以前我們需要配置的東西Springboot都幫我們自動配置。
	        	1.底層有-> @AutoConfigurationPackage(自動配置包，Spring底層註解)
	        	2.★會把主配置類所在包類底下都掃描進去 (也就是com.example.jay這個包底下全部都會掃描，demo那包就會掃不到)
	        	3.底層有-> @Import(AutoConfigurationImportSelector.class)
	        	4.AutoConfigurationImportSelector.class 這裡面有import大約90多個類別功能，就減少手動去注入元件
	        @ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
	        @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })


        */
    }
}
