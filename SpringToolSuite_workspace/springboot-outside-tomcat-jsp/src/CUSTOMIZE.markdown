## 使用外置 Servlet 容器

**一、比較** 
>**※ 嵌入式Servlet容器 : jar 包**
>>
    優點 : 簡單、攜帶方便
    缺點 : 默認不支持 JSP。
    解決 : ① 優化定製會比較複雜(使用定製器【ServerProperties(配置文件設定)，或是繼承自定義 WebServerFactoryCustomizer】)
          ② 自己編寫嵌入式 Servlet 容器的創建工廠【ServletWebServerFactory】。
