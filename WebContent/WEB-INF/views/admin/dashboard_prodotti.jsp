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
		<form method="POST" action="${pageContext.request.contextPath}/admin/prodotti">
			<input type="hidden" value="inserisci" name="action">
			<label for="nomeProdotto">Nome prodotto: </label>
			<input type="text" name="nomeProdotto" id="nomeProdotto" required>
			<label for="prezzo">Prezzo: </label>
			<input type="number" min="0.0" step="0.01" name="prezzo" id="prezzo" required>
			<input type="submit" value="Aggiungi">
		</form>
	</section>
	<section class="glass" id="catalogo">
		<%	Collection<ProdottoBean> catalogo = (Collection<ProdottoBean>) request.getAttribute("listaProdotti"); 
			for(ProdottoBean p : catalogo) { %>
				<form method="POST" action="${pageContext.request.contextPath}/admin/prodotti">
					<input type="hidden" value="" name="action" id="action">
					<input type="hidden" value="<%= p.getIdProdotto() %>" name="id_prodotto">
					<input type="text" value="<%= p.getNomeProdotto() %>" name="nomeProdotto" id="nomeProdotto" required>
					<input type="number" value="<%= p.getPrezzo() %>" min="0.0" step="0.01" name="prezzo" id="prezzo" required>
					<input type="submit" value="Modifica" onClick="aggiornaProdotto()">
					<input type="submit" value="Elimina" onClick="eliminaProdotto()">
				</form>
		<%	} %>
	</section>
	<%@ include file="../footer.jsp" %>
</body>
</html>