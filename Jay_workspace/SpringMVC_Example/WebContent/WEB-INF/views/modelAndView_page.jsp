<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>
	<h4>ModelAndView_page �������\</h4>
	
	<p>--------------�yModelAndView�z����-------------------</p>
	
	<b>�yModelAndView�z����</b>: ${requestScope.time}
	
	<p>--------------�yMap�z����-------------------</p>
		
	<b>�yMap�z����</b>: ${requestScope.name}
	
	<p>---------------�ySessionAttribute�z����------------------</p>
	
	<b>�yRequestScope�z����</b>: ${requestScope.user}
	<br>
	<b>�ySessionScope�z����</b>: ${sessionScope.user}
	<br>
	<b>�yRequest_school�z����</b>: ${requestScope.school}
	<br>
	<b>�ySessionScope_school�z����</b>: ${sessionScope.school}
	<br>
	<b>�yRequest_isNumber�z����</b>: ${requestScope.number}
	<br>
	<b>�ySessionScope_isNumber�z����</b>: ${sessionScope.number}
			
</body>
</html>