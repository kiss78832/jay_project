<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>
	
	<p>-----------------------@RequestMapping�򥻥Ϊk---------------------------------</p>
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
	<p>-------------------REST����²�檩Tomcat8.0�H�W���䴩DELETE�BPUT�X�ݡA�w���ʰ��D-----------------------
	<a href="https://blog.csdn.net/weixin_45165669/article/details/104617304">�ѨM��k�Ѧ�</a>
	</p>
	
	
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
	
	<p>---------------------------@RequestParam�d��--------------------------------</p>
	
	<a href="springmvc/requestParam?username=jay&age=99">Test @RequestParam</a>
	
	<p>---------------------------@RequestHeader�d��--------------------------------</p>
	
	<a href="springmvc/requestHeader">Test @RequestHeader</a>
	
	<p>---------------------------@RequestHeader�d��--------------------------------</p>
	
	<a href="springmvc/cookieValue">Test @CookieValue</a>
	
	<p>---------------------------���p�ݩʡA��@��POJO��ѼơA�d��--------------------------------</p>
	
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
		<input type="submit" value="�e�X���">
	</form>
	
	<p>---------------------------@RequestHeader�d��	--------------------------------</p>
	
	<a href="springmvc/servletAPI">Test ServletAPI</a>
	<br>
	<br>
	<br>
	
	
	<p>---ModelAndView�d��---------------------------------------------------------------------------------------------------------------------------------------------</p>
	
	<a href="springmvc02/modelAndView">Test ModelAndView</a>
	
	<p>---------------------------  Map�d��	--------------------------------</p>
	
	<a href="springmvc02/mapParams">Test Map</a>

	<p>---------------------------  SessionAttribute�d��	--------------------------------</p>
	
	<a href="springmvc02/sessionAttributes">Test Map</a>
	
	<p>---------------------------@ModelAttribute���ѽd��--------------------------------</p>
	
	<!-- �d��1.�ܽd�K�X�O�ŭ� -->
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
		<input type="submit" value="�ק�K�X">
	</form>

	<p>----------------  InternalResourceViewResolver�������ѹL�{	-------------------</p>
	<a href="springmvc02/internalResourceViewResolver">InternalResourceViewResolver�������ѹL�{</a>
	<br>
	<a href="http://localhost:18080/SpringMVC_Example/success">/success�N�i�H�����i�Jindex</a>
	
	<p>----------------  InternalResourceViewResolver�������ѹL�{	-------------------</p>
	<a href="springmvc02/BeanNameViewResolver">BeanNameViewResolver���Ͻd��</a>
	
	<p>----------------  Redirect	-------------------</p>
	<a href="springmvc02/redirect">Redirect���୶���d��</a>
	
	<p>----------------  Forward	-------------------</p>
	<a href="springmvc02/forward">Forward���୶���d��</a>
	
	<br><br><br>
	

</body>
</html>