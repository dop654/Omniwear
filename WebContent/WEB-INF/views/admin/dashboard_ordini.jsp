	<%@page import="omniwear.model.OrdineBean"%>
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
		<div id="catalogo_layout">
			<aside class="glass" id="filtri_laterali">
				<form action="${pageContext.request.contextPath}/admin/ordini" method="GET">
					<h3>Filtra Ordini</h3>
					
					<label for="filtroData">Data Ordine:</label><br>
					<input type="date" id="filtroData" name="filtroData">
					<br><br>
					
					<label for="filtroEmail">Email Utente:</label><br>
					<input type="email" id="filtroEmail" name="filtroEmail">
					<br><br>
	
					<input type="submit" value="Filtra" class="filtra">
					<a href="${pageContext.request.contextPath}/admin/ordini" class="reset">Resetta filtri</a>
				</form>
		</aside>
		<section class="glass" id="prodotti_main">
			<h2>Gestione Ordini</h2>
			<%	List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("listaOrdini");
				String[] statoOrd = {"Annullato", "Ricevuto", "Spedito", "Consegnato"};
				if(ordini == null || ordini.isEmpty()) { %>
					<div class="glass" id="msg" style="padding: 15px; margin-top: 15px;">
						Nessun ordine trovato con i filtri selezionati.
					</div>
			<%	} else {
					for(OrdineBean o : ordini) { %>
						<section class="glass scheda_ordine">
							<ul>
								<li>ID Ordine: <%= o.getIdOrdine() %></li>
								<li>Data: <%= o.getDataOrdine() %></li>
								<li>Cliente:
									<% if(o.getUtente() != null) { %>
										<%= o.getUtente().getNome() %> <%= o.getUtente().getCognome() %> (<%= o.getUtente().getEmail() %>)
									<% } else { %>
										Utente ID: <%= o.getIdUtente() %>
									<% } %>
								</li>
								<li>Totale: <%= o.getTotale() %>€</li>
								
								<li>
									<div id="form">
										<form action="${pageContext.request.contextPath}/admin/ordini" method="POST">
											<input type="hidden" name="id_ordine" value="<%= o.getIdOrdine() %>">
											<label for="stato">Stato:</label>
											<select name="stato" id="stato">
												<% for(int i = 0; i<statoOrd.length; i++) { %>
													<option value="<%= i %>" <%= (o.getStatoOrdine() == i) ? "selected" : "" %>><%= statoOrd[i] %></option>
												<% } %>
											</select>
											<input type="submit" value="Aggiorna stato">
										</form>
									</div>
								</li>
							</ul>
						</section>
			<%		} 
				} %>
		</section>
		</div>
		<%@ include file="../footer.jsp" %>
	</body>
	</html>