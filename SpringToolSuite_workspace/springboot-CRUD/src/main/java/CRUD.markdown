## 國際化

**一、SpringMVC 需要配置以下 :**
>**※ 編寫國際化配置文件。**  
**※ 使用 ResourceBundleMessageSource 管理國際化資源文件。**  
**※ 在頁面使用 fmt:message 取出國際化內容。**  

> **● SpringMVC 設置 i18n 國際化**
>>>>![SpringMVC國際化配置文件](../resources/static/note/SpringMVC_i18n.jpg)


**二、步驟**
>**※ 編寫國際化配置文件**
>>**● 注意:Properties 必須要 UTF-8 才不會出現亂碼。**
>>![SpringBoot國際化配置文件](../resources/static/note/i18n.jpg)

>**※ 使用 ResourceBundleMessageSource 管理國際化資源文件**
>>![SpringBoot國際化配置文件](../resources/static/note/i18n_2.jpg)

**三、原理**

> **※ 國際化 Locale (區域訊息物件) 【springBoot 都配置到 LocaleResolver.class(獲取區域訊息物件) 】**
>>![SpringBoot國際化配置文件](../resources/static/note/i18n_4.jpg)

> **※ 自行實作 LocaleResolver 】**
>>![SpringBoot國際化配置文件](../resources/static/note/i18n_3.jpg)

## 登入頁面 

**一、前台輸入帳密進去到後台頁面**
>![login_1](../resources/static/note/login_1.jpg)  
>**@RequestParam 用法**  
>>
**※ 傳遞方式 : 通過 @RequestParam，例如: blogs?blogId=1 **  
**※ 使用場景 : 當 URL 需要對資源或者資源列表進行過濾，篩選時，用 @RequestParam **  
**※ 例如 : /blogs?state=publish 而不是 /blogs/state/publish **  
**※ 特殊使用 : @RequestParam (name="id",required= false ,defaultValue="0") 當 【?id = null】 ，request過來是空值，就會啟動默認值 "0"**  

**二、設置輸入帳密錯誤，顯示錯誤訊息**
>![login_2](../resources/static/note/login_2.jpg)  

**三、避免後台頁面不被為登入者直接用URL進去，使用攔截器 (interceptor)**
>![login_3](../resources/static/note/login_3.jpg)  

## CRUD - 員工列表

**一、需求:**
> **※ Restful 風格**

**二、URI_CRUD  v.s.  Restful_CRUD**
> **※ URI = URL + URN**
> * ** URL = https://XXX.XXX/index.html**
> * ** URN = XXX.XXX/index.html**  
>![URI_vs_Restful](../resources/static/note/URI_vs_Restful.jpg)  

**三、thymeleaf 公共頁面元素抽取**

>**順序 :**  
>* **1. 抽取公共片段**  
>
    <div th:fragment="copy">      
         &copy; 2011 The Good Thymes Virtual Grocery
    </div> 

>* **2. 引入公共片段**  
>**※第一種導入語法 : ~{templatename::selector} 【模板名::選擇器】**  
>**※第二種導入語法 : ~{templatename::fragmentname} 【模板名::片段名稱 ex:【dashboard::topbar】**  
>
    <div th:insert="~{footer :: copy}"></div> 

>* **3. 引入時注意事項:**  
>**※ th:insert : 將公共片段整個插入到聲明引入元素中。**  
>**※ 導入方式採取 : ~{templatename::fragmentname}**  
>**※ 標籤內的內文要引用方式 : 1. [[ ~{ XXXXX } ]]   2.[( ~{ XXXXX } )] **  
>![thymeleaf_fragment](../resources/static/note/thymeleaf_fragment.png)  

>* **4. 引入選擇器方式 【 ~{templatename::selector}  】:**  
>![thymeleaf_selector](../resources/static/note/thymeleaf_selector.png)

>* **5. 轉換連結時，在該頁面的連結會呈現文字效果 :**  
>![thymeleaf_parameter](../resources/static/note/thymeleaf_parameter.png)  

>* **6. 取得後台設定資料 :** 
>
>![thymeleaf_foreach](../resources/static/note/thymeleaf_foreach.png)  

**四、新增員工**
>**※ 流程:**  
>![addEmp](../resources/static/note/addEmp.png)  

>**※ 日期格式設定:**  
>![addEmp_date](../resources/static/note/addEmp_date.png)  

**五、修改員工**
>![modifyEmp](../resources/static/note/modifyEmp.png)  
>**※ WebMvcAutoConfiguration 配置 hiddenHttpMethodFilter 源碼 : **  
>![HiddenHttpMethodFilter](../resources/static/note/HiddenHttpMethodFilter.png)  

**六、刪除員工**
>![deleteEmp](../resources/static/note/deleteEmp.png)  

## 錯誤機制處理

**一、SpringBoot 默認的錯誤處理機制**

>**※ 默認效果**
>>**Ⅰ.返回一個默認錯誤頁面**  
>>
>![NotFound_404](../resources/static/note/NotFound_404.png)  

>>**Ⅱ.自訂錯誤頁面步驟與原理 ( 瀏覽器發出請求範例 )**  
>>>** ① ErrorPageCustomizer : 系統出現 4XX 或者 5XX 之類的錯誤，就會生效並到請求 【 /error 】，觸發BasicErrorController。**  
>>>![ErrorPageCustomizer](../resources/static/note/ErrorPageCustomizer.png)  

> ***

>>>**② BasicErrorController : 是專門處理 【 /error 】，這邊會根據【瀏覽器】【客戶端】做不同處理。**  
>>>![BasicErrorController](../resources/static/note/BasicErrorController.png)  

>>> ***

>>>**③ DefaultErrorViewResolver : BasicErrorController 處理【 /error 】需經由DefaultErrorViewResolver 返回 ModelAndView 。**  
>>>![DefaultErrorViewResolver](../resources/static/note/DefaultErrorViewResolver.png)  

>>> ***

>>>**③ DefaultErrorAttributes : 負責前台頁面數據包裝成物件傳遞到後台，扮演 ModelAndView 的 Model。**  
>>>![DefaultErrorAttributes](../resources/static/note/DefaultErrorAttributes.png)  

>>> ***


>>**Ⅲ.自訂錯誤頁面步驟與原理 ( 客戶端發出請求範例 )**  
>>>![customizeAttributes](../resources/static/note/customizeGetAttributes.png)  

