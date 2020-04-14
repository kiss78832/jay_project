<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript">
	$(function(){
		$("#testJson").click(function(){
			var url = this.href;
			var args = {};
			$.post(url,args,function(data){ 
				for(var i = 0 ; i < data.length; i++){
					var id = data[i].id;
					var lastName = data[i].lastName;
					
					alert("testJson: "id + ":" + lastName);
				}
			});
			return false;
		});
	})
</script>


</head>
<body>
	
	<!-- 映射到EmployeeHandler的@RequestMapping("/emps")回傳list頁面 -->
	<b style="color:red;background-color: #ffdbdb;">『轉到全體員工頁面(/emps)』:</b><br>
	<a href="emps">List All Employees</a>
	
	<br><br>
	
	<b style="color:red;background-color: #ffdbdb;">『傳Json格式，前後端呼應』:</b><br>
	<a href="testJson" id="testJson">Test Json @ResponseBody and @RequestBody</a>
	
	<br><br>
	
	
	<b style="color:red;background-color: #ffdbdb;">『@ResponseBody設參數，@ResponseBody設方法(須研究)』:</b><br>
	<form action="testHttpMessageConverter" method="POST" enctype="multipart/form-data">
		File: <input type="file" name="file"/>
		Desc: <input type="text" name="desc"/>
		<input type="submit" value="Submit"/>
	</form>
	
		<br>
	<b style="color:red;background-color: #ffdbdb;">『測試下載檔案』:</b><br>
	<a href="testResponseEntity">Test ResponseEntity</a>
	
	
		<br><br>
	<b style="color:red;background-color: #ffdbdb;">『國際化測試』:</b><br>	
	<a href="i18n">I18N PAGE</a>
	
	
		<br><br>
	
	<!-- 檔案上傳連結 -->
	<b style="color:red;background-color: #ffdbdb;">『測試上傳時轉成json印到console』:</b>
	<form action="testFileUpload" method="POST" enctype="multipart/form-data">
		File: <input type="file" name="file"/>
		Desc: <input type="text" name="desc"/>
		<input type="submit" value="Submit"/>
	</form>
	
	<!-- 測試錯誤導向指定"頁面" -->
	<br>
	<b style="color:red;background-color: #ffdbdb;">『測試錯誤導向指定"頁面"，嘗試修改url的i值，錯誤請改0』:</b><br>
	<a href="testExceptionHandlerExceptionResolver?i=10">Test ExceptionHandlerExceptionResolver</a>
		
	
		<!-- 測試錯誤導向指定"錯誤處理"(自行設定500.404.403等等)1， @ResponseStatus 定義在類別測試-->
	<br><br>
	<b style="color:red;background-color: #ffdbdb;">『測試錯誤導向指定"錯誤處理"，錯誤請改13』:</b><br>
	<a href="testResponseStatusExceptionResolver?i=10">Test ResponseStatusExceptionResolver</a>
		<br>
		
		<!-- 測試錯誤導向指定"錯誤處理"(自行設定500.404.403等等)2， @ResponseStatus 定義在方法測試 -->
	<br>
	<b style="color:red;background-color: #ffdbdb;">『測試錯誤導向指定"錯誤處理"，會直接跳404，但方法會執行。』:</b><br>
	<a href="testResponseStatusExceptionResolver2?i=10">Test ResponseStatusExceptionResolver</a>
		<br>
		
		<!-- 針對http的錯誤處理，例如錯誤請求(POST、GET...)，錯誤頁面都是官方預設的 -->
	<br>
	<b style="color:red;background-color: #ffdbdb;">『測試針對Http特殊錯誤進行處理』:</b><br>
	<a href="testDefaultHandlerExceptionResolver">Test DefaultHandlerExceptionResolver</a>
</body>
</html>