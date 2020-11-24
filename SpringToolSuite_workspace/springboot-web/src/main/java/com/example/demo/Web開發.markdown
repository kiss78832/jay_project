##Web開發
**一、使用SpringBoot**
>1. 創建 SpringBoot 應用，選取需要的模塊。
2. SpringBoot 已經默認將這些 Jar 檔都配置好。  

- **WEB 模組**

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

##SpringBoot對靜態資源的映射規則

**一、WebMvcAutoConfiguration.java 可以看出 SpringBoot 對靜態資源的規範**  

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            if (!this.resourceProperties.isAddMappings()) {
                logger.debug("Default resource handling disabled");
                return;
            }
            Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
            CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
            
            
            if (!registry.hasMappingForPattern("/webjars/**")) {
                customizeResourceHandlerRegistration(registry.addResourceHandler("/webjars/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/")
                        .setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
            }
            
            String staticPathPattern = this.mvcProperties.getStaticPathPattern();
            if (!registry.hasMappingForPattern(staticPathPattern)) {
                customizeResourceHandlerRegistration(registry.addResourceHandler(staticPathPattern)
                        .addResourceLocations(getResourceLocations(this.resourceProperties.getStaticLocations()))
                        .setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
            }
        }
> **所有 [ / webjars /... ] 都去 classpath:/META-INF/resources/webjars/ 下找資源。**  
>* [webjars](https://www.webjars.org/) : 以Jar包的方式引入靜態資源，webjars 包含很多前端框架。▼
![SLF4j圖表](../../../../resources/static/Webjars.jpg)

>* 若要訪問到 webjars 底下的 jquery.js   
正確路徑應該為(記得啟動SpringBoot) : [localhost:8088/webjars/jquery/3.5.1/jquery.js](localhost:8088/webjars/jquery/3.5.1/jquery.js) [因為 ( /webjars/ ) 都去/META-INF/resources/webjars/ 下找資源]  

>>![SLF4j圖表](../../../../resources/static/jQuery.jpg)

**二、自己撰寫的靜態資源，也可以做設置指定路徑**  

    /* 可以設置和靜態資源有關的參數及緩存時間 */
    @ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
    public class ResourceProperties {
    
        /* 這段是會自行去讀取以下資料夾的路徑 */
        private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
            "classpath:/resources/", "classpath:/static/", "classpath:/public/" };
    
        private String[] staticLocations = CLASSPATH_RESOURCE_LOCATIONS;
                ...

        /* 配置歡迎頁面映射 */
        @Bean
        public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext,
                FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider) {
            WelcomePageHandlerMapping welcomePageHandlerMapping = new WelcomePageHandlerMapping(
                    new TemplateAvailabilityProviders(applicationContext), applicationContext, getWelcomePage(),
                    this.mvcProperties.getStaticPathPattern());
            welcomePageHandlerMapping.setInterceptors(getInterceptors(mvcConversionService, mvcResourceUrlProvider));
            welcomePageHandlerMapping.setCorsConfigurations(getCorsConfigurations());
            return welcomePageHandlerMapping;
        }
    }
> **1. 當用 [ /??? ] 訪問當前項目的任何靜態資源，會到以下四個路徑下尋找檔案**
>>a. classpath:/META-INF/resources/  
b. classpath:/resources/  
c. classpath:/static/  
d. classpath:/public/  
e. / → 當前項目的根路徑  

