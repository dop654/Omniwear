<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="omniwear.model.OrdineBean" %>
<%@ page import="omniwear.model.OrdineProdottoBean" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Omniwear - Dettaglio Ordine</title>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/styles/style.css">
</head>
<body>
	<%@ include file="header.jsp" %>
	
	<%@ include file="errorHandler.jsp" %>
	
	<section class="glass" id="cart_container">
		<%
			OrdineBean ordine = (OrdineBean) request.getAttribute("ordine");
			List<OrdineProdottoBean> prodottiOrdine = (List<OrdineProdottoBean>) request.getAttribute("prodottiOrdine");
			String[] statoOrd = {"Annullato", "Ricevuto", "Spedito", "Consegnato"};
			
			if (ordine == null) {
		%>
				<div class="glass" id="msg">
					Nessun dettaglio trovato per questo ordine.
					<hr><br>
					<a href="<%= request.getContextPath() %>/user_ordini">Torna ai tuoi ordini</a>
				</div>
		<%
			} else {
		%>
				<h3>Dettaglio Ordine #<%= ordine.getIdOrdine() %></h3>
				<hr><br>
				
				<ul>
					<li>Data acquisto: <%= ordine.getDataOrdine() %></li>
					<li>Stato attuale: <%= statoOrd[ordine.getStatoOrdine()] %></li>
					<li>Totale pagato: <%= ordine.getTotale() %> €</li>
				</ul>
				
				<h4>Prodotti acquistati</h4>
	
				<%
					if (prodottiOrdine != null && !prodottiOrdine.isEmpty()) {
						for(OrdineProdottoBean op : prodottiOrdine) {
				%>
							<div>
								<p><%= op.getProdotto().getNomeProdotto() %></p>
								<p>Prezzo: <%= op.getPrezzo() %> €</p>
								<p>Quantità: <%= op.getQuantita() %></p>
							</div>
							<hr><br>
				<%
						}
					} else {
				%>
						<div id="msg">
							Nessun prodotto trovato.
						</div>
				<%
					}
				%>
				
				<br><hr><br>
				<div style="text-align: center;">
					<a href="${pageContext.request.contextPath}/user_ordini">
						Torna allo storico
					</a>
				</div>
		<%
			}
		%>
	</section>
	
	<%@ include file="footer.jsp" %>
</body>
</html>