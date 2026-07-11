<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<header class="glass">
		<div id="logo">
			<img src="images/logo_b.svg" alt="logo">
			<h1>OMNIWEAR</h1>
		</div>
		<nav>
			<ul>
				<li><a href="${request.getContextPath}HomeServlet">Home</a></li>
				<li><a href="#">Sconti</a></li>
				<li>
					<% if(session.getAttribute("id_utente") == null) { %>
							<a href="${request.getContextPath}LoginServlet">Accedi</a>
						<% } else {%>
						<% String nomeUtente = (String) session.getAttribute("nome_utente");%>	
							<a href="${request.getContextPath}LogoutServlet">Ciao, <%= nomeUtente%></a>
					<% } %>
				</li>
				<li><a href="#">Carrello</a></li>
			</ul>
		</nav>
	</header>
</body>
</html>