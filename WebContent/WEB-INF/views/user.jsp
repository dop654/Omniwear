<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="omniwear.model.UtenteBean" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>
	<%	UtenteBean utente = (UtenteBean) request.getAttribute("utente");
			if(utente != null) { %>
			<%= utente.getNome() %>
			<%= utente.getCognome() %>
	<%	} %>
</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
<script src="scripts/utils.js"></script>
</head>
<body>
	<section class="glass" id="form">
			<h3>I tuoi dati</h3>
			<hr><br>
			
			<form action="user_page" method="POST">
				<div class="row">
					<label for="nome">Nome:</label>
					<input type="text" id="nome" name="nome" value="<%= utente.getNome()%>" required>
				</div>
				
				<div class="row">
					<label for="cognome">Cognome:</label>
					<input type="text" id="cognome" name="cognome" value="<%= utente.getCognome()%>" required>
				</div>
				
				<div class="row">
					<label for="data_nascita">Data di nascita:</label>
					<input type="date" id="data_nascita" name="data_nascita" value="<%= utente.getDataNascita()%>" required>
				</div>
				
				<div class="row">
					<label for="password">Nuova Password:</label>
					<input type="password" id="password" name="password" placeholder="">
				</div>
				
				<br>
				<div class="row">
					<input type="submit" value="Aggiorna dati">
			</div>
		</form>
	</section>
	
	<section id="dash">
			<a href="user_ordini">I miei Ordini</a>
	</section>
</body>
</html>