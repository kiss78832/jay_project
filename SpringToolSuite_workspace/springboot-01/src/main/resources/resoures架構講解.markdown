##src / main / resources 目錄講解

**一、Resoures 目錄結構**

    ● static : 保存靜態資源 → js css images  
    ● templates:保存所有模板頁面 → Springboot 默認 jar 包崁入式 Tomcat，默認不支持 JSP 頁面可以使用模板引擎 (freemarker.thymeleaf)   
    ● application.properties → Springboot 配置文件，Springboot 都是默認配置好了，透過此 properties 可以修改。 

**二、配置文件可以分成兩種，兩種都能當作全局配置**  

    ● application.properties
    ● application.yml

**三、YAML(YAML Ain't Markup Language)[*.yml] **  
>相比xml、json更為數據為中心，更適合為配置文件。

    YAML:配置方式
        server:
            port:8081

    XML:配置方式
        <server>
            <port>8081</port>
        </server>

**YAML基本語法:**  

    ● 是以Key:(空格)value : 表示一對鍵值(空格必須有)。
    ● 以[空格]的縮排來控制層級關係(如上的YAML配置方式)，只要是左對齊的一列數據，都是同層級。

**「Key: Value」的寫法:**  

>字面:普通的值(數字、字串、boolean) -> Key: Value  
* 字串默認不用加上單引號跟雙引號  
* 雙引號 [ " " ] ，使用特殊符號的時候用。ex: \n 就會產生 enter 效果。  
* 單引號 [ ' ' ] ，不會產生特殊符號，單純一般純文字。  
        

**物件寫法:**
>Map(屬性和值)[鍵值對] -> Key: Value 
    
            寫法範例一:
            friends: (注意縮排)
                lastName: XXX
                age: 20

            寫法範例二:
            friends:{lastName: XXX,age: 20}

**陣列寫法:(List.Set) [裡面可以是物件]**
        用值表示陣列中的一個元素
    
            寫法範例一:
            pets:
                - cat
                - dog
                - pig

            寫法範例二:
            pets: [cat,dog,pig]
    
    