>>>**範例 : 尋找 resources/static/Webjars.jpg** (記得啟動SpringBoot)
* [localhost:8088/Webjars.jpg](http://localhost:8088/Webjars.jpg)

> **2. 首頁(歡迎頁)**
>>a. 靜態資源資料夾下所有的 index.html 頁面，被[ /??? ] 映射，(localhost:8088/)下找 index 頁面。  
b.設定首頁標籤小icon，Springboot1.x版所有的 /favicon.ico 都是在靜態文件下找， 在2.x版本設定標籤icon的方式改變，[修改內容](https://blog.csdn.net/yemuyouhan/article/details/105784153?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.compare&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.compare)  
c.若想自訂靜態資源資料夾，可以到application.properties主配置內加入下列這段▼  
    
            /* 這樣就規定靜態資源資料夾只能到 hello 、 test 資料夾下找。(此配置為陣列，可已配置多個) */
                properties 設置:
                    spring.resources.locations = classpath:/hello/,classpath:/test/

##模板引擎

**基本原理:相較JSP很多模板的原理都類似，都是透過表達式獲取值，只是每個模板的表達式不同**  
**SpringBoot 推薦用thymeleaf (語法更簡單、功能更強大)**
>![各種模板都基於這流程](../../../../resources/static/Template.jpg)

**一、引入 Thymeleaf 模板**

        <!-- 引入thymeleaf模塊 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

>* **注意: thymeleaf 版本目前分成 ( thymeleaf_2 + layout_1 ) 或者 ( thymeleaf_3 + layout_2 )**。  
* **如果利用 <dependency> 導入 thymeleaf 有可能會版本過低，需自行調整 <properties> 下修改版本**  
* **現行SpringBoot_2.x都已經整合 thymeleaf 版本，所以導入 themeleaf 就已經是 3.X 版本**


**二、可以到POM檔下查看原本starter引入的版本**

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.5.RELEASE</version>
        <relativePath /> <!-- lookup parent from repository -->
    </parent>
                                ▼
    <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-dependencies</artifactId>
      <version>2.3.5.RELEASE</version>
    </parent>
                                ▼
    <thymeleaf.version>3.0.11.RELEASE</thymeleaf.version>
    <thymeleaf-extras-data-attribute.version>2.0.1</thymeleaf-extras-data-attribute.version>
    <thymeleaf-extras-java8time.version>3.0.4.RELEASE</thymeleaf-extras-java8time.version>
    <thymeleaf-extras-springsecurity.version>3.0.4.RELEASE</thymeleaf-extras-springsecurity.version>
    <thymeleaf-layout-dialect.version>2.4.1</thymeleaf-layout-dialect.version>
    
**三、Thymeleaf 使用 & 語法**

>* **只要我們把HTML頁面放在 [ classpath:/templates/ ] ，templates 就會自動渲染**

    @ConfigurationProperties(prefix = "spring.thymeleaf")
    public class ThymeleafProperties {
    
        private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;
        /* SpringBoot 會到 classpath:/templates/ 底下找 [ .html ] 的檔案*/
        public static final String DEFAULT_PREFIX = "classpath:/templates/";
        public static final String DEFAULT_SUFFIX = ".html";
        ...
    }

>* **導入 thymeleaf 輔助提示標籤**

    <html xmlns:th="http://www.thymeleaf.org" >
    
>* **使用 thymeleaf 語法**

    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
    <html xmlns:th="http://www.thymeleaf.org" >
        <head>
            <meta content="text/html; charset=UTF-8">
            <title>Insert title here</title>
        </head>
        <body>
            <h1>成功!!!!!!</h1>
            /*如果透過模板引擎進入畫面，會看到 ${hello} 帶出來的值，若直接開success.html文件只會看到 "這是顯示歡迎訊息"*/
            <div th:text="${hello}">這是顯示歡迎訊息</div>   
            
        </body>
    </html>

**四、語法規則**
>* th:text = 可改變當元素的內容，包括 th:id 、 th:class ...等等，[ th: ] 標籤可以替代 html 原生的標籤，再透過[ ${ } ] 表達式互相結合。
>* th:text vs th:utext 差別 ， th:text 純文字 ， th:utext 可解析特殊文字 ex: **<h1>**。  
>* 補充 : [[...]] → th:text  ;  [(...})] → th:utext 

>![th:text vs th:utext 差別](../../../../resources/static/thymeleaf_1.jpg)

**五、 thymeleaf 表達式語法** *[Thymeleaf 官方文件 ( 4.Standard Expression Syntax)](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.pdf)*

>* **基本表達式 ▼**

>![基本表達式](../../../../resources/static/SimpleExpressions.jpg)

>* **文字 ▼**

>![文字](../../../../resources/static/literals.jpg)

>* **文本操作 ▼**

>![文本操作](../../../../resources/static/TextOperations.jpg) 

>* **算數操作 ▼**

>![算數操作](../../../../resources/static/Arithmetic.jpg) 

>* **布林操作 ▼**

>![布林操作](../../../../resources/static/boolean.jpg) 

>* **比較運算操作 ▼**

>![比較運算操作](../../../../resources/static/comparators.jpg) 

>* **條件運算操作 ▼**

>![條件運算操作](../../../../resources/static/conditional.jpg) 

>* **特殊符號操作 ▼**

>![特殊符號操作](../../../../resources/static/special.jpg) 

## SpringBoot 對 SpringMVC 的自動配置

**[ Spring MVC 官方文件 ](https://docs.spring.io/spring-boot/docs/2.2.11.RELEASE/reference/htmlsingle/#boot-features-spring-mvc)**

**一、SpringBoot 對 SpringMVC 的默認配置**

>● Inclusion of **[ ContentNegotiatingViewResolver ]** and **[ BeanNameViewResolver ] ** beans .  
>>**※ 自動配置 ViewResolver**   
『 視圖解析器:根據方法的返回值得到視圖物件 ( View )，視圖物件決定如何渲染 ( 轉發頁面或重導等等功能 ) 』  
>>**※ ContentNegotiatingViewResolver : 組合所有的視圖解析器**  
>>**※ 如何訂製 : 我們可以給自己容器中添加一個視圖解析器，就會自動組合進來**  
>>* **WebMvcAutoConfiguration.class**  
>> 
        ※ 自動配置第一種 Bean 『BeanNameViewResolver』，返回 resolver。
        @Bean
        @ConditionalOnBean(View.class)
        @ConditionalOnMissingBean
        public BeanNameViewResolver beanNameViewResolver() {
            BeanNameViewResolver resolver = new BeanNameViewResolver();
            resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
            return resolver;
        }
        ※ 自動配置第二種 Bean 『ContentNegotiatingViewResolver』，返回 resolver。
        @Bean
        @ConditionalOnBean(ViewResolver.class)
        @ConditionalOnMissingBean(name = "viewResolver", value = ContentNegotiatingViewResolver.class)
        public ContentNegotiatingViewResolver viewResolver(BeanFactory beanFactory) {
            ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
            resolver.setContentNegotiationManager(beanFactory.getBean(ContentNegotiationManager.class));
            // ContentNegotiatingViewResolver uses all the other view resolvers to locate
            // a view so it should have a high precedence
            resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
            return resolver;
        }
>>**[源碼分析](https://zhuanlan.zhihu.com/p/56466149)**

>● Support for serving **static resources, including support for WebJars** (covered later in this document)).
>>**※  靜態資源資料夾路徑,webjars 。**  

>● Automatic registration of Converter, GenericConverter, and Formatter beans.
>>  **Converter : ** 轉換器，像頁面的數字或字串，到後端來自動轉換成String、Integer。  
>> **Formatter : ** 格式化，頁面日期轉換成 Date 等等...。
『 自動註冊 **Converter、GenericConverter、Formatter ** 』  

>● Support for HttpMessageConverters (covered later in this document).
>>**※  SpringMVC 用來轉換Http請求跟回應的。**ex : 後台 User Object 轉換 Json格式。  
>>※  HttpMessageConverters 是從容器中確定，獲取所有的HttpMessageConverter。  
>>>** ※『自己給容器中添加HttpMessageConverter，只需要將自己的元件注入在容器中(@Bean,@Component)』**  
>>>
    @Configuration(proxyBeanMethods = false)
    public class MyConfiguration {
        @Bean
        public HttpMessageConverters customConverters() {
            HttpMessageConverter<?> additional = ...
            HttpMessageConverter<?> another = ...
            return new HttpMessageConverters(additional, another);
        }
    }

>● Automatic registration of MessageCodesResolver (covered later in this document).
>>**※  定義錯誤代碼生成規則 。**  

>● Static index.html support.
>>**※  靜態首頁的訪問 。**  

>● Custom Favicon support (covered later in this document).
>>**※  favicon.io，對於標籤icon圖示設定 。**  

>● Automatic use of a ConfigurableWebBindingInitializer bean (covered later in this document).
>>**※  靜態首頁的訪問 。**  

>>* ** 可以自己配置一個ConfigurableWebBindingInitializer來替代默認的，記得添加到容器中。 **
>>* ** ConfigurableWebBindingInitializer 作用 : 將請求數據綁定到 JavaBean 中。**
>>
        @Override
        protected ConfigurableWebBindingInitializer getConfigurableWebBindingInitializer(
                FormattingConversionService mvcConversionService, Validator mvcValidator) {
            try {
                return this.beanFactory.getBean(ConfigurableWebBindingInitializer.class);
            }
            catch (NoSuchBeanDefinitionException ex) {
                // ※若找不到有關ConfigurableWebBindingInitializer 的 Bean  ，就到super父類別找默認。
                return super.getConfigurableWebBindingInitializer(mvcConversionService, mvcValidator);
            }
        }

>**※ org.springframework.boot.autoconfigure.web : ** Web 所有自動配置 Source Code。

**二、擴展 SpringMVC**
>* **springmvc.xml**  
>
>>
    <mvc:view-controller path="/hello" view-name="success"/>
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/hello"/>
            <bean></bean>
        </mvc:interceptor>
    </mvc:interceptors>

>* **MyMvcConfig.class **

>> **※ 在Spring Boot 1.x，如果需要自定義SpringMVC 配置，直接繼承WebMvcConfigurerAdapter 類即可。**  
>> **※ 從Spring5 開始，由於我們要使用Java8，而Java8 中的接口允許存在default 方法，因此官方建議直接實現**  
>> **　 WebMvcConfigurer 接口，而不是繼承WebMvcConfigurerAdapter 。**

>>
    ※使用 WebMvcConfigurationSupport 可以來擴展 SpringMVC 的功能
    @Configuration
    public class MyMvcConfig extends WebMvcConfigurer{
        @Override
        protected void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/example").setViewName("success");;
        }
    }

**三、擴展 SpringMVC 原理:**

>【Ⅰ】.WebMvcConfigurer .class 是 SpringMVC 的擴充自動配置介面。
>>
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = Type.SERVLET)
    @ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
    @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
    @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
    @AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
            ValidationAutoConfiguration.class })
    public class WebMvcAutoConfiguration {
    ....
    ....
        【內部類】
        @Configuration(proxyBeanMethods = false)
        ★@Import(EnableWebMvcConfiguration.class)★
        @EnableConfigurationProperties({ WebMvcProperties.class, ResourceProperties.class })
        @Order(0)
        public static class WebMvcAutoConfigurationAdapter implements WebMvcConfigurer { 【WebMvcConfigurer 提供很多實作方法】
            ....
            ....
            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                this.messageConvertersProvider
                        .ifAvailable((customConverters) -> converters.addAll(customConverters.getConverters()));
            }
            ....
            @Override
            public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
                if (this.beanFactory.containsBean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)) {
                    Object taskExecutor = this.beanFactory
                            .getBean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME);
                    if (taskExecutor instanceof AsyncTaskExecutor) {
                        configurer.setTaskExecutor(((AsyncTaskExecutor) taskExecutor));
                    }
                }
        }
            ....
        【內部類】
        @Configuration(proxyBeanMethods = false)
        public static class ★EnableWebMvcConfiguration★ extends DelegatingWebMvcConfiguration implements ResourceLoaderAware {
            ....
            ....
        }
    }
    

