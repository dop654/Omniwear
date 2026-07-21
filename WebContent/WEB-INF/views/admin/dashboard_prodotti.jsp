<%@page import="omniwear.model.ProdottoBean"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Dashboard Admin - Prodotti</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css">
	<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicons/favicon.ico">
	<script src="${pageContext.request.contextPath}/scripts/admin.js"></script>
</head>
<body>
	<%@ include file="../header.jsp" %>
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
	<section class="glass" id="add_prodotto">
		<form method="POST" action="${pageContext.request.contextPath}/admin/prodotti" id="interface">
			<input type="hidden" value="inserisci" name="action" id="action">
			<input type="hidden" value="" name="id_prodotto" id="id_prodotto">
			
			<label for="nomeProdotto">Nome prodotto: </label>
			<input type="text" value="" placeholder="Nome prodotto" name="nomeProdotto" id="nomeProdotto" required>
			
			<label for="prezzo">Prezzo: </label>
			<input type="number" value="" placeholder="Prezzo" min="0.0" step="0.01" name="prezzo" id="prezzo" required>
			
			<label for="quantita">Quantità: </label>
			<input type="number" value="1" min="0" step="1" name="quantita" id="quantita" required>
			
			<input type="submit" value="Aggiungi" id="aggiungi">
			<button type="button" value="elimina" id="elimina" onClick="eliminaProdotto(this)">Elimina</button>
		</form>
	</section>
	<section class="glass" id="catalogo">
		<%	Collection<ProdottoBean> prodotti = (Collection<ProdottoBean>) request.getAttribute("listaProdotti");
			if(prodotti != null) {
				for(ProdottoBean p : prodotti) { %>
					<section class="glass" id="scheda_prodotto">
						<h4><%= p.getNomeProdotto() %></h4>
						<p>Prezzo: <%= p.getPrezzo() %> €</p>
						<p>Qt. in magazzino: <%= p.getQt() %></p>
			
						<button type="button" value="modifica" onClick="caricaProdotto(<%= p.getIdProdotto() %>,'<%= p.getNomeProdotto() %>', <%= p.getPrezzo() %>, <%= p.getQt() %>)">Modifica</button>
					</section>
		<%		} 
			}		%>
	</section>
	<%@ include file="../footer.jsp" %>
</body>
</html>