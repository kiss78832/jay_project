<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>

	<a href="helloworld">hello World</a>
	<br>
	
	<a href="springmvc/testRequestMapping">@testRequestMapping</a>
	<br>
	
	<!-- �w�]��get�ШD�A�|�ɭP���ѡC -->
	<a href="springmvc/testMethod">@testMethod(����ǿ��k���ѽd��)</a>
	<br><br>
	
	<form action="springmvc/testMethod" method="POST">
		<input type="submit" value="@testMethod(����ǿ��k���\�d��)">
	</form>
	<br>
	
	<!-- age�Y����0�A�|�ɭP���ѡA�ѷ�@RequestMapping�W�h -->
	<a href="springmvc/testParamsAnHeaders?username=jay&age=10">@Params ?�᭱���Ѽ�</a>
	<br>
	
	<!-- (�����X�ӡA��Ѵ�)�A�|�ɭP���ѡA�ѷ�@RequestMapping�W�h -->
	<a href="springmvc/testParamsAnHeaders02">@Header�������Ѽ�</a>
	<br>
	
	<!-- Ant����A�ѷ�@RequestMapping�W�h -->
	<a href="springmvc/antStyle/BBB/abc">@Ant����</a>
	
	<br>
	<p>-------------------REST����²�檩Tomcat8.0�H�W���䴩DELETE�BPUT�X�ݡA�w���ʰ��D-----------------------</p>
	
	<!-- REST���� @PathVariable �e��� -->
	<a href="springmvc/pathVariable/jay">@PathVariable �e���</a>
	<br><br>
	
	<!-- REST����AGet��k-->
	<a href="springmvc/rest01/myGetMethod">@Rest Get��k</a>
	<br><br>
	
	<!-- REST����ADELETE��k -->
	<form action="springmvc/rest02/myDeletetMethod" method="POST">
		<input type="hidden" name="_method" value="DELETE">
		<input type="submit" value="@Rest Delete��k">
	</form>
	<br>
	
	<!--REST����APut��k -->
	<form action="springmvc/rest03/myPutMethod" method="POST">
		<input type="hidden" name="_method" value="PUT">
		<input type="submit" value="@Rest ��k">
	</form>
	<br><br>
	

</body>
</html>