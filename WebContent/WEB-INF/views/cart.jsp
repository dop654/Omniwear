<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="omniwear.model.Carrello"%>
<%@page import="omniwear.model.ProdottoCarrello"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="UTF-8">
	<title>Omniwear - Carrello</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
	<script src="${pageContext.request.contextPath}/scripts/ajax.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/cart.js"></script>
</head>
<body>
	<%@ include file="header.jsp" %>
	<%@ include file="errorHandler.jsp" %>
	
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
						<div class="bottoni_carrello">
							<input type="submit" class="bottone_sx" value="Aggiorna">
						</div>
					</form>
					<form action="${pageContext.request.contextPath}/CartServlet" method="POST">
						<input type="hidden" name="action" value="rimuovi">
						<input type="hidden" name="id_prodotto" value="<%= p.getId_prodotto() %>">
						<div class="bottoni_carrello">
							<input type="submit" class="bottone_dx" value="Rimuovi">
						</div>
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
				
				<% if(session.getAttribute("utente") != null) { %>
					<a href="${pageContext.request.contextPath}/checkout">Procedi all'ordine</a>
				<% } else { %>
					<p>Devi effettuare l'accesso per completare l'ordine.</p>
					<a href="${pageContext.request.contextPath}/LoginServlet">Accedi per ordinare!</a>
				<% } %>
			</aside>
		<%	} else { %>
			<h1>Il carrello è vuoto</h1><br>
			<a href="${pageContext.request.contextPath}/index">Torna alla home</a>
		<%	} %>
	</section>
	
	<%@ include file="footer.jsp" %>
</body>
</html>