<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="omniwear.model.Carrello"%>
<%@page import="omniwear.model.ProdottoCarrello"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Omniwear - Carrello</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
	<script src="${pageContext.request.contextPath}/scripts/ajax.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/cart.js"></script>
</head>
<body>
	<%@ include file="header.jsp" %>
	<%	String msg = (String) request.getAttribute("msg");
		if(msg != null && !msg.isEmpty()) { %>
			<div class="glass" id="msg">
				<%= msg %>
			</div>
	<% } %>
	<%	List<String> errors = (List<String>) request.getAttribute("errors");
		if(errors != null && !errors.isEmpty()) { %>
		<div class="glass" id="error">
			<ul>
				<% for(String e : errors) { %>
					<li><%= e %></li>
				<% } %>
			</ul>
		</div>
	<% } %>
	
	
	<section class="glass" id="cart_container">
		<%	
			Carrello cart = (Carrello) session.getAttribute("carrello");
			if(cart != null && cart.getNProdotti() > 0) { 
		%>
			<h2>Prodotti:</h2>
			<section id="elenco_prodotti">
				<% 
					for(ProdottoCarrello p : cart.getProdotti()) { 
				%>
					<h3><%= p.getProd().getNomeProdotto() %></h3>
					<p>Prezzo: <%= p.getPrezzo() %>€</p>
					
					<!-- TODO: AGGIORNA IL FORM PER LEVARE IL PULSANTE -->
					<form action="${pageContext.request.contextPath}/CartServlet" method="POST">
						<input type="hidden" name="action" value="modifica">
						<input type="hidden" name="id_prodotto" value="<%= p.getId_prodotto() %>">
						<label>Quantità:</label>
						<input type="number" name="quantita" value="<%= p.getQuantita() %>" min="0">
						<input type="submit" value="Aggiorna">
					</form>
					<form action="${pageContext.request.contextPath}/CartServlet" method="POST">
						<input type="hidden" name="action" value="rimuovi">
						<input type="hidden" name="id_prodotto" value="<%= p.getId_prodotto() %>">
						<input type="submit" value="Rimuovi">
					</form>
				<% 
					}
				%>
			</section>
			<aside id="riepilogo">
				<h3>Riepilogo ordine:</h3><br>
				<ul id="prezzi_prodotti">
					
				</ul>
				<hr>
				<p id="n_prodotti">Numero prodotti: <%= cart.getNProdotti() %></p>
				<h4 id="totale">Totale: <%= cart.getTotale() %>€</h4>
			</aside>
		<%	} else { %>
			<h1>Il carrello è vuoto</h1><br>
			<a href="${pageContext.request.contextPath}/index">Torna alla home</a>
		<%	} %>
	</section>
	
	<%@ include file="footer.jsp" %>
</body>
</html>