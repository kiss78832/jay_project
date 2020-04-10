<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<p>------------------ConversionService測試表單	------------------</p>
	<form action="testCoversionServiceConerter" method="POST">
		Employee:<input type="text" name="employee">
			<input type="submit" value="送出">
	</form>
	<p>------------------ConversionService測試表單	------------------</p>
<br><br>

	<!-- 
		(1)	.為什麼使用from標籤呢?       Ans:可以更快速的開發出表單頁面，而且可以更方便進行表單值得回覆顯示。
		(2)	.注意 「可以通過modelAttribute屬性指定綁定的模型屬性，若沒有指定該屬性，則會默認從request域
				   對象中讀取command的表單bean，如果該屬性值也不存在，則會發生錯誤。」 
				   
		delimiter :兩者之間要插入什麼字串或符號。
	 -->
	 <form:form action="${pageContext.request.contextPath}/emp" method="POST" modelAttribute="employee">
		<!-- path屬性對應html表單標籤的name屬性值 -->
		<c:if test="${employee.id == null}">
		 	LastName: <form:input path="lastName"/>
		 	<form:errors path="lastName"></form:errors>
		</c:if>
		
		<c:if test="${employee.id != null}">
		<!-- 	對於_method不能使用再form:hidden標籤，是因為modelAttribute對應的bean中沒有_method這個屬性 -->
			<form:hidden path="id"/>
			<input type="hidden" name="_method" value="PUT">
		</c:if>
		
		 	<br>
		 	Email: <form:input path="email"/>
		 	<form:errors path="email"></form:errors>
		 	<br>
		 	
		 	<% 
				Map<String,String> genders = new HashMap();
		 		genders.put("1","Male");
		 		genders.put("0","Female");
		 		
		 		request.setAttribute("genders", genders);
		 	%>
		 	Gender:<br><form:radiobuttons path="gender" items="${genders}" delimiter="<br>"/>
		 	<br>
			<!-- 	
				要把全部部門顯示，所以要傳department全部的值。
				path:將文本框輸入值綁定到form backing object(支持) 的一個屬性。
				items：用於生成 input 元素的 Collection、Map 或 Array。(一個集合物件)
				itemLabel：items 屬性中指定的集合對象的屬性，為每個 input 元素提供 label。(選擇顯示頁面的選項標籤)
				itemValue：items 屬性中指定的集合對象的屬性，為每個 input 元素提供 value。(後端接受到的資料)
			-->
		 	Department: 
		 	<form:select path="department.id" items="${departments}" itemLabel="departmentName" itemValue="id">
		 	</form:select>
		 	<br>
			<!-- 
				原理:
				1.SpringMVC框架會把ServletRequest對象及目標方法的入參實例傳遞給WebDataBinderFactory實例，創建DataBinder實例對象。
					原始碼:WebDataBinder binder = binderfactory.createBinder(request,attribute,name)
					bindRequestParameters(binder,request);綁定 ，binder裡面有多個參數conversionService(數據類型轉換和格式化)、Validator、bindingResult等參數。
					validateIfApplicable(binder,parameter);進行校驗
				2.DataBinder調用裝配在SpringMVC上下文中的ConversionService組件進行數據類型轉換、數據格式化工作。
				     將Servlet中的請求訊息填充到入參對象中。
				3.調用Validator組件對已經綁定了請求消息的入參對象中進行數據合法性校驗，並最終生成數據綁定結果，BindingData對象。
				4.SpringMVC抽取BindingResult中的入參對象和校驗錯誤對象，將他們賦給處理方法的響應入參。(校驗錯誤就會放進BindingResult)
			
				問題一.表單輸入是字串而Birth是Date類型，型態轉換問題:
				問題二.指定Date格式問題:
				問題三.Date驗證問題:
						Ans:
							綁定流程 : 	(1).ServletRequest(request請求)
								   	(2).處理方法入參對象(employee)
								   	(3).創建DataBinder
								   	(4).ConversionService(進行格式化跟類型轉變工作，支持大部分的功能，若有自行規範可實作。)
								   	(5).Validator(進行驗證)
								   	(6).若有錯誤會放在BindingResult裡頭
			測試用: 
			 -->
			Birth:<form:input path="birth"/>
			<form:errors path="birth"></form:errors>
			<br>
			Salary:<form:input path="salary"/>
		 	
		 	<br>
			<input type="submit" value="送出">
		 	
	 </form:form>
	 
	 
	 
</body>
</html>