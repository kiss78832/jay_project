<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>
	<h4>ModelAndView_page 铬Θ</h4>
	
	<p>--------------ModelAndView代刚-------------------</p>
	
	<b>ModelAndView代刚</b>: ${requestScope.time}
	
	<p>--------------Map代刚-------------------</p>
		
	<b>Map代刚</b>: ${requestScope.name}
	
	<p>---------------SessionAttribute代刚------------------</p>
	
	<b>RequestScope代刚</b>: ${requestScope.user}
	<br>
	<b>SessionScope代刚</b>: ${sessionScope.user}
	<br>
	<b>Request_school代刚</b>: ${requestScope.school}
	<br>
	<b>SessionScope_school代刚</b>: ${sessionScope.school}
	<br>
	<b>Request_isNumber代刚</b>: ${requestScope.number}
	<br>
	<b>SessionScope_isNumber代刚</b>: ${sessionScope.number}
			
</body>
</html>