>>> **※ WebMvcAutoConfigurationAdapter 實作 WebMvcConfigurer 的方法**
>>> **本身是實現了WebMvcConfigurer 接口，然後抽像類裡邊都是空方法**
>>>![WebMvcConfiguration](../../../../resources/static/WebMvcConfiguration.jpg) 
>>
>【Ⅱ】.在做其他自動配置時，會導入 @Import(EnableWebMvcConfiguration.class)
>>
        【內部類】
        @Configuration(proxyBeanMethods = false)
        ★@Import(EnableWebMvcConfiguration.class)★
        @EnableConfigurationProperties({ WebMvcProperties.class, ResourceProperties.class })
        @Order(0)
        public static class WebMvcAutoConfigurationAdapter implements WebMvcConfigurer {
            ....
            ....
        }
        【內部類】
        @Configuration(proxyBeanMethods = false)
        public static class ★EnableWebMvcConfiguration★ extends ★DelegatingWebMvcConfiguration★ implements ResourceLoaderAware {
            ....
            ....
        }

>【Ⅲ】.EnableWebMvcConfiguration.class 繼承 DelegatingWebMvcConfiguration.class  
>>※setConfigurers(List＜WebMvcConfigurer＞) : @Autowired 註冊在方法上，方法上的參數就會從容器中獲取，有繼承 DelegatingWebMvcConfiguration 都取的到。從容器中獲取所有的 WebMvcConfigurer。 
>>
    @Configuration(proxyBeanMethods = false)
    public class ★DelegatingWebMvcConfiguration★ extends WebMvcConfigurationSupport {
        ========================================================
        private final WebMvcConfigurerComposite configurers = new WebMvcConfigurerComposite();
        ========================================================
        ....
        @Autowired(required = false)
        public void setConfigurers(List<WebMvcConfigurer> configurers) {
            if (!CollectionUtils.isEmpty(configurers)) {
                this.configurers.addWebMvcConfigurers(configurers); 【最後是把 WebMvcConfigurer 賦值給 private final WebMvcConfigurerComposite configurers 】
            }
        ....
        ================【拿其中一個實作方法當範例】=================
        @Override
        protected void addViewControllers(ViewControllerRegistry registry) {
            ★this.configurers.addViewControllers(registry);★
        }
        ========================================================
        ....
    }

>【Ⅳ】.WebMvcConfigurerComposite.addViewControllers(ViewControllerRegistry registry)  addViewControllers 實作方法當範例
>> **※ this.delegates → 拿到所有的 WebMvcConfigurer。**  
>> **※ delegate.addViewControllers(registry) → 將拿到的 WebMvcConfigurer 進行調用。(包括自己擴展的配置都會起作用)**  
>>
    class WebMvcConfigurerComposite implements WebMvcConfigurer {
        ....
        ....
        @Override
        public void ★addViewControllers★(ViewControllerRegistry registry) {
            for (WebMvcConfigurer delegate : this.delegates) {
                delegate.addViewControllers(registry);
            }
        }
    }
    
**四、SpringMVC配置 注意事項:**
>**※ 使用SpringBoot 只需要微調一個配置方法，不要去繼承 WebMvcConfigurationSupport**
>>
    Spring Boot 給我們提供了很多自動化配置，很多時候當我們修改這些配置的時候，並不是要全盤否定Spring Boot 
    提供的自動化配置，我們可能只是針對某一個配置做出修改，其他的配置還是按照Spring Boot默認的自動化配置來，
    而繼承WebMvcConfigurationSupport 來實現對SpringMVC 的配置會導致所有的SpringMVC 
    自動化配置失效，因此，一般情況下我們不選擇這種方案。
    
>>
    SpringBoot 針對 SpringMVC 的配置都在 WebMvcAutoConfiguration.class，
    WebMvcAutoConfiguration上有 @ConditionalOnMissingBean(WebMvcConfigurationSupport.class) 註解意思
    當不存在該WebMvcConfigurationSupport這個自動化配置才會生效，導致Spring Boot 中SpringMVC 的自動化配置失效。

>**※ @EnableWebMvc**
>>
    它的作用就是啟用 WebMvcConfigurationSupport，所以在Spring Boot 中，
    我們也不建議使用@EnableWebMvc 註解，因為它一樣會導致Spring Boot 中的SpringMVC 自動化配置失效。
    
>>
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    ★@Import(DelegatingWebMvcConfiguration.class)★
    public @interface EnableWebMvc {
    }
    
>>
    @Configuration(proxyBeanMethods = false)
    public class ★DelegatingWebMvcConfiguration★ extends ★WebMvcConfigurationSupport★ {
        private final WebMvcConfigurerComposite configurers = new WebMvcConfigurerComposite();
        ....
        ....
    }


>**※ 在純Java 配置的SSM、SSH 環境中自定義 SpringMVC 配置**
>>
    第一種方式 : 直接繼承自WebMvcConfigurationSupport 來完成SpringMVC 配置
    第二種方式 : 配置類上額外添加@EnableWebMvc 註解，表示啟用WebMvcConfigurationSupport，這樣配置才會生效。
    【在純Java 配置的SSM 中，如果你需要自定義SpringMVC 配置，你離不開WebMvcConfigurationSupport ，所以在這種情況
    下建議通過繼承WebMvcConfigurationSupport 來實現自動化配置。】

>**※ SpringMVC 配置測試結果**
>>![SpringMVC_Configuration](../../../../resources/static/SpringMVC_Configuration配置.jpg) 

## 如何修改 SpringBoot 的默認配置

* **補充 xmlns:mvc="http://www.springframework.org/schema/mvc" (加上 xml 會自動補齊 mvc:)**  

>【Ⅰ】.**SpringBoot 在自動配置很多元件的時候，先看看容器中有沒有自己配置的 (@Bean、@Component)，**   
>>** 如果有就使用自訂的配置，沒有才會自動配置默認。如果有多個元件( ViewResolver ) 將用戶配置和自己默認的組合起來。**  

>【Ⅱ】.**在 SpringBoot 中會有非常多的XXXConfigurer 幫助我們進行擴展配置。**