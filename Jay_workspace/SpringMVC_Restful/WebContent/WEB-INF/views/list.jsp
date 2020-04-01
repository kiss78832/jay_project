<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript">

	/*
		按下class是"delete"就會把href內的網址，引用到action裡面，再執行送出。	
	*/
	$(function(){
		$(".delete").click(function(){
			var href = $(this).attr("href");
			$("form").attr("action",href).submit();
			return false;
		});
	})
</script>
</head>
<body>
<!-- 
	SpringMVC 處理靜態資源(CSS.JS.圖片):
		(1)  為什麼會有這樣的問題?   優雅的REST風格的資源URL不希望帶 .html 或 .do 等後綴，
				若將DispatcherSerclet 請求映射配置為 / ，則Spring MVC將捕捉到WEB所有請求，包括靜態資源的請求 ，
				    SpringMVC 會將他們當作一個普通的請求處理，因找不到"對應的處理器"(映射的RequestMapping)將導致錯誤。
		(2) 解決辦法:  
				a.直接處理帶有後綴的請求。(不推薦，不符合REST風格)
				b.在SpringMVC的配置文件中配置<mvc:default-servlet-handler/>
 -->
 
	<!-- (隱藏刪除表單)<input name="_method"> HiddenHttpMethodFilter設置請求參數必須為 "_method" -->
	<form action="" method="POST">
		<input type="hidden" name="_method" value="DELETE">
				
	</form>


	<c:if test="${empty requestScope.employees}">
		沒有任何員工訊息
	</c:if>
	
	<c:if test="${!empty requestScope.employees}">
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<th>ID</th>
				<th>LastName</th>
				<th>Email</th>
				<th>Gender</th>
				<th>Department</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>		
		
			<!--
				items="${requestScope.employees}"去取map內的key為"employees"，
			 	EmployeeHandler.list()有塞全部資料->map.put("employees", employeeDao.getAll());
			 	在設置參數var去取單個單個的資料。 
			-->
			<c:forEach items="${requestScope.employees}" var="emp">
				<tr>
					<td>${emp.id}</td>
					<td>${emp.lastName}</td>
					<td>${emp.email}</td>
					<td>${emp.gender == 0 ? 'female' : 'Male'}</td>
					<td>${emp.department.departmentName}</td>
					<!-- 修改連結，連結後面帶一個id過去 -->
					<td><a href="emp/${emp.id}">Edit</a></td>
					<!-- 刪除連結，連結後面帶一個id過去 -->
					<td><a class="delete" href="emp/${emp.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<br><br>
	
	<a href="emp">新增員工</a>
	

</body>
</html>