	<%@page import="omniwear.model.UtenteBean"%>
	<%@page import="java.util.List"%>
	<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	<!DOCTYPE html>
	<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="UTF-8">
		<title>Dashboard Admin</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
		<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
	</head>
	<body>
		<%@ include file="../header.jsp" %>
		<%@ include file="../errorHandler.jsp" %>
		
		<section class="glass" id="utenti" class="dash_container">
			<%	List<UtenteBean> utenti = (List<UtenteBean>) request.getAttribute("listaUtenti");
			
				if(utenti == null || utenti.isEmpty()) { %>
					<div class="glass" id="msg">
						Non ci sono utenti registrati.
						<hr><br>
						<a href="${pageContext.request.contextPath}/admin/home">Torna alla Dashboard</a>
				</div>
		<%	} else {
				for(UtenteBean u : utenti) { %>
	
						<p>ID Utente: <%= u.getIdUtente() %></p><br>
						<p>Nome e Cognome: <%= u.getNome() %> <%= u.getCognome() %></p><br>
						<p>Email: <%= u.getEmail() %></p><br>
						
						<div id="form">
							<% if(!u.getAdmin()) {%>
										<form action="${pageContext.request.contextPath}/admin/users" method="POST">
										<input type="hidden" name="action" value="rimuovi">
										<input type="hidden" name="id_utente" value="<%= u.getIdUtente() %>">
										<input type="submit" value="Rimuovi Utente">
									</form>
								<% } %>
						</div>
						
					</ul>
		<%		} 
			} %>
	</section>
	
	<%@ include file="../footer.jsp" %>
</body>
</html>