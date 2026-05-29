<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	<section id="form">
		<h3>Accedi</h3><br>
		<form method="POST" action="${request.getContextPath}LoginServlet">
			<label for="email">E-Mail: </label>
			<input type="email" name="email" id="email"><br>
			<label for="pass">Password: </label>
			<input type="password" name="pass" id="pass"><br>
			<input type="submit" value="Accedi">
		</form>
		Non sei registrato? <a href="${request.getContextPath}RegisterServlet">Fallo ora!</a>
	</section>
	
	<%@ include file="footer.jsp" %>
</body>
</html>