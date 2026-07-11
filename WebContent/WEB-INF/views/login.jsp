<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Omniwear - Login</title>
	<link rel="stylesheet" type="text/css" href="styles/style.css">
	<link rel="icon" type="image/x-icon" href="images/favicons/favicon.ico">
</head>
<body>
	<%@ include file="header.jsp" %>
	
	<section class="glass" id="form">
		<h3>Accedi</h3><br>
		<%List<String> errors = (List<String>) request.getAttribute("errors");%>
		<% if(errors != null && !errors.isEmpty()) { %>
			<div class="glass error">
				<ul>
					<% for(String e : errors) { %>
						<li><%= e %></li>
					<% } %>
				</ul>
			</div>
		<% } %>
		<form method="POST" action="${request.getContextPath}LoginServlet">
			<span class="row"><label for="email">E-Mail: </label>
			<input type="email" name="email" id="email"></span>
			<span class="row"><label for="pass">Password: </label>
			<input type="password" name="pass" id="pass"></span>
			<input type="submit" value="Accedi">
		</form>
		<br>
		Non sei registrato? <a href="${request.getContextPath}RegisterServlet">Fallo ora!</a>
	</section>
	
	<%@ include file="footer.jsp" %>
</body>
</html>