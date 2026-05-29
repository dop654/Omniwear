<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Omniwear - Registrazione</title>
	<link rel="stylesheet" type="text/css" href="styles/style.css">
	<link rel="icon" type="image/x-icon" href="images/favicons/favicon.ico">
</head>
<body>
	<%@ include file="header.jsp" %>
	
	<section id="form">
		<h3>Registrazione</h3><br>
		<form method="POST" action="${request.getContextPath}RegisterServlet">
			<span class="row"><label for="nome">Nome: </label>
			<input type="text" name="nome" id="nome" required></span>
			<span class="row"><label for="cognome">Cognome: </label>
			<input type="text" name="cognome" id="cognome" required></span>
			<span class="row"><label for="email">E-Mail: </label>
			<input type="email" name="email" id="email" required></span>
			<span class="row"><label for="password">Password: </label>
			<input type="password" name="password" id="password" required></span>
			<span class="row"><label for="dataNascita">Data di nascita: </label>
			<input type="date" name="dataNascita" id="dataNascita" required></span>
			<input type="submit" value="Registrati">
		</form>
		<br>
		Già registrato? <a href="${request.getContextPath}LoginServlet">Accedi!</a>
	</section>
	
	<%@ include file="footer.jsp" %>
</body>
</html>