	<%@page import="omniwear.model.UtenteBean"%>
	<%@page import="java.util.List"%>
	<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>Dashboard Admin</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
		<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
	</head>
	<body>
		<%@ include file="../header.jsp" %>
		<%@ include file="../errorHandler.jsp" %>
		
		<section class="glass" id="utenti">
			<%	List<UtenteBean> utenti = (List<UtenteBean>) request.getAttribute("listaUtenti");
			
				if(utenti == null || utenti.isEmpty()) { %>
					<div class="glass" id="msg">
						Non ci sono utenti registrati.
						<hr><br>
						<a href="${pageContext.request.contextPath}/admin/home">Torna alla Dashboard</a>
				</div>
		<%	} else {
				for(UtenteBean u : utenti) { %>
					<ul>
						<li>ID Utente: <%= u.getIdUtente() %></li>
						<li>Nome e Cognome: <%= u.getNome() %> <%= u.getCognome() %></li>
						<li>Email: <%= u.getEmail() %></li>
						
						<li>
							<div id="form">
				
								<form action="${pageContext.request.contextPath}/admin/users" method="POST">
									<input type="hidden" name="action" value="rimuovi">
									<input type="hidden" name="id_utente" value="<%= u.getIdUtente() %>">
									<input type="submit" value="Rimuovi Utente">
								</form>
							</div>
						</li>
					</ul>
		<%		} 
			} %>
	</section>
	
	<%@ include file="../footer.jsp" %>
</body>
</html>