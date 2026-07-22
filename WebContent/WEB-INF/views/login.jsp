<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="UTF-8">
	<title>Omniwear - Login</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
</head>
<body>
	<%@ include file="header.jsp" %>
	
	<section class="glass" id="form">
		<h3>Accedi</h3><br>
		
		<%@ include file="errorHandler.jsp" %>
		
		<%	String email = (String) request.getAttribute("email");
			if(email == null) { email = ""; }
			String pass = (String) request.getAttribute("pass");
			if(pass == null) { pass = ""; }
		%>
		<form method="POST" action="${pageContext.request.contextPath}/LoginServlet">
			<span class="row"><label for="email">E-Mail: </label>
			<input type="email" name="email" id="email"></span>
			<span class="row"><label for="pass">Password: </label>
			<input type="password" name="pass" id="pass"></span>
			<input type="submit" value="Accedi">
		</form>
		<br>
		Non sei registrato? <a href="${pageContext.request.contextPath}/RegisterServlet">Fallo ora!</a>
	</section>
	
	<%@ include file="footer.jsp" %>
</body>
</html>