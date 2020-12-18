## 配置嵌入式 Servlet 容器


**一、如何自訂和修改 Servlet 容器的相關配置**
>**Ⅰ. 修改 server 有關配置 ( ServerProperties.class )**
>>
    #server 設定
        server.XXX
        server.port=8088
    #Tomcat 設定
        server.tomcate.xxx
        server.tomcat.uri-encoding=UTF-8

>**Ⅱ. 編寫一個 WebServerFactoryCustomizer 崁入式 Servlet 容器自訂器**
>
>![WebServerFactoryCustomizer](../resources/static/note/WebServerFactoryCustomizer.png)

**二、 註冊 Servlet 三大元件 【①ServletRegistrationBean ②FilterRegistrationBean ③ServletListenerRegistrationBean】**
>**Ⅰ. 修改 server 有關配置 ( ServerProperties.class )**
>
>![xxxRegistrationBean](../resources/static/note/xxxRegistrationBean.png)

**三、嵌入式Servlet容器自動配置原理 (ServletWebServerFactoryAutoConfiguration)**
>**※ SpringBoot Servlet 自動配置檔在【ServletWebServerFactoryAutoConfiguration】**
>
>![ServletWebServerFactoryAutoConfiguration](../resources/static/note/ServletWebServerFactoryAutoConfiguration.png)

>**Ⅰ. 針對import.Tomcat容器當作範例 (Jetty.Undertow忽略)**
>
>![EmbeddedTomcat](../resources/static/note/EmbeddedTomcat.png)

>**Ⅱ. 針對import.BeanPostProcessorsRegistrar講解**
>
>![BeanPostProcessorsRegistrar](../resources/static/note/BeanPostProcessorsRegistrar.png)

>**Ⅲ.步驟 : **
>>>
    ① SpringBoot 根據導入依賴情況給容器添加相對應的 XXXServletWebServerFactory，
            【TomcatServletWebServerFactory、JettyServletWebServerFactory、UndertowServletWebServerFactory】
    ② 容器中某個元件要創建物件就會執行到後置處理器 【WebServerFactoryCustomizerBeanPostProcessor.class】，
            主要是嵌入了 Servlet 容器工廠，後置處理器就工。
    ③ 後置處理器【WebServerFactoryCustomizerBeanPostProcessor】從容器中獲取所有的 WebServerFactoryCustomizer，
            調用定制器的方法。

**四、嵌入式Servlet容器啟動原理** 

>**※. **

>**Ⅰ. SpringBoot 應用啟動運行 run() 方法，SpringBoot 刷新 IOC 容器【創建 IOC 容器物件，並初始化容器，創建容器中的每一個元件】，還會判斷是否為 Servlet 環境。**
>
>>![run](../resources/static/note/run.png)
>
***
>**Ⅱ. refreshContext(context) : 判斷目前容器是否為 AbstractApplicationContext 子類別，是的話調用 AnnotationConfigServletWebServerApplicationContext # refresh()刷新容器。**
>
>>![refreshContext](../resources/static/note/refreshContext.png)
>
***
>**Ⅲ. onRefresh( ) : 執行父類【ServletWebServerApplicationContext # onRefresh( )】，創建 WebServer【Servlet 容器】，取得 TomcatServletWebServerFactory 工廠。**
>
>>![onRefresh](../resources/static/note/onRefresh.png)
>
***
>**Ⅳ. getWebServer() : 取得 TomcatServletWebServerFactory 工廠，在調用 TomcatServletWebServerFactory # getWebServer() 啟動 Tomcat**
>
>>![getWebServer](../resources/static/note/getWebServer.png)


>**參考網址1 : https://blog.csdn.net/cd546566850/article/details/105329499**  
>**參考網址2 : https://www.cnblogs.com/question-sky/p/9580060.html**  
>**參考網址3 : https://www.mdeditor.tw/pl/pRPS/zh-tw**  


## 使用外置 Servlet 容器

**一、嵌入式比較外部 Servlet 容器** 
>**※ 嵌入式Servlet容器 : jar 包**
>>
    優點 : 簡單、攜帶方便
    缺點 : 默認不支持 JSP。
    解決 : ① 優化定製會比較複雜(使用定製器【ServerProperties(配置文件設定)，或是繼承自定義 WebServerFactoryCustomizer】)
          ② 自己編寫嵌入式 Servlet 容器的創建工廠【ServletWebServerFactory】。

>**※ 外部 Servlet 容器 : 外面安裝Tomcat，應用war 包方式打包 **
>>
>>![outsideTomcat](../resources/static/note/outsideTomcat.png)
