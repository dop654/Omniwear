<%@page import="omniwear.model.ProdottoBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Omniwear - Catalogo</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
</head>
<body>
	<%@ include file="header.jsp" %>
	<%	String msg = (String) request.getAttribute("msg");
			if(msg != null && !msg.isEmpty()) { %>
				<div class="glass" id="msg">
					<%= msg %>
				</div>
		<% } %>
		<%
			List<String> errors = (List<String>) request.getAttribute("errors");
			String email = (String) request.getAttribute("email");
			if(email == null) { email = ""; }
			String pass = (String) request.getAttribute("pass");
			if(pass == null) { pass = ""; }
		%>
		<% if(errors != null && !errors.isEmpty()) { %>
			<div class="glass" id="error">
				<ul>
					<% for(String e : errors) { %>
						<li><%= e %></li>
					<% } %>
				</ul>
			</div>
		<% } %>
	<section class="glass" id = "catalogo">
		<%	List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("listaProdotti"); 
			if(prodotti != null) {
				for(ProdottoBean p : prodotti) { %>
					<form class="glass" id="schedaP" method="POST" action="${pageContext.request.contextPath}/CartServlet">
						<input type="hidden" id="action" name="action" value="aggiungi">
						<input type="hidden" id="id_prodotto" name="id_prodotto" value="<%= p.getIdProdotto() %>">
						<input type="hidden" id="quantita" name="quantita" value="1">
						<label id="nomeProdotto" name="nomeProdotto"><%= p.getNomeProdotto() %></label><br>
						<label id="prezzo" name="prezzo"><%= p.getPrezzo() %></label><br>
						<a href="${pageContext.request.contextPath}/SchedaProdottoServlet">Dettagli</a>
						<input type="submit" value="Aggungi al carrello">
					</form>
			<%	}
			} %>
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>