<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>
	<h4>ModelAndView_page 跳頁成功</h4>
	
	<p>--------------『ModelAndView』測試-------------------</p>
	
	<b>『ModelAndView』測試</b>: ${requestScope.time}
	
	<p>--------------『Map』測試-------------------</p>
		
	<b>『Map』測試</b>: ${requestScope.name}
	
	<p>---------------『SessionAttribute』測試------------------</p>
	
	<b>『RequestScope』測試</b>: ${requestScope.user}
	<br>
	<b>『SessionScope』測試</b>: ${sessionScope.user}
	<br>
	<b>『Request_school』測試</b>: ${requestScope.school}
	<br>
	<b>『SessionScope_school』測試</b>: ${sessionScope.school}
	<br>
	<b>『Request_isNumber』測試</b>: ${requestScope.number}
	<br>
	<b>『SessionScope_isNumber』測試</b>: ${sessionScope.number}
			
</body>
</html>