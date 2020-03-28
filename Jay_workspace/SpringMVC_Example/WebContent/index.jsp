<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>
	
	<p>-----------------------@RequestMapping基本用法---------------------------------</p>
	<a href="helloworld">hello World</a>
	<br>
	
	<a href="springmvc/testRequestMapping">@testRequestMapping</a>
	<br>
	
	<!-- 預設為get請求，會導致失敗。 -->
	<a href="springmvc/testMethod">@testMethod(限制傳輸方法失敗範例)</a>
	<br><br>
	
	<form action="springmvc/testMethod" method="POST">
		<input type="submit" value="@testMethod(限制傳輸方法成功範例)">
	</form>
	<br>
	
	<!-- age若等於0，會導致失敗，參照@RequestMapping規則 -->
	<a href="springmvc/testParamsAnHeaders?username=jay&age=10">@Params ?後面的參數</a>
	<br>
	
	<!-- (測不出來，改天測)，會導致失敗，參照@RequestMapping規則 -->
	<a href="springmvc/testParamsAnHeaders02">@Header的網頁參數</a>
	<br>
	
	<!-- Ant風格，參照@RequestMapping規則 -->
	<a href="springmvc/antStyle/BBB/abc">@Ant風格</a>
	
	<br>
	<p>-------------------REST風格簡單版Tomcat8.0以上不支援DELETE、PUT訪問，安全性問題-----------------------
	<a href="https://blog.csdn.net/weixin_45165669/article/details/104617304">解決辦法參考</a>
	</p>
	
	
	<!-- REST風格 @PathVariable 占位符 -->
	<a href="springmvc/pathVariable/jay">@PathVariable 占位符</a>
	<br><br>
	
	<!-- REST風格，Get方法-->
	<a href="springmvc/rest01/myGetMethod">@Rest Get方法</a>
	<br><br>
	
	<!-- REST風格，DELETE方法 -->
	<form action="springmvc/rest02/myDeletetMethod" method="POST">
		<input type="hidden" name="_method" value="DELETE">
		<input type="submit" value="@Rest Delete方法">
	</form>
	<br>
	
	<!--REST風格，Put方法 -->
	<form action="springmvc/rest03/myPutMethod" method="POST">
		<input type="hidden" name="_method" value="PUT">
		<input type="submit" value="@Rest 方法">
	</form>
	
	<p>---------------------------@RequestParam範例--------------------------------</p>
	
	<a href="springmvc/requestParam?username=jay&age=99">Test @RequestParam</a>
	
	<p>---------------------------@RequestHeader範例--------------------------------</p>
	
	<a href="springmvc/requestHeader">Test @RequestHeader</a>
	
	<p>---------------------------@RequestHeader範例--------------------------------</p>
	
	<a href="springmvc/cookieValue">Test @CookieValue</a>
	
	<p>---------------------------級聯屬性，塞一個POJO當參數，範例--------------------------------</p>
	
	<a href="springmvc/pojoParams">Test pojoParams</a>
	<form action="springmvc/pojoParams" method="POST">
		User:<input type="text" name="username">
		<br>
		Password:<input type="password" name="password">
		<br>
		Email:<input type="text" name="email">
		<br>
		Age:<input type="text" name="age">
		<br>
		City:<input type="text" name="address.city">
		<br>
		Province:<input type="text" name="address.province">
		<br>
		<input type="submit" value="送出表單">
	</form>
	
	<p>---------------------------@RequestHeader範例	--------------------------------</p>
	
	<a href="springmvc/servletAPI">Test ServletAPI</a>
	<br>
	<br>
	<br>
	
	
	<p>---ModelAndView範例---------------------------------------------------------------------------------------------------------------------------------------------</p>
	
	<a href="springmvc02/modelAndView">Test ModelAndView</a>
	
	<p>---------------------------  Map範例	--------------------------------</p>
	
	<a href="springmvc02/mapParams">Test Map</a>

	<p>---------------------------  SessionAttribute範例	--------------------------------</p>
	
	<a href="springmvc02/sessionAttributes">Test Map</a>
	
	<p>---------------------------@ModelAttribute註解範例--------------------------------</p>
	
	<!-- 範例1.示範密碼是空值 -->
	<form action="springmvc02/modelAttribute" method="POST">
		User:<input type="text" name="username">
		<br>
		Email:<input type="text" name="email">
		<br>
		Age:<input type="text" name="age">
		<br>
		Address:<input type="text" name="address.city">
		<br>
		Province:<input type="text" name="address.province">
		<br>
		<input type="submit" value="修改密碼">
	</form>

	<p>----------------  InternalResourceViewResolver視圖講解過程	-------------------</p>
	<a href="springmvc02/internalResourceViewResolver">InternalResourceViewResolver視圖講解過程</a>
	<br>
	<a href="http://localhost:18080/SpringMVC_Example/success">/success就可以直接進入index</a>
	
	<p>----------------  InternalResourceViewResolver視圖講解過程	-------------------</p>
	<a href="springmvc02/BeanNameViewResolver">BeanNameViewResolver視圖範例</a>
	
	<p>----------------  Redirect	-------------------</p>
	<a href="springmvc02/redirect">Redirect跳轉頁面範例</a>
	
	<p>----------------  Forward	-------------------</p>
	<a href="springmvc02/forward">Forward跳轉頁面範例</a>
	
	<br><br><br>
	

</body>
</html>