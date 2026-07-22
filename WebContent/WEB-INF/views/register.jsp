<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="UTF-8">
	<title>Omniwear - Registrazione</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
	<script src="${pageContext.request.contextPath}/scripts/validate.js"></script>
</head>
<body>
	<%@ include file="header.jsp" %>
	
	<section class="glass" id="form">
		<h3>Registrazione</h3><br>
		
		<%@ include file="errorHandler.jsp" %>
		
		<form method="POST" action="${pageContext.request.contextPath}/RegisterServlet">
			<span class="row"><label for="nome">Nome: </label>
				<input type="text" name="nome" id="nome" required pattern="^[A-z]+(\s[A-z]+)*$" onchange="validateField(this, document.getElementById('errore_nome'), msg_solo_lettere)">
				<span id="errore_nome"></span>
			</span>
			<span class="row"><label for="cognome">Cognome: </label>
				<input type="text" name="cognome" id="cognome" required pattern="^[A-z]+(\s[A-z]+)*$" onchange="validateField(this, document.getElementById('errore_cognome'), msg_solo_lettere)">
				<span id="errore_cognome"></span>
			</span>
			<span class="row">
				<label for="email">E-Mail: </label>
				<input type="email" name="email" id="email" required onchange="validateField(this, document.getElementById('errore_mail'), msg_email)">
				<span id="errore_mail"></span>
			</span>
			<span class="row">
				<label for="password">Password: </label>
				<input type="password" name="password" id="password" required pattern="/^\w[A-z\d_.,!?]{8,}$/" onchange="validateField(this, document.getElementById('errore_pw'), msg_pw)">
				<span id="errore_pw"></span>
			</span>
			<span class="row">
				<label for="dataNascita">Data di nascita: </label>
				<input type="date" name="dataNascita" id="dataNascita" required>
			</span>
			<input type="submit" value="Registrati">
		</form>
		<br>
		Già registrato? <a href="${pageContext.request.contextPath}/LoginServlet">Accedi!</a>
	</section>
	
	<%@ include file="footer.jsp" %>
</body>
</html>