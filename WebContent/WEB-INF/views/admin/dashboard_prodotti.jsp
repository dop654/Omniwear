<%@page import="omniwear.model.ImmagineBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="omniwear.model.ProdottoBean"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
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

	<div id="catalogo_layout">
		<aside class="glass" id="filtri_laterali">
			<h3>Gestione Prodotto</h3>
			<form method="POST" action="${pageContext.request.contextPath}/admin/prodotti" id="interface">
				<input type="hidden" value="inserisci" name="action" id="action">
				<input type="hidden" value="" name="id_prodotto" id="id_prodotto">
				
				<label for="nomeProdotto">Nome prodotto: </label><br>
				<input type="text" value="" placeholder="Nome prodotto" name="nomeProdotto" id="nomeProdotto" required>
				<br><br>
				
				<label for="prezzo">Prezzo: </label><br>
				<input type="number" value="" placeholder="Prezzo" min="0.0" step="0.01" name="prezzo" id="prezzo" required>
				<br><br>
				
				<label for="quantita">Quantità: </label><br>
				<input type="number" value="1" min="0" step="1" name="quantita" id="quantita" required>
				<br><br>
				
				<input type="submit" value="Aggiungi" id="aggiungi">
				<button type="button" value="elimina" id="elimina" onClick="eliminaProdotto(this)">Elimina</button>
			</form>

			<hr style="margin: 20px 0; border: 0; border-top: 1px solid rgba(255,255,255,0.5);">

			<h3>Carica Immagine</h3>
			<form method="post" action="${pageContext.request.contextPath}/admin/image" enctype="multipart/form-data">
				<input type="hidden" name="action" value="upload"/>
				<input type="hidden" value="" name="id_prodotto_img" id="id_prodotto_img">
				
				<input type="file" name="image" id="image" accept="image/*">
				<input type="submit" value="Carica">
			</form>
		</aside>

		<section class="glass" id="prodotti_main">
			<h2>Catalogo Prodotti</h2>
			<%	Collection<ProdottoBean> prodotti = (Collection<ProdottoBean>) request.getAttribute("listaProdotti");
				if(prodotti != null && !prodotti.isEmpty()) {
					for(ProdottoBean p : prodotti) { %>
						<section class="glass scheda_ordine" style="margin-bottom: 20px; padding: 15px;">
							<h4><%= p.getNomeProdotto() %></h4>
							<p>Prezzo: <%= p.getPrezzo() %> €</p>
							<p>Qt. in magazzino: <%= p.getQt() %></p>
							<div class="immagini_prodotto">
								<% if(p.getImmagini() != null) { 
									for(ImmagineBean i : p.getImmagini()) { %>
									<div class="anteprima_immagine">
										<img src="<%= i.getPath() %>" alt="<%= p.getNomeProdotto() %>">
										
										<form method="post" action="${pageContext.request.contextPath}/admin/image">
											<input type="hidden" name="action" value="elimina">
											<input type="hidden" name="path" value="<%= i.getPath() %>">
											<button type="submit" class="elimina_immagine" title="Rimuovi immagine">Rimuovi</button>
										</form>
									</div>
								<% 	} 
								} %>
							</div>
				
							<button type="button" value="modifica" onClick="caricaProdotto(<%= p.getIdProdotto() %>,'<%= p.getNomeProdotto() %>', <%= p.getPrezzo() %>, <%= p.getQt() %>)">Modifica</button>
						</section>
			<%		} 
				} else { %>
					<p style="padding: 15px;">Nessun prodotto presente nel magazzino.</p>
			<% } %>
		</section>

	</div>


	<%@ include file="../footer.jsp" %>
</body>
</html>